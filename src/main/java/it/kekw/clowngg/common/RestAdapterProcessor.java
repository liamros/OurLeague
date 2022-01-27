package it.kekw.clowngg.common;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Component
public class RestAdapterProcessor implements BeanPostProcessor, ApplicationContextAware {

    @Autowired
    private HashMap<String, String> restAdapterUsers;

    private ApplicationContext applicationContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAdapterProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * if the bean is a RestAdapter type this method instantiates a proxy on the
     * interface intended for it.
     * Further it fetches from the spring cointainer the bean to which is mapped,
     * and proceeds to populate the
     * field of the interface type with the new proxy
     */
    @Override
    public Object postProcessAfterInitialization(Object aBean, String aBeanName) throws BeansException {
        Class<?> clazz = aBean.getClass();
        if (!clazz.equals(RestAdapter.class))
            return aBean;

        String s = restAdapterUsers.get(aBeanName);
        if (s == null)
            return aBean;

        RestAdapter ra = (RestAdapter) aBean;
        Class<?> raInterf = ra.getInterfaceProxy();
        Object proxy = Proxy.newProxyInstance(ra.getInterfaceProxy().getClassLoader(),
                new Class[] { ra.getInterfaceProxy() }, ra);

        Object o = applicationContext.getBean(s);

        
        /* checks if the bean is a spring proxy, in that case, the user class will be
         * different by the object's which will be a CGLIB-generated subclass */
        if (!ClassUtils.getUserClass(o).equals(o)) {
            try {
                o = ((Advised) o).getTargetSource().getTarget();
            } catch (Exception e) {
                LOGGER.error("ERROR: Error while getting target from CGLIB-generated subclass", e);
                throw new RuntimeException();
            }
        }

        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(raInterf))
                    field.set(o, proxy);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                LOGGER.error("ERROR: Error during restAdapter assignment", e);
                throw new RuntimeException();
            }
        }
        LOGGER.info("INFO: Inizialized RestAdapter {} for {}", aBeanName, s);
        return proxy;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
package it.kekw.clowngg.common;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestAdapter implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAdapter.class);

    final static private ObjectMapper MAPPER = new ObjectMapper();

    static private ThreadLocal headers = new ThreadLocal<Map<String, String>>();

    private Class<?> interfaceProxy;

    private String baseUrl;

    private Map<String, RestOperation> operations;

    public RestAdapter(Class<?> interfaceProxy, String baseUrl, Map<String, RestOperation> operations) {
        this.interfaceProxy = interfaceProxy;
        this.baseUrl = baseUrl;
        this.operations = operations;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RestOperation op = operations.get(method.getName());
        StringBuffer sb = new StringBuffer(baseUrl);
        sb.append(op.getPath());
        String url = sb.toString();
        Class<?> responseCls = method.getReturnType();
        Object dto = null;
        switch (op.getHttpMethod()) {
            case "GET":
                dto = doGet(url, responseCls, args);
                break;
            default:
                LOGGER.info("KEKW");
        }

        return dto;

    }

    private Object doGet(String url, Class<?> responseClass, Object... parameters) {
        Object dto = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            url = MessageFormat.format(url, parameters);
            HttpGet request = new HttpGet(url);

            Map<String, String> map = (Map<String, String>) headers.get();
            if (map != null) {
                for (Entry<String, String> entry : map.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
                headers.remove();
            }

            CloseableHttpResponse response = httpClient.execute(request);
            LOGGER.info("INFO: Http call performed to {} with response code {}", url, response.getStatusLine().getStatusCode());

            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    dto = MAPPER.readValue(entity.getContent(), responseClass);
                }

            } finally {
                response.close();
            }
        } catch (Exception e) {
            LOGGER.error("ERROR: Error occured while performing http call", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
        return dto;

    }

    public static void addHeader(String key, String value) {
        Map<String, String> map = (HashMap<String, String>) headers.get();
        if (map == null) {
            map = new HashMap<>();
            map.put(key, value);
            headers.set(map);
        } else {
            map.put(key, value);
        }
    }

    public Class<?> getInterfaceProxy() {
        return this.interfaceProxy;
    }

}

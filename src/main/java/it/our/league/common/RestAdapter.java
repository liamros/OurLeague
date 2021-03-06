package it.our.league.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class RestAdapter implements InvocationHandler, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAdapter.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static ThreadLocal<Map<String, String>> headers = new ThreadLocal<Map<String, String>>();

    private Map<String, String> defaultHeaders;

    private Class<?> interfaceProxy;

    private String baseUrl;

    private Map<String, RestOperation> operations;

    public RestAdapter(Class<?> interfaceProxy, String baseUrl, Map<String, RestOperation> operations) {
        this.interfaceProxy = interfaceProxy;
        this.baseUrl = baseUrl;
        this.operations = operations;
    }

    public RestAdapter(Class<?> interfaceProxy, String baseUrl, Map<String, RestOperation> operations,
            Map<String, String> defaultHeaders) {
        this.interfaceProxy = interfaceProxy;
        this.baseUrl = baseUrl;
        this.operations = operations;
        this.defaultHeaders = defaultHeaders;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RestOperation op = operations.get(method.getName());
        String baseUrl = this.baseUrl;
        if (op.getBaseUrlRouting() != null) {
            baseUrl = MessageFormat.format(baseUrl, op.getBaseUrlRouting());
        }
        StringBuffer sb = new StringBuffer(baseUrl);
        sb.append(op.getPath());
        String url = sb.toString();
        Object dto = null;
        try {
            switch (op.getHttpMethod()) {
                case "GET":
                    dto = doGet(url, method, args);
                    break;
                // TODO: test
                case "POST":
                    dto = doPost(new HttpPost(url), method, args[0]);
                    break;
                // TODO: test
                case "PUT":
                    dto = doPost(new HttpPut(url), method, args[0]);
                    break;
                // TODO: test
                case "PATCH":
                    dto = doPost(new HttpPatch(url), method, args[0]);
                    break;
                default:
                    LOGGER.info("KEKW");
            }
        } catch (Exception e) {
            LOGGER.error("Error occured while performing http call", e);
            throw e;
        }

        return dto;

    }

    /**
     * @param url         URL to be formatted with parameters
     * @param responseCls Response dto class type
     * @param parameters  GET request parameters
     * @return Object parsed DTO response
     * @throws Exception
     */
    private Object doGet(String url, Method method, Object... parameters) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        url = MessageFormat.format(url, parameters);
        url = url.replaceAll(" ", "%20");
        url = url.replaceAll("null", "");
        HttpGet request = new HttpGet(url);
        addHeaderToRequest(request);
        Object dto = performHttpRequest(httpClient, request, method);
        httpClient.close();
        return dto;
    }

    /**
     * @param url
     * @param responseCls Response dto class type
     * @param requestBody Request payload DTO
     * @return Object parsed DTO response
     * @throws Exception
     */
    private Object doPost(HttpEntityEnclosingRequestBase request, Method method, Object requestBody) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        addHeaderToRequest(request);
        String json = MAPPER.writeValueAsString(requestBody);
        StringEntity params = new StringEntity(json);
        request.setEntity(params);
        Object dto = performHttpRequest(httpClient, request, method);
        httpClient.close();
        return dto;
    }

    private void addHeaderToRequest(HttpRequestBase request) {
        Map<String, String> map = (Map<String, String>) headers.get();
        if (map != null) {
            for (Entry<String, String> entry : map.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
            headers.remove();
        }
        if (defaultHeaders != null) {
            for (Entry<String, String> entry : defaultHeaders.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Performs the http request and also parses the response to an object of the
     * responseCls passed
     * 
     * @param httpClient
     * @param request
     * @param responseCls
     * @return Object
     * @throws Exception
     */
    private Object performHttpRequest(CloseableHttpClient httpClient, HttpRequestBase request, Method method)
            throws Exception {

        CloseableHttpResponse response = httpClient.execute(request);
        
        if (response.getStatusLine().getStatusCode() >= 400) {
            int statusCode = response.getStatusLine().getStatusCode();
            String statusMessage = response.getStatusLine().getReasonPhrase();
            String errMsg = "Http request failed -- Status Message: {0} - {1} -- URL : {2}";
            errMsg = MessageFormat.format(errMsg, statusCode, statusMessage, request.getURI().toString());
            response.close();
            throw new Exception(errMsg);
        }
        LOGGER.info("Http call performed to {} with response code {}", request.getURI().toString(),
                response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();
        Object dto;
        if (byte[].class.isAssignableFrom(method.getReturnType())) {
            dto = IOUtils.toByteArray(entity.getContent());
        } else if (List.class.isAssignableFrom(method.getReturnType())) {
            ParameterizedType t = (ParameterizedType) method.getGenericReturnType();
            dto = MAPPER.readValue(entity.getContent(), MAPPER.getTypeFactory().constructParametricType(List.class,
                    (Class<?>) t.getActualTypeArguments()[0]));
        } else
            dto = MAPPER.readValue(entity.getContent(), method.getReturnType());
        response.close();
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

    @Override
    public void afterPropertiesSet() throws Exception {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}

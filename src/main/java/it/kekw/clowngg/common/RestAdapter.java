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
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestAdapter implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAdapter.class);

    final static private ObjectMapper MAPPER = new ObjectMapper();

    static private ThreadLocal<Map<String, String>> headers = new ThreadLocal<Map<String, String>>();

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
        try {
            switch (op.getHttpMethod()) {
                case "GET":
                    dto = doGet(url, responseCls, args);
                    break;
                // TODO: test
                case "POST":
                    dto = doPost(url, responseCls, args[0]);
                    break;
                // TODO: test
                case "PUT":
                    dto = doPut(url, responseCls, args[0]);
                    break;
                default:
                    LOGGER.info("KEKW");
            }
        } catch (Exception e) {
            LOGGER.error("ERROR: Error occured while performing http call", e);
        }

        return dto;

    }

    /**
     * @param url         URL to be formatted with parameters
     * @param responseCls Response dto class type
     * @param parameters  GET request parameters
     * @return Object parsed DTO response
     * @throws IOException
     */
    private Object doGet(String url, Class<?> responseCls, Object... parameters) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        url = MessageFormat.format(url, parameters);
        HttpGet request = new HttpGet(url);
        addHeaderToRequest(request);
        Object dto = performHttpRequest(httpClient, request, responseCls);
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
    private Object doPost(String url, Class<?> responseCls, Object requestBody) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        addHeaderToRequest(request);
        addPayloadToRequest(request, requestBody);
        Object dto = performHttpRequest(httpClient, request, responseCls);
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
    private Object doPut(String url, Class<?> responseCls, Object requestBody) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut request = new HttpPut(url);
        addHeaderToRequest(request);
        addPayloadToRequest(request, requestBody);
        Object dto = performHttpRequest(httpClient, request, responseCls);
        httpClient.close();
        return dto;
    }

    private void addPayloadToRequest(HttpEntityEnclosingRequestBase request, Object requestBody) throws Exception {

        String json = MAPPER.writeValueAsString(requestBody);
        StringEntity params = new StringEntity(json);
        request.setEntity(params);
    }

    private void addHeaderToRequest(HttpRequestBase request) {
        Map<String, String> map = (Map<String, String>) headers.get();
        if (map != null) {
            for (Entry<String, String> entry : map.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
            headers.remove();
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
     * @throws IOException
     */
    private Object performHttpRequest(CloseableHttpClient httpClient, HttpRequestBase request, Class<?> responseCls)
            throws IOException {
        CloseableHttpResponse response = httpClient.execute(request);
        LOGGER.info("INFO: Http call performed to {} with response code {}", request.getURI().toString(),
                response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();
        Object dto = MAPPER.readValue(entity.getContent(), responseCls);
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

}

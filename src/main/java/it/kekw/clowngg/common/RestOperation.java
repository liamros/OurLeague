package it.kekw.clowngg.common;

public class RestOperation {
    
    private String path;

    private String httpMethod;

    private String baseUrlRouting;

    

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getBaseUrlRouting() {
        return baseUrlRouting;
    }

    public void setBaseUrlRouting(String baseUrlRouting) {
        this.baseUrlRouting = baseUrlRouting;
    }

    
}

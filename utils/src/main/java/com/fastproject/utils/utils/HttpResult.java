package com.fastproject.utils.utils;

import java.util.List;
import java.util.Map;

public class HttpResult {
    private int code;
    private String body;
    private Map<String, List<String>> headers;

    public HttpResult() {
    }

    public HttpResult(int code, String body, Map<String, List<String>> headers) {
        this.code = code;
        this.body = body;
        this.headers = headers;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public boolean isSuccess() {
        return code >= 200 && code < 300;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", body='" + body + '\'' +
                ", headers=" + headers +
                '}';
    }
}
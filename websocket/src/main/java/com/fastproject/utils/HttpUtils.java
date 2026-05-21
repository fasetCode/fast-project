package com.fastproject.utils;

import com.fastproject.utils.utils.HttpResult;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;

public class HttpUtils {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    /**
     * GET 请求
     */
    public static HttpResult get(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(20))
                    .build();

            HttpResponse<String> response =
                    CLIENT.send(request, BodyHandlers.ofString());

            return new HttpResult(response.statusCode(), response.body(), response.headers().map());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * GET 带请求头
     */
    public static HttpResult get(String url, Map<String, String> headers) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(20));

            if (headers != null) {
                headers.forEach(builder::header);
            }

            HttpResponse<String> response =
                    CLIENT.send(builder.build(), BodyHandlers.ofString());

            return new HttpResult(response.statusCode(), response.body(), response.headers().map());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * POST JSON
     */
    public static HttpResult postJson(String url, String json) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(20))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    CLIENT.send(request, BodyHandlers.ofString());

            return new HttpResult(response.statusCode(), response.body(), response.headers().map());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * POST JSON 带 Header
     */
    public static HttpResult postJson(String url, String json, Map<String, String> headers) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(20))
                    .header("Content-Type", "application/json");

            if (headers != null) {
                headers.forEach(builder::header);
            }

            HttpRequest request = builder
                    .POST(BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    CLIENT.send(request, BodyHandlers.ofString());

            return new HttpResult(response.statusCode(), response.body(), response.headers().map());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * POST 表单
     */
    public static HttpResult postForm(String url, String formData) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(20))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(formData))
                    .build();

            HttpResponse<String> response =
                    CLIENT.send(request, BodyHandlers.ofString());

            return new HttpResult(response.statusCode(), response.body(), response.headers().map());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
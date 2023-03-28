package io.botlify.jast;

import io.botlify.jast.enums.HttpMethod;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JastRouteTest {

    @Test
    void testBasicRoute() throws IOException {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            final int port = (int) (Math.random() * (65535 - 1024)) + 1024;
            final Jast jast = new Jast();
            jast.insertRoute(httpMethod, "/test", (request, response) -> {
                response.sendText("Hello World!");
                return (false);
            });
            jast.listen(port, "0.0.0.0");
            final String requestBody = (httpMethod.hasRequestBody()) ? "Hello World!" : null;
            assertRequest(httpMethod, "http://localhost:" + port + "/test", 200,
                    requestBody, "Hello World!");
        }
    }

    @Test
    void testBasicParamRoute() throws IOException {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            // Random port between 1024 and 65535.
            final int port = (int) (Math.random() * (65535 - 1024)) + 1024;
            // Random param to avoid cache.
            final String randomParam = String.valueOf(Math.random());

            final Jast jast = new Jast();
            jast.insertRoute(httpMethod, "/test/:id", (request, response) -> {
                assertEquals(randomParam, request.getRequestParam("id"));
                response.sendText("Hello World!");
                return (false);
            });
            jast.listen(port, "0.0.0.0");
            final String requestBody = (httpMethod.hasRequestBody()) ? "Hello World!" : null;

            assertRequest(httpMethod, "http://localhost:" + port + "/test/" + randomParam, 200,
                    requestBody, "Hello World!");
        }
    }

    @Test
    void testComplexParamRoute() throws IOException {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            // Random port between 1024 and 65535.
            final int port = (int) (Math.random() * (65535 - 1024)) + 1024;
            // Random param to avoid cache.
            final String randomParamOne = String.valueOf(Math.random());
            final String randomParamTwo = String.valueOf(Math.random());

            final Jast jast = new Jast();
            jast.insertRoute(httpMethod, "/test/:paramOne/test/:paramTwo", (request, response) -> {
                final String paramOne = request.getRequestParam("paramOne");
                final String paramTwo = request.getRequestParam("paramTwo");
                assertEquals(randomParamOne, paramOne);
                assertEquals(randomParamTwo, paramTwo);
                response.sendText("Hello World!");
                return (false);
            });
            jast.listen(port, "0.0.0.0");
            final String requestBody = (httpMethod.hasRequestBody()) ? "Hello World!" : null;

            final String finalUrl = "http://localhost:" + port + "/test/" + randomParamOne
                    + "/test/" + randomParamTwo;

            assertRequest(httpMethod, finalUrl, 200, requestBody, "Hello World!");
        }
    }

    void assertRequest(@NotNull final HttpMethod httpMethod,
                       @NotNull final String url,
                       final int code,
                       @Nullable final String requestBodyString,
                       @NotNull final String bodyExcepted) throws IOException {
        final OkHttpClient client = new OkHttpClient();

        final RequestBody requestBody = (requestBodyString == null)
                ? null : RequestBody.create(requestBodyString.getBytes());

        final Request request = new Request.Builder()
                .url(url)
                .method(httpMethod.name(), requestBody)
                .build();

        try (final Response response = client.newCall(request).execute()) {
            assertEquals(code, response.code());
            if (httpMethod.hasResponseBody())
                assertEquals(bodyExcepted, response.body().string());
        }

    }

}

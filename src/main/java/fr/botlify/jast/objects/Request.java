package fr.botlify.jast.objects;

import com.sun.net.httpserver.HttpExchange;
import fr.botlify.jast.config.RouteConfig;
import fr.botlify.jast.enums.HttpMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {

    @NotNull
    private final HttpExchange exchange;

    @NotNull
    private final RouteConfig routeConfig;

    public Request(@NotNull final RouteConfig routeConfig,
                   @NotNull final HttpExchange exchange) {
        this.routeConfig = routeConfig;
        this.exchange = exchange;
    }

    /**
     * Get the method of the request.
     * @return The method of the request.
     */
    public @NotNull HttpMethod getMethod() {
        return (HttpMethod.valueOf(exchange.getRequestMethod()));
    }

    /**
     * Get the body as a string of the request.
     * @return The body as a string of the request.
     * @throws IllegalStateException If the request method does not have a body.
     */
    public @NotNull String getBody() {
        if (!getMethod().hasBody())
            throw (new IllegalStateException("The request method does not have a body."));
        return (exchange.getRequestBody().toString());
    }

    /**
     * Get the list of all the headers of the request.
     * @return The list of all the headers of the request.
     */
    public @NotNull List<Header> getHeaders() {
        final List<Header> headers = new ArrayList<>();
        for (final String key : exchange.getRequestHeaders().keySet()) {
            final List<String> values = exchange.getRequestHeaders().get(key);
            for (final String value : values) {
                headers.add(new Header(key, value));
            }
        }
        return (headers);
    }

    /**
     * Get all the headers with the specified name.
     * @param name The name of the headers to get.
     * @return A list of all the headers with the specified name.
     */
    public @NotNull List<Header> getHeaders(@NotNull final String name) {
        final List<Header> headers = getHeaders();
        return (headers.stream()
                .filter(header -> header.getName().equals(name))
                .collect(Collectors.toCollection(ArrayList::new)));
    }

    /**
     * Get the first header with the specified name.
     * @param name The name of the header to get.
     * @return The first header with the specified name.
     */
    public @Nullable Header getFirstHeader(@NotNull final String name) {
        return (getHeaders(name).stream().findFirst().orElse(null));
    }

    /**
     * Get the IP address of the client that sent the request.
     * @return The IP address of the client that sent the request.
     */
    public @Nullable String getIp() {
        if (exchange.getRemoteAddress() == null)
            return (null);
        if (exchange.getRemoteAddress().getAddress() == null)
            return (null);
        return (exchange.getRemoteAddress().getAddress().getHostAddress());
    }

    /*
     $      Query parameters
     */

    public @NotNull List<QueryParam> getQueryParams() {
        final List<QueryParam> result = new ArrayList<>();
        final String query = exchange.getRequestURI().getQuery();
        if (query == null)
            return (result);
        final String[] params = query.split("&");
        for (final String param : params) {
            final String[] keyValue = param.split("=");
            if (keyValue.length != 2) continue;
            final String key = keyValue[0];
            final String value = keyValue[1];
            final QueryParam queryParam = new QueryParam(key, value);
            result.add(queryParam);
        }
        return (result);
    }

    public @NotNull List<QueryParam> getQueryParams(@NotNull final String key) {
        final List<QueryParam> result = new ArrayList<>();
        for (final QueryParam queryParam : getQueryParams()) {
            if (queryParam.getKey().equals(key))
                result.add(queryParam);
        }
        return (result);
    }

    public @Nullable QueryParam getFirstQueryParam(@NotNull final String key) {
        final List<QueryParam> result = getQueryParams(key);
        if (result.isEmpty())
            return (null);
        return (result.get(0));
    }

    public @Nullable String getRequestParam(@NotNull final String param) {
        final Map<String, Integer> requestParams = routeConfig.getRequestParam();
        System.out.println("requestParams" + requestParams);
        if (!requestParams.containsKey(param))
            return (null);
        final int index = requestParams.get(param);
        final String[] pathSplit = exchange.getRequestURI().getPath().split("/");
        System.out.println("exchange.getRequestURI().getPath(): " +  exchange.getRequestURI().getPath());
        System.out.println("pathSplit: ");
        for (String s : pathSplit) {
            System.out.println("\t" + s);
        }
        if (pathSplit.length <= index)
            return (null);
        return (pathSplit[index]);
    }

}

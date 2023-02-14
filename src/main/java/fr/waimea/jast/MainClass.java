package fr.waimea.jast;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.Request;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MainClass {

    public static void main(String[] args) throws IOException {
        Jast app = new Jast();
        app.setRoute("/", (request, response) -> {
            JSONObject object = new JSONObject();
            object.put("hello", "world");
            try {
                response.sendJson(object);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        app.listen(8080, "localhost", (Void) -> {
            System.out.println("Server is running");
        });
    }

}

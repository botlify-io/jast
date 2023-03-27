package io.botlify.jast;

import io.botlify.jast.objects.Request;
import io.botlify.jast.objects.Response;
import org.json.JSONObject;
import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws IOException {
        final Jast app = new Jast();

        app.get("/test/", (request, response) -> {
            System.out.println(request.getMethod() + ": " + request.getBody() + ": " + request.getIp());

            JSONObject object = new JSONObject();
            object.put("key", "value");
            response.sendJson(object);
        });

        app.listen(8080, "0.0.0.0", (Void) -> {
            System.out.println("Server is running on port " + 8080);
        });
    }

}

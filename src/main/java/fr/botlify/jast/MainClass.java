package fr.botlify.jast;

import fr.botlify.jast.objects.Request;
import fr.botlify.jast.objects.Response;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpCookie;

public class MainClass {

    public static void main(String[] args) throws IOException {
        final Jast app = new Jast();
        app.useBeforeAll((Request request, Response response) -> {
            return true;
        });

        app.get("/:id1/:id2", (request, response) -> {
            System.out.println(request.getMethod() + ": " + request.getBody() + ": " + request.getIp());
            System.out.println(request.getQueryParams());

            String id1 = request.getRequestParam("id1");
            String id2 = request.getRequestParam("id2");

            JSONObject object = new JSONObject();
            object.put(":id1", (id1 == null) ? "null" : id1);
            object.put(":id2", (id2 == null) ? "null" : id2);

            response.sendJson(object);
        });
        app.listen(8080, "localhost", (Void) -> {
            System.out.println("Server is running on port " + 8080);
        });
    }

}

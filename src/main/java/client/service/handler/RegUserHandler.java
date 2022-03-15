package client.service.handler;

import client.view.RegistrationResponseConsoleView;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.concurrent.ExecutionException;

public class RegUserHandler extends AbstractHttpHandler {

    @Override
    public void sendRequest(HttpClient httpClient, String uri, String[] requestData) throws URISyntaxException {
        String username = requestData[0];
        URI currentUri = URI.create(uri + "user/registration");

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(currentUri)
                    .POST(HttpRequest.BodyPublishers.ofString(username))
                    .build();
            response = getResponse(httpClient, request);
            System.out.println(new RegistrationResponseConsoleView(username, response));
        } catch (IllegalArgumentException e) {
            throw new URISyntaxException(uri, "Неверный или недоступный URI");

        } catch (InterruptedException | ExecutionException e) {
            System.out.println(response);
        }
    }
}

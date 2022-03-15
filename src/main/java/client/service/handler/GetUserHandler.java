package client.service.handler;

import client.view.TaskListConsoleView;
import client.view.converter.JsonConverter;
import com.google.gson.JsonParseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.concurrent.ExecutionException;

public class GetUserHandler extends AbstractHttpHandler {
    @Override
    public void sendRequest(HttpClient httpClient, String uri, String[] requestData) throws URISyntaxException {
        String username = requestData[0];
        URI currentUri = URI.create(uri + "user/" + username);

        try {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(currentUri)
                .build();
            response = getResponse(httpClient, request);
            System.out.println(new TaskListConsoleView(new JsonConverter(), response));
        } catch (IllegalArgumentException e) {
            throw new URISyntaxException(currentUri.toString(), "Неверный или недоступный URI");

        } catch (InterruptedException | ExecutionException |JsonParseException e) {
            System.out.println(response);
        }
    }
}

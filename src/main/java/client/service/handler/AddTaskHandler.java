package client.service.handler;

import client.view.UserDtoConsoleView;
import client.view.converter.JsonConverter;
import com.google.gson.JsonParseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class AddTaskHandler extends AbstractHttpHandler {
    @Override
    public void sendRequest(HttpClient httpClient, String uri, String[] requestData) throws ParseException, URISyntaxException {
        String task = requestData[0];
        String username = requestData[1];
        URI currentUri = URI.create(uri + "user/" + username +"/new_task");

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(currentUri)
                    .POST(BodyPublishers.ofString(task))
                    .build();
            response = getResponse(httpClient, request);
            System.out.println(new UserDtoConsoleView(new JsonConverter(), response));
        } catch (IllegalArgumentException e) {
            throw new URISyntaxException(currentUri.toString(), "Неверный или недоступный URI");

        } catch (InterruptedException | ExecutionException |JsonParseException e) {
            System.out.println(response);
        }
    }
}

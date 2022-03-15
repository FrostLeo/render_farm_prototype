package client.service.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ExecutionException;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractHttpHandler implements HttpHandler {
    String response;

    public AbstractHttpHandler() {
        response = "Не удалось получить ответ от сервера";
    }

    protected String getResponse(HttpClient httpClient, HttpRequest request) throws InterruptedException, ExecutionException {
        return httpClient.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body).get();
    }
}

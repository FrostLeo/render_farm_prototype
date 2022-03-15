package client.service.handler;


import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.text.ParseException;

public interface HttpHandler {
    void sendRequest(HttpClient httpClient, String uri, String[] requestData) throws ParseException, URISyntaxException;
}

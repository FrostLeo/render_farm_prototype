package client;

import client.httpClient.HttpClient;
import client.service.ClientServiceImpl;

public class RenderFarmClientApplication {
    public static void main(String[] args) {
        HttpClient client = new HttpClient(new ClientServiceImpl());
        client.begin();
    }
}

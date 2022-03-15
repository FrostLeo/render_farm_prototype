package client.service;

import java.net.URISyntaxException;
import java.text.ParseException;

public interface ClientService {
    void executeCommand(String uri, String command) throws ParseException, URISyntaxException;
}

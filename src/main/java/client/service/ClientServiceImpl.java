package client.service;

import client.service.handler.HttpHandler;
import client.service.handler.RegUserHandler;
import client.service.handler.AddTaskHandler;
import client.service.handler.GetUserHandler;
import client.service.handler.util.AddCommandExtractor;
import client.service.handler.util.GetCommandExtractor;
import client.service.handler.util.RegCommandExtractor;
import client.service.handler.util.CommandExtractor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.util.Pair;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.text.ParseException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientServiceImpl implements ClientService {
    HttpClient httpClient;

    public ClientServiceImpl() {
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public void executeCommand(String uri, String command) throws ParseException, URISyntaxException {
        Pair<HttpHandler, CommandExtractor> selectedPair = parseCommandAndSelectHandler(command);
        if (selectedPair != null) {
            HttpHandler handler = selectedPair.getFirst();
            CommandExtractor extractor = selectedPair.getSecond();

            String[] requestData = extractor.extract(command);
            handler.sendRequest(httpClient, uri, requestData);
        }
    }

    private Pair<HttpHandler, CommandExtractor> parseCommandAndSelectHandler(String command) throws ParseException {
        if (command.trim().length() < 3)
            throw new ParseException(command, 0);

        String firstTag = command.trim().substring(0,3);
        switch (firstTag) {
            case "REG":
                return Pair.of(new RegUserHandler(), new RegCommandExtractor());
            case "ADD":
                return Pair.of(new AddTaskHandler(), new AddCommandExtractor());
            case "GET":
                return Pair.of(new GetUserHandler(), new GetCommandExtractor());
            case "END":
                return null;
            default:
                throw new ParseException(command,0);
        }
    }
}

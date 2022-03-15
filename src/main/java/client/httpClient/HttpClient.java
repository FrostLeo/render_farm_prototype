package client.httpClient;

import client.service.ClientService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.ParseException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HttpClient {
    ClientService clientService;

    public HttpClient(ClientService clientService) {
        this.clientService = clientService;
    }

    public void begin() {
        System.out.println("Клиент запущен. Введите URI сервера:");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String uri = getUri(reader);
            showClientInstruction();
            beginMainLoop(reader, uri);
        } catch (IOException e) {
            System.out.println("Произошла ошибка ввода-вывода. Клиент будет закрыт");
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage() + " Клиент будет закрыт");
        }
    }

    private void showClientInstruction() {
        System.out.println("Запрос к серверу должен быть следующего вида:\n" +
                "- \"REG username\" - для регистрации пользователя \"username\";\n" +
                "- \"ADD task TO username\" - для добавления пользователю \"username\" задачи \"task\"\n" +
                "- \"GET username\" - для получения списка задач пользователя \"username\"\n" +
                "- \"END\" - для завершения работы клиента");
    }

    private void beginMainLoop(BufferedReader reader, String uri) throws URISyntaxException {
        String command = "";
        while (isNotEnd(command)) {
            try {
                command = reader.readLine();
                clientService.executeCommand(uri, command);
            } catch (ParseException e) {
                System.out.println("Неверный синтаксис.");
                showClientInstruction();
            } catch (IOException e) {
                System.out.println("Ошибка ввода-вывода. Повторите запрос");
            }
        }
    }

    private String getUri(BufferedReader reader) throws IOException {
        String baseUri = reader.readLine().trim();
        return (baseUri.endsWith("/"))
                ? baseUri
                : baseUri + "/";
    }

    private boolean isNotEnd(String command) {
        return !command.equals("END");
    }
}


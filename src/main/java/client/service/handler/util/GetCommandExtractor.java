package client.service.handler.util;

public class GetCommandExtractor implements CommandExtractor {
    @Override
    public String[] extract(String command) {
        return new String[]
                {command.replaceFirst("GET ", "")
                .trim()};
    }
}

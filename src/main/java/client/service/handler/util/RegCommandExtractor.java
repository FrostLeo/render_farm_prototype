package client.service.handler.util;

public class RegCommandExtractor implements CommandExtractor {

    @Override
    public String[] extract(String command) {
        return new String[]
                {command.replaceFirst("REG ", "").trim()};
    }
}

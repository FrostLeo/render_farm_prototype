package client.service.handler.util;

import java.text.ParseException;
import java.util.Arrays;

public class AddCommandExtractor implements CommandExtractor {
    @Override
    public String[] extract(String command) throws ParseException {
        String preparedToSplit = command.replaceFirst("ADD ", "");
        String[] splitData = Arrays.stream(preparedToSplit.split(" TO "))
                .map(String::trim)
                .toArray(String[]::new);

        if (splitData.length != 2) {
            throw new ParseException(command, 0);
        }
        return splitData;
    }
}

package client.service.handler.util;

import java.text.ParseException;

public interface CommandExtractor {
    String[] extract(String command) throws ParseException;
}

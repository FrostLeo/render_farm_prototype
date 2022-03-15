package client.view.converter;

import com.google.gson.Gson;
import server.dto.UserDto;

public class JsonConverter implements Converter {
    private final Gson gsonFormat;

    public JsonConverter() {
        gsonFormat = new Gson();
    }

    @Override
    public UserDto recoveryUserData(String userData) {
        return gsonFormat.fromJson(userData, UserDto.class);
    }
}

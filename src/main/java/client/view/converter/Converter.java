package client.view.converter;

import server.dto.UserDto;

public interface Converter {
    UserDto recoveryUserData(String userData);
}

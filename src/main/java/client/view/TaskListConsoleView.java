package client.view;

import client.view.converter.Converter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import server.dto.UserDto;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskListConsoleView {
    UserDto userDto;

    public TaskListConsoleView(Converter converter, String userData) {
        this.userDto = converter.recoveryUserData(userData);
    }

    @Override
    public String toString() {
        return userDto.toView();
    }
}

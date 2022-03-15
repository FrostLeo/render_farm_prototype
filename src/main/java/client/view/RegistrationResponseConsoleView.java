package client.view;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationResponseConsoleView {
    String username;
    String response;

    @Override
    public String toString() {
        return response + ": " + username;
    }
}

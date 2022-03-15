package server.exception;

public class UserNotFoundedException extends RuntimeException {
    public UserNotFoundedException() {
        super("Пользователь с таким именем не зарегистрирован");
    }
}

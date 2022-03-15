package server.exception;

public class UsernameIsAlreadyTaken extends RuntimeException {
    public UsernameIsAlreadyTaken() {
        super("Пользователь с таким именем уже существует");
    }
}

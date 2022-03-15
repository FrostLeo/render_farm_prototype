package server.service;

import server.dto.UserDto;

public interface UserService {
    void saveUser(String username);
    UserDto getUser(String username);
    UserDto updateUser(String username, String taskName);
}

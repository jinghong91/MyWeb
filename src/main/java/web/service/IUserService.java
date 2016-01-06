package web.service;

import web.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    void addUser(User user);

    User getUserById(Integer id);

    void deleteUser(User user);

    void deleteUserById(Integer id);

    void updateUser(User user);
}

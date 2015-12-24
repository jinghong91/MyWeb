package web.service;

import web.data.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    void addUser(User user);
}

package web.dao;


import web.model.User;

import java.util.List;

public interface IUserDAO {
    List<User> getAll();

    void saveUser(User user);

    User getById(Integer id);

    void deleteUser(User user);

    void updateUser(User user);
}

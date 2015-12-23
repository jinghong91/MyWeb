package web.data.dao;


import web.data.model.User;

import java.util.List;

public interface IUserDAO {
    List<User> getAll();
}

package web.dao.impl;

import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.IUserDAO;
import web.model.User;

import java.util.List;

@Repository
public class UserDAO extends AbstractDAO<User> implements IUserDAO {
    @Override
    public List<User> getAll() {
        return createEntityCriteria().list();
    }

    @Override
    public void saveUser(User user) {
        persist(user);
    }

    @Override
    public User getById(Integer id) {
        return getByPK(id);
    }

    @Override
    public void deleteUser(User user) {
        delete(user);
    }

    @Override
    public void updateUser(User user) {
        update(user);
    }
}

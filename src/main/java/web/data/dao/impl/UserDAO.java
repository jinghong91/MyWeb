package web.data.dao.impl;

import org.springframework.stereotype.Repository;
import web.data.dao.AbstractDao;
import web.data.dao.IUserDAO;
import web.data.model.User;

import java.util.List;

@Repository
public class UserDAO extends AbstractDao<User> implements IUserDAO {
    @Override
    public List<User> getAll() {
        return createEntityCriteria().list();
    }

    @Override
    public void saveUser(User user) {
        persist(user);
    }
}

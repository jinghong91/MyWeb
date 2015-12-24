package web.service.impl;

import web.data.dao.IUserDAO;
import web.data.dao.impl.UserDAO;
import web.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.service.IUserService;

import java.util.List;

@Service("userService")
@Transactional
public class UserService implements IUserService {
    @Autowired
    private IUserDAO userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void addUser(User user) {
        userDao.saveUser(user);
    }
}

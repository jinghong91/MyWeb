package web.service.impl;

import web.dao.IUserDAO;
import web.model.User;
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

    @Override
    public User getUserById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userDao.deleteUser(userDao.getById(id));
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }


}

package web.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.*;
import web.model.*;
import web.service.IMenuService;

import java.util.List;

@Service("menuService")
@Transactional
public class MenuService implements IMenuService {
    @Autowired
    IMenuDAO menuDao;

    @Override
    public List<Menu> getAll() {
        return menuDao.getAll();
    }
}

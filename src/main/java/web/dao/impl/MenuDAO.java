package web.dao.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.IMenuDAO;
import web.model.Menu;

import java.util.List;

@Repository
public class MenuDAO extends AbstractDAO<Menu> implements IMenuDAO {
    @Override
    public List<Menu> getAll() {
        return (List<Menu>) createEntityCriteria()
                .createAlias("subMenuList","subMenuList")
                .addOrder(Order.asc("order"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}

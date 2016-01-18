package web.dao.impl;

import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.ICommonDeliveryDAO;
import web.model.CommonDelivery;

import java.util.List;

@Repository
public class CommonDeliveryDAO extends AbstractDAO<CommonDelivery> implements ICommonDeliveryDAO {
    @Override
    public List<CommonDelivery> getAll() {
        return createEntityCriteria().list();
    }

    @Override
    public void addCommonDelivery(CommonDelivery commonDelivery) {
        merge(commonDelivery);
    }
}

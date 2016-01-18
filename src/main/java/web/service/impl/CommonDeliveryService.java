package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ICommonDeliveryDAO;
import web.model.CommonDelivery;
import web.service.ICommonDeliveryService;

import java.util.List;

@Service
@Transactional
public class CommonDeliveryService implements ICommonDeliveryService {
    @Autowired
    ICommonDeliveryDAO commonDeliveryDAO;

    @Override
    public List<CommonDelivery> getAll() {
        return commonDeliveryDAO.getAll();
    }

    @Override
    public void addCommonDelivery(CommonDelivery commonDelivery) {
        commonDeliveryDAO.addCommonDelivery(commonDelivery);
    }
}

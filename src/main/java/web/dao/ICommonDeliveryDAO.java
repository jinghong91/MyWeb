package web.dao;

import web.model.CommonDelivery;

import java.util.List;

public interface ICommonDeliveryDAO {
    List<CommonDelivery> getAll();

    void AddCommonDelivery(CommonDelivery commonDelivery);
}

package web.service;

import web.model.CommonDelivery;

import java.util.List;

public interface ICommonDeliveryService {
    List<CommonDelivery> getAll();

    void addCommonDelivery(CommonDelivery commonDelivery);
}

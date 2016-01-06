package web.dao;

import web.model.PersonalDelivery;

import java.util.List;

public interface IPersonalDeliveryDAO {
    List<PersonalDelivery> getAll();

    void AddPersonalDelivery(PersonalDelivery personalDelivery);
}

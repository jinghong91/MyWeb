package web.dao.impl;

import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.IPersonalDeliveryDAO;
import web.model.PersonalDelivery;

import java.util.List;

@Repository
public class PersonalDeliveryDAO extends AbstractDAO<PersonalDelivery> implements IPersonalDeliveryDAO {
    @Override
    public List<PersonalDelivery> getAll() {
        return createEntityCriteria().list();
    }

    @Override
    public void AddPersonalDelivery(PersonalDelivery personalDelivery) {
        persist(personalDelivery);
    }
}

package web.dao.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.IClientDAO;
import web.model.Client;

import java.util.List;

@Repository
public class ClientDAO extends AbstractDAO<Client> implements IClientDAO {
    @Override
    public List<Client> getAll() {
        return createEntityCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public void addClient(Client client) {
        persist(client);
    }
}

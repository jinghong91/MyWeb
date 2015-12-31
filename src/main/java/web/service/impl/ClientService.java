package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AbstractDAO;
import web.dao.IClientDAO;
import web.model.Client;
import web.service.IClientService;

import java.util.List;

@Service
@Transactional
public class ClientService extends AbstractDAO<Client> implements IClientService {
    @Autowired
    private IClientDAO clientDAO;

    @Override
    public List<Client> getAll() {
        return clientDAO.getAll();
    }

    @Override
    public void addClient(Client client) {
        clientDAO.addClient(client);
    }

    @Override
    public Client getClientById(int clientId) {
        return clientDAO.getClientById(clientId);
    }

}

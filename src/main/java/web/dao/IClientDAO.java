package web.dao;

import web.model.Client;

import java.util.List;

public interface IClientDAO {
    List<Client> getAll();

    void addClient(Client client);

    Client getClientById(int clientId);

    Client getClientByName(String name);

    void updateClient(Client client);
}

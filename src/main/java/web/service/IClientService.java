package web.service;

import web.model.Client;

import java.util.List;

public interface IClientService {
    List<Client> getAll();

    void addClient(Client client);

}

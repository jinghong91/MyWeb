package web.service.impl;

import web.model.Seller;

import java.util.List;

public interface ISellerService {
    List<Seller> getAll();
    Seller getById(int id);
}

package web.dao;

import web.model.Seller;

import java.util.List;

public interface ISellerDAO {
    List<Seller> getAll();

    void AddSeller(Seller seller);
}

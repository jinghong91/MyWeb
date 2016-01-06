package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ISellerDAO;
import web.model.Seller;

import java.util.List;

@Repository
@Transactional
public class SellerService implements ISellerService {
    @Autowired
    ISellerDAO sellerDAO;

    @Override
    public List<Seller> getAll() {
        return sellerDAO.getAll();
    }

    @Override
    public Seller getById(int id) {
        return sellerDAO.getById(id);
    }
}

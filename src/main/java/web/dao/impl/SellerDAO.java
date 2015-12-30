package web.dao.impl;

import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.ISellerDAO;
import web.model.Seller;

import java.util.List;

@Repository
public class SellerDAO extends AbstractDAO<Seller> implements ISellerDAO {
    @Override
    public List<Seller> getAll() {
        return createEntityCriteria().list();
    }

    @Override
    public void AddSeller(Seller seller) {
        persist(seller);
    }
}

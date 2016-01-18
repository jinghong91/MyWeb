package web.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.IOrderDAO;
import web.model.Order;

import java.util.Date;
import java.util.List;

@Repository
public class OrderDAO extends AbstractDAO<Order> implements IOrderDAO {
    @Override
    public List<Order> getAll() {
        return createEntityCriteria()
                .createAlias("client", "client", JoinType.LEFT_OUTER_JOIN)
                .createAlias("personalDelivery", "personalDelivery", JoinType.LEFT_OUTER_JOIN)
                .createAlias("commonDelivery", "commonDelivery", JoinType.LEFT_OUTER_JOIN)
                .createAlias("address", "address", JoinType.LEFT_OUTER_JOIN)
                .createAlias("sellerList", "sellerList", JoinType.LEFT_OUTER_JOIN)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public void addOrder(Order order) {
        persist(order);
    }

    @Override
    public Order getOrderById(int id) {
        return (Order) createEntityCriteria()
                .createAlias("client", "client", JoinType.LEFT_OUTER_JOIN)
                .createAlias("personalDelivery", "personalDelivery", JoinType.LEFT_OUTER_JOIN)
                .createAlias("commonDelivery", "commonDelivery", JoinType.LEFT_OUTER_JOIN)
                .createAlias("address", "address", JoinType.LEFT_OUTER_JOIN)
                .createAlias("sellerList", "sellerList", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();
    }

    @Override
    public void updateOrder(Order order) {
        update(order);
    }

    @Override
    public List<Order> getOrderWithoutCommonDeliveryList() {
        return createEntityCriteria()
                .createAlias("client", "client")
                .createAlias("commonDelivery", "commonDelivery", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.isNull("commonDelivery"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public List<Order> getOrderWithoutCommonDeliveryWithFilter(String paymentStatus, Date createDateFrom, Date createDateTo, int sellerId, int clientId) {
        Criteria crit = createEntityCriteria()
                .createAlias("client", "client")
                .createAlias("sellerList", "seller")
                .createAlias("commonDelivery", "commonDelivery", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.isNull("commonDelivery"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (paymentStatus != null) {
            crit.add(Restrictions.eq("paymentStatus", paymentStatus));
        }
        if (createDateFrom != null) {
            crit.add(Restrictions.ge("createDate", createDateFrom));
        }
        if (createDateTo != null) {
            crit.add(Restrictions.le("createDate", createDateTo));
        }
        if (sellerId != 0) {
            crit.add(Restrictions.eq("seller.id", sellerId));
        }
        if (clientId != 0) {
            crit.add(Restrictions.eq("client.id", clientId));
        }
        return crit.list();
    }

    @Override
    public List<Order> getOrderWithCommonDeliverySortByCreateDate() {
        return createEntityCriteria()
                .createAlias("client", "client")
                .createAlias("commonDelivery", "commonDelivery", JoinType.INNER_JOIN)
                .addOrder(org.hibernate.criterion.Order.asc("createDate"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();

    }
}

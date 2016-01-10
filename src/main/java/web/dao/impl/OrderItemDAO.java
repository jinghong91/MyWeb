package web.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Distinct;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.IOrderItemDAO;
import web.model.OrderItem;

import java.util.List;

@Repository
public class OrderItemDAO extends AbstractDAO<OrderItem> implements IOrderItemDAO {
    @Override
    public List<OrderItem> getAll() {
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
    public void addOrderItem(OrderItem orderItem) {
        persist(orderItem);
    }

    @Override
    public OrderItem getOrderById(int id) {
        return (OrderItem) createEntityCriteria()
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
    public void updateOrder(OrderItem orderItem) {
        update(orderItem);
    }


}

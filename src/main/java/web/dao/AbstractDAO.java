package web.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractDAO<T> {
    private final Class<T> persistenceClass;

    @SuppressWarnings("unchecked")
    protected AbstractDAO() {
        this.persistenceClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        return session;
    }

    protected T getByPK(Integer key) {
        return (T) getSession().get(persistenceClass, key);
    }

    protected void persist(T entity) {
        getSession().persist(entity);
    }

    protected void delete(T entity) {
        getSession().delete(entity);
    }

    protected void update(T entity) {
        getSession().update(entity);
    }

    protected void saveOrUpdate(T entity){
        getSession().saveOrUpdate(entity);
    }

    protected Serializable save(T entity){
        return getSession().save(entity);
    }
    protected Criteria createEntityCriteria() {
        return getSession().createCriteria(persistenceClass);
    }
}

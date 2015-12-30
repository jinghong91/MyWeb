package web.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractDAO<T> {
    private final Class<T> persistenceClass;

    @SuppressWarnings("Uncheck")
    protected AbstractDAO() {
        this.persistenceClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        return session;
    }

    public T getByPK(Integer key) {
        return (T) getSession().get(persistenceClass, key);
    }

    public void persist(T entity) {
        getSession().persist(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    protected Criteria createEntityCriteria() {
        return getSession().createCriteria(persistenceClass);
    }
}

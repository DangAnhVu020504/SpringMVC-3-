package com.abc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abc.entities.Province;

@Repository
@Transactional
public class ProvinceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Province> getAllProvinces() {
        Session session = sessionFactory.getCurrentSession();
        Query<Province> query = session.createQuery("FROM Province", Province.class);
        return query.getResultList();
    }

    public Province getProvinceById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Province.class, id);
    }
}

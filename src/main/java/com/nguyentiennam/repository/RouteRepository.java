package com.nguyentiennam.repository;

import com.nguyentiennam.model.Route;
import com.nguyentiennam.util.DataUtil;
import com.nguyentiennam.util.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class RouteRepository {
    public List<Route> getAll(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        return session.createQuery("from Route",Route.class).list();
    }
    public void saveAll(List<Route> routes){
        if(DataUtil.isEmptyCollection(routes)){
            return;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.getTransaction().begin();
        try{
            for(Route x : routes){
                session.save(x);
            }

        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
    }

    public Route findById(int routeId ){
        Session session = HibernateUtils.getSessionFactory().openSession();
        return session.createQuery("from Route where id = :p_id", Route.class)
                .setParameter("p_id",(long) routeId).getSingleResult();
    }
}

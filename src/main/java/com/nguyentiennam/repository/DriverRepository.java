package com.nguyentiennam.repository;

import com.nguyentiennam.model.Driver;
import com.nguyentiennam.util.DataUtil;
import com.nguyentiennam.util.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class DriverRepository {

    public List<Driver> getAll() {//hien thi list tai xe
        Session session = HibernateUtils.getSessionFactory().openSession();
        return session.createQuery("from Driver",Driver.class).list();
    }

    public void saveAll(List<Driver> drivers){//luu
        if(DataUtil.isEmptyCollection(drivers)){
            return;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.getTransaction().begin();
        try{
            for(Driver x : drivers){
                session.save(x);
            }

        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
    }

    public Driver findById(int driverId){
        Session session = HibernateUtils.getSessionFactory().openSession();
        return session.createQuery("from Driver where id = :p_id", Driver.class)
                .setParameter("p_id",(long) driverId).getSingleResult();
    }
}

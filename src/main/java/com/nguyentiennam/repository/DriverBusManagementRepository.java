package com.nguyentiennam.repository;

import com.nguyentiennam.model.DriverBusManagement;
import com.nguyentiennam.util.DataUtil;
import com.nguyentiennam.util.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class DriverBusManagementRepository {
    public List<DriverBusManagement> getAll(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        return session.createQuery("from DriverBusManagement ",DriverBusManagement.class).list();

    }
    public void saveAll(List<DriverBusManagement> driverBusManagements){
        if (DataUtil.isEmptyCollection(driverBusManagements)) {
            return;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            for (DriverBusManagement x : driverBusManagements) {
                session.save(x);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();

    }
}

package com.gestion.dao;

import com.gestion.entities.TipoVehiculo;
import com.gestion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TipoVehiculoDAO {

    public void save(TipoVehiculo tipoVehiculo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(tipoVehiculo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(TipoVehiculo tipoVehiculo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(tipoVehiculo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(TipoVehiculo tipoVehiculo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(tipoVehiculo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public TipoVehiculo get(int idTipo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        TipoVehiculo tipoVehiculo = null;
        try {
            tipoVehiculo = session.get(TipoVehiculo.class, idTipo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tipoVehiculo;
    }
}
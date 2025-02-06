package com.gestion.dao;

import com.gestion.entities.TipoVehiculo;
import com.gestion.utils.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;
import java.util.List;

public class TipoVehiculoDAO {

    public List<TipoVehiculo> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<TipoVehiculo> tipoVehiculos = null;
        try {
            tipoVehiculos = session.createQuery("from TipoVehiculo", TipoVehiculo.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tipoVehiculos;
    }

    public void save(TipoVehiculo tipoVehiculo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(tipoVehiculo);
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
            session.merge(tipoVehiculo);
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
            session.remove(tipoVehiculo);
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
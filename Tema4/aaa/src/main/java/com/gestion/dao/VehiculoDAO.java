package com.gestion.dao;

import com.gestion.entities.Vehiculo;
import com.gestion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VehiculoDAO {

    public void save(Vehiculo vehiculo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(vehiculo);
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

    public void update(Vehiculo vehiculo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(vehiculo);
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

    public void delete(Vehiculo vehiculo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(vehiculo);
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

    public Vehiculo get(String matricula) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Vehiculo vehiculo = null;
        try {
            vehiculo = session.get(Vehiculo.class, matricula);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return vehiculo;
    }

    public List<Vehiculo> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Vehiculo> vehiculos = null;
        try {
            vehiculos = session.createQuery("from Vehiculo", Vehiculo.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return vehiculos;
    }
}
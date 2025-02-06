package com.gestion.dao;

import com.gestion.entities.HistorialMantenimiento;
import com.gestion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HistorialMantenimientoDAO {

    public void save(HistorialMantenimiento historialMantenimiento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(historialMantenimiento);
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

    public void update(HistorialMantenimiento historialMantenimiento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(historialMantenimiento);
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

    public void delete(HistorialMantenimiento historialMantenimiento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(historialMantenimiento);
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

    public HistorialMantenimiento get(int idMantenimiento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        HistorialMantenimiento historialMantenimiento = null;
        try {
            historialMantenimiento = session.get(HistorialMantenimiento.class, idMantenimiento);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return historialMantenimiento;
    }

    public List<HistorialMantenimiento> getByVehiculo(String matricula) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<HistorialMantenimiento> historiales = null;
        try {
            historiales = session.createQuery("from HistorialMantenimiento hm where hm.vehiculo.matricula = :matricula", HistorialMantenimiento.class)
                    .setParameter("matricula", matricula)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return historiales;
    }
}
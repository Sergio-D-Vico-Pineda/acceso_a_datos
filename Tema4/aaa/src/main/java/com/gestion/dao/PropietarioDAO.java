package com.gestion.dao;

import com.gestion.entities.Propietario;
import com.gestion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PropietarioDAO {

    public void save(Propietario propietario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(propietario);
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

    public void update(Propietario propietario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(propietario);
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

    public void delete(Propietario propietario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(propietario);
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

    public Propietario get(int idPropietario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Propietario propietario = null;
        try {
            propietario = session.get(Propietario.class, idPropietario);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return propietario;
    }
}
package com.gestion.dao;

import com.gestion.entities.Propietario;
import com.gestion.utils.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;
import java.util.List;

public class PropietarioDAO {

    public List<Propietario> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Propietario> propietarios = null;
        try {
            propietarios = session.createQuery("from Propietario", Propietario.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return propietarios;
    }

    public void save(Propietario propietario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(propietario);
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
            session.merge(propietario);
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
            session.remove(propietario);
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
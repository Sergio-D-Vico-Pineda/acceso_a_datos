package com.gestion.dao;

import com.gestion.entities.Vehiculo;
import com.gestion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class VehiculoDAO {

    public void save(Vehiculo vehiculo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(vehiculo);
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
            session.merge(vehiculo);
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
            session.remove(vehiculo);
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

    public Vehiculo getByMatricula(String matricula) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Vehiculo vehiculo = null;
        try {
            // Crear la consulta HQL
            String hql = "FROM Vehiculo WHERE matricula = :matricula";
            Query<Vehiculo> query = session.createQuery(hql, Vehiculo.class);
    
            // Establecer el parámetro
            query.setParameter("matricula", matricula);
    
            // Obtener el resultado único
            vehiculo = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace(); // O usar un sistema de logs
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
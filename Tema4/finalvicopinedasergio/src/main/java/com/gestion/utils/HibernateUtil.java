package com.gestion.utils;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.gestion.entities.*;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    /* private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed." + e);
        }
    } */

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("AAAAAAAAAAAaInitial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void seed() {
        Session session = getSessionFactory().openSession();
        List<TipoVehiculo> tipoVehiculos = session.createQuery("from TipoVehiculo", TipoVehiculo.class).list();
        if (tipoVehiculos.isEmpty()) {
            Transaction tx = session.beginTransaction();
            session.persist(new TipoVehiculo("Sedán"));
            session.persist(new TipoVehiculo("SUV"));
            session.persist(new TipoVehiculo("Deportivo"));
            session.persist(new TipoVehiculo("Camioneta"));
            session.persist(new TipoVehiculo("Moto"));
            tx.commit();
        }
        session.close();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
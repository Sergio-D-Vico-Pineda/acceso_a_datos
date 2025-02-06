package com.gestion.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed." + e);
        }
    }

    public static void seed() {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(new TipoVehiculo("Sedán"));
        session.save(new TipoVehiculo("SUV"));
        session.save(new TipoVehiculo("Deportivo"));
        session.save(new TipoVehiculo("Camioneta"));
        session.save(new TipoVehiculo("Moto"));
        tx.commit();
        session.close();

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
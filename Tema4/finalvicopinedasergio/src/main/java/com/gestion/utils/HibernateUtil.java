package com.gestion.utils;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.gestion.entities.*;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
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

    public static void fromDbtoXml(XmlGenerator xmlGenerator) {
        Session session = getSessionFactory().openSession();

        List<TipoVehiculo> tipoVehiculos = session.createQuery("from TipoVehiculo", TipoVehiculo.class).list();
        List<Vehiculo> vehiculos = session.createQuery("from Vehiculo", Vehiculo.class).list();
        List<Propietario> propietarios = session.createQuery("from Propietario", Propietario.class).list();
        List<HistorialMantenimiento> historiales = session
                .createQuery("from HistorialMantenimiento", HistorialMantenimiento.class).list();

        xmlGenerator.syncronize(tipoVehiculos, vehiculos, propietarios, historiales);

        session.close();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
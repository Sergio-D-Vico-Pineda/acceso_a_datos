package com.example;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        // crear producto

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Producto producto = new Producto("Manzana", 1.2, 50);
        session.persist(producto);

        transaction.commit();
        session.close();
        HibernateUtil.shutdown();

        // Leer producto

        Session session1 = HibernateUtil.getSessionFactory().openSession();
        List<Producto> productos = session1.createQuery("from Producto",
                Producto.class).list();

        productos.forEach(System.out::println);

        session1.close();
        HibernateUtil.shutdown();

        // Actualizar producto

        Session session2 = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction1 = session2.beginTransaction();

        Producto producto1 = session2.get(Producto.class, 1);
        producto1.setNombre("Peras");
        session2.merge(producto1);

        transaction1.commit();
        session2.close();
        HibernateUtil.shutdown();

        // Eliminar producto
        Session session3 = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction2 = session3.beginTransaction();

        Producto producto2 = session3.get(Producto.class, 2);
        session3.remove(producto2);

        transaction2.commit();
        session3.close();
        HibernateUtil.shutdown();
    }
}
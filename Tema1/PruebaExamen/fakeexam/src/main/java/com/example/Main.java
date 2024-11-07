package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// el programa es en español y el código en inglés
public class Main {
    static Scanner input = new Scanner(System.in);
    static boolean txt = true;
    static String filepath = "products";

    public static void main(String[] args) {
        int option = 0;

        do {
            for (int i = 0; i < 20; i++) {
                System.out.println("");
            }

            System.out.println("Inventario de productos");
            System.out.println("-----------------------");
            if (txt) {
                System.out.println("Formato de archivo: TXT");
            } else {
                System.out.println("Formato de archivo: XML");
            }
            System.out.println("Menu");
            System.out.println("");
            System.out.println("1 - Añadir producto");
            System.out.println("2 - Ver todos los proyectos");
            System.out.println("3 - Buscar producto por ID");
            System.out.println("4 - Actualizar precio de un producto por ID");
            System.out.println("5 - Cambiar formato de archivo (TXT o XML)");
            System.out.println("");
            System.out.println("0 - Salir");
            System.out.println("");
            System.out.println("Elige una opción: ");

            try {
                option = input.nextInt();
            } catch (Exception e) {
                option = -1;
                input.next();
            }

            input.nextLine();
            System.out.println("");

            switch (option) {
                case 0 -> ExitProgram();
                case 1 -> AddProduct();
                case 2 -> ListAllProducts();
                case 3 -> SearchProduct();
                case 4 -> UpdatePrice();
                case 5 -> ChangeFileFormat();
                default -> InvalidOption();
            }

            if (option != 0) {
                System.out.println("");
                System.out.println("Pulsa ENTER para continuar...");
                input.nextLine();
            }

        } while (option != 0);

        input.close();
    }

    static void AddProduct() {
        System.out.println("Añadir producto");
        System.out.println("-------------------------");
        System.out.println("");

        System.out.println("Introduce el ID: ");
        int id = input.nextInt();
        input.nextLine();
        System.out.println("Introduce el nombre: ");
        String name = input.nextLine();
        System.out.println("Introduce el precio: ");
        double price = input.nextDouble();
        input.nextLine();
        System.out.println("Introduce la cantidad: ");
        int quantity = input.nextInt();
        input.nextLine();
        System.out.println("");

        Product product = new Product(id, name, price, quantity);
        System.out.println("Se ha creado el siguiente producto:");
        System.out.println(product.showInfo());

        if (txt) {
            SaveOnTXT(product);
        } else
            SaveOnXML(product);
    }

    static void ListAllProducts() {
        System.out.println("Ver todos los productos");
        System.out.println("-------------------------");
        if (txt)
            readWithTXT();
        else
            readWithXML();
    }

    static void SearchProduct() {
        Product newPro;
        System.out.println("Buscar producto por ID");
        System.out.println("-------------------------");
        System.out.println("");
        System.out.println("Introduce el ID del producto: ");
        int id = input.nextInt();
        input.nextLine();

        if (txt)
            newPro = getOneWithTXT(id);
        else
            newPro = getOneWithXML(id);

        if (newPro != null)
            System.out.println(newPro.showInfo());
    }

    static void UpdatePrice() {
        System.out.println("Actualizar precio de un producto por ID");
        System.out.println("-------------------------");
        System.out.println("");

        System.out.println("Introduce el ID del producto:");
        int id = input.nextInt();
        input.nextLine();
        System.out.println("Introduce el nuevo precio:");
        double newprice = input.nextDouble();
        input.nextLine();

        if (txt) {
            try {
                ArrayList<Product> list = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(filepath + ".txt"));

                String line;
                while ((line = br.readLine()) != null) {
                    list.add(Product.fromString(line));
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(filepath + ".txt"));
                for (Product product : list) {
                    if (product.getId() == id) {
                        product.setPrice(newprice);
                    }
                    bw.write(product.toString());
                    bw.newLine();
                }
                bw.close();

                br.close();
            } catch (Exception e) {
                System.out.println("Error al leer el archivo TXT.");
                e.printStackTrace();
            }
        } else {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new File(filepath + ".xml"));
                Element root = doc.getDocumentElement();
                root.normalize();

                NodeList pList = root.getElementsByTagName("product");

                for (int i = 0; i < pList.getLength(); i++) {
                    if (pList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element product = (Element) pList.item(i);
                        String idp = product.getElementsByTagName("id").item(0).getTextContent();
                        if (Integer.parseInt(idp) == id) {
                            product.getElementsByTagName("price").item(0).setTextContent(Double.toString(newprice));
                            TransformerFactory tf = TransformerFactory.newInstance();
                            Transformer t = tf.newTransformer();
                            t.transform(new DOMSource(doc), new StreamResult(new File(filepath + ".xml")));
                            System.out.println("Se ha actualizado el precio del producto.");
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Error al leer el archivo XML");
                e.printStackTrace();
            }
        }
    }

    static void ChangeFileFormat() {
        System.out.println("Cambiar formato de archivo (TXT o XML)");
        System.out.println("-------------------------");
        System.out.println("");

        if (txt) {
            System.out.println("El formato actual es TXT");
            System.out.println("Cambiando a formato XML...");
            System.out.println("Se va a trabajar con los datos del XML a partir de ahora");
            System.out.println("TXT --> XML");
            txt = false;
        } else {
            System.out.println("El formato actual es XML");
            System.out.println("Cambiando a formato TXT...");
            System.out.println("Se va a trabajar con los datos del TXT a partir de ahora");
            System.out.println("XML --> TXT");
            txt = true;
        }
    }

    static void InvalidOption() {
        System.out.println("Opcion no valida");
        System.out.println("");
    }

    static void ExitProgram() {
        System.out.println("Programa terminado. ¡Adiós!");
    }

    static void SaveOnXML(Product obProduct) {
        System.out.println("");
        try {
            File f = new File(filepath + ".xml");
            if (!f.exists())
                f.createNewFile();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);
            Element root = doc.getDocumentElement();
            root.normalize();

            Element product = doc.createElement("product");
            Element id = doc.createElement("id");
            Element name = doc.createElement("name");
            Element price = doc.createElement("price");
            Element quantity = doc.createElement("quantity");

            id.setTextContent(Integer.toString(obProduct.getId()));
            name.setTextContent(obProduct.getName());
            price.setTextContent(Double.toString(obProduct.getPrice()));
            quantity.setTextContent(Integer.toString(obProduct.getQuantity()));

            product.appendChild(id);
            product.appendChild(name);
            product.appendChild(price);
            product.appendChild(quantity);

            root.appendChild(product);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.transform(new DOMSource(doc), new StreamResult(f));

            System.out.println("Se ha guardado correctamente el archivo XML.");
        } catch (Exception e) {
            System.out.println("Error al guardar el archivo XML");
            e.printStackTrace();
        }

    }

    static void SaveOnTXT(Product obProduct) {
        System.out.println("");
        try {
            File f = new File(filepath + ".txt");
            if (!f.exists())
                f.createNewFile();

            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));

            bw.write(obProduct.getId() + "," + obProduct.getName() + "," + obProduct.getPrice() + ","
                    + obProduct.getQuantity());
            bw.newLine();
            bw.close();

            System.out.println("Se ha guardado correctamente el archivo TXT.");
        } catch (Exception e) {
            System.out.println("Error al crear el archivo TXT");
            e.printStackTrace();
        }
    }

    static void readWithTXT() {
        System.out.println("");
        File f = new File(filepath + ".txt");

        if (!f.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println(
                        "ID: " + parts[0] + ", Name: " + parts[1] + ", Price: " + parts[2] + ", Quantity: " + parts[3]);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo TXT.");
            e.printStackTrace();
        }

    }

    static void readWithXML() {
        System.out.println("");
        File f = new File(filepath + ".xml");

        if (!f.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);
            Element root = doc.getDocumentElement();
            root.normalize();

            NodeList pList = root.getElementsByTagName("product");
            for (int i = 0; i < pList.getLength(); i++) {
                if (pList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element product = (Element) pList.item(i);
                    String id = product.getElementsByTagName("id").item(0).getTextContent();
                    String name = product.getElementsByTagName("name").item(0).getTextContent();
                    String price = product.getElementsByTagName("price").item(0).getTextContent();
                    String quantity = product.getElementsByTagName("quantity").item(0).getTextContent();
                    System.out
                            .println("ID: " + id + ", Name: " + name + ", Price: " + price + ", Quantity: " + quantity);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo XML.");
            e.printStackTrace();
        }
    }

    static Product getOneWithXML(int ID) {
        Product newPro = null;
        File f = new File(filepath + ".xml");
        if (!f.exists()) {
            System.out.println("El archivo no existe.");
            return newPro;
        }

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);
            Element root = doc.getDocumentElement();
            root.normalize();

            NodeList pList = root.getElementsByTagName("product");
            for (int i = 0; i < pList.getLength(); i++) {
                if (pList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element product = (Element) pList.item(i);
                    String id = product.getElementsByTagName("id").item(0).getTextContent();
                    if (Integer.parseInt(id) == ID) {
                        String name = product.getElementsByTagName("name").item(0).getTextContent();
                        String price = product.getElementsByTagName("price").item(0).getTextContent();
                        String quantity = product.getElementsByTagName("quantity").item(0).getTextContent();
                        newPro = new Product(ID, name, Double.parseDouble(price), Integer.parseInt(quantity));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo XML.");
            e.printStackTrace();
        }

        if (newPro == null)
            System.out.println("Producto no encontrado");

        return newPro;
    }

    static Product getOneWithTXT(int ID) {
        Product newPro = null;
        File f = new File(filepath + ".txt");

        if (!f.exists()) {
            System.out.println("El archivo no existe.");
            return newPro;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == ID) {
                    newPro = Product.fromString(line);
                }
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo TXT.");
            e.printStackTrace();
        }

        if (newPro == null)
            System.out.println("Producto no encontrado");
        return newPro;
    }
}
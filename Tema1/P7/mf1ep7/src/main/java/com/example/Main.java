package com.example;

import java.io.File;
import java.io.IOException;

// import javax.print.Doc;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) throws Exception {
        String filepath = "biblioteca.xml";

        Document doc = crearDoc(filepath);

        // Obtengo los elementos de etiqueta libro
        NodeList listaLibros = doc.getElementsByTagName("libro");

        leerXML(listaLibros);
        System.out.println("----------------------");

        // Obtengo primero, un array de nodos, solo con biblioteca, luego saco el primer elemento, 
        // que es la biblioteca, lo transformo a Elemento del otro import y lo manejo
        Element biblioteca = (Element) doc.getElementsByTagName("biblioteca").item(0);

        // Creo un nuevo libro
        Element newLibro = anyadirLibro(doc, "El Señor de los Anillos", "J.R.R. Tolkien", "1954");
        // Le agrego una subetiqueta de titulo, autor y año

        Element newLibro2 = anyadirLibro2(doc, "La isla misteriosa", "Julio Verne", "1875");

        biblioteca.appendChild(newLibro2);
        // Y lo meto en el elemento biblioteca recogido del xml antes
        biblioteca.appendChild(newLibro);

        filepath = "biblioteca2.xml";

        saveDoc(doc, filepath);
        // bueno a partir de aqui ya locura
        // transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // bruh
        // saveDoc2(doc, filepath);

        // se repite el proceso para mostrar el nuevo xml
        Document doc1 = crearDoc(filepath);

        NodeList libros1 = doc1.getElementsByTagName("libro");

        leerXML(libros1);
    }

    static void leerXML(NodeList libros) {
        // le paso un array de nodos libros
        for (int i = 0; i < libros.getLength(); i++) {
            Element libro = (Element) libros.item(i);
            // Para titulo, autor y año, saco un array de nodos de dentro de libro con cada uno, 
            // saco el primer el elemento ya que solo hay uno y devuelvo el contenido
            String title = libro.getElementsByTagName("titulo").item(0).getTextContent();
            String author = libro.getElementsByTagName("autor").item(0).getTextContent();
            String year = libro.getElementsByTagName("año").item(0).getTextContent();
            // Se pone bonito y se imprime
            System.out.println(title + " by " + author + " (" + year + ")");
        }
    }

    static void leerXML2(NodeList libros) {
        // le paso un array de nodos libros
        for (int i = 0; i < libros.getLength(); i++) {
            Node nodoLibro = libros.item(i);

            // Se comprueba si es un elemento válido
            if (nodoLibro.getNodeType() == Node.ELEMENT_NODE) {
                Element libro = (Element) nodoLibro;
                String title = libro.getElementsByTagName("titulo").item(0).getTextContent();
                String author = libro.getElementsByTagName("autor").item(0).getTextContent();
                String year = libro.getElementsByTagName("año").item(0).getTextContent();
                // Se pone bonito y se imprime
                System.out.println(title + " by " + author + " (" + year + ")");
            }

        }
    }

    static Element anyadirLibro(Document doc, String title, String author, String year) {
        Element newLibro = doc.createElement("libro");

        newLibro.appendChild(doc.createElement("titulo")).appendChild(doc.createTextNode(title));
        newLibro.appendChild(doc.createElement("autor")).appendChild(doc.createTextNode(author));
        newLibro.appendChild(doc.createElement("año")).appendChild(doc.createTextNode(year));

        return newLibro;
    }

    static Element anyadirLibro2(Document doc, String title, String author, String year) {

        Element newLibro = doc.createElement("libro");

        Element newTitulo = doc.createElement("titulo");
        Element newAutor = doc.createElement("autor");
        Element newAno = doc.createElement("año");

        newTitulo.setTextContent(title);
        newAutor.setTextContent(author);
        newAno.setTextContent(year);

        newLibro.appendChild(newTitulo);
        newLibro.appendChild(newAutor);
        newLibro.appendChild(newAno);

        return newLibro;
    }

    static Document crearDoc(String filepath) {
        Document doc = null;
        try {
            // Crear toda la parafernalia para tratar un documento XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(filepath));
            // Normalizo el xml, para que no de problemas
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    static void saveDoc(Document doc, String filepath) {
        try {
            // Aqui vuelvo a transformar el xml con el nuevo libro // entiendo lo que hace pero wtf
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // Crear fábrica de transformación
            Transformer transformer = transformerFactory.newTransformer();
            // transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Transformador de DOM a XML, en este caso
            // Primero de donde lo cojemos y luego donde va
            // Aqui es donde escribe
            transformer.transform(new DOMSource(doc), new StreamResult(new File(filepath)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    static void saveDoc2(Document doc, String filepath) {
        try {
            // Aqui vuelvo a transformar el xml con el nuevo libro // entiendo lo que hace pero wtf
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // Crear fábrica de transformación
            Transformer transformer = transformerFactory.newTransformer();
            // Transformador de DOM a XML, en este caso
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            // Aqui es donde escribe
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
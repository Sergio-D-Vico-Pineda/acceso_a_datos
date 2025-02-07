// package com.gestion.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gestion.entities.HistorialMantenimiento;
import com.gestion.entities.Propietario;
import com.gestion.entities.TipoVehiculo;
import com.gestion.entities.Vehiculo;

import java.io.File;

public class XMLUtils {

    // Método para guardar un objeto en un archivo XML
    public static void saveToXml(Object object, String filePath) {
        try {
            // Crear un nuevo documento XML
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Crear el elemento raíz
            Element root = document.createElement(object.getClass().getSimpleName());
            document.appendChild(root);
    

            if (object instanceof Propietario) {
                Propietario propietario = (Propietario) object;

                Element id_propietario = document.createElement("IDPropietario");
                id_propietario.appendChild(document.createTextNode(String.valueOf(propietario.getIdPropietario())));
                root.appendChild(id_propietario);

                Element nombre = document.createElement("Nombre");
                nombre.appendChild(document.createTextNode(propietario.getNombre()));
                root.appendChild(nombre);

                Element apellido = document.createElement("Apellido");
                apellido.appendChild(document.createTextNode(propietario.getApellido()));
                root.appendChild(apellido);

                Element dni = document.createElement("DNI");
                dni.appendChild(document.createTextNode(propietario.getDni()));
                root.appendChild(dni);

                Element telefono = document.createElement("Telefono");
                telefono.appendChild(document.createTextNode(propietario.getTelefono()));
                root.appendChild(telefono);
            } else if (object instanceof TipoVehiculo) {
                TipoVehiculo tipoVehiculo = (TipoVehiculo) object;

                Element id_tipo = document.createElement("IDTipo");
                id_tipo.appendChild(document.createTextNode(String.valueOf(tipoVehiculo.getIdTipo())));
                root.appendChild(id_tipo);

                Element tipo = document.createElement("Tipo");
                tipo.appendChild(document.createTextNode(tipoVehiculo.getTipo()));
                root.appendChild(tipo);
            } else if (object instanceof Vehiculo) {
                Vehiculo vehiculo = (Vehiculo) object;

                Element id_vehiculo = document.createElement("IDVehiculo");
                id_vehiculo.appendChild(document.createTextNode(String.valueOf(vehiculo.getIdVehiculo())));
                root.appendChild(id_vehiculo);

                Element matricula = document.createElement("Matricula");
                matricula.appendChild(document.createTextNode(vehiculo.getMatricula()));
                root.appendChild(matricula);

                Element marca = document.createElement("Marca");
                marca.appendChild(document.createTextNode(vehiculo.getMarca()));
                root.appendChild(marca);

                Element modelo = document.createElement("Modelo");
                modelo.appendChild(document.createTextNode(vehiculo.getModelo()));
                root.appendChild(modelo);

                Element año_fabricacion = document.createElement("AñoFabricacion");
                año_fabricacion.appendChild(document.createTextNode(String.valueOf(vehiculo.getAñoFabricacion())));
                root.appendChild(año_fabricacion);

                Element precio = document.createElement("Precio");
                precio.appendChild(document.createTextNode(String.valueOf(vehiculo.getPrecio())));
                root.appendChild(precio);

                Element id_propietario = document.createElement("IDPropietario");
                id_propietario.appendChild(document.createTextNode(String.valueOf(vehiculo.getPropietario())));
                root.appendChild(id_propietario);

                Element id_tipo = document.createElement("IDTipo");
                id_tipo.appendChild(document.createTextNode(String.valueOf(vehiculo.getTipo())));
                root.appendChild(id_tipo);

            } else if (object instanceof HistorialMantenimiento) {
                HistorialMantenimiento historialMantenimiento = (HistorialMantenimiento) object;

                Element id_mantenimiento = document.createElement("IDMantenimiento");
                id_mantenimiento.appendChild(
                        document.createTextNode(String.valueOf(historialMantenimiento.getIdMantenimiento())));
                root.appendChild(id_mantenimiento);

                Element id_vehiculo = document.createElement("IDVehiculo");
                id_vehiculo.appendChild(
                        document.createTextNode(String.valueOf(historialMantenimiento.getIdMantenimiento())));
                root.appendChild(id_vehiculo);

                Element fecha = document.createElement("Fecha");
                fecha.appendChild(document.createTextNode(historialMantenimiento.getFecha().toString()));
                root.appendChild(fecha);

                Element descripcion = document.createElement("Descripcion");
                descripcion.appendChild(document.createTextNode(historialMantenimiento.getDescripcion()));
                root.appendChild(descripcion);

                Element coste = document.createElement("Coste");
                coste.appendChild(document.createTextNode(String.valueOf(historialMantenimiento.getCoste())));
                root.appendChild(coste);
            }

            // Guardar el documento en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para leer un objeto desde un archivo XML
    public static <T> T readFromXml(Class<T> clazz, String filePath) {
        T object = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // Aquí puedes leer los elementos del XML y crear el objeto
            // Por ejemplo, si estás leyendo un Propietario:
            if (clazz == Propietario.class) {
                Element root = document.getDocumentElement();
                // Create a new instance of Propietario
                if (Propietario.class.isAssignableFrom(clazz)) {
                    object = clazz.getConstructor().newInstance();  // Safe instantiation
                }
                // Read XML elements and set properties for the object
                int id_propietario = Integer.parseInt(root.getElementsByTagName("IDPropietario").item(0).getTextContent());
                String nombre = root.getElementsByTagName("Nombre").item(0).getTextContent();
                String apellido = root.getElementsByTagName("Apellido").item(0).getTextContent();
                String dni = root.getElementsByTagName("DNI").item(0).getTextContent();
                String telefono = root.getElementsByTagName("Telefono").item(0).getTextContent();
                // Set properties for the Propietario object
                if (object instanceof Propietario) {
                    ((Propietario) object).setIdPropietario(id_propietario);
                    ((Propietario) object).setNombre(nombre);
                    ((Propietario) object).setApellido(apellido);
                    ((Propietario) object).setDni(dni);
                    ((Propietario) object).setTelefono(telefono);
                }
                // Add any other necessary properties
                
                // Add additional checks for other classes if needed
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
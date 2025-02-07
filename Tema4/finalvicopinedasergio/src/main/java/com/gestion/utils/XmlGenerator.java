package com.gestion.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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
// import java.math.BigDecimal;
// import java.util.Date;

public class XmlGenerator {

    private Document document;

    public XmlGenerator() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Crear el elemento raíz
            document = docBuilder.newDocument();
            Element rootElement = document.createElement("Datos");
            document.appendChild(rootElement);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void addPropietario(Propietario propietario) {
        Element propietarios = getOrCreateElement(document.getDocumentElement(), "Propietarios");
        Element propietarioElement = document.createElement("Propietario");

        addElement(propietarioElement, "idPropietario", String.valueOf(propietario.getIdPropietario()));
        addElement(propietarioElement, "nombre", propietario.getNombre());
        addElement(propietarioElement, "apellido", propietario.getApellido());
        addElement(propietarioElement, "dni", propietario.getDni());
        addElement(propietarioElement, "telefono", propietario.getTelefono());

        propietarios.appendChild(propietarioElement);
    }

    public void addHistorialMantenimiento(HistorialMantenimiento historial) {
        Element historiales = getOrCreateElement(document.getDocumentElement(), "HistorialesMantenimiento");
        Element historialElement = document.createElement("HistorialMantenimiento");

        addElement(historialElement, "idMantenimiento", String.valueOf(historial.getIdMantenimiento()));
        addElement(historialElement, "fecha", historial.getFecha().toString());
        addElement(historialElement, "descripcion", historial.getDescripcion());
        addElement(historialElement, "coste", historial.getCoste().toString());

        // Guardar solo el ID del vehículo
        addElement(historialElement, "idVehiculo", String.valueOf(historial.getVehiculo().getIdVehiculo()));

        historiales.appendChild(historialElement);
    }

    public void addTipoVehiculo(TipoVehiculo tipoVehiculo) {
        Element tiposVehiculo = getOrCreateElement(document.getDocumentElement(), "TiposVehiculo");
        Element tipoVehiculoElement = document.createElement("TipoVehiculo");

        addElement(tipoVehiculoElement, "idTipo", String.valueOf(tipoVehiculo.getIdTipo()));
        addElement(tipoVehiculoElement, "tipo", tipoVehiculo.getTipo());

        tiposVehiculo.appendChild(tipoVehiculoElement);
    }

    public void addVehiculo(Vehiculo vehiculo) {
        Element vehiculos = getOrCreateElement(document.getDocumentElement(), "Vehiculos");
        Element vehiculoElement = document.createElement("Vehiculo");

        addElement(vehiculoElement, "idVehiculo", String.valueOf(vehiculo.getIdVehiculo()));
        addElement(vehiculoElement, "matricula", vehiculo.getMatricula());
        addElement(vehiculoElement, "marca", vehiculo.getMarca());
        addElement(vehiculoElement, "modelo", vehiculo.getModelo());
        addElement(vehiculoElement, "añoFabricacion", String.valueOf(vehiculo.getAñoFabricacion()));
        addElement(vehiculoElement, "precio", vehiculo.getPrecio().toString());

        // Guardar solo el ID del propietario
        addElement(vehiculoElement, "idPropietario", String.valueOf(vehiculo.getPropietario().getIdPropietario()));

        // Guardar solo el ID del tipo de vehículo
        addElement(vehiculoElement, "idTipo", String.valueOf(vehiculo.getTipo().getIdTipo()));

        vehiculos.appendChild(vehiculoElement);
    }

    private Element getOrCreateElement(Element parent, String tagName) {
        Element element = (Element) parent.getElementsByTagName(tagName).item(0);
        if (element == null) {
            element = document.createElement(tagName);
            parent.appendChild(element);
        }
        return element;
    }

    private void addElement(Element parent, String tagName, String value) {
        Element element = document.createElement(tagName);
        element.appendChild(document.createTextNode(value));
        parent.appendChild(element);
    }

    public void saveToFile(String filePath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
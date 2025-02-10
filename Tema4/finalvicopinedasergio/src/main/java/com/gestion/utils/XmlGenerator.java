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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gestion.entities.HistorialMantenimiento;
import com.gestion.entities.Propietario;
import com.gestion.entities.TipoVehiculo;
import com.gestion.entities.Vehiculo;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class XmlGenerator {

    private Document document;
    private String filePath;

    public XmlGenerator(String filePath) {
        this.filePath = filePath;
        loadOrCreateDocument();
    }

    private void loadOrCreateDocument() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                // Cargar el documento existente
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(file);
            } else {
                // Crear un nuevo documento
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.newDocument();
                Element rootElement = document.createElement("Datos");
                document.appendChild(rootElement);

                // Guardar el nuevo documento en el archivo
                saveToFile();
            }
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdatePropietario(Propietario propietario) {
        Element propietarios = getOrCreateElement(document.getDocumentElement(), "Propietarios");
        Element propietarioElement = findElementByIdold(propietarios, "Propietario", "idPropietario",
                String.valueOf(propietario.getIdPropietario()));

        if (propietarioElement == null) {
            propietarioElement = document.createElement("Propietario");
            propietarios.appendChild(propietarioElement);
        }

        propietarioElement.setAttribute("idPropietario", String.valueOf(propietario.getIdPropietario()));
        updateElement(propietarioElement, "idPropietario", String.valueOf(propietario.getIdPropietario()));
        updateElement(propietarioElement, "nombre", propietario.getNombre());
        updateElement(propietarioElement, "apellido", propietario.getApellido());
        updateElement(propietarioElement, "dni", propietario.getDni());
        updateElement(propietarioElement, "telefono", propietario.getTelefono());

        System.out.println("Propietario guardado en XML");
        saveToFile();
    }

    public void addOrUpdateHistorialMantenimiento(HistorialMantenimiento historial) {
        Element historiales = getOrCreateElement(document.getDocumentElement(), "HistorialesMantenimiento");
        Element historialElement = findElementByIdold(historiales, "HistorialMantenimiento", "idMantenimiento",
                String.valueOf(historial.getIdMantenimiento()));

        if (historialElement == null) {
            historialElement = document.createElement("HistorialMantenimiento");
            historiales.appendChild(historialElement);
        }

        historialElement.setAttribute("idMantenimiento", String.valueOf(historial.getIdMantenimiento()));
        updateElement(historialElement, "idMantenimiento", String.valueOf(historial.getIdMantenimiento()));
        updateElement(historialElement, "fecha", historial.getFecha().toString());
        updateElement(historialElement, "descripcion", historial.getDescripcion());
        updateElement(historialElement, "coste", historial.getCoste().toString());

        // Guardar solo el ID del vehículo
        updateElement(historialElement, "idVehiculo", String.valueOf(historial.getVehiculo().getIdVehiculo()));

        System.out.println("Historial guardado en XML");
        saveToFile();
    }

    public void addOrUpdateTipoVehiculo(TipoVehiculo tipoVehiculo) {
        Element tiposVehiculo = getOrCreateElement(document.getDocumentElement(), "TiposVehiculo");
        Element tipoVehiculoElement = findElementByIdold(tiposVehiculo, "TipoVehiculo", "idTipo",
                String.valueOf(tipoVehiculo.getIdTipo()));

        if (tipoVehiculoElement == null) {
            tipoVehiculoElement = document.createElement("TipoVehiculo");
            tiposVehiculo.appendChild(tipoVehiculoElement);
        }

        tipoVehiculoElement.setAttribute("idTipo", String.valueOf(tipoVehiculo.getIdTipo()));
        updateElement(tipoVehiculoElement, "idTipo", String.valueOf(tipoVehiculo.getIdTipo()));
        updateElement(tipoVehiculoElement, "tipo", tipoVehiculo.getTipo());

        System.out.println("Tipo de vehiculo guardado en XML");
        saveToFile();
    }

    public void addOrUpdateVehiculo(Vehiculo vehiculo) {
        Element vehiculos = getOrCreateElement(document.getDocumentElement(), "Vehiculos");
        Element vehiculoElement = findElementByIdold(vehiculos, "Vehiculo", "idVehiculo",
                String.valueOf(vehiculo.getIdVehiculo()));

        if (vehiculoElement == null) {
            vehiculoElement = document.createElement("Vehiculo");
            vehiculos.appendChild(vehiculoElement);
        }

        vehiculoElement.setAttribute("idVehiculo", String.valueOf(vehiculo.getIdVehiculo()));
        updateElement(vehiculoElement, "idVehiculo", String.valueOf(vehiculo.getIdVehiculo()));
        updateElement(vehiculoElement, "matricula", vehiculo.getMatricula());
        updateElement(vehiculoElement, "marca", vehiculo.getMarca());
        updateElement(vehiculoElement, "modelo", vehiculo.getModelo());
        updateElement(vehiculoElement, "añoFabricacion", String.valueOf(vehiculo.getAñoFabricacion()));
        updateElement(vehiculoElement, "precio", vehiculo.getPrecio().toString());

        // Guardar solo el ID del propietario
        updateElement(vehiculoElement, "idPropietario", String.valueOf(vehiculo.getPropietario().getIdPropietario()));

        // Guardar solo el ID del tipo de vehículo
        updateElement(vehiculoElement, "idTipo", String.valueOf(vehiculo.getTipo().getIdTipo()));

        System.out.println("Vehiculo guardado en XML");
        saveToFile();
    }

    private Element getOrCreateElement(Element parent, String tagName) {
        Element element = (Element) parent.getElementsByTagName(tagName).item(0);
        if (element == null) {
            element = document.createElement(tagName);
            parent.appendChild(element);
        }
        return element;
    }

    private Element findElementByIdold(Element parent, String tagName, String idTagName, String idValue) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getElementsByTagName(idTagName).item(0) != null &&
                        element.getElementsByTagName(idTagName).item(0).getTextContent().equals(idValue)) {
                    return element;
                }
            }
        }
        return null;
    }

    private void updateElement(Element parent, String tagName, String value) {
        Element element = (Element) parent.getElementsByTagName(tagName).item(0);
        if (element == null) {
            element = document.createElement(tagName);
            parent.appendChild(element);
        }
        element.setTextContent(value);
    }

    public void saveToFile() {
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

    private void updateElementIfChanged(Element element, String tagName, String newValue) {
        Node node = element.getElementsByTagName(tagName).item(0);
        if (node == null) {
            Element newChild = document.createElement(tagName);
            newChild.setTextContent(newValue);
            element.appendChild(newChild);
        } else if (!newValue.equals(node.getTextContent())) {
            node.setTextContent(newValue);
        }
    }

    private void removeElementsNotInList(NodeList nodeList, List<?> list, String elementTagName,
            String idAttributeName) {
        for (int i = nodeList.getLength() - 1; i >= 0; i--) {
            Element element = (Element) nodeList.item(i);
            boolean exists = false;
            for (Object obj : list) {
                String idValue = getIdValue(obj, idAttributeName);
                if (idValue != null && idValue.equals(element.getAttribute(idAttributeName))) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                element.getParentNode().removeChild(element);
            }
        }
    }

    private Element findElementById(Element parent, String tagName, String idTagName, String idValue) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (idValue.equals(element.getAttribute(idTagName))) {
                return element;
            }
        }
        return null;
    }

    public void syncronize(List<TipoVehiculo> tipoVehiculos, List<Vehiculo> vehiculos, List<Propietario> propietarios,
            List<HistorialMantenimiento> historiales) {
        try {
            Element rootElement = document.getDocumentElement();

            // Sincronizar TiposVehiculo
            Element tiposVehiculoElement = getOrCreateElement(rootElement, "TiposVehiculo");
            NodeList tiposVehiculoNodeList = tiposVehiculoElement.getElementsByTagName("TipoVehiculo");
            for (TipoVehiculo tipo : tipoVehiculos) {
                Element tipoVehiculoElement = findElementById(tiposVehiculoElement, "TipoVehiculo", "idTipo",
                        String.valueOf(tipo.getIdTipo()));
                if (tipoVehiculoElement == null) {
                    tipoVehiculoElement = document.createElement("TipoVehiculo");
                    tipoVehiculoElement.setAttribute("idTipo", String.valueOf(tipo.getIdTipo()));
                    tiposVehiculoElement.appendChild(tipoVehiculoElement);
                }
                updateElementIfChanged(tipoVehiculoElement, "tipo", tipo.getTipo());
            }
            // Eliminar tipoVehiculo que no existen en la lista
            removeElementsNotInList(tiposVehiculoNodeList, tipoVehiculos, "TipoVehiculo", "idTipo");

            // Sincronizar Vehiculos
            Element vehiculosElement = getOrCreateElement(rootElement, "Vehiculos");
            NodeList vehiculosNodeList = vehiculosElement.getElementsByTagName("Vehiculo");
            for (Vehiculo vehiculo : vehiculos) {
                Element vehiculoElement = findElementById(vehiculosElement, "Vehiculo", "idVehiculo",
                        String.valueOf(vehiculo.getIdVehiculo()));
                if (vehiculoElement == null) {
                    vehiculoElement = document.createElement("Vehiculo");
                    vehiculoElement.setAttribute("idVehiculo", String.valueOf(vehiculo.getIdVehiculo()));
                    vehiculosElement.appendChild(vehiculoElement);
                }
                updateElementIfChanged(vehiculoElement, "matricula", vehiculo.getMatricula());
                updateElementIfChanged(vehiculoElement, "marca", vehiculo.getMarca());
                updateElementIfChanged(vehiculoElement, "modelo", vehiculo.getModelo());
                updateElementIfChanged(vehiculoElement, "añoFabricacion", String.valueOf(vehiculo.getAñoFabricacion()));
                updateElementIfChanged(vehiculoElement, "precio", vehiculo.getPrecio().toString());
                updateElementIfChanged(vehiculoElement, "idPropietario",
                        String.valueOf(vehiculo.getPropietario().getIdPropietario()));
                updateElementIfChanged(vehiculoElement, "idTipo", String.valueOf(vehiculo.getTipo().getIdTipo()));
            }
            // Eliminar vehiculo que no existen en la lista
            removeElementsNotInList(vehiculosNodeList, vehiculos, "Vehiculo", "idVehiculo");

            // Sincronizar Propietarios
            Element propietariosElement = getOrCreateElement(rootElement, "Propietarios");
            NodeList propietariosNodeList = propietariosElement.getElementsByTagName("Propietario");
            for (Propietario propietario : propietarios) {
                Element propietarioElement = findElementById(propietariosElement, "Propietario", "idPropietario",
                        String.valueOf(propietario.getIdPropietario()));
                if (propietarioElement == null) {
                    propietarioElement = document.createElement("Propietario");
                    propietarioElement.setAttribute("idPropietario", String.valueOf(propietario.getIdPropietario()));
                    propietariosElement.appendChild(propietarioElement);
                }
                updateElementIfChanged(propietarioElement, "nombre", propietario.getNombre());
                updateElementIfChanged(propietarioElement, "apellido", propietario.getApellido());
                updateElementIfChanged(propietarioElement, "dni", propietario.getDni());
                updateElementIfChanged(propietarioElement, "telefono", propietario.getTelefono());
            }
            // Eliminar propietario que no existen en la lista
            removeElementsNotInList(propietariosNodeList, propietarios, "Propietario", "idPropietario");

            // Sincronizar HistorialMantenimiento
            Element historialMantenimientoElement = getOrCreateElement(rootElement, "HistorialMantenimiento");
            NodeList historialMantenimientoNodeList = historialMantenimientoElement
                    .getElementsByTagName("HistorialMantenimiento");
            for (HistorialMantenimiento historial : historiales) {
                Element historialElement = findElementById(historialMantenimientoElement, "HistorialMantenimiento",
                        "idMantenimiento",
                        String.valueOf(historial.getIdMantenimiento()));
                if (historialElement == null) {
                    historialElement = document.createElement("HistorialMantenimiento");
                    historialElement.setAttribute("idMantenimiento", String.valueOf(historial.getIdMantenimiento()));
                    historialMantenimientoElement.appendChild(historialElement);
                }
                updateElementIfChanged(historialElement, "fecha", formatDate(historial.getFecha()));
                updateElementIfChanged(historialElement, "descripcion", historial.getDescripcion());
                updateElementIfChanged(historialElement, "coste", historial.getCoste().toString());
                Vehiculo vehiculo = historial.getVehiculo();
                if (vehiculo != null) {
                    updateElementIfChanged(historialElement, "idVehiculo", String.valueOf(vehiculo.getIdVehiculo()));
                } else {
                    System.out.println("HistorialMantenimiento with null Vehiculo found.");
                }
            }
            // Eliminar historialMantenimiento que no existen en la lista
            removeElementsNotInList(historialMantenimientoNodeList, historiales, "HistorialMantenimiento",
                    "idMantenimiento");

            // Guardar el archivo
            saveToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getIdValue(Object obj, String attributeName) {
        if (obj instanceof TipoVehiculo) {
            return String.valueOf(((TipoVehiculo) obj).getIdTipo());
        } else if (obj instanceof Vehiculo) {
            return String.valueOf(((Vehiculo) obj).getIdVehiculo());
        } else if (obj instanceof Propietario) {
            return String.valueOf(((Propietario) obj).getIdPropietario());
        } else if (obj instanceof HistorialMantenimiento) {
            return String.valueOf(((HistorialMantenimiento) obj).getIdMantenimiento());
        }
        return null;
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import utils.Utils;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class RuedaSerrano_FranciscoJavier_EjerClaseDOMSAX {
    static final int TAM_NOMBRE = 15, TAM_POWER = 20, TAM_IDENT = 15, TAM_FAMILIAR = 35;
    static final int CANT_FAMILIARES = 3, CANT_DATOS_INTEGER = 1, CANT_DATOS_BOOL = 1;
    static final int TAM_LINEA_DATOS =
            ((4 * CANT_DATOS_INTEGER) + (TAM_NOMBRE + TAM_POWER + TAM_IDENT + (TAM_FAMILIAR * CANT_FAMILIARES))*2 + CANT_DATOS_BOOL);
    public static void main(String[] args) throws IOException, SAXException {
        //String ruta = "/home/dondcas-ubuntu/Escritorio/2DAM/Acceso_a_Datos/Pruebas/Marvel2.txt";
        String ruta = "C:\\Users\\dondc\\OneDrive\\Escritorio\\carpetas\\2DAM\\AccesoADatos\\Pruebas\\Marvel.txt";
        File archivo = new File(ruta);
        File ficheroXML = new File("C:\\Users\\dondc\\OneDrive\\Escritorio\\carpetas\\2DAM\\AccesoADatos\\Pruebas\\Marvel.xml");
        if (!archivo.exists() || archivo.length() == 0) {
            ingresarDatosIniciales(archivo);
        }else Utils.pulsaEnter("Fichero cargado, pulsa enter. Pulsa enter");

        menuCRUD(archivo, ficheroXML);
    }

    private static void menuCRUD(File archivo, File ficheroXml) throws IOException, SAXException {
        int op = -1;
        do{
            mostrarMenu();
            op = Utils.convertirAInt(Utils.pedirPorTeclado("Introduce una opción: "));
            switch (op){
                case -1 -> System.out.println("ERROR: OPCIÓN NO VALIDA");
                case 0 -> System.out.println("ADIOS VUELVA PRONTO! ono");
                case 1 -> generarXML(archivo, ficheroXml);
                case 2 -> leerXML(ficheroXml);
                case 3 -> saxPorDefecto(ficheroXml);
                case 4 -> saxManejadorPropio(ficheroXml);
                default -> System.out.println("Opción erronea");
            };
        }while(op != 0);
    }

    private static void generarXML(File archivo, File ficheroXML) throws IOException {
        System.out.println("Generando XML...");
        try(RandomAccessFile file = new RandomAccessFile(archivo, "r")){
            int id, posicion = 0;
            char aux, nombre[] = new char[TAM_NOMBRE], poder[] = new char[TAM_POWER],
                    identidad[] = new char[TAM_IDENT], familiar[];
            boolean vivo = true;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try{
                DocumentBuilder builder = factory.newDocumentBuilder();
                DOMImplementation implementation = builder.getDOMImplementation();
                Document document = implementation.createDocument(null, "Heroes", null);
                document.setXmlVersion("1.0");
                while (file.getFilePointer() != file.length()){
                    file.seek(posicion);
                    id = file.readInt();
                    if(id > 0){
                        for(int i = 0; i < TAM_NOMBRE; i++){
                            aux = file.readChar();
                            nombre[i] = aux;
                        }
                        for(int i = 0; i < TAM_POWER; i++){
                            aux = file.readChar();
                            poder[i] = aux;
                        }
                        for(int i = 0; i < TAM_IDENT; i++){
                            aux = file.readChar();
                            identidad[i] = aux;
                        }
                        vivo = file.readBoolean();
                        //Insertamos los datos que descuelgan de la etiqueta raiz
                        Element raiz = document.createElement("Heroe");
                        document.getDocumentElement().appendChild(raiz);
                        crearElemento("Id", Integer.toString(id), raiz, document);
                        crearElemento("Nombre", new String(nombre).trim(), raiz, document);
                        crearElemento("SuperPoder", new String(poder).trim(), raiz, document);
                        crearElemento("identidad",new String(identidad).trim(), raiz, document);
                        if (vivo) crearElemento ("EstaVivo", "", raiz, document);
                        //Insertamos ahora los datos hijos de la siguiente etiqueta
                        Element hijo = document.createElement("Familiares");
                        document.getDocumentElement().appendChild(hijo);
                        for(int i = 0; i < CANT_FAMILIARES; i++){
                            familiar = new char[TAM_FAMILIAR];
                            for (int j = 0; j < TAM_FAMILIAR; j++) {
                                aux = file.readChar();
                                familiar[j] = aux;
                            }
                            String familiarString = new String(familiar).trim();
                            String nombreEtiqueta = familiarString.substring(familiarString.indexOf('(')+1,familiarString.indexOf(')'));
                            if ( nombreEtiqueta.contains(" ")) nombreEtiqueta = nombreEtiqueta.replace(' ', '_');
                            String valueEtiqueta = familiarString.substring(0,familiarString.indexOf('(')).trim();
                            crearElemento(nombreEtiqueta, valueEtiqueta, hijo, document );
                        }
                        raiz.appendChild(hijo);
                    }
                    posicion += TAM_LINEA_DATOS;
                }
                Source source = new DOMSource(document);
                //Result result = new StreamResult(new java.io.File("/home/dondcas-ubuntu/Escritorio/2DAM/Acceso_a_Datos/Pruebas/Marvel2.xml"));
                Result result = new StreamResult(new File("C:\\Users\\dondc\\OneDrive\\Escritorio\\carpetas\\2DAM\\AccesoADatos\\Pruebas\\Marvel.xml"));
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.transform(source, result);
                ficheroXML = new File("C:\\Users\\dondc\\OneDrive\\Escritorio\\carpetas\\2DAM\\AccesoADatos\\Pruebas\\Marvel.xml");
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TransformerConfigurationException e) {
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void leerXML(File filexml) throws FileNotFoundException, IOException, SAXException {
        if (!filexml.exists() || filexml == null) Utils.pulsaEnter("No existe el archivo");
        else{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
            Document document = builder.parse(filexml);
            document.getDocumentElement().normalize();
            Element raiz = document.getDocumentElement();
            System.out.printf("Elemento raiz: %s\n", raiz.getNodeName());
            NodeList heroes = document.getElementsByTagName("Heroe");
            System.out.printf("Hay registrados: %d heroes\n", heroes.getLength());
            Utils.pulsaEnter("Pulsa enter para ver cada Heroe uno a uno");
            for(int i = 0; i < heroes.getLength(); i++){
                Utils.limpiarPantalla();
                Node heroe = heroes.item(i);
                if (heroe.getNodeType() == Node.ELEMENT_NODE){
                    Element elementoHeroe = (Element) heroe;
                    String id = elementoHeroe.getElementsByTagName("Id").item(0).getTextContent();
                    String nombre = elementoHeroe.getElementsByTagName("Nombre").item(0).getTextContent();
                    String poder = elementoHeroe.getElementsByTagName("SuperPoder").item(0).getTextContent();
                    String identidad = elementoHeroe.getElementsByTagName("identidad").item(0).getTextContent();
                    String estaVivo = (elementoHeroe.getElementsByTagName("EstaVivo").item(0) != null ? "Esta Vivo" : "Esta Morido");
                    System.out.printf("Nº ID: %s\n" +
                            "- Alias: %s\n" +
                            "- Superpoder principal: %s\n" +
                            "- Identidad Secreta: %s\n" +
                                    "- %s",
                            id, nombre, poder, identidad, estaVivo);
                    System.out.println("\n Familiares conocidos: ");
                    NodeList familia = elementoHeroe.getElementsByTagName("Familiares");
                    if (familia.getLength() <= 0) System.out.printf("Sin familiares conocidos.");
                    else{
                        Node nodoFamiliares = familia.item(0);
                        NodeList listaFamiliares = nodoFamiliares.getChildNodes();
                        for(int j = 0; j < listaFamiliares.getLength(); j++){
                            Node familiar = listaFamiliares.item(j);
                            if (familiar.getNodeType() == Node.ELEMENT_NODE){
                                Element elementFamiliar = (Element) familiar;
                                System.out.printf("- %s (%s)\n", elementFamiliar.getTextContent(), elementFamiliar.getNodeName());
                            }
                        }
                    }
                    Utils.pulsaEnter("Ver el siguiente Heroe de la lista ...");
                }
            }
        }
    }

    private static void saxPorDefecto(File filexml) throws FileNotFoundException, IOException, SAXException {
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser interprete = factory.newSAXParser();
            interprete.parse(filexml, new DefaultHandler());
            Utils.pulsaEnter("El archivo esta perfecto mi rey");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            Utils.pulsaEnter("Hay errores en el archivo");
        }
    }

    private static void saxManejadorPropio(File filexml) throws FileNotFoundException, IOException, SAXException {
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser interprete = factory.newSAXParser();
            XMLReader procesadorxml = interprete.getXMLReader();
            GestorContenido gestorContenido = new GestorContenido();
            procesadorxml.setContentHandler(gestorContenido);
            InputSource is = new InputSource(new FileInputStream(filexml));
            procesadorxml.parse(is);
            Utils.pulsaEnter();

        }catch (Exception e){
            Utils.pulsaEnter("Hay errores en el archivo");
        }
    }

    private static void crearElemento(String nombreEtiqueta, String value, Element etiqueta, Document document) {
        Element elem = document.createElement(nombreEtiqueta);
        etiqueta.appendChild(elem);
        if (value != null || !value.isEmpty()){
            Text text = document.createTextNode(value);
            elem.appendChild(text);
        }
    }

    private static void mostrarMenu() {
        Utils.limpiarPantalla();
        System.out.println("""
                Bienvendo al registro de heroes.
                ====================================
                
                Aqui tienes varias opciones.
                1. Generar un archivo XML
                2. Leer el archivo XML
                3. Parsear a SAX el archivo XML utilizando manejador por defecto
                4. Parsear a SAX el archivo XML utilizando manejador propio
                
                0. Salir del programa
                
                """);
    }


    private static void ingresarDatosIniciales(File archivo) {
        int cantdadDatosIniciales = 3;
        System.out.printf("""
                El archivo no existe o peor ESTA VACIO!
                Vamos a generar %d registros.
                """, cantdadDatosIniciales);
        System.out.println(archivo.getPath());
        try(RandomAccessFile file = new RandomAccessFile(archivo, "rw")){
            for (int i = 0; i < cantdadDatosIniciales; i++){
                //Preparamos la id del usuario
                int id = i+1;
                //Pedimos los datos de los heroes iniciales
                String nombre = Utils.pedirPorTeclado("Introduce el nombre el heroe: ");
                if (nombre.length()<TAM_NOMBRE){
                    for (int j = nombre.length(); j<=10; j++) nombre += " ";
                }
                String poder = Utils.pedirPorTeclado("Introduce el superpoder: ");
                if (poder.length()<TAM_POWER){
                    for (int j = poder.length(); j<=20; j++) poder += " ";
                }
                String identidad = Utils.pedirPorTeclado("Introduce el identidad secreta: ");
                if (identidad.length()<TAM_IDENT){
                    for (int j = identidad.length(); j<=10; j++) identidad += " ";
                }
                //Pedimos un boolean de si esta vivo o no nuestro heroe
                boolean vivo = Utils.pedirPorTecladoSN("Esta vivo(S/N)");

                //Guardamos en fichero.
                file.writeInt(id);
                StringBuffer buffer = new StringBuffer(nombre);
                buffer.setLength(TAM_NOMBRE);
                file.writeChars(buffer.toString());
                buffer = new StringBuffer(poder);
                buffer.setLength(TAM_POWER);
                file.writeChars(buffer.toString());
                buffer = new StringBuffer(identidad);
                buffer.setLength(TAM_IDENT);
                file.writeChars(buffer.toString());
                file.writeBoolean(vivo);
                System.out.println("Vamos a introducir a los familiares. Por favor sigue este patron sin comillas: 'Nombre Completo (Relacion)' ");
                for (int j = 0; j <3 ; j++){
                    String nombreFamiliar = Utils.pedirPorTeclado("Introduce el nombre del familiar numero "+ (j+1)+ ": ");
                    if (nombreFamiliar.length()<TAM_FAMILIAR){
                        for (int k = nombreFamiliar.length(); k<=10; k++) nombreFamiliar += " ";
                    }
                    buffer = new StringBuffer(nombreFamiliar);
                    buffer.setLength(TAM_FAMILIAR);
                    file.writeChars(buffer.toString());
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}


class GestorContenido extends DefaultHandler {
    public GestorContenido() {
        super();
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Comienzo del XML");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Fin del XML");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("Principio del Elemento " + qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("Fin del Elemento " + qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String car = new String(ch, start, length);
        car = car.replaceAll("[\t\n]", "");
        if (!car.isEmpty()) System.out.println('\t'+ car);
    }
}

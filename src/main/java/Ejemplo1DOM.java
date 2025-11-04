import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejemplo1DOM {
    static final int TAM_NOMBRE = 15;
    static final int TAM_POWER = 20;
    static final int TAM_IDENT = 15;
    public static void main(String[] args) {
        File fichero = new File("/home/dondcas-ubuntu/Escritorio/2DAM/Acceso_a_Datos/Pruebas/Marvel.txt");
        try (RandomAccessFile file = new RandomAccessFile(fichero, "r")){
            int id, posicion = 0;
            char nombre[] = new char[TAM_NOMBRE], aux;
            char poder[] = new char[TAM_POWER];
            char identidad[] = new char[TAM_IDENT];
            boolean vivo = true;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try{
                DocumentBuilder builder = factory.newDocumentBuilder();
                DOMImplementation implementation  = builder.getDOMImplementation();
                Document document = implementation.createDocument(null,"MarvelXML",null);
                document.setXmlVersion("1.0");
                while(file.getFilePointer() != file.length()){
                    file.seek(posicion);
                    id = file.readInt();
                    for(int i = 0; i < TAM_NOMBRE; i++){
                        aux = file.readChar();
                        nombre[i] = aux;
                    }
                    String nombreString = new String(nombre);
                    for(int i = 0; i < TAM_POWER; i++){
                        aux = file.readChar();
                        poder[i] = aux;
                    }
                    String poderString = new String(poder);
                    for(int i = 0; i < TAM_IDENT; i++){
                        aux = file.readChar();
                        identidad[i] = aux;
                    }
                    String identidadString = new String (identidad);
                    vivo = file.readBoolean();
                    if (id>0){
                        Element raiz =  document.createElement("Heroe");
                        document.getDocumentElement().appendChild(raiz);
                        CrearElemento("id", Integer.toString(id),raiz,document);
                        CrearElemento("nombre", nombreString.trim(), raiz, document);
                        CrearElemento("SuperPoder", poderString.trim(), raiz, document);
                        CrearElemento("identidadSecreta", identidadString.trim(), raiz, document);
                        CrearElemento("EstaVivo", (vivo ? "Esta Vivo" : "Esta Morido"), raiz, document);
                    }
                    posicion += (4 + (TAM_IDENT+TAM_NOMBRE+TAM_POWER)*2 + 1);
                }
                Source source = new DOMSource(document);
                Result result = new StreamResult(new java.io.File("/home/dondcas-ubuntu/Escritorio/2DAM/Acceso_a_Datos/Pruebas/Marvel.xml"));
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.transform(source, result);
            } catch (ParserConfigurationException e) {
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
    static void CrearElemento(String datoSuper, String value, Element raiz, Document document){
        Element elem = document.createElement(datoSuper);
        Text text = document.createTextNode(value);
        raiz.appendChild(elem);
        elem.appendChild(text);
    }
}

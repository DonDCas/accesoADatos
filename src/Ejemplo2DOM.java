import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Ejemplo2DOM {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("/home/dondcas-ubuntu/Escritorio/2DAM/Acceso_a_Datos/Pruebas/Marvel.xml"));
            document.getDocumentElement().normalize();

        System.out.printf("Elemento raiz: %s %n",
                document.getDocumentElement().getNodeName());
        //Crear una lista con todos los nodos Heroe
        NodeList heroes = document.getElementsByTagName("Heroe");
        System.out.printf("Nodos Heroe a recorrer: %d %n",heroes.getLength());
        //Recorremos la lista
        for(int i = 0; i < heroes.getLength(); i++){
            Node heroe = heroes.item(i);
            if (heroe.getNodeType() == Node.ELEMENT_NODE){
                Element elemento = (Element) heroe;
                System.out.printf("ID = %s - Nombre = %s - Poder = %s - Identidad = %s - Estado = %s %n",
                        elemento.getElementsByTagName("id").item(0).getTextContent(),
                        elemento.getElementsByTagName("nombre").item(0).getTextContent(),
                        elemento.getElementsByTagName("SuperPoder").item(0).getTextContent(),
                        elemento.getElementsByTagName("identidadSecreta").item(0).getTextContent(),
                        elemento.getElementsByTagName("EstaVivo").item(0).getTextContent());
            }
        }
    }
}

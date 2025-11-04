import jakarta.xml.bind.JAXBContext;

import java.util.ArrayList;

import jakarta.xml.bind.*;

public class Ejemplo1Marshalling {
    private static final String RUTA_ARCHIVO = "D:\\2DAM\\AccesoADatos\\libros.xml";
    public static void main(String[] args) {
        //Creamos la lista de libros que van a pertenecer al XML
        ArrayList<Libro> listaLibros = new ArrayList<>();
        Libro libro = new Libro("Alicia Ramos", "Entornos de Desarrollo", "Edelvives", "978-84-1545-297-3");
        listaLibros.add(libro);
        libro = new Libro("Maria Jesús Ramos", "Acceso a Datos", "Ra-Ma", "978-84-1750-557-5");
        listaLibros.add(libro);
        libro = new Libro("Antonio del Olmo", "Base de Datos", "Sintagma", "888-84-1750-557-5");
        listaLibros.add(libro);

        //Creamos la libreria y le asignamos la lista de libros
        Libreria milibreria = new Libreria("Libreria Martos con JAXB", "Martos", listaLibros);

        //Empieza la gracia. Creamos el contexto indicando la clase Raiz
        try {
            JAXBContext context = JAXBContext.newInstance(Libreria.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        System.out.println(milibreria.getLibros());
    }


}
class Libro {
    private String nombreAutor;
    private String titutlo;
    private String editorial;
    private String isbn;

    public Libro(String nombreAutor, String titutlo, String editorial, String isbn) {
        this.nombreAutor = nombreAutor;
        this.titutlo = titutlo;
        this.editorial = editorial;
        this.isbn = isbn;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public String getTitutlo() {
        return titutlo;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getIsbn() {
        return isbn;
    }
}

class Libreria{
    private String nombre;
    private String lugar;
    private ArrayList<Libro> libros;

    public Libreria(String nombre, String lugar, ArrayList<Libro> libros) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }
}

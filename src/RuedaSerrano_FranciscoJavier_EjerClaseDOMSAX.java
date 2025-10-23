import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class RuedaSerrano_FranciscoJavier_EjerClaseDOMSAX {
    static final int TAM_NOMBRE = 15;
    static final int TAM_POWER = 20;
    static final int TAM_IDENT = 15;
    static final int TAM_FAMILIAR = 35;
    public static void main(String[] args) {
        String ruta = "/home/dondcas-ubuntu/Escritorio/2DAM/Acceso_a_Datos/Pruebas/Marvel2.txt";
        File archivo = new File(ruta);
        if (!archivo.exists() || archivo.length() == 0) {
            ingresarDatosIniciales(archivo);
        }else Utils.pulsaEnter("Fichero cargado, pulsa enter. Pulsa enter");

        menuCRUD(archivo);
    }

    private static void menuCRUD(File archivo) {
        int op = -1;
        do{
            mostrarMenu();
            op = Utils.convertirAInt(Utils.pedirPorTeclado("Introduce una opción: "));
            switch (op){
                -1 => System.out.println("ERROR: OPCIÓN NO VALIDA");
                case 0: System.out.println("ADIOS VUELVA PRONTO! ono");
                case 1: generarXML(archivo);
                default: System.out.println("Opción erronea");
            };
        }while(op != 0);
    }

    private static void generarXML(File archivo) {
        System.out.println("Generando XML...");
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
                    String nombreFamiliar = Utils.pedirPorTeclado("Introduce el nombre del familiar numero "+ (j+1)+ ":");
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

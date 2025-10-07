import java.io.*;
import java.util.Scanner;

public class RuedaSerrano_FranciscoJavier_Actividad5 {


    final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        String ruta = "";
        if (args.length > 0) {
            ruta = args[0];
        } else{
            ruta = pedirPorTeclado("Introduce la ruta del archivo a comprobar: ");
        }
        if (ruta.equals("")){
            System.out.println("No existe el archivo a comprobar");
        }else{
            File archivo = new File(ruta);
            if (!archivo.exists()){
                System.out.println("No existe el archivo a comprobar");
            }else{
                if (archivo.isDirectory()) System.out.println("Se introdujo un directorio, no un archivo.");
                else{
                    String stringDeBusqueda = pedirPorTeclado("Introduce el criterio de Busqueda (una sola letra): ");
                    stringDeBusqueda = stringDeBusqueda.trim();
                    if (stringDeBusqueda.length() > 1 || stringDeBusqueda.length() < 1){
                        System.out.println("No existe criterio de busqueda.");
                    }else{
                        char criterioBusqueda = stringDeBusqueda.charAt(0);
                        buscarEnFichero(archivo, criterioBusqueda);
                    }
                }
            }
        }
    }

    private static void buscarEnFichero(File archivo, char criterioBusqueda) {
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            int cuentaLineas = 1;
            do{
                linea = br.readLine();
                if (linea != null) {
                    String[] palabras = linea.split("[\\s,!.]+");
                    for(String palabra : palabras){
                        if (palabra.toLowerCase().startsWith(String.valueOf(criterioBusqueda)) ||
                                palabra.toUpperCase().startsWith(String.valueOf(criterioBusqueda))){
                            System.out.println("Se ha encontrado \"" + palabra + "\" en la linea " + cuentaLineas +
                                    " en la columna " + linea.indexOf(palabra));
                        }
                    }
                    cuentaLineas++;
                }
            } while (linea != null);
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String pedirPorTeclado (String mensaje){
        System.out.print(mensaje);
        return sc.nextLine();
    }
}

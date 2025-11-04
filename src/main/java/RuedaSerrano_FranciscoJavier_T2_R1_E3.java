import java.io.File;
import java.util.Scanner;

public class RuedaSerrano_FranciscoJavier_T2_R1_E3 {

    /*
    *
    * */
    final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        String ruta = ".";

        if (args.length <= 0) ruta = pedirPorTeclado("Introduce una ruta para ver sus archivos: ");
        else ruta = args[0];
        String rutaAdaptada = adaptarRuta(ruta);

        if (ruta.equalsIgnoreCase(rutaAdaptada)) {
            System.out.println("Escribiste correctamente la ruta.");
        }else{
            System.out.println("Hemos tenido que adaptar la ruta. Tenias que escribirla asi: " + rutaAdaptada);
        }
    }

    public static String adaptarRuta(String ruta) {
        // Reemplazamos cualquier separador posible con el del sistema actual
        return ruta.replace("\\", File.separator)
                .replace("/", File.separator);
    }

    public static String pedirPorTeclado(String mensaje){
        System.out.print(mensaje);
        return sc.nextLine();

    }
}

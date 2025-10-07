import java.io.File;
import java.util.Scanner;

public class RuedaSerrano_FranciscoJavier_T2_R1_E1 {

    /*
    *
    * */
    final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        String ruta = ".";

        if (args.length <= 0) ruta = pedirPorTeclado("Introduce una ruta para ver sus archivos: ");
        else ruta = args[0];

        File dir =  new File(ruta);
        if (!dir.exists() ||  !dir.isDirectory()) {
            System.out.println("La ruta no existe o no es un directorio.");
        } else{
            File[] lista = dir.listFiles();
            for (File file : lista) {
                if (file.isFile()) System.out.println(file.getName());
            }
        }

    }
    public static String pedirPorTeclado(String mensaje){
        System.out.print(mensaje);
        return sc.nextLine();

    }
}

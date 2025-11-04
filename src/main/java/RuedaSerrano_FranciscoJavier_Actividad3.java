import java.io.File;
import java.util.Scanner;

public class RuedaSerrano_FranciscoJavier_Actividad3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce una ruta del archivo a modificar: ");
        String ruta = sc.nextLine();
        File f = new File(ruta);
        if (!f.exists()) {
            System.out.println("El archivo no existe");
        }
        if (f.renameTo(new File(f.getParentFile() ,f.getName().substring(0,f.getName().lastIndexOf('.'))))){
            System.out.println("Se modifico el archivo "+ruta);
        }else  {
            System.out.println("No se modifico el archivo "+ruta);
        }

    }
}

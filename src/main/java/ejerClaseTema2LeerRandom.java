import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class ejerClaseTema2LeerRandom {

    final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String ruta = "";
        if  (args.length > 0) ruta = args[0];
        else ruta = pedirPorTeclado("Introduce la ruta al fichero que deseas leer");
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta, "rw");
            String apellido[] = {"Alvarez", "Rueda", "Montiel", "Lopéz", "Reinoso"};
            int dep[] = {10,20,10,30,20};
            Double salario[] = {100.45, 2400.60, 3000.99, 1500.56, 2100.0};

            StringBuffer buffer = null;
            int n = apellido.length;
            for(int i = 0; i < n; i++){
                archivo.writeInt(i+1);

                buffer = new StringBuffer(apellido[i]);
                buffer.setLength(10);
                archivo.writeChars(buffer.toString());
                archivo.writeInt(dep[i]);
                archivo.writeDouble(salario[i]);
            }
            archivo.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static String pedirPorTeclado(String mensaje){
        if(!mensaje.endsWith(": ")) mensaje += ": ";
        System.out.print(mensaje);
        return sc.nextLine();
    }
}

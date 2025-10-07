import java.io.*;
import java.util.Scanner;

public class RuedaSerrano_FranciscoJavier_Actividad6 {
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
                else codificarArchivos(archivo);
            }
        }
    }

    private static void codificarArchivos(File archivo) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(archivo), "UTF-8")
            );
            BufferedWriter bwISO = new BufferedWriter(
              new OutputStreamWriter(new FileOutputStream(archivo.getParent()+"\\salidaISO.txt"), "ISO-8859-1")
            );
            BufferedWriter bwUTF16 = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(archivo.getParent()+"\\salidaUTF16.txt"), "UTF-16")
            );
            String linea = "";
            while ((linea = br.readLine()) != null){
                bwISO.write(linea);
                bwISO.newLine();

                bwUTF16.write(linea);
                bwUTF16.newLine();
            }
            br.close();
            bwISO.close();
            bwUTF16.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
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

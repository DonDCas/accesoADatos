import java.io.*;

public class RuedaSerrano_FranciscoJavier_EjerClaseData {
    public static void main(String[] args) {

        String ruta = "/home/dondcas-ubuntu/Escritorio/2DAM/Acceso a Datos/Pruebas";
        try {
            String nombre = "Javi", apellido = "Rueda";
            int edadInt = 32;
            DataOutputStream archivo = new DataOutputStream(new FileOutputStream(ruta + "/pruebaTexto.txt", true));

            archivo.writeChars("\n" + nombre + "\n");
            archivo.writeChars("\n" + apellido + "\n");

            String edad = String.valueOf(edadInt);
            archivo.writeChars("\n" + edad + "\n");

            archivo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{

            DataInputStream archivoLectura = new DataInputStream(new FileInputStream(ruta+"/pruebaTexto.txt"));
            while(archivoLectura.available() > 0){
                char c = archivoLectura.readChar();
                System.out.print(c);
            }
            archivoLectura.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}



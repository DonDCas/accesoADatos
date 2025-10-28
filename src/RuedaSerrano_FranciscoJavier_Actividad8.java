import java.io.*;

public class RuedaSerrano_FranciscoJavier_Actividad8 {
    public static void main(String[] args) {
        String ruta1="D:\\2DAM\\AccesoADatos\\Archivo1.txt";
        String ruta2="D:\\2DAM\\AccesoADatos\\Archivo2.txt";

        File file1 = new File(ruta1);
        File file2 = new File(ruta2);
        if (compareContenidoFicheros(file1, file2)) System.out.println("Los archivos son iguales");
        else System.out.println("Los archivos no son para nada iguales no te rayes");
    }

    private static boolean compareContenidoFicheros(File file1, File file2) {
        try (FileInputStream fis1 = new FileInputStream(file1);
             FileInputStream fis2 = new FileInputStream(file2)) {
            do {
                if (fis1.read() != fis2.read()) return false;
            }while (fis1.read() != -1 && fis2.read() != -1);
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

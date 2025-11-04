import java.io.File;
import java.io.IOException;

public class RuedaSerrano_FranciscoJavier_Actividad2 {
    public static void main(String[] args) throws IOException {
        String ruta = ".";
        if (args.length > 0) ruta = args[0];
        else ruta = "C:\\Temp";
        File fich = new File(ruta);
        if (!fich.exists()) fich.mkdir();
        File media = new File(ruta, "Media");
        if (!media.exists()) media.mkdir();
        File fotos = new File(ruta, "Fotos");
        if (!fotos.exists()) fotos.mkdir();
        File documentos = new File(fotos, "Documentos.txt");
        if (!documentos.exists()) documentos.createNewFile();
        File fotostxt = new File(fotos, "Fotos.txt");
        if (!fotostxt.exists()) fotostxt.createNewFile();

        File fotografia = new File(media, "Fotografia");
        if (fotos.renameTo(fotografia))
            System.out.println("Se movio la carpeta fotos a "+fotografia);
        else System.out.println("No se pudo mover la carpeta fotos a "+fotografia);
        if (!documentos.exists()) {
            documentos = new File(fotografia,"Documentos.txt");
        }

        if (documentos.renameTo(new File(ruta, "Documentos.txt")))
            System.out.println("Se movio el archivo Documentos.txt a "+ruta+"\\Documentos.txt");
        else
            System.out.println("No se pudo mover el archivo Documentos.txt");
    }
}

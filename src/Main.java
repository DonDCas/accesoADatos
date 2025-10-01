import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        /*String ruta = ".";
        if(args.length>=1) ruta=args[0];
        File fich = new File(ruta);
        if (!fich.exists()) System.out.println("No existe el fichero o directorio ("+ruta+")");
        else{
            if(fich.isFile()) System.out.println(ruta+" es un fichero.");
            else{
                System.out.printf(ruta+ " es un directorio. Contenidos: ");
                File[] ficheros = fich.listFiles();
                for (File f :  ficheros){
                    String textoDescr = descripcionTexto(f);
                    if (f.isFile()) {
                        long tamanio = f.length() / 1024 ;
                        textoDescr += tamanio + " KiB - ";
                    }
                    String fecha[] = String.valueOf(new Date(f.lastModified())).split(" ");
                    String lastModif = fecha[2]+"/"+fecha[1]+"/"+fecha[5];
                    textoDescr += lastModif;
                    System.out.println("(" + textoDescr + ") - " + f.getName());
                }
            }
        }
    }

    public static String descripcionTexto(File f){
        String textoDescr = addDirOrFile(f);
        textoDescr += addPermisos(f);
        try {
            textoDescr += addOwner(f);
        } catch (IOException e) {
            return "FALLO AL RECOGER DATOS DEL ARCHIVO";
        }
        if (f.isFile()) {
            textoDescr += addTamanio(f);
            long tamanio = f.length() / 1024 ;
            textoDescr += tamanio + " KiB - ";
        }

        return textoDescr;
    }

    private static String addTamanio(File f) {
        long tamanio = f.length();
        while (tamanio > 1024) {
            if(tamanio >)
        }
        textoDescr += tamanio + " KiB - ";
    }

    private static String addOwner(File f) throws IOException {
        return " - " + Files.getOwner(Path.of(f.getPath())) + " - ";
    }

    private static String addPermisos(File f) {
        String textoDescr = "";
        textoDescr += " " + (f.canRead() ? "r" : "-");
        textoDescr += f.canWrite() ? "w" : "-";
        textoDescr += f.canExecute() ? "x" : "-";
        return textoDescr;
    }

    private static String addDirOrFile(File f) {return f.isDirectory() ? "d" : f.isFile() ? "-" : "?";}
*/}
}
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class RuedaSerrano_FranciscoJavier_Actividad1 {
    public static void main(String[] args) {
        String ruta = ".";
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
        }
        textoDescr += addLastModif(f);
        return textoDescr;
    }

    private static String addDirOrFile(File f) {return f.isDirectory() ? "d" : f.isFile() ? "-" : "?";}

    private static String addPermisos(File f) {
        String textoDescr = "";
        textoDescr += " " + (f.canRead() ? "r" : "-");
        textoDescr += f.canWrite() ? "w" : "-";
        textoDescr += f.canExecute() ? "x" : "-";
        return textoDescr;
    }

    private static String addOwner(File f) throws IOException {
        return " - " + Files.getOwner(Path.of(f.getPath())) + " - ";
    }

    private static String addTamanio(File f) {
        int contConversion = 0;
        double tamanio = f.length();
        while (tamanio > 1024) {
            tamanio /= 1024.0;
            contConversion++;
        }

        String unidad =  switch (contConversion){
            case 0 -> " B";
            case 1 -> " KiB";
            case 2 -> " MiB";
            case 3 -> " GiB";
            default -> " TiB";
        };

        return (contConversion == 0) ? tamanio + unidad + " -"
                : String.format("%.2f%s -", tamanio, unidad);

    }

    private static String addLastModif(File f) {
        String fecha[] = String.valueOf(new Date(f.lastModified())).split(" ");
        String lastModif = fecha[2]+"/"+fecha[1]+"/"+fecha[5];
        return  lastModif;
    }

}
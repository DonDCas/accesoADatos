import java.io.File;
import java.io.IOException;

public class RuedaSerrano_FranciscoJavier_actividadClase3009 {
    public static void main(String[] args) throws IOException {
        String rutaIncial = "C:\\";
        if (args.length > 0) rutaIncial = args[0];
        File direDocumentos = new File(rutaIncial, "Documentos2");
        if (!direDocumentos.exists()) direDocumentos.mkdir();
        File direUsuario1 = new File(direDocumentos, "Usuario1");
        if (!direUsuario1.exists()) direUsuario1.mkdir();
        File f1 = new File(direUsuario1, "F1");
        if (!f1.exists()) f1.createNewFile();
        File direUsuario2 = new File(direDocumentos, "Usuario1");
        if (!direUsuario2.exists()) direUsuario2.mkdir();
        File d1 = new File(direUsuario2, "d1");
        if (!d1.exists()) d1.createNewFile();
        File f2 = new File(direUsuario1, "F2");
        if (!f2.exists()) f2.createNewFile();
        File d2 = new File(direUsuario2, "d2");
        if (!d2.exists()) d2.createNewFile();
        File f3 = new File(direUsuario1, "F3");
        if (!f1.exists()) f3.createNewFile();
        File f4 = new File(direUsuario1, "F4");
        if (!f4.exists()) f4.createNewFile();
        File d3 = new File(direUsuario1, "d3");
        if (!d3.exists()) d3.mkdir();

      /*
        Esto ya era probando otra forma de hacerlo
        String rutaCompleta = "\\home\\dondcas-ubuntu\\Documentos2\\Usuario2\\d2\\d3";
        String chopRutaCompleta[] = rutaCompleta.split("\\\\");
        File f = null;
        for (String ruta : chopRutaCompleta) {
            if ( f == null) f = new File(ruta);
            else f = new File(f,ruta);
                if (!f.exists()){
                    f.mkdir();
                    if (ruta.equals("d2")){
                        File fContains = new File(f,"f3");
                        fContains.createNewFile();
                        fContains = new File(f,"f4");
                        fContains.createNewFile();
                }
            }
        }
        rutaCompleta = ""*/

    }
}

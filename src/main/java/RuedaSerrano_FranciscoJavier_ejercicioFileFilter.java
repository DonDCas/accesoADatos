import java.io.File;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RuedaSerrano_FranciscoJavier_ejercicioFileFilter {

    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String ruta = "";
        //Se le puede pasar una ruta por argumentos y sino la puedes introducir aqui:
        if (args.length < 1) ruta = pedirPorTeclado("Introduce una ruta para ver sus archivos: ");
        else ruta = args[0];
        File dir =  new File(ruta);
        int op=-1;
        do{
            try{
                op = elegirOpcion("""
                    Elige una de las siguientes opciones:
                    1- Listar entre los ficheros los que empiecen con una letra determinada.
                    2- Listar ficheros de un tamaño superior al indicado.
                    3- Listar ficheros por fecha anterior a una indicada.
                    
                    0- Salir
                    Opción deseada: """);
            } catch (NullPointerException e) { op = Integer.parseInt(mensajeError(e, ""));}
            switch (op) {
                case 1 -> listarFicherosStarWiths(dir);
                case 2 -> listarFicherosTamanio(dir);
                case 3 -> listarFicherosDate(dir);
                case 0 -> System.out.println("Adios.");
                default -> System.out.println( op != -1 ? "ERROR AL ELEGIR OPCIÓN" : "");
            }
        }while(op<0 || op >4);

    }

    //Devuelve el numero de conversiones que habra que realizar para pasar el tamaño deseado a Bytes.
    //Si la decisión no es correcta se devuelve -1

    //Función para filtrar ficheros pidiendo al usuario la letra
    private static void listarFicherosStarWiths(File dir) {
        String stringletter = "";
        char letterFilter = ' ';
        do{
            stringletter = pedirPorTeclado("Introduce la letra que quieres para buscar: ");
            stringletter = stringletter.trim();
            if (stringletter.length() == 1) letterFilter = stringletter.charAt(0);
        }while (letterFilter == ' ');
        FilenameFilter filter = new  FilterByLetter(letterFilter);
        File[] files = dir.listFiles(filter);
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    private static void listarFicherosTamanio(File dir) {
        String stringSize = "";
        long sizeFilter = -1;
        do{
            stringSize = pedirPorTeclado("Introduce el tamaño de los archivos que quieres filtrar seguido de su unidad (ej: 2 GB): ");
            stringSize = stringSize.trim();
            String[] stringSizeArray = stringSize.split(" ");
            if (stringSizeArray.length == 2){
                int numConversiones = numConversiones(stringSizeArray[1]);
                try{
                    sizeFilter = Long.parseLong(stringSizeArray[0]);
                } catch (NumberFormatException e) {
                    mensajeError(e, "La longitud del archivo no es valido");
                }
                if (numConversiones > 0 && sizeFilter != -1) for (int i = 1; i <= numConversiones; i++) sizeFilter *= 1024;
                if (numConversiones == -1){
                    System.out.println("No se introdujo debidamente la unidad del tamaño de los archivos. FIJATE EN EL EJEMPLO");
                    sizeFilter = -1;
                }
            }else System.out.println("No se introdujo debidamente el tamaño de los archivos para filtrar. FIJATE EN EL EJEMPLO");
        }while (sizeFilter == -1);
        FilenameFilter filter = new  FilterBySize(sizeFilter);
        File[] files = dir.listFiles(filter);
        for (File file : files) {
            System.out.println(file.getName() + " - " + addTamanio(file));
        }
    }

    private static void listarFicherosDate(File dir) {
        String stringDate = "";
        Date dateFilter = null;
        do{
            stringDate = pedirPorTeclado("Introduce la fecha desde la que empezar a buscar (dd/MM/yyyy): ");
            stringDate = stringDate.trim();
            dateFilter = parseDate(stringDate);
        }while (dateFilter == null);
        FilenameFilter filter = new  FilterByDate(dateFilter);
        File[] files = dir.listFiles(filter);
        for (File file : files) {
            System.out.println(file.getName() + " - " + new Date(file.lastModified()));
        }
    }

    private static int numConversiones(String unidadTamanio) {
        return switch (unidadTamanio.charAt(0)){
            case 'B' -> 0;
            case 'K' -> 1;
            case 'M' -> 2;
            case 'G' -> 3;
            case 'T' -> 4;
            case 'P' -> 5;
            default -> -1;
        };
    }

    private static int elegirOpcion(String mensaje) {return Integer.parseInt(pedirPorTeclado(mensaje));}

    //Con este metodo se muestra por consola el error que pueda surgir durante un try... Catch...
    private static String mensajeError(Exception e, String mensaje) {
        if (e instanceof NumberFormatException){
            if (!mensaje.isEmpty()) System.out.println(mensaje);
            else System.out.println("ERROR AL INTRODUCIR UN NUMERO!!");
            return "-1";
        }
        if (e instanceof ParseException){
            if (!mensaje.isEmpty()) System.out.println(mensaje);
            else System.out.println("ERROR AL INTRODUCIR LA FECHA!!");
            return "-1";
        }
        return "";
    }

    public static String pedirPorTeclado(String mensaje){
        System.out.print(mensaje);
        return sc.nextLine();

    }

    //Metodo para parsear la fecha que se introduce a Date
    private static Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            mensajeError(e, "ERROR AL INTRODUCIR LA FECHA. REVISA EL FORMATO" );
        }
        return null;
    }

    //Filtrado por letra. Comprobamos que el archivo que llega al filtro empiece por la letra que sele pase
    //Empiece este archivo en mayusculas o minusculas
    private static class FilterByLetter implements FilenameFilter{
        private char letterFiltre;

        public FilterByLetter(char letterFiltre) {
            this.letterFiltre = letterFiltre;
        }

        @Override
        public boolean accept(File directory, String fileName) {
            return fileName.startsWith(String.valueOf(letterFiltre).toLowerCase())
                    || fileName.startsWith(String.valueOf(letterFiltre).toUpperCase());
        }
    }

    //Filtrado por fechas superiores a la que se le pase al constructor del filtro.
    private static class FilterByDate implements FilenameFilter{
        private Date dateFilter;

        public FilterByDate(Date dateFilter) {
            this.dateFilter = dateFilter;
        }

        @Override
        public boolean accept(File directory, String fileName) {
            return new File(directory, fileName).lastModified() >= dateFilter.getTime();
        }
    }

    //Filtrado por tamaño, primero hay que realizar la conversión del tamaño para fitlrar a Bytes
    private static class FilterBySize implements FilenameFilter{
        private long sizeFiltre;

        public FilterBySize(long sizeFiltre) {
            this.sizeFiltre = sizeFiltre;
        }

        @Override
        public boolean accept(File directory, String fileName) {
            return new File(directory, fileName).length() >= sizeFiltre;
        }
    }

    //Conversión de Bytes al tamaño correcto para mostrar por pantalla
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

}

import java.io.File;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class ejercicioFileFilter {

    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String ruta = "";
        if (args.length <= 1) ruta = pedirPorTeclado("Introduce una ruta para ver sus archivos: ");
        else ruta = args[0];
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
                case 1 -> listarFicherosStarWiths();
            /*    case 2 -> listarFicherosTamanio();
                case 3 -> listarFicherosDate();*/
                case 0 -> System.out.println("Adios.");
                default -> System.out.println( op != -1 ? "ERROR AL ELEGIR OPCIÓN" : "");
            }
        }while(op<0 || op >4);

    }

    private static void listarFicherosStarWiths() {
    }

    private static int elegirOpcion(String mensaje) {return Integer.parseInt(pedirPorTeclado(mensaje));}

    private static String mensajeError(Exception e, String mensaje) {
        if (e instanceof NumberFormatException){
            if (!mensaje.isEmpty()) System.out.println(mensaje);
            else System.out.println("ERROR AL INTRODUCIR UN NUMERO!!");
            return "-1";
        }
        return "";
    }

    public static String pedirPorTeclado(String mensaje){
        System.out.print(mensaje);
        return sc.nextLine();

    }

    class FilterByLetter implements FileNameFilter{
        private char letterFiltre;

        public FilterByLetter(char letterFiltre) {
            this.letterFiltre = letterFiltre;
        }

        @Override
        public boolean accept(File f) {}
    }

    class FilterByDate implements FileNameFilter{
        private Date dateFiltre;

        public FilterByDate(Date dateFiltre) {
            this.dateFiltre = dateFiltre;
        }

        @Override
        public boolean accept(File f) {}
    }

    class FilterBySize implements FileNameFilter{
        private long SizeFiltre;

        public FilterBySize(long sizeFiltre) {
            SizeFiltre = sizeFiltre;
        }

        @Override
        public boolean accept(File f) {}
    }

}

import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class RuedaSerrano_FranciscoJavier_EjercicioFicherosAccesoAleatorio {
    final static Scanner sc =  new Scanner(System.in);
    static final int TAM_NOMBRE = 15;
    static final int TAM_POWER = 20;
    static final int TAM_IDENT = 15;

    public static void main(String[] args) {
        String ruta = "";
        boolean out = false;
        if (args.length > 0) ruta = args[0];
        else ruta = pedirPorTeclado("Introduce la ruta al archivo");
        File archivo = new File(ruta);
        if (!archivo.exists() || archivo.length() == 0) {
            ingresarDatosIniciales(archivo);
        }else Utils.pulsaEnter("Fichero cargado, pulsa enter. Pulsa enter");
        int  opMenu=-1;
        do{
        mostrarMenu();
        try{
            opMenu = Integer.parseInt(pedirPorTeclado("Introduce la opción deseada"));
        }catch(NumberFormatException e){
            System.out.println("ERROR: Formato de dato no valido");
        }
        out = tratarOpcion(opMenu, archivo);
        if (out == false) Utils.pulsaEnter("Accion realizada.");
        else Utils.pulsaEnter("HASTA LA PROXIMA.");
        }while(!out);

    }

    private static void mostrarMenu() {
        Utils.limpiarPantalla();
        System.out.println("""
                Bienvendo al registro de heroes.
                ====================================
                
                Aqui tienes varias opciones.
                1. Leer el registro
                2. Modificar un registro existente.
                3. Añadir un nuevo registro
                4. Eliminar un registro
                
                """);
    }

    //En caso de que el fichero no exista o este vacio y haya que crearlo con este metodo ingresamos datos nuevos
    private static void ingresarDatosIniciales(File archivo) {
        int cantdadDatosIniciales = 3;
        System.out.printf("""
                El archivo no existe o peor ESTA VACIO!
                Vamos a generar %d registros.
                """, cantdadDatosIniciales);
        System.out.println(archivo.getPath());
        try(RandomAccessFile file = new RandomAccessFile(archivo, "rw")){
            for (int i = 0; i < cantdadDatosIniciales; i++){
                //Preparamos la id del usuario
                int id = i+1;
                //Pedimos los datos de los heroes iniciales
                String nombre = pedirPorTeclado("Introduce el nombre el heroe: ");
                if (nombre.length()<TAM_NOMBRE){
                    for (int j = nombre.length(); j<=10; j++) nombre += "*";
                }
                String poder = pedirPorTeclado("Introduce el superpoder: ");
                if (poder.length()<TAM_POWER){
                    for (int j = poder.length(); j<=20; j++) poder += "*";
                }
                String identidad = pedirPorTeclado("Introduce el identidad secreta: ");
                if (identidad.length()<TAM_IDENT){
                    for (int j = identidad.length(); j<=10; j++) identidad += "*";
                }
                //Pedimos un boolean de si esta vivo o no nuestro heroe
                boolean vivo = pedirPorTecladoSN("Esta vivo(S/N)");

                //Guardamos en fichero.
                file.writeInt(id);
                StringBuffer buffer = new StringBuffer(nombre);
                buffer.setLength(TAM_NOMBRE);
                file.writeChars(buffer.toString());
                buffer = new StringBuffer(poder);
                buffer.setLength(TAM_POWER);
                file.writeChars(buffer.toString());
                buffer = new StringBuffer(identidad);
                buffer.setLength(TAM_IDENT);
                file.writeChars(buffer.toString());
                file.writeBoolean(vivo);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static boolean tratarOpcion(int opMenu, File archivo) {
        //Todas las funciones retornan false para que el programa siga funcionando despues de terminar una acción
        //Esta hecho asi por si se añade una acción para que el usuario decida terminar el programa despues de terminar funcion
        switch (opMenu) {
            case 1: return leerRegistros(archivo);
            case 2: return modificarRegistro(archivo);
            case 3: return addRegistro(archivo);
            case 4: return borrarRegistro(archivo);
            case 0:  return true;
            case -1: return false;
            default: opcionNoValida("OPCIÓN NO VALIDA, volviendo al menu", false);
        };
        return false;
    }

    private static boolean borrarRegistro(File archivo) {
        int idABuscar = -1;
        if (!archivo.exists() && archivo.length() == 0){
            Utils.pulsaEnter("El archivo no existe");
            return false;
        }else{
            do{
                idABuscar = pedirId(archivo);
            }while(idABuscar == -1);
            if (idABuscar != 0){
                try (RandomAccessFile file = new RandomAccessFile(archivo, "rw")){
                    while(file.getFilePointer()< file.length()){
                        //Guardamos la posición en la que empieza cada nueva linea
                        long posicionInicio = file.getFilePointer();
                        int id = file.readInt();
                        if (id == idABuscar){
                            //Calculamos la posición del dato que vamos a modificar
                            file.seek(posicionInicio);
                            file.writeInt(-1);
                        }
                        file.skipBytes((TAM_NOMBRE + TAM_POWER + TAM_IDENT)*2+1);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        }
    }

    private static boolean addRegistro(File archivo) {
        try(RandomAccessFile file = new RandomAccessFile(archivo, "rw")){
            int idNuevo = ultimoId(file)+1;
            String nombreNuevo = pedirPorTeclado("Introduce el nombre el heroe: ");
            if (nombreNuevo.length()<TAM_NOMBRE) for (int i = nombreNuevo.length(); i < TAM_NOMBRE; i++) nombreNuevo += "*";
            String poderNuevo = pedirPorTeclado("Introduce su poder: ");
            if (poderNuevo.length()<TAM_POWER) for (int i = poderNuevo.length(); i < TAM_POWER; i++) nombreNuevo += "*";
            String identidadNueva = pedirPorTeclado("Introduce el identidad secreta: ");
            if (nombreNuevo.length()<TAM_IDENT) for (int i = identidadNueva.length(); i < TAM_IDENT; i++) nombreNuevo += "*";
            boolean vivo = pedirPorTecladoSN("Esta vivo(S/N)");

            //Nos posicionamos en la ultima posición escrita
            file.seek(file.length());

            file.writeInt(idNuevo);
            StringBuffer buffer = new StringBuffer(nombreNuevo);
            buffer.setLength(TAM_NOMBRE);
            file.writeChars(buffer.toString());
            buffer = new StringBuffer(poderNuevo);
            buffer.setLength(TAM_POWER);
            file.writeChars(buffer.toString());
            buffer = new StringBuffer(identidadNueva);
            buffer.setLength(TAM_IDENT);
            file.writeChars(buffer.toString());
            file.writeBoolean(vivo);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static int ultimoId(RandomAccessFile file) throws IOException {
        int ultimoIdLeido = 0;
        int aux = 0;
        while(file.getFilePointer() < file.length()){
            aux = file.readInt();
            if (aux != -1) {
                ultimoIdLeido = aux;
                file.skipBytes((TAM_POWER+TAM_IDENT+TAM_NOMBRE)*2+1);
            }else file.skipBytes((TAM_POWER+TAM_NOMBRE+TAM_IDENT)*2+1);
        }
        return ultimoIdLeido;
    }

    //Dependiendo del dato a modificar el tamaño es variable. Con esta función optendremos siempre el tamaño correcto dependiendo de la opción elegida
    private static int tamanioDato(int opAModif) {
        return switch (opAModif){
            case 1 -> TAM_NOMBRE;
            case 2 -> TAM_POWER;
            case 3 -> TAM_IDENT;
            case 4 -> 1;
            default -> -1;
        };
    }

    private static long calculaPosicion(int opAModif, long posicionInicio) {
        return switch (opAModif){
            case 1 -> posicionInicio + 4;
            case 2 -> posicionInicio + 4 + (TAM_NOMBRE * 2);
            case 3 -> posicionInicio + 4 + (TAM_NOMBRE * 2) + (TAM_POWER * 2);
            case 4 -> posicionInicio + 4 + (TAM_NOMBRE * 2) + (TAM_POWER * 2) + (TAM_IDENT * 2);
            default -> -1;
        };
    }

    private static int pedirOpModif() {
        System.out.print("""
                ¿Que quieres modificar?
                1- Nombre del super
                2- Poder
                3- Identidad
                4- Estado de vida
                """);
        try{
            return Integer.parseInt(pedirPorTeclado("Elige la opción deseada"));
        }catch(NumberFormatException e){
            throw new RuntimeException(e);
        }
    }

    private static int pedirId(File archivo) {
        if (archivo.exists() && archivo.canRead()) {
            try(RandomAccessFile file = new RandomAccessFile(archivo, "r")){
                while(file.getFilePointer()< file.length()){
                    long inicioLinea =  file.getFilePointer();
                    int id = file.readInt();
                    if (id != -1){
                        char[] nombre = new char[TAM_NOMBRE];
                        char aux;
                        for (int i = 0; i < TAM_NOMBRE; i++){
                            aux = file.readChar();
                            if (aux != '*') nombre[i] = aux;
                            else nombre[i] = ' ';
                        }
                        String linea = String.format("%d - %s", id, new String(nombre));
                        System.out.println(linea);
                        file.skipBytes((TAM_POWER+TAM_IDENT)*2+1);
                    }else file.skipBytes((TAM_POWER+TAM_IDENT+TAM_NOMBRE)*2+1);
                }
                System.out.println("Pulsa 0 para salir.");
                return Integer.parseInt(pedirPorTeclado("Escoge el Super que quieres modificar: "));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
        return -1;
    }

    private static boolean leerRegistros(File archivo) {
        try (RandomAccessFile file = new RandomAccessFile(archivo, "r")){
            while(file.getFilePointer()<file.length()){
                int id = file.readInt();
                if (id != -1){
                    char[] nombre = new char[TAM_NOMBRE];
                    char aux;
                    for (int i = 0; i < TAM_NOMBRE; i++){
                        aux = file.readChar();
                        if (aux != '*') nombre[i] = aux;
                        else nombre[i] = ' ';
                    }
                    char[] poder = new char[TAM_POWER];
                    for (int i = 0; i<TAM_POWER; i++){
                        aux = file.readChar();
                        if (aux != '*') poder[i] = aux;
                        else poder[i] = ' ';
                    }
                    char[] identidad = new char[TAM_IDENT];
                    for ( int i = 0; i < TAM_IDENT; i++){
                        aux = file.readChar();
                        if (aux != '*') identidad[i] = aux;
                        else identidad[i] = ' ';
                    }
                    boolean vivo = file.readBoolean();

                    String linea = String.format("ID: %d, Nombre: %s, Poder: %s, Identidad: %s, ¿Vivo?: %s \n", id, new String(nombre), new String(poder), new String(identidad), (vivo) ? "Esta vivo": "Esta desvivido");
                    System.out.print(linea);
                } else file.skipBytes((TAM_POWER+TAM_IDENT+TAM_NOMBRE)*2+1);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static boolean modificarRegistro(File archivo) {
        int idABuscar = -1;
        int opAModif = -1;
        String nuevoDato = "";
        if (!archivo.exists() && archivo.length() == 0){
            Utils.pulsaEnter("El archivo no existe");
            return false;
        }else{
            do{
                idABuscar = pedirId(archivo);
                if (idABuscar > 0){
                    opAModif = pedirOpModif();
                }
                if (idABuscar == 0) opAModif = 0;
                if (opAModif != 4 && opAModif != 0) nuevoDato = pedirPorTeclado("Introduce el nuevo dato a ingresas: ");
            }while(idABuscar == -1 || opAModif == -1);
            if (idABuscar != -1){
                try (RandomAccessFile file = new RandomAccessFile(archivo, "rw")){
                    while(file.getFilePointer()< file.length()){
                        //Guardamos la posición en la que empieza cada nueva linea
                        long posicionInicio = file.getFilePointer();
                        int id = file.readInt();
                        if (id == idABuscar){
                            //Calculamos la posición del dato que vamos a modificar
                            long posicionModif = calculaPosicion(opAModif, posicionInicio);
                            file.seek(posicionInicio);

                            //En caso de que se vayan a modificar datos STRING
                            if(opAModif != 4){
                                //Nos posicionamos en el dato a modificar
                                file.seek(posicionModif);
                                //Preparamos el buffer con el dato que vamos a modificar
                                for(int i = 0; i < tamanioDato(opAModif); i++) nuevoDato += "*";
                                StringBuffer buffer = new StringBuffer(nuevoDato);
                                //Indicamos el tamaño del dato que se va a modificar
                                buffer.setLength(tamanioDato(opAModif));
                                file.writeChars(buffer.toString());
                                System.out.println("Dato modificado");
                                return false;
                            }else{ //EN CASO DE QUE EL DATO A MODIFICAR SEA UN BOOLEAN
                                file.seek(posicionModif);
                                boolean valorActual = file.readBoolean();
                                file.seek(posicionModif);
                                file.writeBoolean(!valorActual);
                                System.out.println("Dato modificado");
                                return false;
                            }
                        }
                        file.skipBytes((TAM_NOMBRE + TAM_POWER + TAM_IDENT)*2+1);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        }

    }

    public static String pedirPorTeclado(String mensaje){
        System.out.print((mensaje.endsWith(": ") ? mensaje : mensaje+": "));
        return sc.nextLine();
    }

    private static boolean pedirPorTecladoSN(String mensaje) {
        do{
            String respuesta = pedirPorTeclado(mensaje);
            if (respuesta.length() == 1){
                if (respuesta.equalsIgnoreCase("s")) return true;
                if (respuesta.equalsIgnoreCase("n")) return false;
            }
        }while(true);
    }

    private static boolean opcionNoValida(String mensaje, boolean b) {
        System.out.println(mensaje);
        return b;
    }
}

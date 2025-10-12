package cliente;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import comun.DiaSemana;
import reservas.GestorReservas;
import reservas.Sesion;


public class ClienteSockets {

    // Sustituye esta clase por tu versión de la clase UsuarioLocal de la práctica 1

    // Modifícala para que instancie un objeto de la clase AuxiliarClienteSockets

    // Modifica todas las llamadas al objeto de la clase GestorReservas
    // por llamadas al objeto de la clase AuxiliarClienteSockets.
    // Los métodos a llamar tendrán la misma signatura.

	
     
    public static int menu(Scanner teclado) {
        int opcion;
        System.out.println("\n\n");
        System.out.println("=====================================================");
        System.out.println("============            MENU        =================");
        System.out.println("=====================================================");
        System.out.println("0. Salir");
        System.out.println("1. Listar las reservas");
        System.out.println("2. Listar plazas disponibles de una actividad");
        System.out.println("3. Hacer una reserva");
        System.out.println("4. Modificar una reserva");
        System.out.println("5. Cancelar una reserva");
        do {
            System.out.print("\nElige una opcion (0..5): ");
            opcion = teclado.nextInt();
        } while ( (opcion<0) || (opcion>5) );
        teclado.nextLine(); // Elimina retorno de carro del buffer de entrada
        return opcion;
    }

    /**
     * Programa principal. Muestra el menú repetidamente y atiende las peticiones del usuario.
     *
     * @param args	no se usan argumentos de entrada al programa principal
     * @throws IOException 
     * @throws UnknownHostException 
     * @throws SocketException 
     */
    public static void main(String[] args) {
    	
        Scanner teclado = new Scanner(System.in);
        String localhost = "localhost";
        String puerto = "12345";
        // Crea un gestor de reservas
        try {
        	AuxiliarClienteSockets cliente = new AuxiliarClienteSockets(localhost, puerto);
        

        System.out.print("Introduce tu código de usuario: ");
        String codUsuario = teclado.nextLine();

        int opcion;
        do {
            opcion = menu(teclado);
            switch (opcion) {
                case 0 -> { // Guardar los datos en el fichero y salir del programa

                    cliente.cierraSesion();
                    System.exit(0);

                }
                case 1 -> { // Listar los paquetes enviados por el cliente
                    JSONArray res = cliente.listaReservasUsuario(codUsuario);
                    if(res.isEmpty()) {
                    	System.out.print("El cliente no tiene reservas");
                    }
                    else {
                    	System.out.print(res);
                    }

                }
                case 2 -> { // Listar los plazas disponibles de una actividad
                	Scanner sc = new Scanner(System.in);
                	System.out.print("Dame el nombre de un actividad: ");
                	String act = sc.nextLine();
                	JSONArray res = cliente.listaPlazasDisponibles(act);
                	if (res.isEmpty()) {
                		System.out.print("No hay sesiones disponibles para esa actividad");
                	}
                	else {
                		System.out.print(res);
                	}    	

                }
                case 3 -> { // Hacer una reserva


                	System.out.println("Dame una actividad: ");
                    String actividad = teclado.nextLine();

                    System.out.println("Dame un dia (ejemplo: lunes, martes...): ");
                    DiaSemana dia = DiaSemana.leerDia(teclado);
                    
                    System.out.println("Dame una hora (número entero, ej: 9 o 18): ");
                    long hora = teclado.nextLong();
                   
                    
                    JSONObject nuevaReserva = cliente.hazReserva(codUsuario, actividad, dia, hora);

                    if (nuevaReserva.isEmpty()) {
                        System.out.println("No se pudo hacer la reserva (actividad inexistente o sin plazas).");
                    } else {
                        System.out.println("Reserva realizada con éxito: " + nuevaReserva.toJSONString());
                    }
                    
                    
                    



                }
                case 4 -> { // Cambiar de día y hora una reserva

                	System.out.println("Dame el código de una reserva: ");
                    long codReserva = teclado.nextLong();
                    teclado.nextLine();
                    
                    System.out.println("Nuevo día: ");
                    DiaSemana dia = DiaSemana.leerDia(teclado);
                    
                    System.out.println("Nueva hora: ");
                    long hora = teclado.nextLong();
                   
                    
                    JSONObject nuevaModificada = cliente.modificaReserva(codUsuario, codReserva, dia, hora);
                    
                    if (nuevaModificada.isEmpty()) {
                        System.out.println("No se pudo modificar la reserva.");
                    } else {
                        System.out.println("Reserva modificada con éxito: " + nuevaModificada.toJSONString());
                    }


                }
                case 5 -> { // Cancelar una reserva
                	
                	System.out.println("Dame el código de una reserva: ");
                    long codReserva = teclado.nextLong();
                    teclado.nextLine();
                    
                    JSONObject reservaCancelada = cliente.cancelaReserva(codUsuario, codReserva);
                    
                    if(reservaCancelada.isEmpty()) {
                    	System.out.println("No se pudo cancelar la reserva");
                    }else {
                    	System.out.println("Reserva cancelada con éxito: " + reservaCancelada.toJSONString());
                    }

                }

            } // fin switch

        } while (opcion != 0);
        }
        catch (SocketException ex) {
			System.out.println("Capturada SocketException");
		}
		catch (IOException ex) {
			System.out.println("Capturada IOException");
		}
		catch (Exception ex) {
			System.out.println("Exception caught in thread: " + ex);
		} // fin catch

    } // fin de main
} // fin class

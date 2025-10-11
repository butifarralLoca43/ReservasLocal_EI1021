package servidor;

import java.io.IOException;
import java.net.SocketException;

import gestor.GestorReservas;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import comun.DiaSemana;
import comun.MyStreamSocket;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del servicio de reservas.
 * El método run contiene la lógica para gestionar una sesión con un cliente.
 */

class HiloServidorSockets implements Runnable {


	final private MyStreamSocket myDataSocket;
	final private GestorReservas gestor;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 * @param	unGestor		gestor de viajes
	 */
	HiloServidorSockets(MyStreamSocket myDataSocket, GestorReservas unGestor) {
		this.myDataSocket = myDataSocket;
		this.gestor = unGestor;
	}

	/**
	 * Gestiona una sesión con un cliente
	 */
	public void run( ) {
		String operacion = "0";
		boolean done = false;
		// ...
		try {
			while (!done) {
				// Recibe una petición del cliente
				String peticion = myDataSocket.receiveMessage();
				// Extrae la operación y sus parámetros
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(peticion);
				operacion = json.get("operacion").toString();

				switch (operacion) {
					case "0":
						done = true;
						
						gestor.guardaDatos();
						
						JSONObject respuesta = new JSONObject();
						respuesta.put("resutado", "Sesión cerrada y datos guardados");
						myDataSocket.sendMessage(respuesta.toJSONString());
						
						break;
						

					case "1": { // Devuelve una lista de reservas de un usuario
						String codUsuario = json.get("codUsuario").toString();
						
						JSONArray arrayReservas = gestor.listaReservasUsuario(codUsuario);
						myDataSocket.sendMessage(arrayReservas.toJSONString());
						
						break;
					}
					case "2": { // Devuelve una lista de plazas disponibles de una actividad
						String actividad = json.get("actividad").toString();
						
						JSONArray arrayPlazas = gestor.listaPlazasDisponibles(actividad);
						myDataSocket.sendMessage(arrayPlazas.toJSONString());

						break;
					}
					case "3": { // Un usuario hace una reserva
						String codUsuario = json.get("codUsuario").toString();
						String actividad = json.get("actividad").toString();
						DiaSemana dia = DiaSemana.valueOf((String) json.get("dia"));
                        long hora = (long) json.get("hora");
						
						JSONObject reserva = gestor.hazReserva(codUsuario, actividad, dia, hora);
						myDataSocket.sendMessage(reserva.toString());
						
						break;
					}
					case "4": { // Un usuario modifica una de sus reservas
						String codUsuario = json.get("codUsuario").toString();
						long codReserva = (long) json.get("codReserva");
						DiaSemana dia = DiaSemana.valueOf((String) json.get("nuevoDia"));
						long hora = (long) json.get("nuevaHora");
						
						JSONObject reservaModificada = gestor.modificaReserva(codUsuario, codReserva, dia, hora);
						myDataSocket.sendMessage(reservaModificada.toString());
						
						break;
					}
					case "5": { // Un usuario cancela una de sus reservas
						String codUsuario = json.get("codUsuario").toString();
						long codReserva = (long) json.get("codReserva");
						
						JSONObject reservaCancelada = gestor.cancelaReserva(codUsuario, codReserva);
						myDataSocket.sendMessage(reservaCancelada.toString());

						break;
					}

				} // fin switch
			} // fin while   
		} // fin try
		catch (SocketException ex) {
			System.out.println("Capturada SocketException");
		}
		catch (IOException ex) {
			System.out.println("Capturada IOException");
		}
		catch (Exception ex) {
			System.out.println("Exception caught in thread: " + ex);
		} // fin catch
	} //fin run

} //fin class 

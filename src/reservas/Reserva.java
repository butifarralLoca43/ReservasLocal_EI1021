package reservas;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import org.json.simple.JSONObject;



/**
 * Clase que representa una reserva de una sesión de una actividad deportiva durante la próxima semana
 */
public class Reserva implements Serializable {
	
    @Serial
    private static final long serialVersionUID = 1L;

    private long codReserva;		// Código único de la reserva generado al crearla
    private String codUsuario;		// Código único del usuario que hace la reserva
    private String actividad;		// Nombre de la actividad deportiva (p.e. Taichí, Yoga, etc.)
    private DiaSemana dia;			// Día de la semana de la sesión reservada
    private long hora;				// Hora de la reserva (en formato 24h)

    /**
     * Constructor para crear una reserva.
     *
     * @param codUsuario Código del usuario que realiza la reserva.
     * @param actividad Actividad reservada.
     * @param dia Día de la reserva.
     * @param hora Hora de la reserva en formato 24 horas.
     */
    public Reserva(String codUsuario, String actividad, DiaSemana dia, long hora) {
        this.codReserva = generaCodReserva(); // El código de la reserva se genera al construirla
        this.codUsuario = codUsuario;
        this.actividad = actividad;
        this.dia = dia;
        this.hora = hora;
    }

    /**
     * Constructor para crear una reserva a partir de un objeto JSON.
     *
     * @param jsonReserva Objeto JSON con la información de la reserva.
     */
    public Reserva(JSONObject jsonReserva) {
        // POR IMPLEMENTAR
    }

    /**
     * Devuelve la representación JSON de la reserva.
     *
     * @return Objeto JSON con la información de la reserva.
     */
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("codReserva", this.codReserva);
        jsonObject.put("codUsuario", this.codUsuario);
        jsonObject.put("actividad", this.actividad);
        jsonObject.put("dia", this.dia.name());
        jsonObject.put("hora", this.hora);
        
        return jsonObject;
    }
    

	@Override
	public String toString() {
		return this.toJSON().toJSONString();
	}

	/*
	 * Genera el código 'unico' de la reserva como un entero aleatorio de 4 dígitos.
	 * Usar ThreadLocalRandom hace que sea seguro en entornos multihebra
	 */
    private long generaCodReserva() {
        return ThreadLocalRandom.current().nextLong(1000, 10000);
    }
    
	public long getCodReserva() {
		return codReserva;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public DiaSemana getDia() {
		return dia;
	}

	public void setDia(DiaSemana dia) {
		this.dia = dia;
	}

	public long getHora() {
		return hora;
	}

    public void setHora(long hora) {
        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException("La hora debe estar en el rango de 0 a 23.");
        }
        this.hora = hora;
    }

    // Getters y setters
    
    public static void main(String[] args) {
        System.out.println("--- INICIO DE PRUEBAS PARA LA CLASE RESERVA ---");

        // 1. Creamos una reserva usando el constructor principal.
        System.out.println("\n[Paso 1] Creando una reserva para 'cli01' de Yoga el JUEVES a las 18:00...");
        Reserva reservaOriginal = new Reserva("cli01", "Yoga", DiaSemana.jueves, 18);
        System.out.println("Reserva creada con código: " + reservaOriginal.getCodReserva());

        // 2. ✅ Prueba del método toJSON()
        System.out.println("\n[Paso 2] Probando el método toJSON()...");
        JSONObject jsonGenerado = reservaOriginal.toJSON();
        System.out.println("Salida JSON -> " + jsonGenerado.toJSONString());
        
        // La prueba de toString() es implícita, ya que llama a toJSON().
        System.out.println("Salida toString() -> " + reservaOriginal.toString());


        // 3. ✅ Prueba del constructor Reserva(JSONObject)
        System.out.println("\n[Paso 3] Probando el constructor que recibe un JSON...");
        System.out.println("Creando una nueva reserva a partir del JSON anterior...");
        Reserva reservaDesdeJSON = new Reserva(jsonGenerado);
        System.out.println("¡Nueva reserva creada con éxito!");
        
        // 4. Verificación de los datos
        System.out.println("\n[Paso 4] Verificando que los datos se han cargado correctamente:");
        System.out.println("  -> Código Usuario: " + reservaDesdeJSON.getCodUsuario() + " (Esperado: cli01)");
        System.out.println("  -> Actividad: " + reservaDesdeJSON.getActividad() + " (Esperado: Yoga)");
        System.out.println("  -> Día: " + reservaDesdeJSON.getDia() + " (Esperado: JUEVES)");
        System.out.println("  -> Hora: " + reservaDesdeJSON.getHora() + " (Esperado: 18)");
        System.out.println("  -> Código Reserva: " + reservaDesdeJSON.getCodReserva() + " (Esperado: " + reservaOriginal.getCodReserva() + ")");

        System.out.println("\n--- FIN DE LAS PRUEBAS ---");
    }
    
    


}

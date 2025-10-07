package gestor;

import org.json.simple.JSONObject;

/**
 * Representa una sesión de una actividad en un día y hora específicos.
 */
public class Sesion {
    
	// Sustituye esta clase por tu versión de la misma de la práctica 1
	private String actividad; // Nombre de la actividad
    private long hora;        // Hora de la sesión en formato 24h
    private long plazas;      // Número de plazas disponibles

    /**
     * Constructor para inicializar una sesión.
     * 
     * @param actividad Nombre de la actividad.
     * @param hora Hora de la sesión en formato 24 horas.
     * @param plazas Número de plazas disponibles.
     */
    public Sesion(String actividad, long hora, long plazas) {
        this.actividad = actividad;
        this.hora = hora;
        this.plazas = plazas;
    }
    
    /**
     * Devuelve un objeto JSON con la información de la sesión.
     * 
     * @return JSONObject con los detalles de la sesión.
     */
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
    	JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("actividad", this.actividad); 
        jsonObject.put("hora", this.hora);           
        jsonObject.put("plazas", this.plazas);       
        
        
        return jsonObject;
    }
    
    /**
     * Devuelve una representación como cadena de la sesión en formato JSON.
     * 
     * @return Cadena con los datos de la sesión en formato JSON.
     */
    @Override
    public String toString() {
        return this.toJSON().toJSONString();
    }


    public String getActividad() {
        return actividad;
    }


    public void setActividad(String actividad) {
        this.actividad = actividad;
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



    public long getPlazas() {
        return plazas;
    }


    public void setPlazas(long plazas) {
        if (plazas < 0) {
            throw new IllegalArgumentException("El número de plazas no puede ser negativo.");
        }
        this.plazas = plazas;
    }
    
    //MÉTODO MAIN TEMPORAL PARA PRUEBAS
    public static void main(String[] args) {
        System.out.println("--- Probando la clase Sesion ---");

        Sesion sesionDePrueba = new Sesion("Ironfit", 10, 15);
        
      
        System.out.println("\nProbando el método toJSON():");
        JSONObject objetoJSON = sesionDePrueba.toJSON();
        System.out.println("Salida JSON: " + objetoJSON.toJSONString());
        
        System.out.println("\n--- Fin de las pruebas ---");
    }
    
}

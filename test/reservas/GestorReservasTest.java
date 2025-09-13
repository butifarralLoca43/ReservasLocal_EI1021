package reservas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GestorReservasTest {

	@BeforeEach
	void setUp() {
		GestorReservas gestor = new GestorReservas();
	}

	@Test
	void testBuscaSesion() {
		Sesion encontrada = gestor.buscaSesion("Yoga", DiaSemana.LUNES, 10);
        assertNotNull(encontrada);
        assertEquals("Yoga", encontrada.getActividad());
        assertEquals(10, encontrada.getHora());
	}


}

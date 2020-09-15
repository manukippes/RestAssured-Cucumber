package ar.com.manutesting.servicios;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.log4j.Log4j;

@Log4j
public class ReporteHook {
	
	private String nombreEscenario;
	private String estadoEscenario;
	
	@Before
	public void iniciarEscenario(Scenario escenario) {
		nombreEscenario = escenario.getName().toUpperCase();
		log.info("<-- EL ESCENARIO: "+nombreEscenario+" COMENZO. -->");
	}


	@After
	public void finalizarEscenario(Scenario escenario) {
		try {
			nombreEscenario = escenario.getName().toUpperCase();
			estadoEscenario = escenario.getStatus().toString();
			if (escenario.isFailed()) 
				log.error("<-- EL ESCENARIO: "+ nombreEscenario+" FALLO. -->");
			if (estadoEscenario == "PASSED") {
				log.info("<-- EL ESCENARIO: "+ nombreEscenario+" FINALIZÓ CORRECTAMENTE. -->");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

}

package ar.com.manutesting.servicios;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.lessThan;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;

import ar.com.manutesting.utiles.PropertyManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j;

@Log4j
public class BusquedaProductos {

	private static RequestSpecification request;
	private static String criterioBusqueda;

	public void configuracionInicial() throws FileNotFoundException, IOException {
		baseURI = PropertyManager.getIntance().getUrl();
	}

	@Given("El servicio de busqueda de productos de mercado libre Argentina con el criterio de busqueda {string}")
	public void el_servicio_de_busqueda_de_productos_de_mercado_libre_Argentina_con_el_criterio_de_busqueda(String pCriterioBusqueda) throws FileNotFoundException, IOException {
		configuracionInicial();
		criterioBusqueda = pCriterioBusqueda;
		log.info("Ejecutando path: search con criterio de búsqueda " + criterioBusqueda);
		request = given()
					.log()
					.uri()
					.filter(new AllureRestAssured());
	}

	@Then("Nos arroja como resultado productos que coinciden con el criterio de busqueda")
	public void nos_arroja_como_resultado_productos_que_coinciden_con_el_criterio_de_busqueda() {
		JsonPath respuesta = request
								.when()
									.queryParam("q", criterioBusqueda.replace(" ", "%20"))
									.get()
								.then()
									.time(lessThan(10000L))
									.log().all()
									.assertThat()
									.statusCode(200)
									.contentType(ContentType.JSON)
									.statusLine(containsString("OK"))
									.extract().body().jsonPath();
		validarRespuesta(respuesta, criterioBusqueda);
	}

	private void validarRespuesta(JsonPath respuesta, String criterioBusqueda) {
		String[] palabrasDelCriterioBusqueda = criterioBusqueda.split(" ");
		String[] titulosDeCadaProductoDelResultado = respuesta.getString("results.title").split(",");
		
		log.info("El servicio nos arrojó "+titulosDeCadaProductoDelResultado.length+" resultados.");
		
		for (int i = 0; i < titulosDeCadaProductoDelResultado.length; i++) {
			for (int j = 0; j < palabrasDelCriterioBusqueda.length; j++) {
				Boolean expresionConPalabraInicialEnMayuscula = titulosDeCadaProductoDelResultado[i].contains(palabrasDelCriterioBusqueda[j].substring(0, 1).toUpperCase() + palabrasDelCriterioBusqueda[j].substring(1));
				Boolean expresionConPalabraInicialEnMinuscula = titulosDeCadaProductoDelResultado[i].contains(palabrasDelCriterioBusqueda[j].substring(0, 1).toLowerCase() + palabrasDelCriterioBusqueda[j].substring(1));
				Assert.assertTrue("El resultado "+titulosDeCadaProductoDelResultado[i]+" no coincide la palabra del criterio de busqueda: "+palabrasDelCriterioBusqueda[j], (expresionConPalabraInicialEnMayuscula || expresionConPalabraInicialEnMinuscula));
				
				log.info("El resultado "+titulosDeCadaProductoDelResultado[i]+" coincide la palabra del criterio de busqueda: "+palabrasDelCriterioBusqueda[j]);
			}
		}
	}
}

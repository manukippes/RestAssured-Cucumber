package ar.com.manutesting.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue="ar.com.manutesting.servicios",
        tags = {"@BusquedaProductos","@High"},
        plugin= {"json:target/cucumber.json", "junit:target/cucumber.xml", "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm"},
        monochrome=true,
        strict = true,
        dryRun = false
)


public class RunnerTest {
	@AfterClass
	public static void reportar() {
		File reportOutputDirectory = new File("target");
		List<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add("./target/cucumber.json");

		String buildNumber = "1";
		String projectName = "API";
	
		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
		configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
		configuration.setBuildNumber(buildNumber);
		configuration.addClassifications("API", "BusquedaProductos");
		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		reportBuilder.generateReports();
	}		
}

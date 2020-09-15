# Test-API-ML

Framework de pruebas sobre API de ML

Tecnologias utilizadas:
- Lenguaje: Java - Maven
- Librerias: RestAssured - Cucumber
- Reporte: Allure - Cucumber-reporting
- IDE: Eclipse
- SO: Windows 10

Configuración de ambiente:
1. Descargar y configurar variables de entorno de JAVA (JAVA_HOME y PATH): JDK_1.8.0
2. Para instalar Allure Report: <br>
a. Descargar scoop <br>
b. Instalar Allure: scoop install allure 

Ejecutar pruebas:
- mvn clean test (basica)
- mvn clean test -Dcucumber.options="--tags '@BusquedaProductos' --tags '@High'" (detallada)

Reporte Cucumber-reporting:
- \target\cucumber-html-reports\overview-features.html

Ejecutar reporte Allure desde la raiz de proyecto:
- allure serve -h 127.0.0.1 -p 8087 --> -h 127.0.0.1 -p 8087 son opcionales

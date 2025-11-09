package com.formacionbdi.springboot.app.productos;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//AEnableDiscoveryClient
@SpringBootApplication
public class SpringbootServicioProductosApplication {

	public static void main(String[] args) {
		// Configurar zona horaria antes de iniciar la aplicación
		TimeZone.setDefault(TimeZone.getTimeZone("America/Santiago"));
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}

}

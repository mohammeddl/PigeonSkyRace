package com.PigeonSkyRace.PigeonSkyRace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.PigeonSkyRace.PigeonSkyRace",
    "com.PigeonSkyRace.PigeonSkyRace.config",
    "com.PigeonSkyRace.PigeonSkyRace.service",
    "com.PigeonSkyRace.PigeonSkyRace.controller"
})
public class PigeonSkyRaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigeonSkyRaceApplication.class, args);
	}

}

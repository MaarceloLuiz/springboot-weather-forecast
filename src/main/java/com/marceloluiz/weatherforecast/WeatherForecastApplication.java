package com.marceloluiz.weatherforecast;

import com.marceloluiz.weatherforecast.services.ReadmeManagerService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class WeatherForecastApplication implements CommandLineRunner {
	private final ReadmeManagerService readmeManagerService;

	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try{
			readmeManagerService.updateReadme();
		}catch (Exception e){
			e.getMessage();
			e.printStackTrace();

			System.exit(1);
		}
		System.exit(0);
	}
}

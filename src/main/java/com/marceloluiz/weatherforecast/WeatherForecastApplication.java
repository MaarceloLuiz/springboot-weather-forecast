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
			String tableType = System.getenv("TABLE_TYPE");
			if (tableType == null || tableType.isEmpty()|| !isValidTableType(tableType)) {
				System.out.println("Invalid or missing TABLE_TYPE. Defaulting to 'both'.");
				tableType = "both";
			}

			readmeManagerService.updateReadme(tableType);
		}catch (Exception e){
			System.err.println("Error updating README: " + e.getMessage());
			e.printStackTrace();

			System.exit(1);
		}
		System.exit(0);
	}

	private boolean isValidTableType(String tableType) {
		return tableType.equalsIgnoreCase("hourly")
				|| tableType.equalsIgnoreCase("multi-day")
				|| tableType.equalsIgnoreCase("both");
	}
}

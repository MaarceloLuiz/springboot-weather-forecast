package com.marceloluiz.weatherforecast;

import com.marceloluiz.weatherforecast.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherForecastApplicationTests {

	@Mock
	private WeatherService weatherService;

	@Test
	void contextLoads() {
	}

}

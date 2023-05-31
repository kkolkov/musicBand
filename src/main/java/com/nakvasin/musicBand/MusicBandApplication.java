package com.nakvasin.musicBand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MusicBandApplication {
	public static void main(String[] args) {
		System.setProperty("de.flapdoodle.os.explain", "true");
		log.info("Platform.detect: {}", System.getProperty("de.flapdoodle.os.explain"));
		SpringApplication.run(MusicBandApplication.class, args);
	}
}

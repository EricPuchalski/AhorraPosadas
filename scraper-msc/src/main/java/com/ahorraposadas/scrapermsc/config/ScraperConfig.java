package com.ahorraposadas.scrapermsc.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        CaliforniaScraperProperties.class,
        HiperLibertadScraperProperties.class
})
public class ScraperConfig {
}

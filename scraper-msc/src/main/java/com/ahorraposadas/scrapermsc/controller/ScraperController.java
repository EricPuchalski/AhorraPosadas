package com.ahorraposadas.scrapermsc.controller;

import com.ahorraposadas.scrapermsc.service.impl.CaliforniaScraperService;
import com.ahorraposadas.scrapermsc.service.impl.HiperLibertadScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scraper")
public class ScraperController {

    private final CaliforniaScraperService californiaScraperService;
    private final HiperLibertadScraperService hiperLibertadScraperService;

    public ScraperController(CaliforniaScraperService californiaScraperService,
                             HiperLibertadScraperService hiperLibertadScraperService) {
        this.californiaScraperService = californiaScraperService;
        this.hiperLibertadScraperService = hiperLibertadScraperService;
    }

    @PostMapping("/california/run")
    public ResponseEntity<Void> runCaliforniaScrape() {
        californiaScraperService.scrapeProducts();
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/hiperlibertad/run")
    public ResponseEntity<Void> runHiperLibertadScrape() {
        hiperLibertadScraperService.scrapeProducts();
        return ResponseEntity.accepted().build();
    }
}

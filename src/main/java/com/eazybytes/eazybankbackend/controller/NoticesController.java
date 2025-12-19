package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.Notice;
import com.eazybytes.eazybankbackend.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
// Controller REST per esporre gli avvisi pubblici
public class NoticesController {

    // Repository per recuperare gli avvisi attivi
    private final NoticeRepository noticeRepository;

    // Endpoint pubblico che ritorna gli avvisi correnti
    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices() {
        // Recupera tutti gli avvisi con finestra di validità attuale
        List<Notice> notices = noticeRepository.findAllActiveNotices();
        if (notices != null) {
            // Risposta 200 OK con cache HTTP abilitata per 60 secondi
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                    .body(notices);
        } else {
            // In caso di null viene ritornato null (potrebbe generare 500 lato framework)
            return null;
        }
    }

}

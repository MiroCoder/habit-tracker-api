package com.mirocoder.habittracker;

import com.mirocoder.habittracker.dto.DailyPhraseRequest;
import com.mirocoder.habittracker.model.DailyPhrase;
import com.mirocoder.habittracker.service.DailyPhraseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DailyPhraseController {

    private final DailyPhraseService dailyPhraseService;

    public DailyPhraseController(DailyPhraseService dailyPhraseService){
        this.dailyPhraseService = dailyPhraseService;
    }

    @PostMapping("/daily-phrases")
    public ResponseEntity<Void> addDailyPhrase(
            @Valid @RequestBody DailyPhraseRequest request
    ) {
        dailyPhraseService.addDailyPhrase(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/daily-phrases")
    public List<DailyPhrase> getAllDailyPhrases(){
        return dailyPhraseService.getAllDailyPhrases();
    }
}

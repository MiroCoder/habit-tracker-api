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

    public DailyPhraseController(DailyPhraseService dailyPhraseService) {
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
    public List<DailyPhrase> getAllDailyPhrases() {
        return dailyPhraseService.getAllDailyPhrases();
    }


    @DeleteMapping("/daily-phrases/{id}")
    public ResponseEntity<Void> deleteDailyPhrase(@PathVariable long id) {
        boolean deleted = dailyPhraseService.deleteById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/daily-phrases/{id}")
    public ResponseEntity<DailyPhrase> updateDailyPhrase(
            @PathVariable long id,
            @Valid @RequestBody DailyPhraseRequest request
    ) {
        DailyPhrase updatedPhrase =
                dailyPhraseService.updateDailyPhrase(id, request);

        if (updatedPhrase == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedPhrase);
    }

    @GetMapping("/daily-phrases/{id}")
    public ResponseEntity<DailyPhrase> getDailyPhraseById(@PathVariable long id) {
        DailyPhrase phrase = dailyPhraseService.getDailyPhraseById(id);

        if( phrase == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(phrase);
    }

    @GetMapping("/daily-phrases/today")
    public ResponseEntity<DailyPhrase> getPhraseForToday() {
        DailyPhrase phrase = dailyPhraseService.getPhraseForToday();

        if (phrase == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(phrase);
    }

    @GetMapping("/daily-phrases/count")
    public int getPhrasesAmount(){
        return dailyPhraseService.getPhrasesAmount();
    }

}

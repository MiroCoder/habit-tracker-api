package com.mirocoder.habittracker.service;

import com.mirocoder.habittracker.dto.DailyPhraseRequest;
import com.mirocoder.habittracker.model.DailyPhrase;
import com.mirocoder.habittracker.repository.DailyPhraseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class DailyPhraseService {

    private final DailyPhraseRepository dailyPhraseRepository;

    public DailyPhraseService(DailyPhraseRepository dailyPhraseRepository){
        this.dailyPhraseRepository = dailyPhraseRepository;
    }

    public void addDailyPhrase(DailyPhraseRequest request) {
        DailyPhrase dailyPhrase = new DailyPhrase(
                0,
                request.getPhrase(),
                request.getAuthor()
        );

        dailyPhraseRepository.save(dailyPhrase);
    }

    public List<DailyPhrase> getAllDailyPhrases() {
        return dailyPhraseRepository.findAll();
    }

    public boolean deleteById(long id) {
        return dailyPhraseRepository.deleteById(id);
    }
}

package com.mirocoder.habittracker.service;

import com.mirocoder.habittracker.model.DailyPhrase;
import com.mirocoder.habittracker.repository.DailyPhraseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DailyPhraseServiceTest {

    @Mock
    private DailyPhraseRepository repository;

    @InjectMocks
    private DailyPhraseService service;

    @Test
    void getDailyPhraseByIdReturnsPhrase() {
        DailyPhrase phrase =
                new DailyPhrase(1L, "Small steps every day", "Unknown");

        when(repository.findById(1L))
                .thenReturn(phrase);

        DailyPhrase result = service.getDailyPhraseById(1L);
        assertEquals(phrase, result);
    }
}

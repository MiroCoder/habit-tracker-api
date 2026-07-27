package com.mirocoder.habittracker.exception;

public class DuplicateDailyPhraseException extends RuntimeException{

    public DuplicateDailyPhraseException(String message) {
        super(message);
    }
}

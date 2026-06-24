package com.mirocoder.habittracker.dto;

public class DailyPhraseResponse {
    private String phrase;
    private String author;

    public DailyPhraseResponse(String phrase, String author) {
        this.phrase = phrase;
        this.author = author;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getAuthor() {
        return author;
    }
}
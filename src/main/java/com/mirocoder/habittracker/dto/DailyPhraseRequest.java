package com.mirocoder.habittracker.dto;

import jakarta.validation.constraints.NotBlank;

public class DailyPhraseRequest {
    @NotBlank(message = "Phrase cannot be empty")
    private String phrase;
    @NotBlank(message = "Author cannot be empty")
    private String author;

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase){
        this.phrase = phrase;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }
}

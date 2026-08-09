package com.mirocoder.habittracker.model;


public class DailyPhrase {
    private long id;
    private String phrase;
    private String author;
    private boolean active;

    public DailyPhrase(long id, String phrase, String author, boolean active) {
        this.id = id;
        this.phrase = phrase;
        this.author = author;
        this.active = active;
    }

    public void setId(long id) {
            this.id = id;}
    public long getId() {return id;}

    public void setPhrase(String phrase) {this.phrase = phrase;}
    public String getPhrase() {return phrase;}

    public void setAuthor(String author) {this.author = author;}
    public String getAuthor() {return author;}

    public void setActive(boolean active) {this.active = active;}
    public boolean isActive() {return active;}


}

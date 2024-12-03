package com.example.demo.models;

public class searchInfo {
    private String searchBy;
    private String word;
    public searchInfo(String searchby, String w){
        word = w;
        searchBy = searchby;
    }
    public searchInfo(){
        word = "";
        searchBy = "";
    }

    // Getters and Setters
    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}


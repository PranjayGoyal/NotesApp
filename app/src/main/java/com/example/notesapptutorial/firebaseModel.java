package com.example.notesapptutorial;

public class firebaseModel {
    private String title;
    private String content;  // this variable names should be same as given in haspMap area the key names;

    public firebaseModel() {

    }

    public firebaseModel(String title, String content){
        this.title = title;
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

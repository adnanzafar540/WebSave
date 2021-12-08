package com.example.websave;

public class screeen_item {
    String Titel,Description;
    int ScreenImg;

    public screeen_item(String titel, String description, int screenImg) {
        Titel = titel;
        Description = description;
        ScreenImg = screenImg;
    }

    public void setTitel(String titel) {
        Titel = titel;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    public String getTitel() {
        return Titel;
    }

    public String getDescription() {
        return Description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }
}

package cic.du.ac.in.software;

/**
 * Created by Scorpion on 4/15/2018.
 */

public class datafetch {
    private String  Genre;
    private String Title;
    private String Semaphore;
    private String Content;

    public datafetch() {
    }

    public datafetch(String genre, String title, String semaphore, String content) {
        Genre = genre;
        Title = title;
        Semaphore = semaphore;
        Content = content;
    }

    public String getGenre() {
        return Genre;
    }

    public String getTitle() {
        return Title;
    }

    public String getSemaphore() {
        return Semaphore;
    }

    public String getContent() {
        return Content;
    }
}
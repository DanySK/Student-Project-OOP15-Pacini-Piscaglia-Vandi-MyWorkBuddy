package it.unibo.oop.myworkoutbuddy.view.style;

/**
 *
 * It models the CSS style of the application.
 *
 */
public class StyleSingleton {

    private String cssStyleSheet;

    private StyleSingleton() {
    }

    public void setCssStyle(String sheetPath) {
        this.cssStyleSheet = sheetPath;
    }

    public String getCssStyle() {
        return this.cssStyleSheet;
    }
}

package it.unibo.oop.myworkoutbuddy.view.factory;

/**
 *
 * It models the CSS style of the application using the pattern Singleton.
 *
 */
public final class StyleSingleton {

    private static String cssStyleSheet = "original.css";

    private StyleSingleton() {
    }

    /**
     * 
     * @param sheetPath
     *            set the path of cssSheet.
     */
    public static void setCssStyle(final String sheetPath) {
        cssStyleSheet = sheetPath;
    }

    /**
     * 
     * @return cssStyleSheet
     */
    public static String getCssStyle() {
        return cssStyleSheet;
    }
}

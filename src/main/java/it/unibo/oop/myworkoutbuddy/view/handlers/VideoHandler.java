package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.factory.MediaControl;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * 
 *
 */
public class VideoHandler {

    @FXML
    private BorderPane container;

    /**
     * 
     */
    public void initialize() {
        final MediaPlayer mediaPlayer = new MediaPlayer(
                new Media("http://videocdn.bodybuilding.com/video/mp4/118000/118911m.mp4"));
        container.setLeft(new MediaControl(mediaPlayer));
    }

}

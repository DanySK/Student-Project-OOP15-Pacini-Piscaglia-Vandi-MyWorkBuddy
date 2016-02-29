package it.unibo.oop.myworkoutbuddy.view.factory;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * A media player pane used to play exercises videos fetched by
 * https://docs.oracle.com/javafx/2/media/playercontrol.htm
 *
 */
public class MediaControl extends BorderPane {

    private final MediaPlayer mp;
    private final MediaView mediaView;
    private static final boolean REPEAT = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private final Slider timeSlider;
    private final Label playTime;
    private final Slider volumeSlider;
    private final HBox mediaBar;
    private static final int PADDING_TOP = 5;
    private static final int PADDING_RIGHT = 10;
    private static final int PADDING_BOTTOM = 5;
    private static final int PADDING_LEFT = 10;
    private static final int TIME_SLIDER_MIN_WIDTH = 50;
    private static final int PLAY_TIME_PREF_WIDTH = 130;
    private static final int PLAY_TIME_MIN_WIDTH = 50;
    private static final int VOLUME_SLIDER_PREF_WIDTH = 70;
    private static final int VOLUME_SLIDER_MIN_WIDTH = 30;
    private static final int SEXADECIMAL_BASE = 60;

    /**
     * 
     * @param mp
     *            MediaPlayer.
     */
    public MediaControl(final MediaPlayer mp) {
        this.mp = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        mediaView = new MediaView(mp);
        final Pane mvPane = new Pane() {
        };
        mvPane.getChildren().add(mediaView);
        mvPane.setStyle("-fx-background-color: black;");
        setCenter(mvPane);

        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);

        final Button playButton = new Button(">");

        playButton.setOnAction(e -> {
            final Status status = mp.getStatus();

            if (status == Status.UNKNOWN || status == Status.HALTED) {
                // don't do anything in these states
                return;
            }
            if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
                // rewind the movie if we're sitting at the end
                if (atEndOfMedia) {
                    mp.seek(mp.getStartTime());
                    atEndOfMedia = false;
                }
                mp.play();
            } else {
                mp.pause();
            }
        });
        mp.currentTimeProperty().addListener(o -> updateValues());

        mp.setOnPlaying(() -> {
            if (stopRequested) {
                mp.pause();
                stopRequested = false;
            } else {
                playButton.setText("||");
            }
        });

        mp.setOnPaused(() -> playButton.setText(">"));

        mp.setOnReady(() -> {
            duration = mp.getMedia().getDuration();
            updateValues();
        });

        mp.setCycleCount(REPEAT ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(() -> {
            if (!REPEAT) {
                playButton.setText(">");
                stopRequested = true;
                atEndOfMedia = true;
            }
        });

        mediaBar.getChildren().add(playButton);
        // Add spacer
        final Label spacer = new Label("   ");
        mediaBar.getChildren().add(spacer);

        // Add Time label
        final Label timeLabel = new Label("Time: ");
        mediaBar.getChildren().add(timeLabel);

        // Add time slider
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.setMinWidth(TIME_SLIDER_MIN_WIDTH);
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeSlider.valueProperty().addListener(o -> {
            if (timeSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
            }
        });
        mediaBar.getChildren().add(timeSlider);

        // Add Play label
        playTime = new Label();
        playTime.setPrefWidth(PLAY_TIME_PREF_WIDTH);
        playTime.setMinWidth(PLAY_TIME_MIN_WIDTH);
        mediaBar.getChildren().add(playTime);

        // Add the volume label
        final Label volumeLabel = new Label("Vol: ");
        mediaBar.getChildren().add(volumeLabel);

        // Add Volume slider
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(VOLUME_SLIDER_PREF_WIDTH);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(VOLUME_SLIDER_MIN_WIDTH);
        volumeSlider.valueProperty().addListener(o -> {
            if (volumeSlider.isValueChanging()) {
                mp.setVolume(volumeSlider.getValue() / 100.0);
            }
        });
        mediaBar.getChildren().add(volumeSlider);

        setBottom(mediaBar);
    }

    /**
     * 
     */
    protected void stopMediaPlayer() {
        mp.stop();
    }

    /**
     * 
     */
    private void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(() -> {
                final Duration currentTime = mp.getCurrentTime();
                playTime.setText(formatTime(currentTime, duration));
                timeSlider.setDisable(duration.isUnknown());
                if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                    timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100.0);
                }
                if (!volumeSlider.isValueChanging()) {
                    volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
                }
            });
        }
    }

    private static String formatTime(final Duration elapsed, final Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        final int elapsedHours = intElapsed / (SEXADECIMAL_BASE * SEXADECIMAL_BASE);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * SEXADECIMAL_BASE * SEXADECIMAL_BASE;
        }
        final int elapsedMinutes = intElapsed / SEXADECIMAL_BASE;
        final int elapsedSeconds = intElapsed - elapsedHours * SEXADECIMAL_BASE * SEXADECIMAL_BASE
                - elapsedMinutes * SEXADECIMAL_BASE;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            final int durationHours = intDuration / (SEXADECIMAL_BASE * SEXADECIMAL_BASE);
            if (durationHours > 0) {
                intDuration -= durationHours * SEXADECIMAL_BASE * SEXADECIMAL_BASE;
            }
            final int durationMinutes = intDuration / SEXADECIMAL_BASE;
            final int durationSeconds = intDuration - durationHours * SEXADECIMAL_BASE * SEXADECIMAL_BASE
                    - durationMinutes * SEXADECIMAL_BASE;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
            }
        }
    }
}
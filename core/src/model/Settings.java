package model;

import static com.dungeonadventure.game.DungeonAdventure.myBackgroundMusic;

import com.badlogic.gdx.audio.Sound;

/**
 * Represents the settings for sound and volume in the game.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class Settings {
    private int myVolumeLevel;
    private boolean myIsSoundOn;
    private boolean myIsMuted;
    private Sound mySound;
    /**
     * Constructs a new Settings instance with default values.
     * Default volume level is set to 5, sound is on, and mute is off.
     */
    public Settings() {
        myVolumeLevel = 5; // Default volume level
        myIsSoundOn = true; // Default sound state
        myIsMuted = false; // Default mute state
        mySound = null;     //no sounds by default
    }

    /**
     * Gets the current volume level.
     *
     * @return the volume level (0-10)
     */
    public int getVolumeLevel() {
        return myVolumeLevel;
    }

    /**
     * Sets the volume level.
     *
     * @param theVolumeLevel the new volume level (0-10)
     */
    public void setVolumeLevel(final int theVolumeLevel) {
        if (theVolumeLevel >= 0 && theVolumeLevel <= 10) {
            myVolumeLevel = theVolumeLevel;
        }
    }

    /**
     * Checks if the sound is currently enabled.
     *
     * @return true if sound is on, false otherwise
     */
    public boolean isSoundOn() {
        return myIsSoundOn;
    }

    /**
     * Toggles the sound state between on and off.
     */
    public void toggleSound() {
        myIsSoundOn = !myIsSoundOn;
        updateMusic();
    }
    public void playSound(final Sound theSound){
        if(mySound != null){
            mySound.stop();
        }
        mySound = theSound;
        mySound.play();
    }
    /**
     * Checks if the sound is currently muted.
     *
     * @return true if muted, false otherwise
     */
    public boolean isMuted() {
        return myIsMuted;
    }

    /**
     * Toggles the mute state.
     */
    public void toggleMute() {
        myIsMuted = !myIsMuted;
    }

    /**
     * Increases the volume level by 1, up to a maximum of 10.
     */
    public void increaseVolume() {
        if (myVolumeLevel < 10) {
            myVolumeLevel++;
        }
        updateMusic();
    }

    /**
     * Decreases the volume level by 1, down to a minimum of 0.
     */
    public void decreaseVolume() {
        if (myVolumeLevel > 0) {
            myVolumeLevel--;
        }
        updateMusic();
    }

    /**
     * Sets the sound state.
     *
     * @param isSoundOn the new sound state
     */
    public void setSoundOn(boolean isSoundOn) {
        myIsSoundOn = isSoundOn;
    }

    /**
     * Sets the mute state.
     *
     * @param isMuted the new mute state
     */
    public void setMuted(boolean isMuted) {
        myIsMuted = isMuted;
    }
    public void updateMusic() {
        if (this.isSoundOn()) {
            if (!myBackgroundMusic.isPlaying()) {
                myBackgroundMusic.setLooping(true);
                myBackgroundMusic.play();
            }
            myBackgroundMusic.setVolume(this.getVolumeLevel() / 10f); // Assuming volume level is between 0 and 10
        } else {
            myBackgroundMusic.pause();
        }
    }
}
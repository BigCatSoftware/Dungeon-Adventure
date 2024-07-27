package controller;

public class Settings {
    private int myVolumeLevel;
    private boolean myIsSoundOn;
    private boolean myIsMuted;

    public Settings() {
        myVolumeLevel = 5; // Default volume level
        myIsSoundOn = true; // Default sound state
        myIsMuted = false; // Default mute state
    }

    public int getVolumeLevel() {
        return myVolumeLevel;
    }

    public void setVolumeLevel(final int theVolumeLevel) {
        if (myVolumeLevel >= 0 && myVolumeLevel <= 10) {
            myVolumeLevel = theVolumeLevel;
        }
    }

    public boolean isSoundOn() {
        return myIsSoundOn;
    }

    public void toggleSound() {
        myIsSoundOn = !myIsSoundOn;
    }

    public boolean isMuted() {
        return myIsMuted;
    }

    public void toggleMute() {
        myIsMuted = !myIsMuted;
    }

    public void increaseVolume() {
        if (myVolumeLevel < 10) {
            myVolumeLevel++;
        }
    }

    public void decreaseVolume() {
        if (myVolumeLevel > 0) {
            myVolumeLevel--;
        }
    }

    public void setSoundOn(boolean isSoundOn) {
    }

    public void setMuted(boolean isMuted) {

    }
}

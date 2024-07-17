package Controller;

public class Settings {
    private int volumeLevel;
    private boolean isSoundOn;
    private boolean isMuted;

    public Settings() {
        this.volumeLevel = 5; // Default volume level
        this.isSoundOn = true; // Default sound state
        this.isMuted = false; // Default mute state
    }

    public int getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(int volumeLevel) {
        if (volumeLevel >= 0 && volumeLevel <= 10) {
            this.volumeLevel = volumeLevel;
        }
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public void toggleSound() {
        this.isSoundOn = !isSoundOn;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void toggleMute() {
        this.isMuted = !isMuted;
    }

    public void increaseVolume() {
        if (volumeLevel < 10) {
            volumeLevel++;
        }
    }

    public void decreaseVolume() {
        if (volumeLevel > 0) {
            volumeLevel--;
        }
    }
}

package org.scrum.psd.battleship.ascii.sound;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;



public class SoundEffectPlayer {
    static final String soundFolderPath = "src/main/java/org/scrum/psd/battleship/ascii/sound";

    public static void playSound(String path) {
        try {
            // Load the audio file
            File soundFile = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Get a sound clip resource
            Clip clip = AudioSystem.getClip();

            // Open the audio stream to the clip
            clip.open(audioStream);

            // Start playing the sound
            clip.start();

            // Optional: Wait until the sound is finished playing
            clip.drain();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }



    public static void playWinSound(){
        String path = SoundEffectPlayer.soundFolderPath + "/fanfare.wav";
        SoundEffectPlayer.playSound(path);
    }
    public static void playHitSound(){
        String path = SoundEffectPlayer.soundFolderPath + "/explosion.wav";
        SoundEffectPlayer.playSound(path);
    }
    public static void playSinkSound(){
        String path = SoundEffectPlayer.soundFolderPath + "/big-explosion.wav";
        SoundEffectPlayer.playSound(path);
    }
    public static void playMissSound(){
        String path = SoundEffectPlayer.soundFolderPath + "/plums.wav";
        SoundEffectPlayer.playSound(path);
    }





}

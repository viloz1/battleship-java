package org.scrum.psd.battleship.ascii.sound;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundEffectPlayer {

    public static void playSound(String sound) {
        try {
            // Load the audio file
            String path="";
            switch(sound){
                case "fanfare": {
                    path = "src/main/java/org/scrum/psd/battleship/ascii/sound/fanfare.wav";
                    break;
                }
            }

            System.out.println("path of sound is: " + path);
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

    public static void main(String[] args) {
        // Provide the path to the sound file
        String filePath = "path_to_your_sound_file.wav";

        // Play the sound effect
        playSound(filePath);
    }
}

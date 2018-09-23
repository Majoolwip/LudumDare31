package com.base.engine;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer
{
	private Clip clip;
	
	public AudioPlayer(String s)
	{
		try
		{
			
			InputStream audioSrc = getClass().getResourceAsStream(s);
			//add buffer for mark/reset support
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false);
			
			AudioInputStream dais = 
					AudioSystem.getAudioInputStream(decodeFormat, ais);
			
			clip = AudioSystem.getClip();
			clip.open(dais);
			
			FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-20.0f);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}
	
	public void play()
	{
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		while(!clip.isRunning())
		{
			clip.start();
		}
	}
	
	public void stop()
	{
		if(clip.isRunning()) clip.stop();
	}
	
	public void close()
	{
		stop();
		clip.close();
	}
	
	public void loop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		while(!clip.isRunning())
		{
			clip.start();
		}
	}
	
}

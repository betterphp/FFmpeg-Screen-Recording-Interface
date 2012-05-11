package uk.co.jacekk.ffmpeginterface.gui;

import java.util.TimerTask;

import uk.co.jacekk.ffmpeginterface.FFmpegScreenRecorder;

public class GuiTimerUpdateTask extends TimerTask {
	
	private GuiMainWindow window;
	private FFmpegScreenRecorder recorder;
	
	public GuiTimerUpdateTask(GuiMainWindow window, FFmpegScreenRecorder recorder){
		this.window = window;
		this.recorder = recorder;
	}
	
	public void run(){
		if (!recorder.isRecording()){
			return;
		}
		
		long duration = recorder.getRecordingDuration();
		
		int seconds = (int) (duration % 60);
		int minutes = (int) ((duration / 60) % 60);
		int hours = (int) ((duration / (60 * 60)) % 24);
		
		window.timer.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
	}
	
}

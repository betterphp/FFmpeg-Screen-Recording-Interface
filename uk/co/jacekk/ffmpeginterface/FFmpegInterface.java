package uk.co.jacekk.ffmpeginterface;

import uk.co.jacekk.ffmpeginterface.gui.GuiMainWindow;

public class FFmpegInterface {
	
	public FFmpegScreenRecorder recorder;
	
	public FFmpegInterface(){
		this.recorder = new FFmpegScreenRecorder();
		
		new GuiMainWindow(this.recorder);
	}
	
	public static void main(String[] args){
		new FFmpegInterface();
	}
	
}

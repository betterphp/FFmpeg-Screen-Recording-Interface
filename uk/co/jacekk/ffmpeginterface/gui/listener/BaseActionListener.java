package uk.co.jacekk.ffmpeginterface.gui.listener;

import uk.co.jacekk.ffmpeginterface.FFmpegScreenRecorder;
import uk.co.jacekk.ffmpeginterface.gui.GuiMainWindow;

public abstract class BaseActionListener  {
	
	protected GuiMainWindow window;
	protected FFmpegScreenRecorder recorder;
	
	public BaseActionListener(GuiMainWindow window, FFmpegScreenRecorder recorder){
		this.window = window;
		this.recorder = recorder;
	}
	
}

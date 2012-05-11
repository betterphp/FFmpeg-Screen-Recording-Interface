package uk.co.jacekk.ffmpeginterface.gui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import uk.co.jacekk.ffmpeginterface.FFmpegException;
import uk.co.jacekk.ffmpeginterface.FFmpegScreenRecorder;
import uk.co.jacekk.ffmpeginterface.gui.GuiMainWindow;

public class ToggleRecordingButtonMouseListener extends BaseActionListener implements MouseListener {

	public ToggleRecordingButtonMouseListener(GuiMainWindow window, FFmpegScreenRecorder recorder){
		super(window, recorder);
	}
	
	public void mouseClicked(MouseEvent event){  }
	public void mouseEntered(MouseEvent event){  }
	public void mouseExited(MouseEvent event){  }
	public void mousePressed(MouseEvent event){  }
	
	public void mouseReleased(MouseEvent event){
		if (recorder.isRecording()){
			try{
				recorder.stop();
			}catch (FFmpegException e){
				window.alert(e.getMessage());
				e.printStackTrace();
			}
			
			window.recordButton.setText("Start Recording");
			window.selectWindowButton.setEnabled(true);
		}else{
			try{
				recorder.start();
			}catch (Exception e){
				window.alert(e.getMessage());
				e.printStackTrace();
			}
			
			window.recordButton.setText("Stop Recording");
			window.selectWindowButton.setEnabled(false);
		}
	}

}

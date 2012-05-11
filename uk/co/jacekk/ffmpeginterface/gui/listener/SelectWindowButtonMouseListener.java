package uk.co.jacekk.ffmpeginterface.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import uk.co.jacekk.ffmpeginterface.FFmpegScreenRecorder;
import uk.co.jacekk.ffmpeginterface.gui.GuiMainWindow;

public class SelectWindowButtonMouseListener extends BaseActionListener implements MouseListener {
	
	public SelectWindowButtonMouseListener(GuiMainWindow window, FFmpegScreenRecorder recorder){
		super(window, recorder);
	}
	
	public void actionPerformed(ActionEvent event){  }
	public void mouseClicked(MouseEvent event){  }
	public void mouseEntered(MouseEvent event){  }
	public void mouseExited(MouseEvent event){  }
	public void mousePressed(MouseEvent event){  }
	
	public void mouseReleased(MouseEvent event){
		Process winId = null;
		BufferedReader reader = null;
		
		try{
			winId = Runtime.getRuntime().exec("xwininfo");
			winId.waitFor();
			
			reader = new BufferedReader(new InputStreamReader(winId.getInputStream()));
			
			String response = "";
			String line;
			
			while ((line = reader.readLine()) != null){
				response += line + '\n';
			}
			
			if (response.length() == 0){
				throw new Exception("Unable to get window size");
			}
			
			String xSearch = "Absolute upper-left X:";
			String ySearch = "Absolute upper-left Y:";
			String wSearch = "Width:";
			String hSearch = "Height:";
			
			int xIndex = response.indexOf(xSearch);
			int yIndex = response.indexOf(ySearch);
			int wIndex = response.indexOf(wSearch);
			int hIndex = response.indexOf(hSearch);
			
			recorder.setParam("X", response.substring(xIndex + xSearch.length(), response.indexOf('\n', xIndex)).trim());
			recorder.setParam("Y", response.substring(yIndex + ySearch.length(), response.indexOf('\n', yIndex)).trim());
			recorder.setParam("Width", response.substring(wIndex + wSearch.length(), response.indexOf('\n', wIndex)).trim());
			recorder.setParam("Height", response.substring(hIndex + hSearch.length(), response.indexOf('\n', hIndex)).trim());
			
			window.updateStatus();
		}catch (Exception e){
			window.alert(e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				reader.close();
			}catch (IOException e){
				e.printStackTrace();
			}
			
			winId.destroy();
		}
	}
	
}

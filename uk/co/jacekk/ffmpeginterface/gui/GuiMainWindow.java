package uk.co.jacekk.ffmpeginterface.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import uk.co.jacekk.ffmpeginterface.FFmpegScreenRecorder;
import uk.co.jacekk.ffmpeginterface.gui.listener.SelectWindowButtonMouseListener;
import uk.co.jacekk.ffmpeginterface.gui.listener.ToggleRecordingButtonMouseListener;

public class GuiMainWindow {
	
	private FFmpegScreenRecorder recorder;
	
	private JFrame window;
	
	public JLabel timer;
	
	public HashMap<String, JLabel> paramsLabels;
	
	public JButton recordButton;
	public JButton selectWindowButton;
	
	public GuiMainWindow(FFmpegScreenRecorder recorder){
		this.recorder = recorder;
		this.paramsLabels = new HashMap<String, JLabel>();
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e){
			e.printStackTrace();
		}
		
		this.window = new JFrame("FFmpeg Interface");
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel timerArea = this.createTimerArea();
		JPanel statusArea = this.createStatusArea();
		JPanel buttonArea = this.createButtonArea();
		
		this.addActionListeners();
		
		this.addAreasToWindow(timerArea, statusArea, buttonArea);
		
		this.window.pack();
		this.window.setResizable(false);
		this.window.setVisible(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.window.setLocation(screenSize.width / 2 - 400, screenSize.height / 2 - 300);
		
		(new Timer()).schedule(new GuiTimerUpdateTask(this, recorder), 0, 1000);
	}
	
	private JPanel createTimerArea(){
		JPanel area = new JPanel();
		
		this.timer = new JLabel("00:00:00");
		this.timer.setFont(new Font("Sans-Serif", Font.BOLD, 16));
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		layout.columnWidths = new int[]{200};
		layout.rowHeights = new int[]{26};
		
		layout.setConstraints(this.timer, constraints);
		
		area.setLayout(layout);
		
		area.add(this.timer);
		
		area.setBorder(BorderFactory.createEtchedBorder());
		
		return area;
	}
	
	private JPanel createStatusArea(){
		JPanel area = new JPanel();
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(2, 2, 2, 2);
		
		int[] rowHeights = new int[this.recorder.params.size()];
		
		for (int i = 0; i < rowHeights.length; ++i){
			rowHeights[i] = 22;
		}
		
		layout.columnWidths = new int[]{100, 100};
		layout.rowHeights = rowHeights;
		
		ArrayList<JLabel> toAdd = new ArrayList<JLabel>();
		
		for (Entry<String, String> param : this.recorder.params.entrySet()){
			JLabel name = new JLabel(param.getKey());
			name.setFont(new Font("Sans-Serif", Font.BOLD, 14));
			
			JLabel value = new JLabel(param.getValue());
			value.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
			
			toAdd.add(name);
			toAdd.add(value);
			
			this.paramsLabels.put(param.getKey(), value);
			
			constraints.gridwidth = GridBagConstraints.RELATIVE;
			layout.setConstraints(name, constraints);
			
			constraints.gridwidth = GridBagConstraints.REMAINDER;
			layout.setConstraints(value, constraints);
		}
		
		area.setLayout(layout);
		
		for (JLabel add : toAdd){
			area.add(add);
		}
		
		area.setBorder(BorderFactory.createEtchedBorder());
		
		return area;
	}
	
	private JPanel createButtonArea(){
		JPanel area = new JPanel();
		
		this.recordButton = new JButton("Start Recording");
		this.recordButton.setToolTipText("Starts or stops recording the current screen area");
		
		this.selectWindowButton = new JButton("Select Window");
		this.selectWindowButton.setToolTipText("Selects a window to use as the recording area");
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(2, 2, 2, 2);
		
		layout.columnWidths = new int[]{200};
		layout.rowHeights = new int[]{36, 36};
		
		layout.setConstraints(this.recordButton, constraints);
		layout.setConstraints(this.selectWindowButton, constraints);
		
		area.setLayout(layout);
		
		area.add(this.recordButton);
		area.add(this.selectWindowButton);
		
		return area;
	}
	
	private void addActionListeners(){
		this.selectWindowButton.addMouseListener(new SelectWindowButtonMouseListener(this, this.recorder));
		this.recordButton.addMouseListener(new ToggleRecordingButtonMouseListener(this, this.recorder));
	}
	
	private void addAreasToWindow(JPanel timerArea, JPanel statusArea, JPanel buttonArea){
		JPanel content = (JPanel) this.window.getContentPane();
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(2, 2, 2, 2);
		
		layout.setConstraints(timerArea, constraints);
		layout.setConstraints(statusArea, constraints);
		layout.setConstraints(buttonArea, constraints);
		
		content.setLayout(layout);
		
		content.add(timerArea);
		content.add(statusArea);
		content.add(buttonArea);
	}
	
	public void showSaveRecording(){
		JFileChooser chooser = new JFileChooser();
		
		chooser.showSaveDialog(this.window);
		
		File tempFile = new File(this.recorder.getTempFileName());
		File destFile = chooser.getSelectedFile();
		
		if (destFile == null){
			tempFile.delete();
		}else{
			tempFile.renameTo(destFile);
		}
	}
	
	public void updateStatus(){
		for (Entry<String, String> param : this.recorder.params.entrySet()){
			this.paramsLabels.get(param.getKey()).setText(param.getValue());
		}
	}
	
	public void alert(String message){
		JOptionPane.showMessageDialog(null, message, "Alert!", JOptionPane.WARNING_MESSAGE);
	}
	
}

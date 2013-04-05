package uk.co.jacekk.ffmpeginterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyInputStreamThread extends Thread implements Runnable {
	
	private InputStream input;
	private OutputStream output;
	
	public CopyInputStreamThread(InputStream input, OutputStream output){
		this.input = input;
		this.output = output;
	}
	
	public void run(){
		try{
			int read;
			
			while ((read = this.input.read()) != -1){
				this.output.write(read);
			}
		}catch (IOException e){
			e.printStackTrace();
			return;
		}
	}
	
}

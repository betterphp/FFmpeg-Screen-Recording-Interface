package uk.co.jacekk.ffmpeginterface;

public class FFmpegException extends Exception {
	
	private static final long serialVersionUID = 4736932414011996218L;
	
	private String message;
	
	public FFmpegException(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
	
}

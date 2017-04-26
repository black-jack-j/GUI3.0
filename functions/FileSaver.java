package functions;

import java.io.File;

public class FileSaver implements Runnable{
	private File file;
	
	FileSaver(File f){
		this.file = f;
	}

	@Override
	public void run() {
		synchronized(file){
			
		}
		
	}
	
}

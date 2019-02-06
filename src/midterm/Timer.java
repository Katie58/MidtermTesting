package midterm;
import java.util.TimerTask;

public class Timer {
	
	private int time = 0;
		
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public Timer() {
		final java.util.Timer tmr = new java.util.Timer();
		tmr.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				time++;
			     if (time >= 300) {
			         tmr.cancel();
			         tmr.purge();
			         return;
			     }
			}
		},0,1000);
	}
	
	public Timer(int x) {
		final java.util.Timer tmr = new java.util.Timer();
		tmr.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				time++;
			    if (time >= 30) {
			        tmr.cancel();
			        tmr.purge();
			        return;
			    }
				System.out.println(" ");			    
			}
		},0,500);
	}
}



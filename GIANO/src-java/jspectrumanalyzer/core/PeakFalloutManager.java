package jspectrumanalyzer.core;

public class PeakFalloutManager {
	
	private static PeakFallout  peackFallout = PeakFallout.INF;	
	
	public static boolean isInf(){
		return peackFallout == PeakFallout.INF;
	}
	
	public static PeakFallout getPeackFallout() {
		return peackFallout;
	}
	
	public static void setPeackFallout(PeakFallout peackFallout) {
		PeakFalloutManager.peackFallout = peackFallout;
	}

	public static double getMillis(){
		if(peackFallout == PeakFallout.SHORT)
			return 500;
		if(peackFallout == PeakFallout.MEDIUM)
			return 1000;
		if(peackFallout == PeakFallout.LONG)
			return 5000;
		return -1;
	}
	
}

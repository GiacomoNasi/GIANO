package jspectrumanalyzer.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DatasetTresholdDetector {
	
	private DatasetSpectrum spectrum;
	
	public DatasetTresholdDetector(DatasetSpectrum spectrum) {
		this.spectrum = spectrum;
	}
	
	public Map<Double, Double> getIntervals(float treshold, double freqStart, double freqEnd){
		
		int tstart = spectrum.indexOf(freqStart * 1000000), 
			tend = spectrum.indexOf(freqEnd * 1000000);
		System.out.println(tstart + " (" + spectrum.getFrequency(tstart) / 1000000 + ")" +  " - " + tend + "(" + spectrum.getFrequency(tend) / 1000000+ ")" + " <-- " + spectrum.getSpectrumArray().length);
		
		float values[] = Arrays.copyOfRange(
				spectrum.getSpectrumArray(), 
				tstart,
				tend);
		Map<Double, Double> intervals = new TreeMap<>();
					
		
		boolean inInterval = false, 
				inPreviuous = false;
		double start = 0, 
				end = 0;
		//intervals.put(spectrum.getFrequency(tstart), spectrum.getFrequency(tend));
		for(int i=tstart; i<tend; i++){
			if(spectrum.getFrequency(i) < freqStart * 1000000) continue;
			else if(spectrum.getFrequency(i) > freqEnd * 1000000) break;
			inInterval = spectrum.getPower(i) >= treshold;
			if(inInterval && !inPreviuous)
				start = spectrum.getFrequency(i);
			if(!inInterval && inPreviuous){
				end = spectrum.getFrequency(i);
				intervals.put(start, end);
			}
			inPreviuous = inInterval;
		}
		
		return intervals;
	}

}

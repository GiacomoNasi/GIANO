package jspectrumanalyzer.core;

import java.util.ArrayList;
import java.util.Arrays;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

public class DatasetSpectrumPeak extends DatasetSpectrum
{
	protected long lastSliceMillis = -1;
	
	protected long		lastAdded			= System.currentTimeMillis();
	/**
	 * Periodo di aggiornamento dei picchi
	 */
	protected long		peakFalloutMillis	= 1000;
	protected float		peakFallThreshold;
	/**
	 * stores EMA decaying peaks
	 */
	protected float[]	spectrumPeak;

	/**
	 * stores real peaks and if {@link #spectrumPeak} falls more than preset value below it, start using values from {@link #spectrumPeak}
	 */
	protected float[]	spectrumPeakHold;
	
	public DatasetSpectrumPeak(float fftBinSizeHz, int freqStartMHz, int freqStopMHz, float spectrumInitPower, float peakFallThreshold, long peakFalloutMillis)
	{
		super(fftBinSizeHz, freqStartMHz, freqStopMHz, spectrumInitPower);

		this.peakFalloutMillis = peakFalloutMillis;
		this.spectrumInitPower = spectrumInitPower;
		this.peakFallThreshold = peakFallThreshold;
		int datapoints = (int) (Math.ceil(freqStopMHz - freqStartMHz) * 1000000d / fftBinSizeHz);
		spectrum = new float[datapoints];
		Arrays.fill(spectrum, spectrumInitPower);
		spectrumPeak = new float[datapoints];
		Arrays.fill(spectrumPeak, spectrumInitPower);
		spectrumPeakHold = new float[datapoints];
		Arrays.fill(spectrumPeakHold, spectrumInitPower);
		

	}

	public void copyTo(DatasetSpectrumPeak filtered)
	{
		super.copyTo(filtered);
		System.arraycopy(spectrumPeak, 0, filtered.spectrumPeak, 0, spectrumPeak.length);
		System.arraycopy(spectrumPeakHold, 0, filtered.spectrumPeakHold, 0, spectrumPeakHold.length);
	}

	/**
	 * Fills data to {@link XYSeries}, uses x units in MHz
	 * @param series
	 */
	public void fillPeaksToXYSeries(XYSeries series)
	{
		fillToXYSeriesPriv(series, spectrumPeakHold);
	}

	public double calculateSpectrumPeakPower(){
		double powerSum	= 0;
		for (int i = 0; i < spectrumPeakHold.length; i++) {
			powerSum	+= Math.pow(10, spectrumPeakHold[i]/10); /*convert dB to mW to sum power in linear form*/
		}
		powerSum	= 10*Math.log10(powerSum); /*convert back to dB*/ 
		return powerSum;
	}
	
	public void refreshPeakSpectrum()
	{
		
		long timeDiffFromPrevValueMillis = System.currentTimeMillis() - lastAdded;
		lastAdded = System.currentTimeMillis();
		if(lastSliceMillis == -1)
			lastSliceMillis =  System.currentTimeMillis();
		boolean slice = !PeakFalloutManager.isInf()  &&  System.currentTimeMillis() - lastSliceMillis >= PeakFalloutManager.getMillis();

		peakFallThreshold = 10;
		for (int i = 0; i < spectrum.length; i++)
		{
			float spectrumVal = spectrum[i];
			
			if (spectrumVal > spectrumPeakHold[i] || slice) {
				spectrumPeakHold[i] = spectrumPeak[i] = spectrumVal;
				if (slice) lastSliceMillis = System.currentTimeMillis();
			}
			
			/*if(!PeakFalloutManager.isInf() && millis >= PeakFalloutManager.getMillis()){
				spectrumPeak[spectrIndex] = (float) EMA.calculateTimeDependent(spectrumVal, spectrumPeak[spectrIndex], timeDiffFromPrevValueMillis,	PeakFalloutManager.getMillis());
				millis = 0;
			}*/
			if (spectrumPeakHold[i] - spectrumPeak[i] > peakFallThreshold )
			{
				spectrumPeakHold[i] = spectrumPeak[i];
			}
		}
	}

	public void resetPeaks()
	{
		Arrays.fill(spectrumPeak, spectrumInitPower);
		Arrays.fill(spectrumPeakHold, spectrumInitPower);
	}

	@Override protected Object clone() throws CloneNotSupportedException
	{
		DatasetSpectrumPeak copy = (DatasetSpectrumPeak) super.clone();
		copy.spectrumPeakHold = spectrumPeakHold.clone();
		copy.spectrumPeak = spectrumPeak.clone();
		return super.clone();
	}

}

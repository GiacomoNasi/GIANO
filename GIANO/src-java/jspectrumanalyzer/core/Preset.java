package jspectrumanalyzer.core;

import java.io.Serializable;

public class Preset implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int minValue = 0;
	private int maxValue = 0;
	private String name = "";
	
	public Preset(int minValue, int maxValue, String name) {
		if(minValue < 1 || maxValue > 7250 || name.isEmpty() || minValue > maxValue)
			throw new IllegalArgumentException();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.name = name;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

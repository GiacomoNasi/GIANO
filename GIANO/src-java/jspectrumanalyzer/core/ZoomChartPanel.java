package jspectrumanalyzer.core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

public class ZoomChartPanel extends ChartPanel{

	private boolean mousePressed = false, 
					mouseMoving = false;
	
	private double freqStart, freqEnd;
	private IMain main;
	
	private boolean isMouseDragged(){
		return mouseMoving && mousePressed;
	}
	
	public ZoomChartPanel(JFreeChart chart, IMain main) {
		super(chart);
		this.main = main;
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				XYPlot plot = getChart().getXYPlot();
				double x = e.getX();
				
				Rectangle2D subplotArea = getChartRenderingInfo().getPlotInfo().getDataArea();
				freqEnd = plot.getDomainAxis().java2DToValue(x, subplotArea, plot.getDomainAxisEdge());
				main.updateFrequency(freqStart, freqEnd);				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				XYPlot plot = getChart().getXYPlot();
				double x = e.getX();
				
				Rectangle2D subplotArea = getChartRenderingInfo().getPlotInfo().getDataArea();
				freqStart = plot.getDomainAxis().java2DToValue(x, subplotArea, plot.getDomainAxisEdge());
			}

		});
		
	}

	
	
}

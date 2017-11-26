package jspectrumanalyzer.core;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.Layer;

import jspectrumanalyzer.HackRFSweepSpectrumAnalyzer;

public class ZoomChartPanel extends ChartPanel{
	
	private JLabel zoomLabel = new JLabel(), 
				   mouseLabel = new JLabel();
	
	private double freqStart, freqEnd;
	private ValueMarker markerStart = new ValueMarker(0, Color.WHITE, new BasicStroke(1f)), 
						markerEnd  = new ValueMarker(0, Color.WHITE, new BasicStroke(1f)),
						domainMarker = null,
						rangeMarker = null;
	private IMain main;
	
	private static final String emptyLabel = "(-; -)";
	
	public ZoomChartPanel(JFreeChart chart, IMain main) {
		super(chart);
		this.main = main;
		
		Font spunto = HackRFSweepSpectrumAnalyzer.font;
		setFont(new Font(spunto.getFontName(), spunto.getStyle(), (int)(spunto.getSize()*0.8)));
		
		zoomLabel.setText(emptyLabel);
		zoomLabel.setFont(super.getFont());
		zoomLabel.setForeground(Color.WHITE);
		zoomLabel.setVisible(false);

		mouseLabel.setText(emptyLabel);
		mouseLabel.setFont(super.getFont());
		mouseLabel.setForeground(Color.WHITE);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
					.addContainerGap(351, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(mouseLabel)
						.addComponent(zoomLabel))
					.addGap(27))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(mouseLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(zoomLabel)
					.addContainerGap(206, Short.MAX_VALUE))
		);
		
		setLayout(layout);
		
		add(zoomLabel, BorderLayout.BEFORE_FIRST_LINE);
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				XYPlot plot = getChart().getXYPlot();
				double x = e.getX();
				
				Rectangle2D subplotArea = getChartRenderingInfo().getPlotInfo().getDataArea();
				freqEnd = plot.getDomainAxis().java2DToValue(x, subplotArea, plot.getDomainAxisEdge());
				main.updateFrequency(freqStart, freqEnd);
				zoomLabel.setVisible(false);
				
				plot.clearDomainMarkers();
				plot.clearRangeMarkers();
				
				if(domainMarker != null){
					domainMarker.setValue(freqEnd);
					plot.addDomainMarker(domainMarker);
				}
				if(rangeMarker != null){
					rangeMarker.setValue(plot.getDomainAxis().java2DToValue(e.getY(), subplotArea, plot.getDomainAxisEdge()));
					plot.addRangeMarker(rangeMarker);
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {				
				XYPlot plot = getChart().getXYPlot();
				double x = e.getX();
				
				if(plot.getDomainMarkers(Layer.FOREGROUND) != null && plot.getDomainMarkers(Layer.FOREGROUND).size() > 0)
					domainMarker = (ValueMarker)(plot.getDomainMarkers(Layer.FOREGROUND).toArray()[0]);
				if(plot.getRangeMarkers(Layer.FOREGROUND) != null && plot.getRangeMarkers(Layer.FOREGROUND).size() > 0)
					rangeMarker = (ValueMarker)(plot.getRangeMarkers(Layer.FOREGROUND).toArray()[0]);

				
				zoomLabel.setVisible(true);
				Rectangle2D subplotArea = getChartRenderingInfo().getPlotInfo().getDataArea();
				freqStart = plot.getDomainAxis().java2DToValue(x, subplotArea, plot.getDomainAxisEdge());
				setZoomLabelText(""+(int)freqStart, "-");
				
				
			}
			

		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				XYPlot plot = getChart().getXYPlot();
				double x = e.getX();	
				Rectangle2D subplotArea = getChartRenderingInfo().getPlotInfo().getDataArea();
				double tempFreq = plot.getDomainAxis().java2DToValue(x, subplotArea, plot.getDomainAxisEdge());
				setZoomLabelText(""+(int)freqStart, ""+(int)tempFreq);
				
				
				plot.clearDomainMarkers();
				plot.clearRangeMarkers();
				
				markerStart.setValue(freqStart);
				markerEnd.setValue(tempFreq);
				plot.addDomainMarker(markerStart);
				plot.addDomainMarker(markerEnd);
				
				
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				XYPlot plot = getChart().getXYPlot();				
				double x = e.getX();
				double y = e.getY();
				
				Rectangle2D subplotArea = getChartRenderingInfo().getPlotInfo().getDataArea();
				double db	= plot.getRangeAxis().java2DToValue(y, subplotArea, plot.getRangeAxisEdge());
				double freq = plot.getDomainAxis().java2DToValue(x, subplotArea, plot.getDomainAxisEdge());
				
				setMouseLabelText(""+((int)freq), ""+(int)db);
			}
		});
		
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				main.zoom(e.getWheelRotation());
			}
		});
		
	}
	
	private void setMouseLabelText(String freq, String db){
		mouseLabel.setText("(" + freq + (freq.equals("-") ? "" : "MHz; ") + db + (freq.equals("-") ? "" : "dB") + ")");		
	}
	
	private void setZoomLabelText(String freqStart, String freqEnd){
		zoomLabel.setText("(" + freqStart + (freqStart.equals("-") ? "" : "MHz; ") + freqEnd + (freqEnd.equals("-") ? "" : "MHz") + ")");		
	}

	
	
}

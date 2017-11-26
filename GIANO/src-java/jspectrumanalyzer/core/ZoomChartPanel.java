package jspectrumanalyzer.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

public class ZoomChartPanel extends ChartPanel{
	
	private JLabel zoomLabel = new JLabel(), 
				   mouseLabel = new JLabel();
	
	private double freqStart, freqEnd;
	private IMain main;
	
	private static final String emptyLabel = "(-; -)";
	
	public ZoomChartPanel(JFreeChart chart, IMain main) {
		super(chart);
		this.main = main;
		
		zoomLabel.setText(emptyLabel);
		zoomLabel.setFont(super.getFont());
		zoomLabel.setForeground(Color.WHITE);

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
				zoomLabel.setText(emptyLabel);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				XYPlot plot = getChart().getXYPlot();
				double x = e.getX();
				
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
				//System.out.println("wheel!");
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

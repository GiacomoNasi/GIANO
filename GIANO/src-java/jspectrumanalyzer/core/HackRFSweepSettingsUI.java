package jspectrumanalyzer.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JSpinner.ListEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jspectrumanalyzer.HackRFSweepSpectrumAnalyzer;
import jspectrumanalyzer.Version;
import net.miginfocom.swing.MigLayout;

public class HackRFSweepSettingsUI extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7721079457485020637L;
	private JLabel txtHackrfConnected;
	
	private JTabbedPane tabbedPane;
	private JPanel frequencyStartPanel, frequencyEndPanel, frequencyPanel; 
	private JPanel FFTPanel, gainPanel, samplesPanel, samplingPanel;
	private JPanel waterfallStartPanel, waterfallLengthPanel, waterfallPanel; 
	private JPanel peaksPanel;

	private JPanel centerPanel, bottomPanel;
	
	private JPanel frequencyLayoutPanel, samplingLayoutPanel, waterfallLayoutPanel, peaksLayoutPanel, centerLayoutPanel, bottomLayoutPanel;

	public static final Color mainColor = new Color(0x625954);
	public static final Color settingColor = new Color(0x878f77);
	
	
	
	/**
	 * Create the panel.
	 */
	
	public HackRFSweepSettingsUI(HackRFSettings hackRFSettings)
	{
		
		
		
		setLayout(new BorderLayout());
		setForeground(Color.WHITE);
		setBackground(mainColor);
		
		int minFreq = 1;
		int maxFreq = 7250;
		int freqStep = 1;

		Color bgc = mainColor;
	    Color fgc = mainColor;
	    

	    //UIManager.put("TabbedPane.shadow",                fgc);
	    UIManager.put("TabbedPane.darkShadow",            fgc);
	    UIManager.put("TabbedPane.light",                 fgc);
	    UIManager.put("TabbedPane.highlight",             Color.WHITE);
	    //UIManager.put("TabbedPane.tabAreaBackground",     fgc);
	    //UIManager.put("TabbedPane.unselectedBackground",  fgc);
	    UIManager.put("TabbedPane.background",            bgc);
	    UIManager.put("TabbedPane.foreground",            Color.WHITE);
	    //UIManager.put("TabbedPane.focus",                 fgc);
	    UIManager.put("TabbedPane.contentAreaColor",      fgc);
	    UIManager.put("TabbedPane.selected",              Color.DARK_GRAY);
	    //UIManager.put("TabbedPane.selectHighlight",       fgc);
	    //UIManager.put("TabbedPane.borderHightlightColor", fgc);
	    
	    

	    

		tabbedPane = new JTabbedPane();
		tabbedPane.setOpaque(false);
		tabbedPane.setBackground(mainColor);
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frequencyLayoutPanel = new JPanel();
		frequencyLayoutPanel.setBackground(mainColor);
		frequencyLayoutPanel.setLayout(new BorderLayout());
		samplingLayoutPanel = new JPanel();
		samplingLayoutPanel.setBackground(mainColor);
		samplingLayoutPanel.setLayout(new BorderLayout());
		waterfallLayoutPanel = new JPanel();
		waterfallLayoutPanel.setBackground(mainColor);
		waterfallLayoutPanel.setLayout(new BorderLayout());
		peaksLayoutPanel = new JPanel();
		peaksLayoutPanel.setBackground(mainColor);
		peaksLayoutPanel.setLayout(new BorderLayout());
		centerLayoutPanel = new JPanel();
		centerLayoutPanel.setBackground(mainColor);
		centerLayoutPanel.setLayout(new BorderLayout());
		bottomLayoutPanel = new JPanel();
		bottomLayoutPanel.setBackground(mainColor);
		bottomLayoutPanel.setLayout(new BorderLayout());
		
		frequencyStartPanel = new JPanel();
		frequencyStartPanel.setBackground(mainColor);
		frequencyEndPanel = new JPanel();
		frequencyEndPanel.setBackground(mainColor);
		frequencyPanel = new JPanel();
		frequencyPanel.setBackground(mainColor);
		frequencyStartPanel.setLayout(new BorderLayout());
		frequencyEndPanel.setLayout(new BorderLayout());
		frequencyPanel.add(frequencyStartPanel);
		frequencyPanel.add(frequencyEndPanel);
		frequencyLayoutPanel.add(frequencyPanel, BorderLayout.LINE_START);
		
		FFTPanel = new JPanel();
		FFTPanel.setBackground(mainColor);
		gainPanel = new JPanel();
		gainPanel.setBackground(mainColor);
		samplesPanel = new JPanel();
		samplesPanel.setBackground(mainColor);
		samplingPanel = new JPanel();
		samplingPanel.setBackground(mainColor);
		FFTPanel.setLayout(new BorderLayout());
		gainPanel.setLayout(new BorderLayout());
		samplesPanel.setLayout(new BorderLayout());
		samplingPanel.add(FFTPanel);
		samplingPanel.add(gainPanel);
		samplingPanel.add(samplesPanel);
		samplingLayoutPanel.add(samplingPanel, BorderLayout.LINE_START);

		
		waterfallStartPanel = new JPanel();
		waterfallStartPanel.setBackground(mainColor);
		waterfallLengthPanel = new JPanel();
		waterfallLengthPanel.setBackground(mainColor);
		waterfallPanel = new JPanel();
		waterfallPanel.setBackground(mainColor);
		waterfallStartPanel.setLayout(new BorderLayout());
		waterfallLengthPanel.setLayout(new BorderLayout());
		waterfallPanel.add(waterfallStartPanel);
		waterfallPanel.add(waterfallLengthPanel);
		waterfallLayoutPanel.add(waterfallPanel, BorderLayout.LINE_START);

		
		peaksPanel = new JPanel();
		peaksPanel.setBackground(mainColor);
		peaksPanel.setLayout(new BorderLayout());
		peaksLayoutPanel.add(peaksPanel, BorderLayout.LINE_START);
		
		
		tabbedPane.add("Frequency", frequencyLayoutPanel);
		tabbedPane.add("Sampling", samplingLayoutPanel);
		tabbedPane.add("Waterfall", waterfallLayoutPanel);
		tabbedPane.add("Peaks", peaksLayoutPanel);	
		/*tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, new JButton(
		        "+"));*/
		add(tabbedPane, BorderLayout.NORTH);
		
		/*tabbedPane.setBackgroundAt(0, mainColor);
		tabbedPane.setBackgroundAt(1, mainColor);
		tabbedPane.setBackgroundAt(2, mainColor);
		tabbedPane.setBackgroundAt(3, mainColor);
		frequencyLayoutPanel.getParent().setBackground(mainColor);*/
		
		centerPanel = new JPanel();
		centerPanel.setBackground(mainColor);
		centerLayoutPanel.add(centerPanel,BorderLayout.LINE_START);
		add(centerLayoutPanel, BorderLayout.CENTER);
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(mainColor);
		bottomPanel.setLayout(new FlowLayout());
		bottomLayoutPanel.add(bottomPanel, BorderLayout.LINE_START);
		add(bottomLayoutPanel, BorderLayout.SOUTH);
		

		
		//setLayout(new MigLayout("", "[123.00px,grow,leading]", "[][20px][][][20px][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));

		JLabel lblNewLabel = new JLabel("Frequency start [MHz]");
		//lblNewLabel.setForeground(Color.WHITE);
		//add(lblNewLabel, "cell 0 0,growx,aligny center");
		frequencyStartPanel.add(lblNewLabel, BorderLayout.NORTH);

		FrequencySelectorPanel frequencySelectorStart = new FrequencySelectorPanel(minFreq, maxFreq, freqStep, minFreq);
		//add(frequencySelectorStart, "cell 0 1,grow");
		frequencySelectorStart.setValue(hackRFSettings.getFrequencyStart());
		frequencyStartPanel.add(frequencySelectorStart, BorderLayout.CENTER);


		JLabel lblFrequencyEndmhz = new JLabel("Frequency end [MHz]");
		//lblFrequencyEndmhz.setForeground(Color.WHITE);
		//add(lblFrequencyEndmhz, "cell 0 3,alignx left,aligny center");
		frequencyEndPanel.add(lblFrequencyEndmhz, BorderLayout.NORTH);

		
		FrequencySelectorPanel frequencySelectorEnd = new FrequencySelectorPanel(minFreq, maxFreq, freqStep, maxFreq);
		//add(frequencySelectorEnd, "cell 0 4,grow");
		frequencySelectorEnd.setValue(hackRFSettings.getFrequencyEnd());
		frequencyEndPanel.add(frequencySelectorEnd, BorderLayout.CENTER);


		JLabel lblFftBinhz = new JLabel("FFT Bin [Hz]");
		//lblFftBinhz.setForeground(Color.WHITE);
		//add(lblFftBinhz, "cell 0 6");
		FFTPanel.add(lblFftBinhz, BorderLayout.NORTH);

		JSpinner spinnerFFTBinHz = new JSpinner();
		spinnerFFTBinHz.setFont(new Font("Monospaced", Font.BOLD, 16));
		spinnerFFTBinHz.setModel(new SpinnerListModel(
				new String[] { "1000", "2000", "5000", "10 000", "20 000", "50 000", "100 000", "200 000", "500 000", "1 000 000", "2 000 000", "5 000 000" }));
		//add(spinnerFFTBinHz, "cell 0 7,growx");
		((ListEditor) spinnerFFTBinHz.getEditor()).getTextField().setHorizontalAlignment(JTextField.RIGHT);
		spinnerFFTBinHz.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				hackRFSettings.setFFTBin(Integer.parseInt(spinnerFFTBinHz.getValue().toString().replaceAll("\\s", "")));
			}
		});
		spinnerFFTBinHz.setValue("100 000");
		FFTPanel.add(spinnerFFTBinHz, BorderLayout.CENTER);

		/*JLabel lblGain = new JLabel("Gain [dB]\t");
		//lblGain.setForeground(Color.WHITE);
		add(lblGain, "cell 0 9");
		gainPanel.add(lblGain, BorderLayout.NORTH);*/

		JSlider sliderGain = new JSlider(JSlider.HORIZONTAL, 0, 100, 2);
		sliderGain.setFont(new Font("Monospaced", Font.BOLD, 16));
		sliderGain.setBackground(Color.BLACK);
		sliderGain.setForeground(Color.WHITE);
		//add(sliderGain, "flowy,cell 0 10,growx");
		gainPanel.add(sliderGain, BorderLayout.CENTER);

		JLabel lbl_gainValue = new JLabel(hackRFSettings.getGain() + "dB");
		//lbl_gainValue.setForeground(Color.WHITE);
		//add(lbl_gainValue, "cell 0 10,alignx right");
		
		sliderGain.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				int val = sliderGain.getValue();
				hackRFSettings.setGain(val);
				lbl_gainValue.setText(
						String.format("Gain [dB]     "+" %ddB  [LNA: %ddB  VGA: %ddB]", hackRFSettings.getGain(), hackRFSettings.getLNAGain(), hackRFSettings.getVGAGain()));
			}
		});
		sliderGain.setValue(hackRFSettings.getGain());
		gainPanel.add(lbl_gainValue, BorderLayout.NORTH);

		JLabel lblNumberOfSamples = new JLabel("Number of samples");
		//lblNumberOfSamples.setForeground(Color.WHITE);
		//add(lblNumberOfSamples, "cell 0 12");
		samplesPanel.add(lblNumberOfSamples, BorderLayout.NORTH);

		JSpinner spinner_numberOfSamples = new JSpinner();
		spinner_numberOfSamples.setModel(new SpinnerListModel(new String[] { "8192", "16384", "32768", "65536", "131072", "262144" }));
		spinner_numberOfSamples.setFont(new Font("Monospaced", Font.BOLD, 16));
		((ListEditor) spinner_numberOfSamples.getEditor()).getTextField().setHorizontalAlignment(JTextField.RIGHT);
		((ListEditor) spinner_numberOfSamples.getEditor()).getTextField().setEditable(false);
		;
		//add(spinner_numberOfSamples, "cell 0 13,growx");
		samplesPanel.add(spinner_numberOfSamples, BorderLayout.CENTER);
		
		JCheckBox chckbxAntennaPower = new JCheckBox("Antenna power");
		chckbxAntennaPower.setBackground(Color.BLACK);
		chckbxAntennaPower.setForeground(Color.WHITE);
		//add(chckbxAntennaPower, "cell 0 15");
		chckbxAntennaPower.setSelected(hackRFSettings.getAntennaPowerEnable());
		chckbxAntennaPower.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hackRFSettings.setAntennaPowerEnable(chckbxAntennaPower.isSelected());
			}
		});
		centerPanel.add(chckbxAntennaPower);


		JLabel lblWaterfallPaletteStart = new JLabel("Waterfall palette start [dB]");
		//lblWaterfallPaletteStart.setForeground(Color.WHITE);
		//add(lblWaterfallPaletteStart, "cell 0 18");
		waterfallStartPanel.add(lblWaterfallPaletteStart, BorderLayout.NORTH);

		JSlider slider_waterfallPaletteStart = new JSlider();
		slider_waterfallPaletteStart.setForeground(Color.WHITE);
		slider_waterfallPaletteStart.setBackground(Color.BLACK);
		slider_waterfallPaletteStart.setMinimum(-100);
		slider_waterfallPaletteStart.setMaximum(0);
		slider_waterfallPaletteStart.setValue(-30);
		//add(slider_waterfallPaletteStart, "cell 0 19,growx");
		slider_waterfallPaletteStart.setValue(hackRFSettings.getSpectrumPaletteStart());
		slider_waterfallPaletteStart.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				hackRFSettings.setSpectrumPaletteStart(slider_waterfallPaletteStart.getValue());
			}
		});
		waterfallStartPanel.add(slider_waterfallPaletteStart, BorderLayout.CENTER);
		

		spinner_numberOfSamples.setValue(hackRFSettings.getSamples() + "");
		spinner_numberOfSamples.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				hackRFSettings.setSamples(Integer.parseInt(spinner_numberOfSamples.getValue().toString()));
			}
		});

		JLabel lblWaterfallPaletteLength = new JLabel("Waterfall palette length [dB]");
		//lblWaterfallPaletteLength.setForeground(Color.WHITE);
		//add(lblWaterfallPaletteLength, "cell 0 21");
		waterfallLengthPanel.add(lblWaterfallPaletteLength, BorderLayout.NORTH);

		JSlider slider_waterfallPaletteSize = new JSlider(HackRFSweepSpectrumAnalyzer.SPECTRUM_PALETTE_SIZE_MIN, 100);
		slider_waterfallPaletteSize.setBackground(Color.BLACK);
		slider_waterfallPaletteSize.setForeground(Color.WHITE);
		//add(slider_waterfallPaletteSize, "cell 0 22,growx");

		slider_waterfallPaletteSize.setValue(hackRFSettings.getSpectrumPaletteSize());

		slider_waterfallPaletteSize.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				hackRFSettings.setSpectrumPaletteSize(slider_waterfallPaletteSize.getValue());
			}
		});
		waterfallLengthPanel.add(slider_waterfallPaletteSize, BorderLayout.CENTER);

		FrequencyRangeSelector frequencyRangeSelector = new FrequencyRangeSelector(frequencySelectorStart, frequencySelectorEnd, new PropertyChangeListener()
		{
			@Override public void propertyChange(PropertyChangeEvent evt)
			{
				hackRFSettings.setFrequency(frequencySelectorStart.getValue(), frequencySelectorEnd.getValue());
			}
		});

		JCheckBox chckbxShowPeaks = new JCheckBox("Show peaks");		
		chckbxShowPeaks.setForeground(Color.WHITE);
		chckbxShowPeaks.setBackground(Color.BLACK);
		//add(chckbxShowPeaks, "cell 0 24,growx");
		peaksPanel.add(chckbxShowPeaks, BorderLayout.NORTH);
		
		PeakFalloutPanel peakFalloutPanel = new PeakFalloutPanel();
		peakFalloutPanel.setVisible(false);
		//add(peakFalloutPanel, "cell 0 25,growx");
		peaksPanel.add(peakFalloutPanel, BorderLayout.CENTER);
		
		JCheckBox chckbxRemoveSpurs = new JCheckBox("Spur filter (may distort real signals)");
		chckbxRemoveSpurs.setForeground(Color.WHITE);
		chckbxRemoveSpurs.setBackground(Color.BLACK);
		//add(chckbxRemoveSpurs, "cell 0 26");
		centerPanel.add(chckbxRemoveSpurs);
		
		txtHackrfConnected = new JLabel();
		txtHackrfConnected.setText("HackRF connected");
		//txtHackrfConnected.setForeground(Color.WHITE);
		txtHackrfConnected.setBackground(Color.BLACK);
		//add(txtHackrfConnected, "cell 0 29,growx");
		//txtHackrfConnected.setColumns(10);
		txtHackrfConnected.setBorder(null);
		bottomPanel.add(txtHackrfConnected);
		
		JButton btnPause = new JButton("Pause");
		//add(btnPause, "cell 0 31,growx");
		btnPause.setBackground(Color.black);
		bottomPanel.add(btnPause);
		
		
		
		
		btnPause.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				hackRFSettings.setCapturing(!hackRFSettings.isCapturing());	
			}
		});
		hackRFSettings.registerListener(new HackRFSettings.HackRFEventAdapter()
		{
			@Override public void captureStateChanged(boolean isCapturing)
			{
				btnPause.setText(isCapturing ? "Pause"  : "Resume");
			}
			@Override public void hardwareStatusChanged(boolean hardwareSendingData)
			{
				txtHackrfConnected.setText("GIANO "+(hardwareSendingData ? "Found":"Found"));
			}
		});;
		
		JCheckBox chckbxFilterSpectrum = new JCheckBox("Filter spectrum");
		chckbxFilterSpectrum.setBackground(Color.BLACK);
		chckbxFilterSpectrum.setForeground(Color.WHITE);
		//		add(chckbxFilterSpectrum, "cell 0 23");

		chckbxShowPeaks.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				hackRFSettings.setChartPeaksVisibility(chckbxShowPeaks.isSelected());
				peakFalloutPanel.setVisible(chckbxShowPeaks.isSelected());
			}
		});

		chckbxFilterSpectrum.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				hackRFSettings.setFilterSpectrum(chckbxFilterSpectrum.isSelected());
			}
		});
		
		chckbxRemoveSpurs.setSelected(hackRFSettings.isSpurRemoval());
		chckbxRemoveSpurs.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				hackRFSettings.setSpurRemoval(chckbxRemoveSpurs.isSelected());
			}
		});
	}

}

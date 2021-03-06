package jspectrumanalyzer.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.text.NumberFormat;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JSpinner.ListEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.block.LineBorder;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;

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
	
	//private JTabbedPane tabbedPane;
	private JPanel frequencyStartPanel, frequencyEndPanel, frequencyTitlePanel,frequencyPanel; 
	private JPanel FFTPanel, gainPanel, samplesPanel, samplingPanel;
	private JPanel waterfallStartPanel, waterfallLengthPanel, waterfallPanel; 
	private JPanel peaksPanel;
	private JPanel tresholdPanel;
	private JPanel presetsLayoutPanel, presetsUpperPanel, presetsCenterPanel, presetsSouthPanel;

	private JPanel centerPanel, bottomPanel;
	
	private JPanel frequencyLayoutPanel, samplingLayoutPanel, waterfallLayoutPanel, peaksLayoutPanel, centerLayoutPanel, bottomLayoutPanel;
	private FrequencySelectorPanel frequencySelectorStart;
	private FrequencySelectorPanel frequencySelectorEnd;
	private JPanel tresholdLayoutPanel;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu frequencyMenu, samplingMenu, waterfallMenu, peaksMenu, filterMenu, tresholdMenu, presetsMenu;
	private JButton searchTreasholdButton;
	private JTextField tresholdTextField;
	private JSpinner tresholdSpinner;
	
	private IOPresets ioPresets = null;
	private JList presetList;
	private JButton newPresetButton, savePresetButton, deletePresetButton, selectPresetButton; /*modifyPresetButton,*/
	private String frequencyTitle = "Default";
	private JLabel lblFrequencyTitle;
	private Preset selected;
	
	public JButton getSearchTreasholdButton() {
		return searchTreasholdButton;
	}

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

		/*tabbedPane = new JTabbedPane();
		tabbedPane.setOpaque(false);
		tabbedPane.setBackground(mainColor);*/
		
		frequencyMenu = new JMenu("Frequency");
		frequencyMenu.setForeground(Color.WHITE);
		samplingMenu = new JMenu("Sampling");
		samplingMenu.setForeground(Color.WHITE);
		waterfallMenu = new JMenu("Waterfall");
		waterfallMenu.setForeground(Color.WHITE);
		peaksMenu = new JMenu("Peaks");
		peaksMenu.setForeground(Color.WHITE);
		filterMenu = new JMenu("Filter");
		filterMenu.setForeground(Color.WHITE);
		tresholdMenu = new JMenu("Treshold");
		tresholdMenu.setForeground(Color.WHITE);
		presetsMenu = new JMenu("Presets");
		presetsMenu.setForeground(Color.WHITE);
		
		menuBar.setBackground(mainColor);
		
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
		tresholdLayoutPanel = new JPanel();
		tresholdLayoutPanel.setBackground(mainColor);
		presetsLayoutPanel = new JPanel();
		presetsLayoutPanel.setBackground(mainColor);
		presetsLayoutPanel.setLayout(new BorderLayout());
		
		frequencyStartPanel = new JPanel();
		frequencyStartPanel.setBackground(mainColor);
		frequencyEndPanel = new JPanel();
		frequencyEndPanel.setBackground(mainColor);
		frequencyPanel = new JPanel();
		frequencyPanel.setLayout(new BorderLayout());
		frequencyPanel.setBackground(mainColor);
		frequencyStartPanel.setLayout(new BorderLayout());
		frequencyEndPanel.setLayout(new BorderLayout());
		frequencyTitlePanel = new JPanel();
		frequencyTitlePanel.setBackground(mainColor);
		frequencyPanel.add(frequencyTitlePanel, BorderLayout.NORTH);
		frequencyPanel.add(frequencyStartPanel, BorderLayout.LINE_START);
		frequencyPanel.add(frequencyEndPanel, BorderLayout.LINE_END);
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
		
		tresholdPanel = new JPanel();
		tresholdPanel.setBackground(mainColor);
		searchTreasholdButton = new JButton();
		searchTreasholdButton.setText("RICERCA");
		searchTreasholdButton.setBackground(settingColor);
		searchTreasholdButton.setContentAreaFilled(false);
		searchTreasholdButton.setOpaque(true);
		searchTreasholdButton.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
		/*tresholdTextField = new JFormattedTextField(DoubleFormat.getNumberInstance());
		tresholdTextField.setMaximumSize(new Dimension(0, -40));
		tresholdTextField.setColumns(10);*/
		SpinnerModel model = new SpinnerNumberModel(-60, -110, 0, 10);     
		tresholdSpinner = new JSpinner(model);
		tresholdPanel.add(searchTreasholdButton);
		tresholdPanel.add(tresholdSpinner);
		tresholdLayoutPanel.add(tresholdPanel);
		tresholdMenu.add(tresholdLayoutPanel);
		//TODO 
		
		//Presets
		presetsUpperPanel = new JPanel();
		presetsUpperPanel.setBackground(mainColor);
		presetsUpperPanel.setForeground(Color.WHITE);
		
		presetsCenterPanel = new JPanel();
		presetsCenterPanel.setBackground(mainColor);
		presetsCenterPanel.setForeground(Color.WHITE);
		
		presetsSouthPanel = new JPanel();
		presetsSouthPanel.setBackground(mainColor);
		presetsSouthPanel.setForeground(Color.WHITE);
		
		try {
			ioPresets = new IOPresets();
		} catch (ClassNotFoundException e1) {
			new PresetErrorFrame(1);
		} catch (FileNotFoundException e2) {
			new PresetErrorFrame(1);
		} catch (IOException e2) {
			new PresetErrorFrame(2);
		}

		
		Collection<String> listPresetsName = null;
		try {
			listPresetsName = ioPresets.getListName();
		} catch (ClassNotFoundException e1) {
			new PresetErrorFrame(2);
		} catch (FileNotFoundException e1) {
			new PresetErrorFrame(1);
		} catch (IOException e1) {
			new PresetErrorFrame(2);
		}
		
		DefaultListModel<String> presetListModel = new DefaultListModel<String>();
		for(String s : listPresetsName){
			presetListModel.addElement(s);
		}
		presetList = new JList<String>(presetListModel);
		presetList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Collection<Preset> listP = null;
				try {
					listP = ioPresets.getPresets();
				} catch (ClassNotFoundException e1) {
					new PresetErrorFrame(2);
				} catch (FileNotFoundException e1) {
					new PresetErrorFrame(1);
				} catch (IOException e1) {
					new PresetErrorFrame(2);
				}
				if(ioPresets.getDefaultPreset().getName().equals((String)presetListModel.getElementAt(presetList.getSelectedIndex()))){
					selectPresetButton.setEnabled(true);
					deletePresetButton.setEnabled(false);
				}
				else{
					selectPresetButton.setEnabled(true);
					deletePresetButton.setEnabled(true);
				}
				for(Preset p : listP){
					if(p.getName().equals((String) presetListModel.getElementAt(presetList.getSelectedIndex())))
							selected = p;
				} 
			}
		});
		
		//Presets Buttons and their relatives ActionListener/Perfromed
		newPresetButton = new JButton("NEW");
		newPresetButton.setBackground(settingColor);
		newPresetButton.setContentAreaFilled(false);
		newPresetButton.setOpaque(true);
		newPresetButton.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
		newPresetButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//UI
				JFrame insertFrame = new JFrame("New Preset");
				insertFrame.setBackground(HackRFSweepSettingsUI.mainColor);
				insertFrame.setVisible(true);
				insertFrame.setSize(new Dimension(400,150));
				insertFrame.setLocationRelativeTo(null);
				
				JPanel layoutPanel, centerPanel, bottomPanel;
				JLabel lblName, lblMinV, lblMaxV, lblMessage;
				JTextField txtName, txtMinV, txtMaxV;
				JButton saveButton;
				
				//UI
				layoutPanel = new JPanel();
				layoutPanel.setLayout(new BorderLayout());
				layoutPanel.setBackground(HackRFSweepSettingsUI.mainColor);
				
				centerPanel = new JPanel();
				centerPanel.setLayout(new GridLayout(3,2));
				centerPanel.setBackground(HackRFSweepSettingsUI.mainColor);
				
				bottomPanel = new JPanel();
				bottomPanel.setLayout(new BorderLayout());
				bottomPanel.setBackground(HackRFSweepSettingsUI.mainColor);
				
				lblMessage = new JLabel("Insert values and Save");
				lblMessage.setForeground(HackRFSweepSettingsUI.mainColor);
				lblMessage.setBackground(Color.BLACK);
				
				lblName = new JLabel("Name");
				lblName.setBackground(HackRFSweepSettingsUI.mainColor);
				lblName.setForeground(Color.BLACK);
				
				txtName = new JTextField();
				txtName.setEditable(true);
				
				lblMinV = new JLabel("Freq. Min");
				lblMinV.setBackground(HackRFSweepSettingsUI.mainColor);
				lblMinV.setForeground(Color.BLACK);
				
				txtMinV = new JTextField();
				txtMinV.setEditable(true);
				
				lblMaxV = new JLabel("Freq. Max");
				lblMaxV.setBackground(HackRFSweepSettingsUI.mainColor);
				lblMaxV.setForeground(Color.BLACK);
				
				txtMaxV = new JTextField();
				txtMaxV.setEditable(true);
				
				saveButton = new JButton("SAVE");
				saveButton.setBackground(HackRFSweepSettingsUI.settingColor);
				saveButton.setContentAreaFilled(false);
				saveButton.setOpaque(true);
				saveButton.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
				
				centerPanel.add(lblName);
				centerPanel.add(txtName);
				centerPanel.add(lblMinV);
				centerPanel.add(txtMinV);
				centerPanel.add(lblMaxV);
				centerPanel.add(txtMaxV);
				
				bottomPanel.add(saveButton, BorderLayout.CENTER);
				
				layoutPanel.add(centerPanel, BorderLayout.CENTER);
				layoutPanel.add(bottomPanel, BorderLayout.SOUTH);
				
				insertFrame.add(layoutPanel);
				
				//ActionListener\Performed
				saveButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String name, sMinV, sMaxV;
						int minV, maxV;
						Preset newP = null;
						
						name = txtName.getText();
						sMinV = txtMinV.getText();
						sMaxV = txtMaxV.getText();
						System.out.println(name + sMinV + sMaxV);
						minV = Integer.parseInt(sMinV);
						maxV = Integer.parseInt(sMaxV);
						System.out.println(name + minV + maxV);
						try{
							newP = new Preset(minV, maxV, name);
							ioPresets.add(newP);
							presetListModel.addElement(newP.getName());
						}catch(IllegalArgumentException iea){
							insertFrame.setVisible(false);
							new PresetErrorFrame(4);
						} catch (ClassNotFoundException | IOException e1) {
							new PresetErrorFrame(2);
						}
						insertFrame.setVisible(false);
					}
				});
			}	
		});
		
		selectPresetButton = new JButton("SELECT");
		selectPresetButton.setBackground(settingColor);
		selectPresetButton.setContentAreaFilled(false);
		selectPresetButton.setOpaque(true);
		selectPresetButton.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
		selectPresetButton.setEnabled(false);
		selectPresetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frequencySelectorStart.setValue(selected.getMinValue());
				frequencySelectorEnd.setValue(selected.getMaxValue());
				lblFrequencyTitle.setText(selected.getName());
			}
		});
		
		deletePresetButton = new JButton("DELETE");
		deletePresetButton.setBackground(settingColor);
		deletePresetButton.setContentAreaFilled(false);
		deletePresetButton.setOpaque(true);
		deletePresetButton.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
		deletePresetButton.setEnabled(false);
		deletePresetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ioPresets.deletePreset(selected);
				} catch (ClassNotFoundException e1) {
					new PresetErrorFrame(2);
				} catch (FileNotFoundException e1) {
					new PresetErrorFrame(1);
				} catch (IOException e1) {
					new PresetErrorFrame(2);
				}
				presetListModel.removeElement(presetListModel.getElementAt(presetList.getSelectedIndex()));
			}
		});
		
		savePresetButton = new JButton("SAVE");
		savePresetButton.setBackground(settingColor);
		savePresetButton.setContentAreaFilled(false);
		savePresetButton.setOpaque(true);
		savePresetButton.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
		savePresetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//String name = JOptionPane.showInputDialog("Name");
				JFrame insertFrame = new JFrame("Save Preset");
				insertFrame.setBackground(HackRFSweepSettingsUI.mainColor);
				insertFrame.setVisible(true);
				insertFrame.setSize(new Dimension(400,150));
				insertFrame.setLocationRelativeTo(null);
				
				JPanel layout = new JPanel();
				layout.setBackground(mainColor);
				layout.setLayout(new BorderLayout());
				
				JPanel upper = new JPanel();
				upper.setBackground(mainColor);
				upper.setLayout(new GridLayout(1, 2));
				
				JLabel lblName = new JLabel("Name");
				lblName.setBackground(HackRFSweepSettingsUI.mainColor);
				lblName.setForeground(Color.BLACK);
				
				JTextField txtName = new JTextField();
				txtName.setSize(300, 200);
				txtName.setEditable(true);
				
				JButton saveButton = new JButton("SAVE");
				saveButton.setBackground(HackRFSweepSettingsUI.settingColor);
				saveButton.setContentAreaFilled(false);
				saveButton.setOpaque(true);
				saveButton.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
				
				upper.add(lblName);
				upper.add(txtName);
				layout.add(upper, BorderLayout.CENTER);
				layout.add(saveButton, BorderLayout.SOUTH);
				insertFrame.add(layout);
				
				saveButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Preset newP = null;
						String name = txtName.getText();
						if(name.isEmpty()){
							new PresetErrorFrame(3);
						}
						else{
							int minV = frequencySelectorStart.getValue();
							int maxV = frequencySelectorEnd.getValue();
							try{
								newP = new Preset(minV, maxV, name);
								ioPresets.add(newP);
								presetListModel.addElement(newP.getName());
							}catch(IllegalArgumentException iea){
								insertFrame.setVisible(false);
								new PresetErrorFrame(3);
							} catch (ClassNotFoundException e1) {
								new PresetErrorFrame(2);
							} catch (FileNotFoundException e1) {
								new PresetErrorFrame(1);
							} catch (IOException e1) {
								new PresetErrorFrame(2);
							}
						}
						insertFrame.setVisible(false);
					}
				});
			}
		});
		
		presetsUpperPanel.add(newPresetButton);
		presetsCenterPanel.add(new JScrollPane(presetList));
		presetsSouthPanel.add(selectPresetButton);
		presetsSouthPanel.add(deletePresetButton);
		presetsLayoutPanel.add(presetsUpperPanel, BorderLayout.NORTH);
		presetsLayoutPanel.add(presetsCenterPanel, BorderLayout.CENTER);
		presetsLayoutPanel.add(presetsSouthPanel, BorderLayout.SOUTH);
		presetsMenu.add(presetsLayoutPanel);
		frequencyPanel.add(savePresetButton, BorderLayout.SOUTH);
		
		frequencyMenu.add(frequencyLayoutPanel);
		samplingMenu.add(samplingLayoutPanel);
		waterfallMenu.add(waterfallLayoutPanel);
		peaksMenu.add(peaksLayoutPanel);
		filterMenu.add(centerLayoutPanel);
		presetsMenu.add(presetsLayoutPanel);
		
		menuBar.add(frequencyMenu);
		menuBar.add(samplingMenu);
		menuBar.add(waterfallMenu);
		menuBar.add(peaksMenu);
		menuBar.add(filterMenu);
		menuBar.add(tresholdMenu);
		menuBar.add(presetsMenu);
		add(menuBar, BorderLayout.LINE_START);
		
		centerPanel = new JPanel();
		centerPanel.setBackground(mainColor);
		centerLayoutPanel.add(centerPanel,BorderLayout.LINE_START);
		//add(centerLayoutPanel, BorderLayout.CENTER);
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(mainColor);
		bottomPanel.setLayout(new FlowLayout());
		bottomLayoutPanel.add(bottomPanel, BorderLayout.LINE_START);
		add(bottomLayoutPanel, BorderLayout.LINE_END);
		

		
		//setLayout(new MigLayout("", "[123.00px,grow,leading]", "[][20px][][][20px][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));
		lblFrequencyTitle = new JLabel("\t"+frequencyTitle+"\t");	
		lblFrequencyTitle.setForeground(Color.WHITE);
		frequencyTitlePanel.add(lblFrequencyTitle, BorderLayout.CENTER);
		
		
		JLabel lblNewLabel = new JLabel("Frequency start [MHz]");
		lblNewLabel.setForeground(Color.WHITE);
		//lblNewLabel.setForeground(Color.WHITE);
		//add(lblNewLabel, "cell 0 0,growx,aligny center");
		frequencyStartPanel.add(lblNewLabel, BorderLayout.NORTH);

		frequencySelectorStart = new FrequencySelectorPanel(minFreq, maxFreq, freqStep, minFreq);
		//add(frequencySelectorStart, "cell 0 1,grow");
		frequencySelectorStart.setValue(hackRFSettings.getFrequencyStart());
		frequencyStartPanel.add(frequencySelectorStart, BorderLayout.CENTER);


		JLabel lblFrequencyEndmhz = new JLabel("Frequency end [MHz]");
		lblFrequencyEndmhz.setForeground(Color.WHITE);
		//lblFrequencyEndmhz.setForeground(Color.WHITE);
		//add(lblFrequencyEndmhz, "cell 0 3,alignx left,aligny center");
		frequencyEndPanel.add(lblFrequencyEndmhz, BorderLayout.NORTH);

		
		frequencySelectorEnd = new FrequencySelectorPanel(minFreq, maxFreq, freqStep, maxFreq);
		//add(frequencySelectorEnd, "cell 0 4,grow");
		frequencySelectorEnd.setValue(hackRFSettings.getFrequencyEnd());
		frequencyEndPanel.add(frequencySelectorEnd, BorderLayout.CENTER);


		JLabel lblFftBinhz = new JLabel("FFT Bin [Hz]");
		lblFftBinhz.setForeground(Color.WHITE);
		//lblFftBinhz.setForeground(Color.WHITE);
		//add(lblFftBinhz, "cell 0 6");
		FFTPanel.add(lblFftBinhz, BorderLayout.NORTH);

		JSpinner spinnerFFTBinHz = new JSpinner();
		spinnerFFTBinHz.setFont(new Font("Monospaced", Font.BOLD, 16));
		//spinnerFFTBinHz.getEditor().getComponent(0).setBackground(settingColor);
		//spinnerFFTBinHz.setOpaque(true);
		//spinnerFFTBinHz.setBackground(settingColor);		
		//((JSpinner.DefaultEditor)spinnerFFTBinHz.getEditor()).getTextField().setBackground(mainColor);
		//((JSpinner.DefaultEditor)spinnerFFTBinHz.getEditor()).getTextField().setOpaque(true);
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
		sliderGain.setBackground(settingColor);
		sliderGain.setForeground(Color.WHITE);
		sliderGain.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
		//add(sliderGain, "flowy,cell 0 10,growx");
		gainPanel.add(sliderGain, BorderLayout.CENTER);

		JLabel lbl_gainValue = new JLabel(hackRFSettings.getGain() + "dB");
		lbl_gainValue.setForeground(Color.WHITE);
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
		lblNumberOfSamples.setForeground(Color.WHITE);
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
		chckbxAntennaPower.setBackground(mainColor);
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
		lblWaterfallPaletteStart.setForeground(Color.WHITE);
		//lblWaterfallPaletteStart.setForeground(Color.WHITE);
		//add(lblWaterfallPaletteStart, "cell 0 18");
		waterfallStartPanel.add(lblWaterfallPaletteStart, BorderLayout.NORTH);

		JSlider slider_waterfallPaletteStart = new JSlider();
		slider_waterfallPaletteStart.setForeground(Color.WHITE);
		slider_waterfallPaletteStart.setBackground(settingColor);
		slider_waterfallPaletteStart.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
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
		lblWaterfallPaletteLength.setForeground(Color.WHITE);
		//lblWaterfallPaletteLength.setForeground(Color.WHITE);
		//add(lblWaterfallPaletteLength, "cell 0 21");
		waterfallLengthPanel.add(lblWaterfallPaletteLength, BorderLayout.NORTH);

		JSlider slider_waterfallPaletteSize = new JSlider(HackRFSweepSpectrumAnalyzer.SPECTRUM_PALETTE_SIZE_MIN, 100);
		slider_waterfallPaletteSize.setBackground(settingColor);
		slider_waterfallPaletteSize.setForeground(Color.WHITE);
		slider_waterfallPaletteSize.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));

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
		chckbxShowPeaks.setBackground(mainColor);
		//add(chckbxShowPeaks, "cell 0 24,growx");
		peaksPanel.add(chckbxShowPeaks, BorderLayout.NORTH);
		
		PeakFalloutPanel peakFalloutPanel = new PeakFalloutPanel();
		peakFalloutPanel.setVisible(true);
		peakFalloutPanel.setBackground(mainColor);
		//add(peakFalloutPanel, "cell 0 25,growx");
		peaksPanel.add(peakFalloutPanel, BorderLayout.CENTER);
		
		JCheckBox chckbxRemoveSpurs = new JCheckBox("Spur filter (may distort real signals)");
		chckbxRemoveSpurs.setForeground(Color.WHITE);
		chckbxRemoveSpurs.setBackground(mainColor);
		//add(chckbxRemoveSpurs, "cell 0 26");
		centerPanel.add(chckbxRemoveSpurs);
		
		txtHackrfConnected = new JLabel();
		txtHackrfConnected.setText("HackRF connected");
		//txtHackrfConnected.setForeground(Color.WHITE);
		txtHackrfConnected.setBackground(Color.BLACK);
		txtHackrfConnected.setForeground(Color.WHITE);
		//add(txtHackrfConnected, "cell 0 29,growx");
		//txtHackrfConnected.setColumns(10);
		txtHackrfConnected.setBorder(null);
		bottomPanel.add(txtHackrfConnected);
		
		JButton btnPause = new JButton("Pause");
		//add(btnPause, "cell 0 31,growx");
		btnPause.setBackground(settingColor);
		btnPause.setContentAreaFilled(false);
		btnPause.setOpaque(true);
		btnPause.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
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
				//peakFalloutPanel.setVisible(chckbxShowPeaks.isSelected());
				peakFalloutPanel.setEnabledButton(chckbxShowPeaks.isSelected());
				
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
	
	public JSpinner getTresholdSpinner() {
		return tresholdSpinner;
	}

	public FrequencySelectorPanel getFrequencySelectorStart() {
		return frequencySelectorStart;
	}
	
	public FrequencySelectorPanel getFrequencySelectorEnd() {
		return frequencySelectorEnd;
	}
	
	public IOPresets getIOPresets(){
		return ioPresets;
	}

}

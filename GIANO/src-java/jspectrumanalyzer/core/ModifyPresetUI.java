package jspectrumanalyzer.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ModifyPresetUI extends JFrame {
	private JPanel layoutPanel, centerPanel, bottomPanel;
	private JLabel lblName, lblMinV, lblMaxV, lblMessage;
	private JTextField txtName, txtMinV, txtMaxV;
	private JButton saveButton;
	private IOPresets ioPreset = null;
	private Preset preset = null;
	
	public ModifyPresetUI(int index ,String name, String minValue, String maxValue) throws FileNotFoundException, IOException {
		try {
			ioPreset = new IOPresets();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Set
		this.setForeground(HackRFSweepSettingsUI.mainColor);
		this.setPreferredSize(new Dimension(700, 400));
		this.setVisible(true);
		
		//UI
		layoutPanel = new JPanel();
		layoutPanel.setLayout(new BorderLayout());
		layoutPanel.setForeground(HackRFSweepSettingsUI.mainColor);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(3,2));
		centerPanel.setForeground(HackRFSweepSettingsUI.mainColor);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setForeground(HackRFSweepSettingsUI.mainColor);
		
		lblMessage = new JLabel("Insert values and Save");
		lblMessage.setForeground(HackRFSweepSettingsUI.mainColor);
		lblMessage.setBackground(Color.BLACK);
		
		lblName = new JLabel("Name");
		lblName.setForeground(HackRFSweepSettingsUI.mainColor);
		lblName.setBackground(Color.BLACK);
		
		txtName = new JTextField(name);
		txtName.setEditable(true);
		
		lblMinV = new JLabel("Freq. Min");
		lblMinV.setForeground(HackRFSweepSettingsUI.mainColor);
		lblMinV.setBackground(Color.BLACK);
		
		txtMinV = new JTextField(minValue);
		txtMinV.setEditable(true);
		
		lblMaxV = new JLabel("Freq. Max");
		lblMaxV.setForeground(HackRFSweepSettingsUI.mainColor);
		lblMaxV.setBackground(Color.BLACK);
		
		txtMaxV = new JTextField(maxValue);
		txtMaxV.setEditable(true);
		
		centerPanel.add(lblName);
		centerPanel.add(txtName);
		centerPanel.add(lblMinV);
		centerPanel.add(txtMinV);
		centerPanel.add(lblMaxV);
		centerPanel.add(txtMaxV);
		
		saveButton = new JButton();
		saveButton.setBackground(HackRFSweepSettingsUI.settingColor);
		saveButton.setContentAreaFilled(false);
		saveButton.setOpaque(true);
		saveButton.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
		
		bottomPanel.add(saveButton, BorderLayout.CENTER);
		
		layoutPanel.add(centerPanel, BorderLayout.CENTER);
		layoutPanel.add(bottomPanel, BorderLayout.LINE_END);
		
		this.add(layoutPanel);
		
		//ActionListener\Performed
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name, sMinv, sMaxV;
				int minV, maxV;
				
				name = txtName.getText();
				sMinv = txtMinV.getText();
				sMaxV = txtMaxV.getText();
				try{
					minV = Integer.parseInt(sMinv);
					maxV = Integer.parseInt(sMaxV);
					preset = new Preset(minV, maxV, name);
					//ioPreset.modifyPreset(index, minV, maxV, name);
				}catch(Exception er){
					lblMessage.setText("Error! Insert right vslues!");
					lblMessage.setForeground(Color.RED);
				}
				dispose();
			}
		});
	}
	
	public Preset getPreset(){
		return preset;
	}
}

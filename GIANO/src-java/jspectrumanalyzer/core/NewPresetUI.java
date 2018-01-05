package jspectrumanalyzer.core;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;

public class NewPresetUI extends JFrame {
	private JPanel layoutPanel, centerPanel, bottomPanel;
	private JLabel lblName, lblMinV, lblMaxV, lblMessage;
	private JTextField txtName, txtMinV, txtMaxV;
	private JButton saveButton;
	private IOPresets ioPreset = null;
	private Preset preset = null;
	
	public NewPresetUI() throws FileNotFoundException, IOException{
		try {
			ioPreset = new IOPresets();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Set
		this.setBackground(HackRFSweepSettingsUI.mainColor);
		this.setVisible(true);
		this.setSize(new Dimension(400,150));
		
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
					ioPreset.add(preset);
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

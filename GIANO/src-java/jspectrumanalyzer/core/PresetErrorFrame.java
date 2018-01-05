package jspectrumanalyzer.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class PresetErrorFrame extends JFrame {
	private JLabel errorMessage;
	private JPanel lblPanel, buttonPanel, layoutPanel;
	private JButton okButton;
	
	public PresetErrorFrame(int i){
		this.setTitle("ERROR!");
		this.setLocationRelativeTo(null);
		this.setBackground(HackRFSweepSettingsUI.mainColor);
		this.setSize(new Dimension(500, 100));
		this.setResizable(false);
		this.setVisible(true);
		
		layoutPanel = new JPanel();
		layoutPanel.setLayout(new BorderLayout());
		layoutPanel.setBackground(HackRFSweepSettingsUI.mainColor);
		lblPanel = new JPanel();
		lblPanel.setBackground(HackRFSweepSettingsUI.mainColor);
		buttonPanel = new JPanel();
		buttonPanel.setBackground(HackRFSweepSettingsUI.mainColor);
		
		if(i == 1){//IOPreset initialization
			
			errorMessage = new JLabel("File SavedPreset not found!");
			errorMessage.setBackground(HackRFSweepSettingsUI.mainColor);
			errorMessage.setForeground(Color.WHITE);
			lblPanel.setBackground(HackRFSweepSettingsUI.mainColor);
			lblPanel.add(errorMessage);
			layoutPanel.add(lblPanel, BorderLayout.CENTER);
			
		}
		if(i == 2){//IOFile
			
			errorMessage = new JLabel("I/O Error!");
			errorMessage.setBackground(HackRFSweepSettingsUI.mainColor);
			errorMessage.setForeground(Color.WHITE);
			lblPanel.setBackground(HackRFSweepSettingsUI.mainColor);
			lblPanel.add(errorMessage);
			layoutPanel.add(lblPanel, BorderLayout.CENTER);
		}
		if(i == 3){ //IllegalArgumentException
			
			errorMessage = new JLabel("Insert valid name!");
			errorMessage.setBackground(HackRFSweepSettingsUI.mainColor);
			errorMessage.setForeground(Color.WHITE);
			lblPanel.setBackground(HackRFSweepSettingsUI.mainColor);
			lblPanel.add(errorMessage);
			layoutPanel.add(lblPanel, BorderLayout.CENTER);
		}
		if(i == 4){ //IllegalArgumentException
			
			errorMessage = new JLabel("Inserire dati corretti! (Name; FrqStart>1; FrqEnd<7250)");
			errorMessage.setBackground(HackRFSweepSettingsUI.mainColor);
			errorMessage.setForeground(Color.WHITE);
			lblPanel.setBackground(HackRFSweepSettingsUI.mainColor);
			lblPanel.add(errorMessage);
			layoutPanel.add(lblPanel, BorderLayout.CENTER);
		}
		
		if(i == 5){ //FileReset
			
			errorMessage = new JLabel("Preset File Backup not found! Now Restored");
			errorMessage.setBackground(HackRFSweepSettingsUI.mainColor);
			errorMessage.setForeground(Color.WHITE);
			lblPanel.setBackground(HackRFSweepSettingsUI.mainColor);
			lblPanel.add(errorMessage);
			layoutPanel.add(lblPanel, BorderLayout.CENTER);
		}
		
		okButton = new JButton("OK");
		okButton.setBackground(HackRFSweepSettingsUI.settingColor);
		okButton.setContentAreaFilled(false);
		okButton.setOpaque(true);
		okButton.setBorder((Border) new javax.swing.border.LineBorder(Color.BLACK));
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		});
		
		buttonPanel.add(okButton);
		layoutPanel.add(buttonPanel, BorderLayout.SOUTH);
		this.add(layoutPanel);
	}
}

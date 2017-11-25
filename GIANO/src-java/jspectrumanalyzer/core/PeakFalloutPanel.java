package jspectrumanalyzer.core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PeakFalloutPanel extends JPanel{

	private JRadioButton shortButton, mediumButton, longButton, infButton;
	private ButtonGroup buttonGroup;
	
	public PeakFalloutPanel() {
		
		this.setForeground(Color.WHITE);
		this.setBackground(Color.BLACK);
		
		shortButton = new JRadioButton("0,5");
		shortButton.setForeground(Color.WHITE);
		shortButton.setBackground(Color.BLACK);
		
		
		mediumButton = new JRadioButton("1");
		mediumButton.setForeground(Color.WHITE);
		mediumButton.setBackground(Color.BLACK);
		
		longButton = new JRadioButton("5");
		longButton.setForeground(Color.WHITE);
		longButton.setBackground(Color.BLACK);
		
		infButton = new JRadioButton("INFINITE");
		infButton.setForeground(Color.WHITE);
		infButton.setBackground(Color.BLACK);
		infButton.setSelected(true);
		
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(shortButton);
		buttonGroup.add(mediumButton);
		buttonGroup.add(longButton);
		buttonGroup.add(infButton);

		
		add(shortButton);
		add(mediumButton);
		add(longButton);
		add(infButton);
		
		ActionListener actionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(shortButton.isSelected()) 			
					PeakFalloutManager.setPeackFallout(PeakFallout.SHORT);
				else if(mediumButton.isSelected())		
					PeakFalloutManager.setPeackFallout(PeakFallout.MEDIUM);
				else if(longButton.isSelected())		
					PeakFalloutManager.setPeackFallout(PeakFallout.LONG);
				else if(infButton.isSelected())			
					PeakFalloutManager.setPeackFallout(PeakFallout.INF);
				//System.out.println(PeakFalloutManager.getMillis());
			}
		};
		
		shortButton.addActionListener(actionListener);
		mediumButton.addActionListener(actionListener);
		longButton.addActionListener(actionListener);
		infButton.addActionListener(actionListener);
		
	}
	
	/*public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.add(new PeakFalloutPanel());
	}*/
	
}

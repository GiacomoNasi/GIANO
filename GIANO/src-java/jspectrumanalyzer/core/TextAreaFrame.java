package jspectrumanalyzer.core;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class TextAreaFrame {

	private static TextAreaFrame instance = null;
	private JFrame frame;
	private JTextArea textArea;
	private JLabel upperLabel;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextAreaFrame window = new TextAreaFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TextAreaFrame() {
		initialize();
	}
	
	public static TextAreaFrame getNewFrame(){
		if(instance == null)
			instance = new TextAreaFrame();
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		
		upperLabel = new JLabel("");
		frame.getContentPane().add(upperLabel, BorderLayout.NORTH);
		
		
		textArea = new JTextArea();
		textArea.setBackground(HackRFSweepSettingsUI.mainColor);
		textArea.setForeground(Color.WHITE);
		textArea.setEditable(false);
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				instance = null;
				
			}
		});
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}
	
	public JLabel getUpperLabel() {
		return upperLabel;
	}
	
	public JFrame getFrame() {
		return frame;
	}

}

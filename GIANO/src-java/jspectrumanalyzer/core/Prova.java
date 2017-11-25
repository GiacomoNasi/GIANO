package jspectrumanalyzer.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
//  w w  w .j  av a 2 s  . co m
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.Component;
import javax.swing.JToggleButton;

public class Prova extends JPanel {

	/**
	 * Create the panel.
	 */
	public Prova() {
		
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		setLayout(new BorderLayout(0, 0));
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setOpaque(true);
		tabbedPane.setBackground(Color.YELLOW);
		add(tabbedPane, BorderLayout.NORTH);
		
		JLabel lblCiao = new JLabel("Ciao");
		tabbedPane.addTab("New tab", null, lblCiao, null);
		tabbedPane.setBackgroundAt(0, Color.CYAN);
		
		tabbedPane.getTabComponentAt(0).setBackground(Color.red);
		tabbedPane.setIgnoreRepaint(false);

		
		
		UIManager.put("TabbedPane.contentAreaColor", Color.BLACK);

	}
	
	
	

}



package jspectrumanalyzer.core;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ochafik.swing.syntaxcoloring.InputHandler.next_char;

public class IOPresets {
	private File backupFile;
	
	private ObjectOutputStream outS;
	private ObjectInputStream inS;
	
	private Preset defaultP;
	private Collection<Preset> listPresets;
	private boolean aggiorna;
	
	/*Inizializzo i file
	public IOPresets() throws FileNotFoundException, IOException, ClassNotFoundException{ 
		backupFile = new File("SavedPreset");
		
		outS = new ObjectOutputStream(new FileOutputStream(backupFile));
		
		defaultP = new Preset(2400, 2500, "Default");
		outS.writeObject(defaultP);
		aggiorna = true;
		
		listPresets = new ArrayList<Preset>();
		readPresets();
		
		outS.close();
		
		inS = new ObjectInputStream(new FileInputStream(backupFile));
		
		defaultP = (Preset) inS.readObject();
		System.out.println(defaultP.getName()+defaultP.getMaxValue()+defaultP.getMinValue());
		
		inS.close();
	}*/

	public IOPresets() throws FileNotFoundException, IOException, ClassNotFoundException{
		backupFile = new File("SavedPreset");		
		
		listPresets = new ArrayList<Preset>();
		try{
			readPresets();
		}catch(IOPresetInitException e){
			outS = new ObjectOutputStream(new FileOutputStream(backupFile));
			
			defaultP = new Preset(2400, 2500, "Default");
			outS.writeObject(defaultP);
			aggiorna = true;
			
			listPresets = new ArrayList<Preset>();
			try {
				readPresets();
			} catch (IOPresetInitException e1) {
				new PresetErrorFrame(1);
			}
			
			outS.close();
			new PresetErrorFrame(5);
		}
	
		aggiorna = false;
		
		inS = new ObjectInputStream(new FileInputStream(backupFile));
		
		defaultP = (Preset) inS.readObject();
		System.out.println(defaultP.getName()+defaultP.getMaxValue()+defaultP.getMinValue());
		
		inS.close();
	}
	
	public void add(Preset p) throws ClassNotFoundException, FileNotFoundException, IOException{
		listPresets.add(p);
		aggiorna = true;
	}
	
	public Collection<Preset> getPresets() throws ClassNotFoundException, FileNotFoundException, IOException{
		return listPresets;
	}
	
	public void deletePreset(Preset p) throws ClassNotFoundException, FileNotFoundException, IOException{
		listPresets.remove(p);
		aggiorna = true;
	}
	
	public Collection<String> getListName() throws ClassNotFoundException, FileNotFoundException, IOException{
		Collection<String> namePreset = new ArrayList<String>();
		
		for(Preset p : listPresets){
			namePreset.add(p.getName());
		}
		
		return namePreset;
	}
	
	public Preset getDefaultPreset(){
		return defaultP;
	}
	
	public void aggiornaFile() throws FileNotFoundException, IOException{
		if(aggiorna){
			outS = new ObjectOutputStream(new FileOutputStream(backupFile));
			
			for(Preset p : listPresets){
				outS.writeObject(p);
			}
			outS.close();
		}
	}
	
	private void readPresets() throws IOPresetInitException{
		try{
			inS = new ObjectInputStream(new FileInputStream(backupFile));
			
			while(true){
				try{
					listPresets.add((Preset) inS.readObject());
				}catch(EOFException e){
					break;
				}
			}
			
			inS.close();
		}catch(IOException | ClassNotFoundException e){
			throw new IOPresetInitException();
		}
	}

}

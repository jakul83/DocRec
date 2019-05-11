package GUI;

import java.awt.image.BufferedImage;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScanGroup {
	
	public SimpleIntegerProperty skgr;
	public SimpleStringProperty dataSkanu;
	public SimpleStringProperty nazwaGrupy;
	public SimpleIntegerProperty imagepages;
	public SimpleStringProperty dzialDocelowy;
	public SimpleBooleanProperty selected;
	public BufferedImage image;

	/**
	 * @return the skgr
	 */
	public int getSkgr() {
		return skgr.get();
	}
	/**
	 * @param skgr the skgr to set
	 */
	public void setSkgr(int skgr) {
		this.skgr = new SimpleIntegerProperty(skgr);
	}
	/**
	 * @return the dataSkanu
	 */
	public String getDataSkanu() {
		return dataSkanu.get();
	}
	/**
	 * @param dataSkanu the dataSkanu to set
	 */
	public void setDataSkanu(String dataSkanu) {
		this.dataSkanu = new SimpleStringProperty(dataSkanu);
	}
	/**
	 * @return the nazwaGrupy
	 */
	public String getNazwaGrupy() {
		return nazwaGrupy.get();
	}
	/**
	 * @param nazwaGrupy the nazwaGrupy to set
	 */
	public void setNazwaGrupy(String nazwaGrupy) {
		this.nazwaGrupy = new SimpleStringProperty(nazwaGrupy);
	}
	/**
	 * @return the imagepages
	 */
	public int getImagepages() {
		return imagepages.get();
	}
	/**
	 * @param imagepages the imagepages to set
	 */
	public void setImagepages(int imagepages) {
		this.imagepages = new SimpleIntegerProperty(imagepages);
	}
	/**
	 * @return the dzialDocelowy
	 */
	public String getDzialDocelowy() {
		return dzialDocelowy.get();
	}
	/**
	 * @param dzialDocelowy the dzialDocelowy to set
	 */
	public void setDzialDocelowy(String dzialDocelowy) {
		this.dzialDocelowy = new SimpleStringProperty(dzialDocelowy);
	}
	
	public boolean getSelected() {
		return selected.get();
	}
	/**
	 * @param dzialDocelowy the dzialDocelowy to set
	 */
	public void setSelected(boolean selected) {
		this.selected = new SimpleBooleanProperty(selected);
	}
	

	
	
	
	public ScanGroup(Integer skgr, String dataSkanu, String nazwaGrupy, int imagepages, String dzialDocelowy) {
		
		this.skgr = new SimpleIntegerProperty(skgr);
		this.dataSkanu =  new SimpleStringProperty(dataSkanu);
		this.nazwaGrupy = new SimpleStringProperty(nazwaGrupy);
		this.imagepages = new SimpleIntegerProperty(imagepages);
		this.dzialDocelowy = new SimpleStringProperty(dzialDocelowy);
		this.selected = new SimpleBooleanProperty(false);
		
		
	}
	public ScanGroup(int skgr, String nazwaGrupy, BufferedImage image) {
		this.skgr = new SimpleIntegerProperty(skgr);
		this.nazwaGrupy = new SimpleStringProperty(nazwaGrupy);
		this.image = image;
	}
	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	}



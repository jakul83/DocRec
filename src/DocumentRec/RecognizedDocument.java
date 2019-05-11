

package DocumentRec;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

public class RecognizedDocument {
	
	public String id;
	public String filePath;
	public String txtFilePath;
	public String idPozyczki;
	public String idProcesuKomornik;
	public String sygKM;
	public String sygSad;
	public String personalizacja;
	public String idWekaModel;
	public String typ;
	public String podtyp;
	public String opis1;
	public int result;
	public byte[] image;
	public String imageText;
	public int numbPages;
	public int sizeImage;
	public String pageInGroup;
	public String link;
	public String isPersonalized;
	public String executionCosts;
	public String firstDateCosts;
	public String db;
	public String spersonalizowac;

	
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the idPozyczki
	 */
	public String getIdPozyczki() {
		return idPozyczki;
	}
	/**
	 * @param idPozyczki the idPozyczki to set
	 */
	public void setIdPozyczki(String idPozyczki) {
		this.idPozyczki = idPozyczki;
	}
	/**
	 * @return the idProcesuKomornik
	 */
	public String getIdProcesuKomornik() {
		return idProcesuKomornik;
	}
	/**
	 * @param idProcesuKomornik the idProcesuKomornik to set
	 */
	public void setIdProcesuKomornik(String idProcesuKomornik) {
		this.idProcesuKomornik = idProcesuKomornik;
	}
	/**
	 * @return the sygKM
	 */
	public String getSygKM() {
		return sygKM;
	}
	/**
	 * @param sygKM the sygKM to set
	 */
	public void setSygKM(String sygKM) {
		this.sygKM = sygKM;
	}
	/**
	 * @return the personalizacja
	 */
	public String getPersonalizacja() {
		return personalizacja;
	}
	/**
	 * @param personalizacja the personalizacja to set
	 */
	public void setPersonalizacja(String personalizacja) {
		this.personalizacja = personalizacja;
	}
	/**
	 * @return the idWekaModel
	 */
	public String getIdWekaModel() {
		return idWekaModel;
	}
	/**
	 * @param idWekaModel the idWekaModel to set
	 */
	public void setIdWekaModel(String idWekaModel) {
		this.idWekaModel = idWekaModel;
	}
	/**
	 * @return the typ
	 */
	public String getTyp() {
		return typ;
	}
	/**
	 * @param typ the typ to set
	 */
	public void setTyp(String typ) {
		this.typ = typ;
	}
	/**
	 * @return the podtyp
	 */
	public String getPodtyp() {
		return podtyp;
	}
	/**
	 * @param podtyp the podtyp to set
	 */
	public void setPodtyp(String podtyp) {
		this.podtyp = podtyp;
	}
	/**
	 * @return the opis1
	 */
	public String getOpis1() {
		return opis1;
	}
	/**
	 * @param opis1 the opis1 to set
	 */
	public void setOpis1(String opis1) {
		this.opis1 = opis1;
	}
	public static void setPagesInGroup(List<RecognizedDocument> p) {
	int v = 0;
	int c = 0;
	for (RecognizedDocument d: p) {
		c=c+d.getNumbPages();
		
	}
	System.out.println("stron w grupie: "+c);
	int g=0;
	for (RecognizedDocument d: p) {
		String usedPages="";
		int f = d.getNumbPages();
		v=v+f;
		
		
			while (g!=v) {
				
				 usedPages = usedPages +Integer.toString(g)+";";
				 g=g+1;
				 c=c-1;
			}
		
		d.setPageInGroup(usedPages);
		System.out.println("pozostało stron: "+c);
		
	}
		
	}
	
	public static void setDataInGroup(List<RecognizedDocument> p, String id, String link, String isPersonalized, String executionCosts, String firstDateCosts) {
		
		for (RecognizedDocument r: p){
			if (r.getId().equals(id)) {
//				System.out.println("w if " +r.getId());
				r.setLink(link);
				r.setIsPersonalized(isPersonalized);
				r.setExecutionCosts(executionCosts);
				r.setFirstDateCosts(firstDateCosts);
//				System.out.println(r.getId()+" | "+r.getLink());
			}
//			System.out.println(r.getId()+" | "+r.getLink());
		}
		
		
	}
	
	public RecognizedDocument(String id, String filePath, String idPozyczki, String idProcesuKomornik, String sygKM, String sygSad, String personalizacja, String idWekaModel, String typ, String podtyp, String opis1, int result, String db) 
	{
		this.id = id;
		this.filePath= filePath;
		this.idPozyczki =idPozyczki;
		this.idProcesuKomornik= idProcesuKomornik;
		this.sygKM=sygKM;
		this.sygSad=sygSad;
		this.personalizacja=personalizacja;
		this.idWekaModel=idWekaModel;
		this.typ=typ;
		this.podtyp=podtyp;
		this.opis1=opis1;
		this.result = result;
		this.db =db;

	}
	public RecognizedDocument(int id, String filePath) {
		this.id = Integer.toString(id);
		this.filePath= filePath;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(filePath, id, idPozyczki, idProcesuKomornik, idWekaModel, opis1, personalizacja, podtyp,
				sygKM, typ);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RecognizedDocument)) {
			return false;
		}
		RecognizedDocument other = (RecognizedDocument) obj;
		return Objects.equals(filePath, other.filePath) 
				&& id == other.id
				&& Objects.equals(idPozyczki, other.idPozyczki)
				&& Objects.equals(idProcesuKomornik, other.idProcesuKomornik)
				&& Objects.equals(sygKM, other.sygKM);
	}
	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}
	/**
	 * @return the sygnaturaSad
	 */
	public String getSygSad() {
		return sygSad;
	}
	/**
	 * @param sygnaturaSad the sygnaturaSad to set
	 */
	public void setSygSad(String sygnaturaSad) {
		sygSad = sygnaturaSad;
	}
	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
	/**
	 * @return the imageText
	 */
	public String getImageText() {
		return imageText;
	}
	/**
	 * @param imageText the imageText to set
	 */
	public void setImageText(String imageText) {
		this.imageText = imageText;
	}
	/**
	 * @return the numbPages
	 */
	public int getNumbPages() {
		return numbPages;
	}
	/**
	 * @param numbPages the numbPages to set
	 */
	public void setNumbPages(int numbPages) {
		this.numbPages = numbPages;
	}
	/**
	 * @return the sizeImage
	 */
	public int getSizeImage() {
		return sizeImage;
	}
	/**
	 * @param sizeImage the sizeImage to set
	 */
	public void setSizeImage(int sizeImage) {
		this.sizeImage = sizeImage;
	}
	/**
	 * @return the pageInGroup
	 */
	public String getPageInGroup() {
		return pageInGroup;
	}
	/**
	 * @param pageInGroup the pageInGroup to set
	 */
	public void setPageInGroup(String pageInGroup) {
		this.pageInGroup = pageInGroup;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the isPersonalized
	 */
	public String getIsPersonalized() {
		return isPersonalized;
	}
	/**
	 * @param isPersonalized the isPersonalized to set
	 */
	public void setIsPersonalized(String isPersonalized) {
		this.isPersonalized = isPersonalized;
	}
	/**
	 * @return the executionCosts
	 */
	public String getExecutionCosts() {
		return executionCosts;
	}
	/**
	 * @param executionCosts the executionCosts to set
	 */
	public void setExecutionCosts(String executionCosts) {
		this.executionCosts = executionCosts;
	}
	/**
	 * @return the firstDateCosts
	 */
	public String getFirstDateCosts() {
		return firstDateCosts;
	}
	/**
	 * @param firstDateCosts the firstDateCosts to set
	 */
	public void setFirstDateCosts(String firstDateCosts) {
		this.firstDateCosts = firstDateCosts;
	}
	/**
	 * @return the db
	 */
	public String getDb() {
		return db;
	}
	/**
	 * @param db the db to set
	 */
	public void setDb(String db) {
		this.db = db;
	}
	/**
	 * @return the spersonalizowac
	 */
	public String getSpersonalizowac() {
		return spersonalizowac;
	}
	/**
	 * @param spersonalizowac the spersonalizowac to set
	 */
	public void setSpersonalizowac(String spersonalizowac) {
		this.spersonalizowac = spersonalizowac;
	}
	public String getTxtFilePath() {
		return txtFilePath;
	}
	public void setTxtFilePath(String txtFilePath) {
		this.txtFilePath = txtFilePath;
	}
	


	
	
}

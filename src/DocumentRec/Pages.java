

package DocumentRec;
import java.awt.image.BufferedImage;


public class Pages {



	public String filePath;
	public int pageStatus;
	public BufferedImage document;
	public double maxVal;

	public Pages(String filePath, int pageStatus, BufferedImage document, double maxVal) {

		
		this.filePath = filePath;
		this.pageStatus = pageStatus;
		this.document = document;
		this.maxVal = maxVal;
	}



	public String getFilePath(int i) {
		return filePath;
	}

	public int getPageStatus(int i) {
		return pageStatus;
	}
	
	public BufferedImage getDocument(int i) {
		return document;
	}
	
	public Double getMaxVal(int i) {
		return maxVal;

}
}


package DocumentRec;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.sun.media.jai.codec.TIFFField;

import docsClassificationNVBT.DocsClassification;
import net.sourceforge.tess4j.TesseractException;

public class TIFFsplit {

	static ArrayList<Mat> Images = new ArrayList<Mat>();

	static List<Pages> Documents = new ArrayList<>();

	static List<RecognizedDocument> RecognizedDocuments = new ArrayList<RecognizedDocument>();
	
	static String skGr;
	static int dateSet = 0;
	static String recognizedDate;
	static String recognizedDate2;

	public static void clearImagesArrayList(ArrayList<Mat> r) {

		r.removeAll(r);

	}

	public static void clearDocumentsList(List<Pages> r) {

		r.removeAll(r);

	}

	private static void saveSeperateDocs(List<Pages> Documents, String ocrFolder) throws IOException {
		// BufferedImage image[] = new BufferedImage[Documents.size()];
		for (int i = 0; i < Documents.size(); i++) {
			List<BufferedImage> imageList = new ArrayList<BufferedImage>();
			TIFFEncodeParam params = new TIFFEncodeParam();
			params.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);

			TIFFField[] extras = new TIFFField[2];
			extras[0] = new TIFFField(282, TIFFField.TIFF_RATIONAL, 1,
					(Object) new long[][] { { (long) 300, (long) 1 }, { (long) 0, (long) 0 } });

			extras[1] = new TIFFField(283, TIFFField.TIFF_RATIONAL, 1,
					(Object) new long[][] { { (long) 300, (long) 1 }, { (long) 0, (long) 0 } });
			params.setExtraFields(extras);

			if (Documents.get(i).getPageStatus(i) == 1) {
				String documentPath = ocrFolder + "\\" + "dok" + i + ".tiff";
				String txtFilePath = documentPath.replace(ocrFolder, ocrFolder + "\\przetworzone");
				txtFilePath = txtFilePath.replace(txtFilePath, txtFilePath + ".txt");
				OutputStream out = new FileOutputStream(documentPath);
				ImageEncoder encoder = ImageCodec.createImageEncoder("tiff", out, params);
				// params.setExtraImages(Documents.listIterator(1));
				imageList.add(Documents.get(i).getDocument(i));
				for (int j = i + 1; j < Documents.size(); j++) {
					if (Documents.get(j).getPageStatus(j) == 1) {

						break;
					}
					if (Documents.get(j).getPageStatus(j) == 0) {

						imageList.add(Documents.get(j).getDocument(j));
					}

				}
				params.setExtraImages(imageList.listIterator(1));
				encoder.encode(imageList.get(0));
				// System.out.println(imageList);
				out.close();

				RecognizedDocument document = new RecognizedDocument(i, documentPath);
				document.setTxtFilePath(txtFilePath);
				RecognizedDocuments.add(document);
				imageToByte(document);

			}

		}

	}

	public static List<RecognizedDocument> imageToByte(RecognizedDocument document) throws IOException {
		String fileName = document.getFilePath();
		System.out.println("Sciezka: " + fileName);
		ImageInputStream is = ImageIO.createImageInputStream(new File(fileName));

		Iterator<ImageReader> iterator = ImageIO.getImageReaders(is);

		ImageReader reader = (ImageReader) iterator.next();
		reader.setInput(is);
		int nbPages = reader.getNumImages(true);

//		System.out.println("image: "+ nbPages);

		Path path = Paths.get(fileName);

		byte[] fileContent = Files.readAllBytes(path);

		document.setImage(fileContent);
		document.setNumbPages(nbPages);
		document.setSizeImage(fileContent.length);

		return RecognizedDocuments;

	}

	private static void saveSeperateImagePage(ArrayList<BufferedImage> bufferedImageList, String pagesFolder)
			throws IOException {

		for (int i = 0; i < bufferedImageList.size(); i++) {
			BufferedImage page = bufferedImageList.get(i);
			String pageNumber = i + "strona" + i;
			String filePath = pagesFolder + "\\0" + pageNumber + ".tiff";

			TIFFEncodeParam params = new TIFFEncodeParam();
			params.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
			TIFFField[] extras = new TIFFField[2];
			extras[0] = new TIFFField(282, TIFFField.TIFF_RATIONAL, 1,
					(Object) new long[][] { { (long) 300, (long) 1 }, { (long) 0, (long) 0 } });

			extras[1] = new TIFFField(283, TIFFField.TIFF_RATIONAL, 1,
					(Object) new long[][] { { (long) 300, (long) 1 }, { (long) 0, (long) 0 } });
			params.setExtraFields(extras);

			OutputStream out = new FileOutputStream(filePath);
			ImageEncoder encoder = ImageCodec.createImageEncoder("tiff", out, params);
			encoder.encode(page);
			out.close();

//			ImageIO.write(page, "TIFF", new File(filePath));
//
//			ImageIO.write(page, "TIFF", new File(filePath));

			matchingMethod(filePath, pagesFolder, page, pageNumber);

		}

	}

	public static ArrayList<BufferedImage> BufferedImageList(ImageInputStream is, ImageReader reader, int nbPages)
			throws IOException {
		ArrayList<BufferedImage> BufferedImageList = new ArrayList<BufferedImage>();
		for (int i = 0; i < nbPages; i++) {

			BufferedImageList.add(reader.read(i));
		}

		return BufferedImageList;

	}

	private static void prepareTemplate(String arg0) throws IOException {
		Mat templ = Imgcodecs.imread(arg0, Imgcodecs.IMREAD_COLOR);
		Mat rotationMatrix;
		// System.out.println("templ: "+templ);

		double r = 0.0;
		int modifyHeight = 0;
		double modifyWeight = 1.5;
		for (int i = 0; i < 8; i++) {
			System.out.println("templ1 " + i + ": " + templ);

			final Mat dst = new Mat();
			rotationMatrix = Imgproc.getRotationMatrix2D(new Point((templ.width() - 40) / 2, (templ.height()) / 2), r,
					1);
			Imgproc.warpAffine(templ, dst, rotationMatrix,
					new Size((templ.cols() - modifyWeight), templ.rows() + modifyHeight), 0, 1, new Scalar(0, 0, 0));

			// System.out.println("dst" + i + ": " + dst);

			Images.add(dst);
			Imgcodecs.imwrite("D:\\pat\\pat" + i + ".tiff", dst);
			r = r + -8;
			modifyHeight = modifyHeight + 7;
			modifyWeight = modifyWeight * 1.29;
		}

	}

	private static void matchingMethod(String filePath, String pagesFolder, BufferedImage page, String pageNumber) {

		try {
			Mat result = new Mat();
			Mat img_display = new Mat();
			Mat img2 = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_COLOR);
			System.out.println(
					"file: " + filePath + " mat: " + img2 + "width: " + img2.width() + " heigth: " + img2.height());
			System.out.println(img2.empty());
			Rect rectCrop = new Rect();
			if (img2.width() / 2 < img2.height()) {
				rectCrop = new Rect(img2.width() / 2, 0, img2.width() / 2, img2.width() / 2);
				System.out.println("RectCrop1");

			} else {
				rectCrop = new Rect(0, 0, img2.width(), img2.height());
				System.out.println("RectCrop2");
			}

			Mat img = new Mat(img2, rectCrop);
//			Rect rectCropForCaseId = new Rect(0, 0, img2.width(), img2.width() / 3);
//			Mat imgForCaseId = new Mat(img2, rectCropForCaseId);
			File m = new File(pagesFolder + "\\matchingResults\\");
			m.mkdir();

			// System.out.println("Number of templates: " + Images.size());
			for (int i = 0; i < Images.size(); i++) {
				img.copyTo(img_display);
				Mat templ = Images.get(i);

				int result_cols = img.cols() - templ.cols() + 1;
				int result_rows = img.rows() - templ.rows() + 1;
				result.create(result_rows, result_cols, CvType.CV_32FC1);

				Imgproc.matchTemplate(img, templ, result, Imgproc.TM_CCOEFF_NORMED);

				Imgproc.threshold(result, result, 0.0, 1.0, Imgproc.THRESH_TOZERO);
				Point matchLoc;
				Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

//				System.out.println("MaxVal for " + filePath + " : " + mmr.maxVal);
				// System.out.println("MinVal for " + filePath +" : " +mmr.minVal);

				matchLoc = mmr.maxLoc;

				if (mmr.maxVal >= 0.5) {
					Imgproc.rectangle(img_display, matchLoc,
							new Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()), new Scalar(0, 255, 0), 5,
							8, 0);

					Imgcodecs.imwrite(pagesFolder + "\\matchingResults\\" + pageNumber + ".tiff", img_display);
					Pages newFirstPage = new Pages(filePath, 1, page, mmr.maxVal);
					Documents.add(newFirstPage);
//					Imgcodecs.imwrite(pagesFolder + "\\ocrCaseId\\" + pageNumber + ".tiff", imgForCaseId);
					if (dateSet < 2) {
						File r = new File(pagesFolder + "\\recognizedDate\\");
						r.mkdir();
						int x = (int) (matchLoc.x - 150);
						int y = (int) (matchLoc.y - 15);
						Rect dateCrop = new Rect(x, y, 260, 100);
						try {
							Mat dateMate = new Mat(img, dateCrop);

							Size sz = new Size(dateMate.width() * 3, dateMate.height());
							Imgproc.resize(dateMate, dateMate, sz);
							Imgproc.cvtColor(dateMate, dateMate, Imgproc.COLOR_RGB2GRAY);
							Imgproc.GaussianBlur(dateMate, dateMate, new Size(3, 3), 1);
							Imgproc.threshold(dateMate, dateMate, 225, 255, Imgproc.THRESH_BINARY);
							Imgcodecs.imwrite(pagesFolder + "\\recognizedDate\\recognizedDate.tiff", dateMate);
							Ocr.OCR(r.getPath());

							while (Ocr.z != Ocr.i) {

								Thread.sleep(1000);
//							// System.out.println("=== doOCR(ocrFolder) running ===")
								;
							}
							setDocumentsDate();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				}
				if (i == Images.size() - 1) {
					Pages newOtherPage = new Pages(filePath, 0, page, mmr.maxVal);
					Documents.add(newOtherPage);
					// System.out.println(filePath);

				}

				continue;

			}
			;
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println(
					"!!!!!!Blad przy matchowaniu pierwszej strony (pattern match), sprawdz rozdizelczosc lub rozmiar obrazu wejsiowego");
			System.exit(-1);
		}

		// showTemplateMatchingResult();

	}

	public static void setDocumentsDate() {

		if (dateSet == 1) {

			recognizedDate2 = recognizedDate2.replaceAll("\\D", "");

			if (recognizedDate2.length() == 8) {
//			System.out.println("Data z pieczątki przed substring: "+recognizedDate + " dlugosc: "  +recognizedDate.length()+ "poz 0: " + recognizedDate.charAt(0)+ "poz 0: " + recognizedDate.charAt(7));
				recognizedDate2 = recognizedDate2.substring(4, 8) + "-" + recognizedDate2.substring(2, 4) + "-"
						+ recognizedDate2.substring(0, 2);
//			System.out.println("Data z pieczątki przed match: "+recognizedDate);
				Pattern r = Pattern.compile("20[123]{1}[1-9]{1}-[01]{1}[1-9]{1}-[0123]{1}[0-9]{1}");
				Matcher m = r.matcher(recognizedDate2);
				if (m.find() & recognizedDate.equals(recognizedDate2)) {
					String s = m.group(0).toString();

					dateSet = 2;
					System.out.println("Data z pieczątki: " + s + " DateSet: " + dateSet);
					System.out.println("recognizedDate: " + recognizedDate);
					System.out.println("recognizedDate2: " + recognizedDate2);
				}

				else {
					System.out.println("brak zgodnosci recognizedDate: " + recognizedDate);
					System.out.println("brak zgodnosci recognizedDate2: " + recognizedDate2);
					recognizedDate = recognizedDate2;
					recognizedDate2 = "";

				}

			}

			else {
				recognizedDate2 = "";
			}
		}
		if (dateSet == 0) {

			recognizedDate = recognizedDate2;
			recognizedDate = recognizedDate.replaceAll("\\D", "");

			if (recognizedDate.length() == 8) {
//			System.out.println("Data z pieczątki przed substring: "+recognizedDate + " dlugosc: "  +recognizedDate.length()+ "poz 0: " + recognizedDate.charAt(0)+ "poz 0: " + recognizedDate.charAt(7));
				recognizedDate = recognizedDate.substring(4, 8) + "-" + recognizedDate.substring(2, 4) + "-"
						+ recognizedDate.substring(0, 2);
//			System.out.println("Data z pieczątki przed match: "+recognizedDate);
				Pattern r = Pattern.compile("20[123]{1}[1-9]{1}-[01]{1}[1-9]{1}-[0123]{1}[0-9]{1}");
				Matcher m = r.matcher(recognizedDate);
				if (m.find()) {
					String s = m.group(0).toString();
					dateSet = 1;
					System.out.println("Data z pieczątki: " + s + " DateSet: " + dateSet);
					System.out.println("recognizedDate: " + recognizedDate);
					System.out.println("recognizedDate2: " + recognizedDate2);
				}

				else {
					recognizedDate = "";
				}

			}

			else {
				recognizedDate = "";
			}
		}
	}

	public static void showTemplateMatchingResult(File a) throws IOException {
		System.out.println("=== TemplateMatching ends now. Results below. ===");

		File resultFile = new File(a + "\\TemplateMatchingResult" + System.currentTimeMillis() + ".csv");
		String enter = System.getProperty("line.separator");

		BufferedWriter bufWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(resultFile), StandardCharsets.UTF_8));
		bufWriter.write('\ufeff');
		bufWriter.write("Lp;SciezkaDokumentu;PageStatus;Result" + enter);
		for (int i = 0; i < Documents.size(); i++) {
			String line = i + " ; " + Documents.get(i).getFilePath(i) + " ; " + Documents.get(i).getPageStatus(i)
					+ " ; " + Documents.get(i).getMaxVal(i) + enter;
			bufWriter.write(line);
		}
		bufWriter.close();

	}

	public static void clearRecognizedDocumentsList(List<RecognizedDocument> r) {

		r.removeAll(r);

	}
	
	public static void returnDataFromRecognizedDocumentsList(List<RecognizedDocument> r) {
		int v = 0;
		for (RecognizedDocument d : r) {

			{

				System.out.println(v + ". " + d.getId() + "  |  " + d.getFilePath() + "  |  " + d.getIdPozyczki()
						+ "  |  " + d.getIdProcesuKomornik() + "  |  " + d.getPersonalizacja() + "  |  " + d.getSygKM()
						+ "  |  " + d.getSygSad() + "  |  " + d.getResult() + "  |  " + d.getIdWekaModel() + "  |  "
						+ d.getTyp() + "  |  " + d.getPodtyp() + "  |  " + d.getOpis1()+ "  |  " + d.getImage()+ "  |  " + d.getSizeImage()+ "  |  " + d.getNumbPages()+ "  |  " + d.getPageInGroup()+ "  |  " + d.getDb()+ "  |  " + d.getSpersonalizowac()
						);
			}
			v = v + 1;
		}

	}
	
	public static void createNewGroupScan(List<RecognizedDocument> r, String p, String file) throws IOException {
		
		ArrayList<BufferedImage> ScanGroup = new ArrayList<BufferedImage>();
		int v=0;
		int nP =0;
		String ipn="";
		String ipo="";
		String iph="";
		for (RecognizedDocument d: r) {
			if (d.getResult()>2) {
				
				iph=iph+d.getPageInGroup();
			}
//			if (d.getResult()<=2) {
//				
//				ipn=ipn+d.getPageInGroup();
//			}
			ipn =iph;
			ipo = ipo+d.getPageInGroup();
			v=v+d.getSizeImage();
			nP=nP+d.getNumbPages();
			ByteArrayInputStream bis = new ByteArrayInputStream(d.getImage());

			ImageInputStream is = ImageIO.createImageInputStream(bis);
		
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(is);
			
			ImageReader reader = (ImageReader) iterator.next();
			reader.setInput(is);
			int nbPages = reader.getNumImages(true);
//			System.out.println("ile stron: " + nbPages);
			
			for (int i = 0; i < nbPages; i++) {

				ScanGroup.add(reader.read(i));
			}
			
		}


		prepareScanGroupForImport(ScanGroup, nP, v, ipo, iph,ipn, file);
}
	
	
	private static void prepareScanGroupForImport(ArrayList<BufferedImage> bufferedImageList, int nP, int v, String ipo, String iph, String ipn, String file) throws IOException {

		TIFFEncodeParam params = new TIFFEncodeParam();
		params.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
		TIFFField[] extras = new TIFFField[2];
		extras[0] = new TIFFField(282, TIFFField.TIFF_RATIONAL, 1,
				(Object) new long[][] { { (long) 300, (long) 1 }, { (long) 0, (long) 0 } });

		extras[1] = new TIFFField(283, TIFFField.TIFF_RATIONAL, 1,
				(Object) new long[][] { { (long) 300, (long) 1 }, { (long) 0, (long) 0 } });
		params.setExtraFields(extras);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageEncoder encoder = ImageCodec.createImageEncoder("tiff", out, params);
		

		params.setExtraImages(bufferedImageList.listIterator(1));

		encoder.encode(bufferedImageList.get(0));
		byte[] dest = out.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(dest);
		System.out.println("Done.");
		insertScanGroupIntoDB(is, nP, dest.length, ipo, iph, ipn, file);
	}
	
	public static String insertScanGroupIntoDB(InputStream imageByte, int nbPages, int length, String ipo, String iph, String ipn, String file) {

       

		Date date = new Date();
		Object paramDate = new java.sql.Timestamp(date.getTime());
        String login = "DocRecognition";
        String nazwa = "DocRec_"+file;
        String db = "wierzytelnosci";
        
        		
        
        try (Connection con = DBconnection.connectionDB(db);) {
        	           	         	           	
        	   String SQL = "INSERT INTO [dbo].[sk_grupy] ([sk_typ],[sk_status],[sk_priorytet],[data_przyj],[data_skanu],[nazwa],[login],[image],[imagesize],[imagetyp],[imagepages],[ipn],[ipo],[dzial_docelowy],[przekazano]) VALUES ('2','1','1',?,?,'"+nazwa+"','"+login+"',?,'"+length+"','6','"+nbPages+"','"+ipn+"','"+ipo+"', 'DWD','1')";
        	  // System.out.println(SQL);
        	   PreparedStatement stmt = con.prepareStatement(SQL);
        	   stmt.setObject(1, paramDate);
        	   stmt.setObject(2, paramDate);
        	   stmt.setBinaryStream(3, imageByte);
               stmt.executeUpdate();                             
               con.close();                
        }
    
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        try(Statement stmt2 = DBconnection.connectionDB(db).createStatement();){
     	   String SQL2 = "SELECT TOP 1 [sk_gr] FROM [dbo].[sk_grupy] WHERE nazwa ='"+nazwa+"' ORDER BY  sk_gr DESC";
     	  // System.out.println(SQL);
            ResultSet rs = stmt2.executeQuery(SQL2);

            
            while (rs.next()) {

            		 skGr  = rs.getString("sk_gr");
                }
            
            System.out.println("skGr: " + skGr);
        	
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return skGr;
    }

	public static void start(ArrayList<BufferedImage> scanGroupList, String file, File selectedFile)
			throws IOException, InterruptedException {
		System.out.println("=== Program starts now ===");


		String g = "\\" + file;

		System.out.println(file);
		File x = new File("D:\\DocRecBeta");
		x.mkdir();
		File a = new File(x.toString() + g);
		a.mkdir();
		File d = new File(a + "\\stronyGrupy");
		d.mkdir();
		String pagesFolder = d.toString();
		System.out.println(pagesFolder);
		File o = new File(a + "\\dokumentyGrupy");
		o.mkdir();
		String ocrFolder = o.toString();
//		File c = new File(pagesFolder + "\\ocrCaseId");
//		c.mkdir();
//		String ocrForCaseIdFolder = c.toString();
//		System.out.println(ocrForCaseIdFolder);

		// String templatePath = "./pattern/pattern300p.png";

		String templatePath = "./pattern/pattern300p.png";
		// System.out.println();

		if (scanGroupList == null) {
			String groupPath = selectedFile.getPath();
			System.out.println("=== Reading source group multitiff file starts now ===");
			ImageInputStream is = ImageIO.createImageInputStream(new File(groupPath));
			if (is == null || is.length() == 0) {
			}
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(is);
			if (iterator == null || !iterator.hasNext()) {
				throw new IOException("Image file format not supported by ImageIO: " + groupPath);
			}
			ImageReader reader = (ImageReader) iterator.next();
			reader.setInput(is);
			int nbPages = reader.getNumImages(true);
			System.out.println("Number of pages in source file: " + nbPages);
			System.out.println("=== Reading source group multitiff file ends now ===");
			scanGroupList = BufferedImageList(is, reader, nbPages);
		}
		System.out.println();
		System.out.println("=== Recognizing documents starts now ===");
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		prepareTemplate(templatePath);
		saveSeperateImagePage(scanGroupList, pagesFolder);
		showTemplateMatchingResult(a);
//		SaveMultipleTIFF(BufferedImageList(is, reader, nbPages));
		saveSeperateDocs(Documents, ocrFolder);
		System.out.println("=== Recognizing documents ends now ===");
		System.out.println("=== OCR starts now ===");

		Ocr.OCR(ocrFolder);

		while (Ocr.z != Ocr.i) {

			Thread.sleep(10000);
//			// System.out.println("=== doOCR(ocrFolder) running ===")
			;
		}

		Path ocrPath = Paths.get(ocrFolder + "\\przetworzone");


		RecognizedDocument.setPagesInGroup(RecognizedDocuments);
		DocsClassification.main(RecognizedDocuments, ocrPath);

		clearImagesArrayList(Images);
		clearDocumentsList(Documents);
		clearRecognizedDocumentsList(RecognizedDocuments);

		dateSet = 0;
		recognizedDate = null;

		System.out.println("=== Program ends now ===");

	}

}
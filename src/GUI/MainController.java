package GUI;

import DocumentRec.DBconnection;
import DocumentRec.TIFFsplit;
import java.awt.Checkbox;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.sun.media.jai.codec.TIFFField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController implements Initializable {

	public static List<ScanGroup> list;
	
	@FXML
	private static String username;
	
	@FXML
	private Label userLabel;

	@FXML
	private TableView<ScanGroup> table = new TableView<>();

	@FXML
	private TableColumn<ScanGroup, Integer> c1 = new TableColumn<>();

	@FXML
	private TableColumn<ScanGroup, String> c2 = new TableColumn<>();
	@FXML
	private TableColumn<ScanGroup, String> c3 = new TableColumn<>();
	@FXML
	private TableColumn<ScanGroup, Integer> c4 = new TableColumn<>();
	@FXML
	private TableColumn<ScanGroup, String> c5 = new TableColumn<>();

	@FXML
	private Button btnSelect;

	@FXML
	private TextField fileBorder;

	@FXML
	private Button btnStart;





	File selectedFile;

	FileChooser fc = new FileChooser();

	@FXML
	private Button btn1 = new Button();;

	public static ObservableList<ScanGroup> data = FXCollections.observableArrayList();

	public static List<ScanGroup> SelectedGroups;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//	table ;
		getUserName();
		ReturnedScanGroups.main();

		c1.setCellValueFactory(new PropertyValueFactory<>("skgr"));
		c2.setCellValueFactory(new PropertyValueFactory<>("DataSkanu"));
		c3.setCellValueFactory(new PropertyValueFactory<>("NazwaGrupy"));
		c4.setCellValueFactory(new PropertyValueFactory<>("Imagepages"));
		c5.setCellValueFactory(new PropertyValueFactory<>("DzialDocelowy"));


		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.setItems(data);
;

		returnScanGroupsFromDataScanGroup(data);



	}
	
	public void getUserName() {
		String username = System.getProperty("user.name");
		userLabel.setText(username);
		System.out.println(username);
		
	}

	public void Button(ActionEvent event) {

		selectGroup();
		try {
			getGroupScanImageFromDB(list);
//			for (int i = 0; i < list.size(); i++) {
//				System.out.println("indeks: " + i + " | skgr: " + list.get(i).getSkgr() + " | nazwa: "
//						+ list.get(i).getNazwaGrupy() + " | nazwa: " + list.get(i).getImage().toString());
//			}
			System.out.println(list.size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<ScanGroup> getGroupScanImageFromDB(List<ScanGroup> list) throws IOException {

		String db = "wierzytelnosci";
		String groups = "";
		for (int i = 0; i < list.size(); i++) {
//    	System.out.println("indeks: " + i +" | skgr: "+ list.get(i).getSkgr()+" | nazwa: "+ list.get(i).getNazwaGrupy());
			groups = groups + " sk_grupy = '" + list.get(i).getSkgr() + "'";
			if (i < list.size() - 1) {

				groups = groups + " or ";
			}
		}
		System.out.println(groups);

		list.removeAll(list);

		try (Statement stmt = DBconnection.connectionDB(db).createStatement();)  {

			String SQL = "SELECT [sk_grupy],[nazwa],[skan] FROM [wierzytelnosci].[dbo].[skany_grupy] WHERE"
					+ groups;
			System.out.println(SQL);
			ResultSet rs = stmt.executeQuery(SQL);

			String enter = System.getProperty("line.separator");

			while (rs.next()) {

				System.out.println(" | " + rs.getInt(1) + " | " + rs.getString(2).replaceAll(" ", "") + " | "
						+ rs.getBytes(3));

				byte[] dataByte = rs.getBytes(3);
				System.out.println("d³: "+dataByte.length);
				ByteArrayInputStream bis = new ByteArrayInputStream(dataByte);

				ImageInputStream is = ImageIO.createImageInputStream(bis);
				if (is == null || is.length() == 0) {
				}
				Iterator<ImageReader> iterator = ImageIO.getImageReaders(is);
				if (iterator == null || !iterator.hasNext()) {
					throw new IOException("Image file format not supported by ImageIO: "+rs.getString(2).replaceAll(" ", ""));
				}
				ImageReader reader = (ImageReader) iterator.next();
				reader.setInput(is);
				int nbPages = reader.getNumImages(true);
				System.out.println(enter + "ile stron: " + nbPages);
				String fileName = rs.getString(2).replaceAll(" ", "");
				TIFFsplit.start(BufferedImageList(is, reader, nbPages),fileName, null);
				info(fileName);
//			      BufferedImage image = ImageIO.read(bis);
//			      System.out.println(bImage2);
//				for (int i =0;  i<dataByte.length; i++) {
//			    System.out.println(enter+dataByte[i]);
//				}
//				saveImage(image);
//				ScanGroup ScanGroup = new ScanGroup(rs.getInt(1), rs.getString(2).replaceAll(" ", ""), image);

//				list.add(ScanGroup);
				System.out.println(enter);
			}

		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public static void returnScanGroupsFromDataScanGroup(ObservableList<ScanGroup> r) {
		int v = 0;
		for (ScanGroup d : r) {

			{

				System.out.println(v + ". " + d.getSkgr() + "  |  " + d.getDataSkanu() + "  |  " + d.getNazwaGrupy()
						+ "  |  " + d.getImagepages() + "  |  " + d.getDzialDocelowy());
			}
			v = v + 1;
		}

	}

	public List<ScanGroup> selectGroup() {
		System.out.println("Haaaaa");
		TableViewSelectionModel<ScanGroup> tsm = table.getSelectionModel();

		// Check, if any rows are selected
		if (tsm.isEmpty()) {
			System.out.println("Wybierz grupê do przetworzenia");

		}

		// Get all selected row indices in an array

		list = new ArrayList<>();
		list.addAll(tsm.getSelectedItems());

//        Integer[] selectedIndices = new Integer[list.size()];
//        selectedIndices = list.toArray(selectedIndices);

//        for (int i = 0; i<list.size();i++) {
//       	System.out.println("indeks: " + i +" | skgr: "+ list.get(i).getSkgr()+" | nazwa: "+ list.get(i).getNazwaGrupy());
//        }
		return list;

	}


	public static ArrayList<BufferedImage> BufferedImageList(ImageInputStream is, ImageReader reader, int nbPages)
			throws IOException {
		ArrayList<BufferedImage> BufferedImageList = new ArrayList<BufferedImage>();
		for (int i = 0; i < nbPages; i++) {

			BufferedImageList.add(reader.read(i));
		}

		return BufferedImageList;

	}


	
	public void Button1Action1(ActionEvent event) {
		if (selectedFile != null &&(selectedFile.getName().toString().toLowerCase().endsWith(".tif") || selectedFile.getName().toString().toLowerCase().endsWith(".tiff")))
		{
			fileBorder.setText(selectedFile.getPath());
			fc.setInitialDirectory(new File(selectedFile.getParent()));
			fc.setTitle("Select Resource File");
			File selectedFile2 = fc.showOpenDialog(null);
			if (selectedFile2 != null&&(selectedFile2.getName().toString().toLowerCase().endsWith(".tif") || selectedFile2.getName().toString().toLowerCase().endsWith(".tiff"))) {
				selectedFile = selectedFile2;
			}
		
			
		}
		else {
		fileBorder.setText("");
		fc.setTitle("Select Resource File");
		fc.setInitialDirectory(new File("C:\\"));
		selectedFile = fc.showOpenDialog(null);
		}
		

		
		if (selectedFile != null && (selectedFile.getName().toString().toLowerCase().endsWith(".tif") || selectedFile.getName().toString().toLowerCase().endsWith(".tiff"))) {
			fileBorder.setText(selectedFile.getPath());
		} else {
			
			System.out.println("file is not valid");
			
		}

	}

	public void Button2Action1(ActionEvent event) throws IOException, InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		if (selectedFile != null && (selectedFile.getPath().toLowerCase().endsWith(".tif"))||selectedFile.getPath().toLowerCase().endsWith(".tiff")) {
			
			System.out.println(selectedFile.getPath());
			ArrayList<BufferedImage> scanGroupList = null;
			String fileName = selectedFile.getName();
			TIFFsplit.start(scanGroupList, fileName, selectedFile);
			
			info(fileName);
			
			
			
			
		} else {
			
			System.out.println("file is not valid");
		}


	}
	
	public static void info(String selectedFile) throws IOException {
		Hyperlink hyperlink = new Hyperlink("Result file");
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	try {
					Desktop.getDesktop().open(new File("D:\\DocRecBeta\\"+selectedFile));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  //              System.out.println("klikniecie");
            }
        });
        Label label = new Label("Recognizing completed. Open link below to see the results");
	
    	Stage secondaryStage = new Stage();
    	  //getClass().getResource
		
		
		hyperlink.setLayoutX(31);
		hyperlink.setLayoutY(53);
		hyperlink.setPrefHeight(23);
		hyperlink.setPrefWidth(100);
		hyperlink.setTextFill(Color.BLUE);
		hyperlink.setUnderline(true);
		hyperlink.setText("See results");
		
		label.setLayoutX(31);
		label.setLayoutY(8);
		label.setPrefHeight(42);
		label.setPrefWidth(244);
		label.setWrapText(true);
		label.setText("Documents recognized. Click the link below to see the results.");
		
		//AnchorPane info = FXMLLoader.load(getClass().getResource("/GUI/Info.fxml"));
		//prefHeight="128.0" prefWidth="305.0"
		AnchorPane info = new AnchorPane();
		info.setPrefHeight(128);
		info.setPrefWidth(305);
		info.getChildren().addAll(hyperlink, label);
		Scene scene2 = new Scene(info);
		secondaryStage.setScene(scene2);
		secondaryStage.setTitle("Info: "+ selectedFile);
		
		secondaryStage.show();
	
		


}

}

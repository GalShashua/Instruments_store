import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddInstrument extends VBox {

	private Stage addWindow;
	private Scene scene;
	private ComboBox<String> comboBox;
	private Button add = new Button("Add");
	private GridPane grid = new GridPane();
	private TextField brand = new TextField();
	private TextField price = new TextField();
	private TextField numOfStrings = new TextField();
	private CheckBox isFretless = new CheckBox();
	// observableList 
	private ComboBox<String> guitarType = new ComboBox<String>(
			FXCollections.observableList(Arrays.asList("Classic", "Acoustic", "Electric")));
	private ComboBox<String> fluteType = new ComboBox<String>(
			FXCollections.observableList(Arrays.asList("Flute", "Recorder", "Bass")));
	private ComboBox<String> WindMaterial = new ComboBox<String>(
			FXCollections.observableList(Arrays.asList("Wood", "Metal", "Plastic")));
	private static final int HEIGHT = 300, WIDTH = 300, SPACE = 10;
	private int row = 0;

	// constructor
	public AddInstrument() {
		mainCombo();
		setupWindow();
		addWindow.show();
	}

	
	//****************** initialize main stage ******************//
	
	// choose instrument to add
	private void mainCombo() {
		ObservableList<String> mainList = FXCollections
				.observableList(Arrays.asList("Guitar", "Bass", "Flute", "Saxophone"));
		comboBox = new ComboBox<String>(mainList);
		comboBox.setPromptText("Choose Instrumet Type Here");
		getChildren().addAll(comboBox, grid, add);
		setAlignment(Pos.CENTER);
		setSpacing(SPACE);
		comboBox.setOnAction(e -> adaptedWindow());
	}

	// set window settings
	private void setupWindow() {
		grid.setVisible(false);
		scene = new Scene(this, HEIGHT, WIDTH);
		addWindow = new Stage();
		addWindow.setTitle("Afeka Instruments - Add new instrument");
		addWindow.setScene(scene);
//		addWindow.initModality(Modality.WINDOW_MODAL);
		addWindow.initOwner(Stage.getWindows().get(0));
		add.setVisible(false);
	}
	
	private void initializeGrid() {
		grid.getChildren().clear();
		brand.clear();
		price.clear();
		numOfStrings.clear();
		isFretless.setSelected(false);
		grid.addRow(row++, new Label("Brand:"), brand);
		grid.addRow(row++, new Label("Price:"), price);
		grid.setVgap(SPACE);
		grid.setHgap(SPACE);
		grid.setAlignment(Pos.CENTER);
		grid.setVisible(true);
	}

	//****************** specific window for each instrument ******************//
	
	private void adaptedWindow() {
		initializeGrid();
		add.setVisible(true);
		switch (comboBox.getValue()) {
		case "Guitar":
			setupGuitar();
			break;
		case "Bass":
			setupBass();
			break;
		case "Flute":
			setupFlute();
			break;
		case "Saxophone":
			setupSaxsophone();
			break;
		}
		row = 0;
	}
	
	private void setupGuitar () {
		setBrand("Ex: Gibson");
		setPrice(7500);
		guitarType.setPromptText("Type");
		numOfStrings.setPromptText("Ex:6");
		grid.addRow(row++, new Label("Number of Strings:"), numOfStrings);
		grid.addRow(row++, new Label("Guitar Type:"), guitarType);
	}

	private void setupBass () {
		setBrand("Ex: Fender Jazz");
		setPrice(7500);
		numOfStrings.setPromptText("Ex:4");
		grid.addRow(row++, new Label("Number of Strings:"), numOfStrings);
		grid.addRow(row++, new Label("Fretless"), isFretless);
	}
		
	private void setupFlute () {
		setBrand("Ex: Levit");
		setPrice(300);
		fluteType.setPromptText("Material");
		WindMaterial.setPromptText("Type");
		grid.addRow(row++, new Label("Material:"), fluteType);
		grid.addRow(row++, new Label("Flute Type:"), WindMaterial);
	}

	private void setupSaxsophone () {
		setBrand("Jupiter");
		setPrice(5490);
	}


	//****************** add a instrument  ******************//
	// throw exception for wrong insert / no insert
	
	public void addInstrument(ArrayList<MusicalInstrument> instruments) {
		int numOfString;
		double price;
		String brand = getBrand().getText();
		try {
			if (brand.isEmpty())
				throw new IllegalArgumentException("Please enter a brand");
			try {
				price = Double.parseDouble(getPrice().getText());
			} catch (NumberFormatException ex) {
				throw new NumberFormatException("Price has to be a number");
			}
			
			//specific exceptions
			switch (comboBox.getValue()) {
			
			case "Guitar":
				try {
					numOfString = Integer.parseInt(numOfStrings.getText());
				} catch (NumberFormatException ex) {
					throw new NumberFormatException("Number of strings has to be a number");
				}
				if (guitarType.getValue() == null)
					throw new InputMismatchException("No insert for guiter type");
				instruments.add(new Guitar(brand, price, numOfString, guitarType.getValue()));
				break;
				
			case "Bass":
				try {
					numOfString = Integer.parseInt(numOfStrings.getText());
				} catch (NumberFormatException ex) {
					throw new NumberFormatException("Number of strings has to be a number");
				}
				instruments.add(new Bass(brand, price, numOfString, isFretless.isSelected()));
				break; 
				
			case "Flute":
				if (fluteType.getValue() == null)
					throw new InputMismatchException("No insert for flute type");
				if (WindMaterial.getValue() == null)
					throw new InputMismatchException("No insert for flute material");
				instruments.add(new Flute(brand, price, fluteType.getValue(), WindMaterial.getValue()));
				break;
				
			case "Saxophone":
				instruments.add(new Saxophone(brand, price));
				break;
			}
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(ex.getMessage());
			alert.show();
		}
	}

	public Button getAdd() {
		return add;
	}

	private TextField getBrand() {
		return brand;
	}

	private void setBrand(String brand) {
		this.brand.setPromptText(brand);
	}

	private TextField getPrice() {
		return price;
	}

	private void setPrice(double price) {
		this.price.setPromptText(price + "");
	}
}
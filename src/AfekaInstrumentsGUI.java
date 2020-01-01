import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AfekaInstrumentsGUI extends Application {
	private final int WINDOW_HEIGHT = 270;
	private final int WINDOW_WIDTH = 650;
	public final int SPACE = 10;
	private Button rightButton, leftButton;
	private BorderPane mainLayout;
	private ArrayList<MusicalInstrument> musicalInstruments = new ArrayList<>();

	// importing another classes
	private SearchInstrument searchBar;
	private BottomFields bottomFields;
	private ShowInstrument showInstrument;
	private AddInstrument addInstrument;


	// constructor
	public AfekaInstrumentsGUI() {
		AfekaInstruments.loadInstrumentsFromFile(getFileFromUser(), musicalInstruments);
	}

	// main
	public static void main(String[] args) {
		launch(args);
	}

	// ------------------START------------------//
	@Override
	public void start(Stage primaryStage) {

		mainLayout = new BorderPane();
		primaryStage.setHeight(WINDOW_HEIGHT);
		primaryStage.setWidth(WINDOW_WIDTH);
		primaryStage.setTitle("Gal's Instruments Music Store");
		Scene primaryScene = new Scene(mainLayout);

		// importing the other class and start them
		bottomFields = new BottomFields();
	//	addInstrument = new AddInstrument(showInstrument, musicalInstruments);
		showInstrument = new ShowInstrument(getMusicalInstruments());
		searchBar = new SearchInstrument(getMusicalInstruments());
		mainLayout.setCenter(showInstrument);
		mainLayout.setBottom(bottomFields.setupBottom());
		mainLayout.setTop(searchBar.setupTop());
		showInstrument.setupTextFields();

		setupRight();
		setupLeft();
		events();
		primaryStage.setScene(primaryScene);
		primaryStage.show();
		// stop pathTransition and clock update when close pressed
		primaryStage.setOnCloseRequest(e -> bottomFields.exit());
	}

	// get file from user
	// file not found or unreadable -> alert message
	private File getFileFromUser() {
		boolean stopLoop = true;
		File file = null;
		Alert alert;
		do {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Confirmation");
			dialog.setHeaderText("Load Instruments From File");
			dialog.setContentText("Please enter file name:");
			// Traditional way to get the response value
			Optional<String> result = dialog.showAndWait();
			if (!result.isPresent()) {
				System.exit(0);
			} else {
				file = new File(result.get());
				stopLoop = file.exists() && file.canRead();
				if (!stopLoop) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("File Error!");
					alert.setContentText("Cannot read from file, please try again");
					alert.showAndWait();
				}
			}
		} while (!stopLoop);
		return file;
	}

	// setup right button
	public void setupRight() {
		rightButton = new Button(">");
		StackPane paneForRight = new StackPane();
		paneForRight.setPadding(new Insets(SPACE));
		paneForRight.setAlignment(Pos.CENTER);
		paneForRight.getChildren().addAll(rightButton);
		mainLayout.setRight(paneForRight);
		// when button '>' pressed --> show next instrument
		rightButton.setOnAction(e -> {
			if (getMusicalInstruments().size() != 0)
				showInstrument.showNextItem();
		});
	}

	// setup left button
	public void setupLeft() {
		leftButton = new Button("<");
		StackPane paneForLeft = new StackPane();
		paneForLeft.setPadding(new Insets(SPACE));
		paneForLeft.setAlignment(Pos.CENTER);
		paneForLeft.getChildren().addAll(leftButton);
		mainLayout.setLeft(paneForLeft);
		// when button '<' pressed --> show previous instrument
		leftButton.setOnAction(e -> {
			if (getMusicalInstruments().size() != 0)
				showInstrument.showPreviousItem();
		});
	}

	// ------------------KEY AND MOUSE EVENT------------------//

	public void events() {

		// button "Go!" pressed -> search instruments
		// find instrument by sub-string
		searchBar.getGo().setOnAction(e -> {
			showInstrument.setInstrumentsArr(searchBar.search());
		});

		// button "Clear" pressed -> initialization array instruments
		bottomFields.getClear().setOnAction(e -> {
			getMusicalInstruments().clear();
			showInstrument.setInstrumentsArr(getMusicalInstruments());
		});

		// button "Delete" pressed -> delete specific instruments from array
		bottomFields.getDelete().setOnAction(e -> {
		try {
			if (getMusicalInstruments().size() > 0) {
				getMusicalInstruments().remove(showInstrument.getCurrentInstrument());
				showInstrument.setInstrumentsArr(getMusicalInstruments());
				showInstrument.showNextItem();
			} else
				showInstrument.setInstrumentsArr(getMusicalInstruments());
		} catch (Exception ex){	}
		});

		// button "Add" pressed -> add new instrument
		bottomFields.getAdd().setOnAction(e -> { 
			addInstrument = new AddInstrument();
			addInstrument.getAdd().setOnAction(e1 -> {
				addInstrument.addInstrument(getMusicalInstruments());
			});
		});
		mainLayout.setOnKeyReleased(e -> {
			// press > on keyboard = instead of ">" button
			if (e.getCode() == KeyCode.RIGHT) {
				if (getMusicalInstruments().size() != 0)
					showInstrument.showNextItem();
			}
			
			// press < on keyboard = instead of "<" button
			if (e.getCode() == KeyCode.LEFT) {
				if (getMusicalInstruments().size() != 0)
					showInstrument.showPreviousItem();
			}
			
			// press ENTER on keyboard = instead of "Go!" button
			if (e.getCode() == KeyCode.ENTER) {
				showInstrument.setInstrumentsArr(searchBar.search());
			}
			
			// press DELETE on keyboard = instead of "Delete" button
			if (e.getCode() == KeyCode.DELETE) {
				try {
					if (getMusicalInstruments().size() > 0) {
						getMusicalInstruments().remove(showInstrument.getCurrentInstrument());
						showInstrument.setInstrumentsArr(getMusicalInstruments());
						showInstrument.showNextItem();
					} else
						showInstrument.setInstrumentsArr(getMusicalInstruments());
				} catch (Exception ex){	}
			}
			
			// press A/a on keyboard = instead of "Add" button
			if (e.getText().equalsIgnoreCase("A")) {
//				addInstrument = new AddInstrument();
//				addInstrument.addInstrument(musicalInstruments);
			}
		});

	}

	public ArrayList<MusicalInstrument> getMusicalInstruments() {
		return musicalInstruments;
	}

	public void setMusicalInstruments(ArrayList<MusicalInstrument> musicalInstruments) {
		this.musicalInstruments = musicalInstruments;
	}
	
	

}

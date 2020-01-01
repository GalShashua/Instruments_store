import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ShowInstrument extends GridPane {
	public static int SPACE = 10;
	public static int WIDTH = 400;
	public static int HEIGHT = 400;
	private Label typeLabel = new Label("Type: ");
	private TextField typeField = new TextField();
	private Label brandLabel = new Label("Brand: ");
	private TextField brandField = new TextField();
	private Label priceLabel = new Label("Price: ");
	private TextField priceField = new TextField();
	private ArrayList<MusicalInstrument> musicalInstruments;
	private int row = 0, index = 0;

	// setup main grid
	public ShowInstrument(ArrayList<MusicalInstrument> musicalInstruments) {
		addRow(addRow(), typeLabel, typeField);
		addRow(addRow(), brandLabel, brandField);
		addRow(addRow(), priceLabel, priceField);
		setAlignment(Pos.CENTER);
		setWidth(WIDTH - SPACE);
		setHeight(HEIGHT);
		setHgap(SPACE);
		setVgap(SPACE);
		setupTextFields();
		setInstrumentsArr(musicalInstruments);
		setPadding(new Insets(SPACE));
	}
	
	public void updateInstrument(MusicalInstrument musicalInstrument) {
		typeField.setText(musicalInstrument.getClass().getSimpleName());
		brandField.setText(musicalInstrument.getBrand());
		priceField.setText(musicalInstrument.getPrice() + "");
	}

	//  fields --> clear
	public void setupTextFields() {
		typeField.setEditable(false);
		brandField.setEditable(false);
		priceField.setEditable(false);
		typeField.setPromptText("no items");
		brandField.setPromptText("no items");
		priceField.setPromptText("no items");
	}
	
	// pass on instruments array - right
	public void showNextItem() {
		this.index=(this.index+1) % musicalInstruments.size();
		updateInstrument(musicalInstruments.get(index));
	}

	// pass on instruments array - left
	public void showPreviousItem() {
		if (this.index == 0)
			this.index = musicalInstruments.size()-1;
		else
			this.index--;
		updateInstrument(musicalInstruments.get(index));
	} 

	// get instrument on screen 
	public MusicalInstrument getCurrentInstrument() {
		return musicalInstruments.get(this.index);
	}

	public void setInstrumentsArr(ArrayList<MusicalInstrument> instruments) {
		this.musicalInstruments = instruments;
		if (instruments.size() != 0)
			updateInstrument(instruments.get(0));
		else {
			typeField.clear();
			brandField.clear();
			priceField.clear();
		}
	}
	
	private int addRow() {
		int temp = row;
		row++;
		return temp;
	}

	
}
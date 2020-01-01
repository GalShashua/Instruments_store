import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class SearchInstrument {
	private final int SPACE = 10;
	private HBox forTop;
	private Button go;
	private TextField inputForSearch;
	private ArrayList<MusicalInstrument> musicalInstruments;

	// constructor
	public SearchInstrument(ArrayList<MusicalInstrument> instrument) {
		setMusicalInstruments(instrument);
	}
	
	public HBox setupTop() {
		final int TEXT_FIELD_WIDTH=570;
		forTop = new HBox(SPACE);
		go = new Button("Go!");
		inputForSearch = new TextField();
		inputForSearch.setPrefWidth(TEXT_FIELD_WIDTH);
		inputForSearch.setPromptText("Search...");
		forTop.getChildren().addAll(inputForSearch, go);
		forTop.setPadding(new Insets(SPACE,SPACE,SPACE,SPACE));
		forTop.setAlignment(Pos.CENTER);
		return forTop;
	}
	
	public ArrayList<MusicalInstrument> search () {  
		ArrayList<MusicalInstrument> userSearch=new ArrayList<>();
		String userInput=inputForSearch.getText();
        for(int i=0; i<musicalInstruments.size(); i++) {
        	if (musicalInstruments.get(i).toString().toLowerCase().contains(userInput)) {
        		userSearch.add(musicalInstruments.get(i));
        	}
        }
        return userSearch;
	}
	
	private void setMusicalInstruments(ArrayList<MusicalInstrument> setMusicalInstruments) {
		this.musicalInstruments = setMusicalInstruments;
	}
	
	// getter for "Go!" button
	public Button getGo() {
		return go;
	}

}
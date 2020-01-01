import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BottomFields extends VBox {
	private final int SPACE = 10;
	private VBox paneForBottom = new VBox();
	private HBox buttonsLayout;
	private Button add, delete, clear;
	private PathTransition pathTransition;
	private Timeline timeClock;

	// build the bottom - buttons and animation
	public VBox setupBottom() {
		paneForBottom.getChildren().addAll(addButtons(), setupTextAnimation());
		return paneForBottom;
	}

	// set buttons 
	private HBox addButtons() {
		buttonsLayout = new HBox();
		add = new Button("Add");
		delete = new Button("Delete");
		clear = new Button("Clear");
		buttonsLayout.getChildren().addAll(add, delete, clear);
		buttonsLayout.setAlignment(Pos.CENTER);
		buttonsLayout.setSpacing(SPACE);
		buttonsLayout.setPadding(new Insets(SPACE));
		return buttonsLayout;
	}

	// set the animation - text moving 
	final int FONT_SIZE=11;
	final int POINT1X=100, POINT2X=500, POINTY=-10;
	final int CYCLE_COUNT=60;
	final int DURATION1=1;
	final int DURATION2=10;
	
	
	// set the animation- moving text
	private Text setupTextAnimation() {
		Text message = new Text(LocalDate.now() + " "
				+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
				+ " Afeka Instrument Music Store $$$ ON SALE!!! $$$ Guitars, Basses, Flutes, Saxophones, and more!");
		message.setFill(Color.RED);
		message.setFont(Font.font("Ariel", FontWeight.BOLD, FONT_SIZE));
		timeClock = new Timeline();
		timeClock.getKeyFrames().add(new KeyFrame(Duration.seconds(DURATION1), e -> {
			message.setText(LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
					+ " Afeka Instrument Music Store $$$ ON SALE!!! $$$ Guitars, Basses, Flutes, Saxophones, and more!");
		}));
		timeClock.setCycleCount(Animation.INDEFINITE);
		timeClock.play();
		pathTransition = new PathTransition();
		Line line = new Line(POINT1X, POINTY, POINT2X, POINTY);
		pathTransition.setNode(message);
		pathTransition.setPath(line);
		pathTransition.setDuration(Duration.seconds(DURATION2));
		pathTransition.setCycleCount(PathTransition.INDEFINITE);
		pathTransition.setAutoReverse(true);
		pathTransition.play();
		// when mouse entered to the text area
		message.setOnMouseEntered(e-> pathTransition.pause());
		message.setOnMouseExited(e-> pathTransition.play());
		return message;
	}

	// stop pathTransition and clock update when close pressed
	public void exit () {
		pathTransition.stop();
		timeClock.stop();
	}
	
	// getters for buttons
	public Button getAdd() {
		return add;
	}

	public Button getDelete() {
		return delete;
	}

	public Button getClear() {
		return clear;
	}

}
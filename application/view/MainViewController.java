package application.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class MainViewController implements Initializable {
	/**
	 * Vegi zusatzwahl
	 */
	@FXML private CheckBox broccoli;
	@FXML private CheckBox spinat;
	@FXML private CheckBox paprika;
	@FXML private CheckBox mais;
	/**
	 * Fleisch zusatzwahl
	 */
	@FXML private CheckBox speck;
	@FXML private CheckBox schinken;
	@FXML private CheckBox salami;
	@FXML private CheckBox kebab;
	/**
	 * div zusatz beläge
	 */
	@FXML private CheckBox knoblauch;
	@FXML private CheckBox zwiebel;
	@FXML private CheckBox ei;
	/**
	 * choicebox käse und grösse
	 */
	@FXML private ChoiceBox<String> kaese;
	@FXML private ChoiceBox<String> groesse;
	
	/**
	 * preis und absenden
	 */
	@FXML private Label preis;
	@FXML private Button absenden;
	@FXML private RadioButton pizza;
	@FXML private RadioButton calzone;
	private int nDiv;
	private int nMeat;
	private int nVegi;
	private float preisGroesse;
	private StringProperty gesammtPreis;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		/**
		 * daten für die ChoiceBox
		 */
		String[] kaeseSorte = {"Edamer", "Mozzarella", "Gryuere", "Appenzeller"};
		String[] pizzaGroesse = {"15 cm", "20 cm", "25 cm", "30 cm"};
		kaese.getItems().addAll(kaeseSorte);
		kaese.getSelectionModel().selectFirst();
		groesse.getItems().addAll(pizzaGroesse);
		groesse.getSelectionModel().select(1);
		preis.textProperty().bind(this.gesammtPreis());
		this.setPizzaSize();
		}
	
	public StringProperty gesammtPreis(){
		if (gesammtPreis == null)
			gesammtPreis = new SimpleStringProperty();
		return gesammtPreis;
	}
	
	public void enableButton() {
		this.absenden.setDisable(false);
	}
	
	public void bestellungAbgesendet () {
		
		Image icon = new Image("pizza-slice.png");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Pizza und Calzone");
		alert.setHeaderText(null);
		alert.setContentText("Die Bestellung für dein Essen ist raus.");
		// hier holen wir die stage des alertWindow ab um auf mehr methoden zurückgreiffen zu können.
		Stage alertBestellung = (Stage) alert.getDialogPane().getScene().getWindow();
		alertBestellung.getIcons().add(icon);
		alert.showAndWait();
	}
	
	public void calculatePreis() {
		int vegiPreis=0;
		int meatPreis=0;
		if (nVegi>2) {
			vegiPreis = (nVegi-2)*2;
		}
		if (nMeat>1) {
			meatPreis = (nMeat-1)*3;
		}
		float tempPreis = preisGroesse + vegiPreis + meatPreis + nDiv;
		//preis.setText(String.format("%.2f", tempPreis));
		gesammtPreis.set(String.format("%.2f", tempPreis));
	}
	
	public void countVegis (ActionEvent e) {
		//hole Checkbox, die das ereignis ausgelöst hat
		CheckBox vegiBox = (CheckBox)e.getSource();
		//wenn diese checkbox ausgewählt wurde erhöhe nVegi zähler
		if (vegiBox.isSelected()==true) {
			nVegi++;
		}
		else {
			nVegi= Math.max(0, nVegi-1); // gibt die grössere der 2 Zahlen zurück
		}
		calculatePreis();
	}
	
	public void countMeat (ActionEvent e) {
		CheckBox meatBox = (CheckBox)e.getSource();
		
		if (meatBox.isSelected()==true) {  // wenn die Checkbox angewählt ist
			nMeat++;
		}
		else {
			nMeat= Math.max(0, nMeat-1);
		}
		calculatePreis();
	}
	
	public void countDiv (ActionEvent e) {
		CheckBox divBox = (CheckBox)e.getSource();
		
		if (divBox.isSelected()==true) {
			nDiv++;
		}
		else {
			nDiv--;
		}
		calculatePreis();	
	}

	public void setPizzaSize () {
		switch (groesse.getValue()) {
		case "15 cm":
			preisGroesse = 7;
			break;
		case "20 cm":
			preisGroesse = 10;
			break;
		case "25 cm":
			preisGroesse = (float) 13.50;
			break;
		case "30 cm":
			preisGroesse = 17;
			break;
		default:
			preisGroesse = 10;
			break;
		}
		calculatePreis();	
	}

}

package cesarsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CesarsFX extends Application{

	public void start(Stage meineStage) throws Exception {
		//eine Instanz von FXMLLoader erzeugen
		FXMLLoader meinLoader = new FXMLLoader(getClass().getResource("cesar.fxml"));
		//die Datei laden
		Parent root = meinLoader.load();
		//den Controller beschaffen
		FXMLController meinController = meinLoader.getController();
		meinController.setAudio();
		//und die Bühne übergeben
		meinController.setMeineStage(meineStage);
		//die Szene erzeugen
		//an den Konstruktor werden der oberste Knoten und die Größe übergeben
		Scene meineScene = new Scene(root, 600, 490);
		//den Titel über stage setzen
		meineStage.setTitle("CesarsFX-Cipher v2.0");
		//die Szene setzen
		meineStage.setScene(meineScene);
		//und anzeigen
		meineStage.show();

	}

}

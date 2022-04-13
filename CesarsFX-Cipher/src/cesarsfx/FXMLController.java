package cesarsfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FXMLController {
	private Stage meineStage;
	@FXML private ImageView image;
	@FXML private TextField eingabeR;
	@FXML private TextField eingabeA;
	@FXML private Button button;
	@FXML private Button reset;
	private boolean aOn, rOn, check=true, valid, inProgress;
	private int countValidLength = 0;
	private int zahlArab;
	private String zahlRoma,frame;
	private int[] frames= new int[4];
	private int zahlen[];
	private char[] charray;
	private Element[] elemente= {new Element(1,"I"), new Element(2,"II"), new Element(3,"III"), new Element(4, "IV"), new Element(5,"V"), new Element(6,"VI"), new Element(7,"VII"), new Element(8,"VIII"), new Element(9, "IX"), new Element(10,"X"), 
			new Element(20,"XX"), new Element(30,"XXX"), new Element(40, "XL"), new Element(50,"L"), new Element(60,"LX"), new Element(70,"LXX"), new Element(80,"LXXX"), new Element(90, "XC"), new Element(100,"C"), 
			new Element(200,"CC"), new Element(300,"CCC"), new Element(400, "CD"), new Element(500,"D"), new Element(600,"DC"), new Element(700,"DCC"), new Element(800,"DCCC"), new Element(900, "CM"), new Element(1000,"M"), 
			new Element(2000,"MM"), new Element(3000,"MMM")};
	private SingleElement[] elemente2= {new SingleElement(1,'I'), new SingleElement(5,'V'), new SingleElement(10,'X'), new SingleElement(50,'L'), new SingleElement(100,'C'), new SingleElement(500,'D'), new SingleElement(1000,'M')};
	
	//Übergibt die Stage
	public void setMeineStage(Stage meineStage) {
		this.meineStage = meineStage;
	}
	//Erfasst Eingabefenster und schaltet anderes (*im Wechsel*) ab
	@FXML private void nonEditableArabic(javafx.scene.input.MouseEvent event) {
		if(event.getClickCount()== 1)
		eingabeA.setDisable(true);
		eingabeR.setDisable(false);
		aOn=false;
		rOn=true;
	}
	//Erfasst Eingabefenster und schaltet anderes (*im Wechsel*) ab
	@FXML private void nonEditableRoma(javafx.scene.input.MouseEvent event) {
		if(event.getClickCount()== 1)
		eingabeA.setDisable(false);
		eingabeR.setDisable(true);
		rOn=false;
		aOn=true;
	}
	
	//Erfasst in was übersetzt werden soll und löst die 
	//Methode der zu übersetzende Sprache aus 
	@FXML private void translate(ActionEvent event) {
		if(aOn == true)
			erfasseEingabeArab();
		else if(rOn == true) 
			erfasseEingabeRoma();
	}
	
	//Werkseinstellung
	@FXML private void reset(ActionEvent event) {
		rOn = true;
		aOn = true;
		image.setVisible(false);
		eingabeA.setText(null);
		eingabeR.setText(null);
		eingabeA.setPromptText("Arabic");
		eingabeR.setPromptText("Roma");
		eingabeA.setDisable(false);
		eingabeR.setDisable(false);
		button.setDisable(false);
		valid=false;
		zahlRoma=null;
		zahlArab=0;
		countValidLength=0;
		frames[0]=0;
		frames[1]=0;
		frames[2]=0;
		frames[3]=0;
	}
	
	//Erfasst die Eingabe, überprüft diese *!HIER 
	//EXCAKT!* auf deren Gültigkeit und übergibt Sie
	//weiter an die nächste Methode ..
	private void erfasseEingabeArab() {
		try {
		zahlArab=Integer.parseInt(eingabeA.getText());
		if (zahlArab >= 1 && zahlArab <= 3999)
			dechiffriereZahlArab();
		else {
			//oder aber löst 
			//einen Error aus
			outputError();
		}
		}
		catch(NumberFormatException e) {
			outputError();
			System.out.println(e);
		}
	}

	//Erfasst die Eingabe, überprüft diese *!HIER NUR DIE
	//WORTLÄNGE!* auf deren Gültigkeit und übergibt
	//Sie weiter an die nächste Methode ..
	private void erfasseEingabeRoma() {
		zahlRoma=eingabeR.getText(); 
		if (zahlRoma.length() >=1 && zahlRoma.length()<=9)
			dechiffriereZahlRoma();	
		else {
			//oder aber löst 
			//einen Error aus
			outputError();
		}
	}
	
	//Dechiffriert die Zahlen einzeln und übergibt sie multipliziert
	//mit dem jeweils benötigten Exponenten an die nächste 
	//Methode in welcher chiffriert wird weiter
	private void dechiffriereZahlArab() {
		String a, b, c, d;
		String zahl; 
		zahl = Integer.toString(zahlArab);

		if(zahl.length() == 4) {
			inProgress=true;
				a = zahl.charAt(0) + "000";
					chiffriereArabToRoma(a);
				b = zahl.charAt(1) + "00";	
					chiffriereArabToRoma(b);
				c= zahl.charAt(2) + "0";	
					chiffriereArabToRoma(c);
					
			inProgress=false;
				d = zahl.charAt(3)+"";
					chiffriereArabToRoma(d);
		}	
		if(zahl.length() == 3) {
			inProgress=true;
				b = zahl.charAt(0) + "00";	
					chiffriereArabToRoma(b);
				c= zahl.charAt(1) + "0";	
					chiffriereArabToRoma(c);
					
			inProgress=false;
				d = zahl.charAt(2)+ "";
					chiffriereArabToRoma(d);
		}
		if(zahl.length() == 2) {
			inProgress=true;
				c= zahl.charAt(0) + "0";	
					chiffriereArabToRoma(c);
					
			inProgress=false;
				d = zahl.charAt(1)+"";
					chiffriereArabToRoma(d);
		}
		
		if(zahl.length() == 1) {
			inProgress=false;
				chiffriereArabToRoma(zahl);
		}
	}
	
	//Dechiffriert die Chiffre in Zeichen, überprüft diese
	//auf ihre Gültigkeit und erzeugt ein Integer-Array #Zahlen
	// welches mit Zahlen gefüllt wird
	private void dechiffriereZahlRoma() {
		zahlen = new int[zahlRoma.length()];
		charray = new char[zahlRoma.length()];
		char einer;
		
		//Liest die Buchstaben einzeln in ein Array ein
		for (int i=0; i<zahlRoma.length(); i++) {					
			charray[i] = zahlRoma.charAt(i);	
		}
		
		//ERSTER KONTROLLSCHRITT
		//Vergleicht die Werte aller eingelesenen Buchstaben und 
		//füllt die Werte in das Array #Zahlen
		for (int i=0; i<charray.length;i++) {
			for (int j=0; j<elemente2.length;j++) 
				if(charray[i] == elemente2[j].buchstabe) {
					zahlen[i] = elemente2[j].zahl;
					countValidLength++;;
				}
			}
		if(countValidLength == zahlen.length)  {
			countValidLength=1;
			chiffriereRomaToArabic();
		}
		else {
			outputError();
		}
	}
	
	//Chiffriert die übergebenen Zahlensekmente in
	//die auszugebende römische Chiffre
	//und stößt die Ausgabe an
	private void chiffriereArabToRoma(String frame) {
		String tmpOutput = null;
		int input=Integer.parseInt(frame);
		//Der Vergleich findet über den ParameterInput dieser Methode 
		//sowie der Klasse Elemente statt, addiert diese und formt 
		//so final die zu übersetzende römische Chiffre
		if(frame.length() == 4) {
			for (int i = 26; i<elemente.length; i++) 
				if(elemente[i].zahl == input) {
					tmpOutput = elemente[i].buchstabe;
					if(zahlRoma == null) 
						zahlRoma=tmpOutput;	
						break;
				}
		}
		if(frame.length() == 3) {
			for (int i = 17; i<elemente.length; i++) 
				if(elemente[i].zahl == input) {
					tmpOutput = elemente[i].buchstabe;
					if(zahlRoma == null) 
						zahlRoma=tmpOutput;		
					else 
						zahlRoma=zahlRoma+tmpOutput;
					break;
				}
		}
		if(frame.length() == 2) {
			for (int i = 8; i<elemente.length; i++) 
				if(elemente[i].zahl == input) {
					tmpOutput = elemente[i].buchstabe;
					if(zahlRoma == null) 
						zahlRoma=tmpOutput;	
					else 
						zahlRoma=zahlRoma+tmpOutput;
					break;
			}
		}
		if(frame.length() == 1) {
			for (int i = 0; i<elemente.length; i++) 
				if(elemente[i].zahl == input) {
					tmpOutput = elemente[i].buchstabe;
					if(zahlRoma == null) 
						zahlRoma=tmpOutput;	
						
					else 
						zahlRoma=zahlRoma+tmpOutput;
					break;
			}
		}
		if(inProgress==false)
		ausgabeSetzen();
	}
	
	//Chiffriert in verschiedenen Etappen die gesuchte Zahl
	// MCXI ----> 0-9
	private void chiffriereRomaToArabic() {
		int slave=0, count = 0;
		//Geht das Array #Zahlen in 2-er Schritten durch und vergleicht auf A<B,
		//trifft dies zu ergibt sich eine Referenz für einen/mehrere
		//Summand-/en für die spätere Kalkulation..
		for(int iMaster=1;iMaster < zahlen.length;iMaster++) {	
			if(zahlen[slave] < zahlen[iMaster]) {
				//Referenz-Wert der späteren Ausgabe
				zahlArab = zahlArab + (-zahlen[slave]) + zahlen[iMaster];	
				//Referenz-Werte Array #Frames für A<B-Validierung, Sie sorgt
				// die Einhaltung der Größenabfolge ...
				if(frames[count] == 0) {
						frames[count] =  (-zahlen[slave]) + zahlen[iMaster];
						count++;
					}
					//formt 2x char zu 1x String für die Überprüfung auf ihre 
					//Existenzberechtigung in dieser Art der Kombination..
					frame = charray[slave] + "" + charray[iMaster];
					//durch die Übergabe des A<B Charakters
					//an folgende Schleife
					for(int i=0; i< elemente.length;i++) {
						if(frame.compareTo(elemente[i].buchstabe) == 0) {
							//wenn vorhanden ...
							valid=true;
							break;
						}
					}	
				//sonst
				if(valid!=true) {
					outputError();
					return;
				}
				else
					//Wenn (A<B) zugetroffen hat werden die
					//Values des Array #Zahlen[] nachdem 
					//addieren auf null gesetzt
					zahlen[slave] = 0;
					zahlen[iMaster] = 0;
					//parametrierung der Schleife für Array #Zahlen
 					if(zahlen[iMaster] != zahlen.length-1) {
						iMaster++;
						slave++;
						}
					}
				else 
					//Für den max.(A=B=C) Vergleich über ..
					if(zahlen[slave] == zahlen[iMaster]) {
						//Referenz-Wert #COUNTVALID(A=B=C)
						countValidLength++;
					}
					slave++;
				}
			//addiert die übrig gebliebenen Summanden des 
			//Arrays #Zahlen zur finalen Endsumme
			for(int i=0;i < zahlen.length;i++) {	 
					zahlArab = zahlArab + zahlen[i];
			}
			//Vergleich die einzuhaltende Größenabfolge 
			//der Referenz-Werte des Arrays #Frames ..
			if(frames[0] != 0 && frames[1] != 0) 
				if(frames[0] > frames[1] && frames[1] > frames[2] && frames[2] >= frames[3]) {
						//und #CHECK((A1<B1)>(A2<B2)>(A3<B3))
						check=true;
					}
					else 
						check=false;
			else
				System.out.println(countValidLength +"    "+check+"    "+zahlArab);
				//Validiert auf Einhaltung sämtlicher Bedingungen
				//#COUNTVALID(A=B=C), 
				//#CHECKPAERCHEN && #CHECKPAERCHENOK(A<B=OK) sowie
				//#CHECK((A1<B1)>(A2<B2)>(A3<B3)) ...
				if(countValidLength<4 && check==true && zahlArab < 4000) {	
					//und gibt die Übersetzung aus, ...
					ausgabeSetzen();
					countValidLength=0;
					}
				else
					//oder aber eine Error-Meldung.
					outputError();
	}
	
	//Setzt eine Fehlermeldung in die Ausgabe der GUI
	private void outputError() {
		eingabeA.setDisable(true);
		eingabeR.setDisable(true);
		eingabeR.setText("INVALID CIPHER");
		eingabeA.setText("RESET");
		image.setVisible(true);
	}
	
	//Setzt die Ausgabe und passt 
	//die Parametrie an
	private void ausgabeSetzen() {
		String arab = Integer.toString(zahlArab);
		if(rOn == true) 
			eingabeA.setText(arab);
			eingabeA.setDisable(true);
		if(aOn == true)
			eingabeR.setText(zahlRoma);
			eingabeR.setDisable(true);
			button.setDisable(true);
			reset.setFocusTraversable(true);		
	}
}

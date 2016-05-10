package it.polito.tdp.lab3.controller;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Model;
import it.polito.tdp.lab3.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

	private Model model;
	List<Corso> corsi = new LinkedList<Corso>();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<Corso> comboCorso;

	@FXML
	private TextField txtMatricola;

	@FXML
	private Button btnCercaNome;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCognome;

	@FXML
	private Button btnCerca;

	@FXML
	private Button btnIscrivi;

	@FXML
	private TextArea txtResult;

	@FXML
	private Button btnReset;

	public void setModel(Model model) {
		this.model = model;
		corsi = model.getTuttiICorsi();
		Corso corsoFinto = new Corso("");
		corsoFinto.setNome(" ");
		corsi.add(corsoFinto);
		Collections.sort(corsi);
		comboCorso.getItems().addAll(corsi);
	}

	@FXML
	void doCerca(ActionEvent event) {
		txtResult.clear();
		txtNome.clear();
		txtCognome.clear();
		Corso corso = comboCorso.getValue();
		Studente studente = null;
		if (!txtMatricola.getText().isEmpty() && (corso == null || corso.getNome().compareTo(" ")==0)) {
			try {
				studente = model.getStudente(Integer.parseInt(txtMatricola.getText()));
				if (studente == null)
					txtResult.setText("Studente non trovato.");
				else {
					txtNome.setText(studente.getNome());
					txtCognome.setText(studente.getCognome());
					List<Corso> corsi = model.getCorsiPerStudente(studente);
					for (Corso c : corsi)
						txtResult.appendText(c.toString());
				}
			} catch (NumberFormatException nfe) {
				txtResult.setText("Inserire solamente caratteri numerici nello slot matricola!");
			}
		} else if (corso != null && txtMatricola.getText().isEmpty()) {
			List<Studente> studenti = model.getStudentiPerCorso(corso);
			for (Studente s : studenti)
				txtResult.appendText(s.toString());
		} else if ((corso == null || corso.getNome().compareTo(" ")==0) && txtMatricola.getText().isEmpty())
			txtResult.setText("Scrivere qualcosa.");
		else
			txtResult.setText("Tentativo non valido.");
	}

	@FXML
	void doCercaNome(ActionEvent event) {
		Studente s = null;
		if (txtMatricola.getText().isEmpty()) {
			txtResult.setText("Inserire un numero di matricola.");
			return;
		}
		try {
			s = model.getStudente(Integer.parseInt(txtMatricola.getText()));
		} catch (NumberFormatException nfe) {
			txtResult.setText("Inserire solamente caratteri numerici nello slot matricola!");
		}
		if (s != null) {
			txtResult.clear();
			txtNome.setText(s.getNome());
			txtCognome.setText(s.getCognome());
		} else
			txtResult.setText("Studente non trovato.");
	}

	@FXML
	void doIscrivi(ActionEvent event) {
		Studente s = null;
		Corso c = null;
		c = comboCorso.getValue();
		try {
			s = model.getStudente(Integer.parseInt(txtMatricola.getText()));
		} catch (NumberFormatException nfe) {
			txtResult.setText("Inserire solamente caratteri numerici nello slot matricola!");
		}

	}

	@FXML
	void doReset(ActionEvent event) {
		comboCorso.getSelectionModel().clearSelection();
		txtMatricola.clear();
		txtNome.clear();
		txtCognome.clear();
		txtResult.clear();
	}

	@FXML
	void initialize() {
		assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaNome != null : "fx:id=\"btnCercaNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
	}
}

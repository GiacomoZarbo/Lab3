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
		if (corso != null && corso.getNome().compareTo(" ") != 0 && txtMatricola.getText().isEmpty()) {
			List<Studente> studenti = model.getStudentiPerCorso(corso);
			for (Studente s : studenti)
				txtResult.appendText(s.toString());
			return;
		}
		try {
			studente = model.getStudente(Integer.parseInt(txtMatricola.getText()));
			if (!txtMatricola.getText().isEmpty() && (corso == null || corso.getNome().compareTo(" ") == 0)) {
				if (studente != null) {
					txtNome.setText(studente.getNome());
					txtCognome.setText(studente.getCognome());
					List<Corso> corsi = model.getCorsiPerStudente(studente);
					for (Corso c : corsi)
						txtResult.appendText(c.toString());
				} else
					txtResult.setText("Studente non trovato.");
			} else if ((corso != null && corso.getNome().compareTo(" ") != 0) && !txtMatricola.getText().isEmpty()) {
				txtNome.setText(studente.getNome());
				txtCognome.setText(studente.getCognome());
				if (model.isStudenteIscrittoACorso(Integer.parseInt(txtMatricola.getText()), corso.getCodIns()))
					txtResult.setText("" + studente.getNome() + " " + studente.getCognome() + " ("
							+ studente.getMatricola() + ") è iscritto al corso di " + corso.getNome() + ".");
				else
					txtResult.setText("" + studente.getNome() + " " + studente.getCognome() + " ("
							+ studente.getMatricola() + ") non è iscritto al corso di " + corso.getNome() + ".");
			} else if ((corso == null || corso.getNome().compareTo(" ") == 0) && txtMatricola.getText().isEmpty())
				txtResult.setText("Scrivere qualcosa.");
			else
				txtResult.setText("Tentativo non valido.");
		} catch (NumberFormatException nfe) {
			txtResult.setText("Errore!");
		}
	}

	@FXML
	void doCercaNome(ActionEvent event) {
		txtNome.clear();
		txtCognome.clear();
		txtResult.clear();
		Studente s = null;
		if (txtMatricola.getText().isEmpty()) {
			txtResult.setText("Inserire un numero di matricola.");
			return;
		}
		try {
			s = model.getStudente(Integer.parseInt(txtMatricola.getText()));
			if (s != null) {
				txtResult.clear();
				txtNome.setText(s.getNome());
				txtCognome.setText(s.getCognome());
			} else
				txtResult.setText("Studente non trovato.");
		} catch (NumberFormatException nfe) {
			txtResult.setText("Inserire solamente caratteri numerici nello slot matricola!");
		}
	}

	@FXML
	void doIscrivi(ActionEvent event) {
		txtResult.clear();
		Studente studente = null;
		try {
			studente = model.getStudente(Integer.parseInt(txtMatricola.getText()));
			if (studente == null)
				txtResult.setText("Studente non trovato.");
		} catch (NumberFormatException nfe) {
			txtResult.setText("Inserire solamente caratteri numerici nello slot matricola!");
		}
		Corso corso = comboCorso.getValue();
		if (corso != null && corso.getNome().compareTo(" ") != 0 && txtMatricola.getText() != null) {
			if (!model.isStudenteIscrittoACorso(Integer.parseInt(txtMatricola.getText()), corso.getCodIns())) {
				boolean b = model.iscriviStudenteACorso(studente.getMatricola(), corso.getCodIns());
				if (b)
					txtResult.setText("Studente iscritto con successo!");
			} else
				txtResult.setText("Studente già iscritto!");
		} else
			txtResult.setText("Tentativo non valido.");
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
		txtResult.setStyle("-fx-font-family: monospace");
	}
}

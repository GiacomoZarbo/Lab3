package it.polito.tdp.lab3.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab3.DAO.DAOCorsoStudente;

public class Model {
	DAOCorsoStudente daoCorsoStudente;

	public Model() {
		daoCorsoStudente = new DAOCorsoStudente();
	}

	public Studente getStudente(int matricola) {
		Studente studente = new Studente(matricola, null, null, null);
		if (daoCorsoStudente.getStudente(studente))
			return studente;
		else
			return null;
	}

	public List<Corso> getTuttiICorsi() {
		List<Corso> lista = new LinkedList<Corso>();
		lista = daoCorsoStudente.getTuttiICorsi();
		return lista;
	}

	public List<Studente> getStudentiPerCorso(Corso corso) {
		List<Studente> lista = new LinkedList<Studente>();
		lista = daoCorsoStudente.getStudentePerCorso(corso);
		return lista;
	}

	public List<Corso> getCorsiPerStudente(Studente studente) {
		List<Corso> lista = new LinkedList<Corso>();
		lista = daoCorsoStudente.getCorsoPerStudente(studente);
		return lista;
	}

	public boolean isStudenteIscrittoACorso(int matricola, String codIns) {
		Studente studente = new Studente(matricola);
		Corso corso = new Corso(codIns);
		return daoCorsoStudente.isStudenteIscrittoACorso(studente, corso);
	}

	public boolean iscriviStudenteACorso(int matricola, String codIns) {
		Studente studente = new Studente(matricola);
		Corso corso = new Corso(codIns);
		return daoCorsoStudente.iscriviStudenteACorso(studente, corso);
	}

}

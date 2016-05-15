package it.polito.tdp.lab3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Studente;

public class DAOCorsoStudente {

	public boolean getStudente(Studente studente) {
		final String sql = "SELECT * FROM studente where matricola=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				studente.setNome(rs.getString("nome"));
				studente.setCognome(rs.getString("cognome"));
				studente.setCds(rs.getString("cds"));
				return true;
			} else
				return false;
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Corso> getTuttiICorsi() {
		final String sql = "SELECT * FROM corso";
		List<Corso> corsi = new LinkedList<Corso>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));
				corsi.add(c);
			}
			return corsi;
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Studente> getStudentePerCorso(Corso corso) {
		List<Studente> studenti = new LinkedList<Studente>();
		final String sql = "SELECT studente.matricola, studente.cognome, studente.nome, studente.CDS FROM iscrizione, studente WHERE iscrizione.matricola=studente.matricola AND codins=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodIns());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				studenti.add(new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"),
						rs.getString("cds")));
			}
			corso.setStudenti(studenti);
			return studenti;
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Corso> getCorsoPerStudente(Studente studente) {
		List<Corso> corsi = new LinkedList<Corso>();
		final String sql = "SELECT corso.codins, corso.crediti, corso.nome, corso.pd FROM iscrizione, corso WHERE iscrizione.codins=corso.codins AND matricola=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				corsi.add(
						new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd")));
			}
			studente.setCorsi(corsi);
			return corsi;
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}

	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso) {
		final String sql = "SELECT matricola FROM iscrizione WHERE matricola=? AND codins=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodIns());
			ResultSet rs = st.executeQuery();
			if (rs.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}

	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		final String sql = "INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES("
				+ studente.getMatricola() + ",'" + corso.getCodIns() + "');";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			int res = st.executeUpdate(sql);
			if (res == 1)
				return true;
			else
				return false;
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}
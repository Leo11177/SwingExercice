package fr.leo.swing;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FenetreAccueil extends JFrame {

	private static final long serialVersionUID = 1L;

	// ATTRIBUTS
	private Connection connection;
	protected JPanel root;
	protected Label lblTitreVue = new Label("");
	protected JTable jtbl_ListeDesUtilisateur;
	protected JButton btnNouvelUtilisateur = new JButton("AJOUTER UN NOUVEL ACCES");

	// CONSTRUCTEUR
	public FenetreAccueil(Connection connection) {
		this.connection = connection;
		initialisationFenetreAccueil();
		initialisationVueListeDesUtilisateurs();
	}

	// INITIALISATION DE LA FENETRE
	protected void initialisationFenetreAccueil() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setSize(1200, 490);
		setVisible(true);
	}

	protected void initialisationVueListeDesUtilisateurs() {
		setTitle("LISTE DES UTILISATEURS");
		// Remplissage de la JTable avec les données provenant de la Bdd
		fillAndDisplayJTable();
		// Ajout des composants à la fenetre
		root = (JPanel) getContentPane();
		root.add(lblTitreVue, BorderLayout.NORTH);
		root.add(jtbl_ListeDesUtilisateur, BorderLayout.CENTER);
		root.add(btnNouvelUtilisateur, BorderLayout.SOUTH);
		// Listeners
		jtbl_ListeDesUtilisateur.addMouseListener(new Evenenements());
		btnNouvelUtilisateur.addMouseListener(new Evenenements());
		//this.repaint();
	}

	// TRAITEMENT DES EVENEMENTS SOURIS
	class Evenenements extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == jtbl_ListeDesUtilisateur) {
				String idString = (String) jtbl_ListeDesUtilisateur
						.getValueAt(jtbl_ListeDesUtilisateur.getSelectedRow(), 0);
				Integer id = Integer.parseInt(idString);
				int row = jtbl_ListeDesUtilisateur.getSelectedRow();
				if (jtbl_ListeDesUtilisateur.getSelectedColumn() == 3) {
					modificationUtilisateur(id, row); // modification
				} else if (jtbl_ListeDesUtilisateur.getSelectedColumn() == 4) {
					SuppressionUtilsateur(id, row); // suppression
				} else if (jtbl_ListeDesUtilisateur.getSelectedColumn() == 5) {
					EditionUtilisateur(id); // edition
				}
			} else if (e.getSource() == btnNouvelUtilisateur) {
				creationUtilisateur(); // creation
			}
		}
	}

	// EDITION UTILISATEUR
	private void EditionUtilisateur(Integer idUtilisateur) {
		
		new VueEditonUtilisateur(this, idUtilisateur, connection);
	}

	// SUPPRESSION UTILISATEUR
	private void SuppressionUtilsateur(Integer idUtilisateur, Integer row) {
		String utilisateurSelectionner = (String) jtbl_ListeDesUtilisateur.getValueAt(row, 1);
		int reponse = JOptionPane.showConfirmDialog(null,
				"Voulez vous vraiment supprimer l'utilisateur " + utilisateurSelectionner + " ?");
		if (reponse == 0) {
			String sqlQuery = "delete from COLLABORATEUR where ID_MATRICULECOLLABORATEUR = " + idUtilisateur;
			try {
				SqlUtilitaires.executeDelete(connection, sqlQuery);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//repaint();
		}
	}

	// MODIFICATION UTILISATEUR
	private void modificationUtilisateur(Integer idUtilisateur, Integer row) {

		String utilisateurSelectionner = (String) jtbl_ListeDesUtilisateur.getValueAt(row, 1);
		int reponse = JOptionPane.showConfirmDialog(null,
				"Voulez vous vraiment modifer l'utilisateur " + utilisateurSelectionner + " ?");
		if (reponse == 0) {
//			String sqlQuery = "update COLLABORATEUR set " + "STR_NOM = " + jtbl_ListeDesUtilisateur.getValueAt(row, 0)
//					+ " , " + "STR_PRENOM = " + jtbl_ListeDesUtilisateur.getValueAt(row, 1) + ", " + "STR_EMAIL = "
//					+ jtbl_ListeDesUtilisateur.getValueAt(row, 2) + " ) " + " where id = " + idUtilisateur;

//			+ " , " + "STR_PRENOM = " + jtbl_ListeDesUtilisateur.getValueAt(row, 1) + ", " + "STR_EMAIL = "
//			+ jtbl_ListeDesUtilisateur.getValueAt(row, 2) + " ) " + " where id = " + idUtilisateur;
			String sqlQuery = "update COLLABORATEUR set STR_NOM = ?, STR_PRENOM = ? where ID_MATRICULECOLLABORATEUR = "
					+ idUtilisateur;
			try {
				PreparedStatement ps = connection.prepareStatement(sqlQuery);
				String nom = (String) jtbl_ListeDesUtilisateur.getValueAt(row, 1);
				ps.setString(1, nom);
				String prenom = (String) jtbl_ListeDesUtilisateur.getValueAt(row, 2);
				ps.setString(2, prenom);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// CREATION UTILISATEUR
	private void creationUtilisateur() {
		root.setVisible(false);
		new VueCreationUtilisateur(this, connection,root);
	}

	// ALIMENTATION JTABLE AVEC DONNEES DE LA BDD
	protected void fillAndDisplayJTable() {
		// Création d'une table associée à un model de 4 colonnes et 0 lignes
		String[] cols = { "Nom", "Prenom", "Email", "Modifer", "Supprimer", "Editer" };
		DefaultTableModel listData = new DefaultTableModel(cols, 0);
		jtbl_ListeDesUtilisateur = new JTable(listData);
		// Association d'un panneau permettant le défilement

		// panTable.setVerticalScrollBar(panTable);
		// panTable.setVisible(true);
		// add(panTable, BorderLayout.CENTER);

		// Ajout dynamique de lignes au modele
		ArrayList<String> tabArrayList = new ArrayList<String>();
		tabArrayList = SqlUtilitaires.executeQuery(connection,
				"select ID_MATRICULECOLLABORATEUR, STR_NOM, STR_PRENOM from COLLABORATEUR");
		listData.addRow(cols);
		String[] tabplus2 = new String[tabArrayList.size()];
		int i = 0;
		for (String string : tabArrayList) {
			Object[] tab = string.split(" ");
			Personne p = new Personne(tab[2].toString(), tab[1].toString(), Integer.parseInt(tab[0].toString()));
			listData.addRow(tab);
			// pers.add(p);
			tabplus2[i] = string;
			i = i + 1;
		}

		JScrollPane jsPane = new JScrollPane(jtbl_ListeDesUtilisateur, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// root.add(jsPane);

	}
}

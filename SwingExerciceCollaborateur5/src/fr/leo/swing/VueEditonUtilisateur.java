package fr.leo.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VueEditonUtilisateur extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// ATTRIBUTS
	private FenetreAccueil fenetreAccueil;
	private Label lblTitreVue = new Label("EDITION UTILISATEUR");
	private Connection connection;
	private ArrayList<String> valuesArrayList = new ArrayList<String>();
	private JButton jBtnCreation = new JButton("Creation");
	private Integer idUtilisateur;

	private JPanel pnlCentral = new JPanel();
	private JLabel lblNom = new JLabel("Nom :");
	private JLabel lblPrenom = new JLabel("Prenom :");
	private JLabel lblAdresse = new JLabel("Adresse :");
	private JLabel lblDateNaissance = new JLabel("Date naissance :");
	private JLabel lblPrefession = new JLabel("Profession :");

	private JTextField jtfNom = new JTextField();
	private JTextField jtfPrenom = new JTextField();
	private JTextField jtfAdresse = new JTextField();
	private JTextField jtfDateNaissance = new JTextField();
	private JTextField jtfProfession = new JTextField();

	// CONSTRUCTEUR
	public VueEditonUtilisateur(FenetreAccueil _fenetreAccueil, Integer _idUtilisateur, Connection _connection) {
		this.fenetreAccueil = _fenetreAccueil;
		this.connection = _connection;
		this.idUtilisateur = _idUtilisateur;
		initialisationFenetreAccueil();
		initialisationVue();
		executeSelectQuery();
	}

	// AFFECTATION NOUVEAU PANEL A LA FENETRE ACCUEIL
	private void initialisationFenetreAccueil() {
		fenetreAccueil.setTitle("EDITION UTILISATEUR");
		this.setLayout(new BorderLayout());
		fenetreAccueil.setContentPane(this);
		fenetreAccueil.repaint();
		this.repaint();
	}

	// INITIALISATION DE LA VUE
	private void initialisationVue() {
		// Placement des composants
		this.setLayout(new BorderLayout());
		pnlCentral.setLayout(new GridLayout(5, 2));
		pnlCentral.setBackground(Color.yellow);
		pnlCentral.add(lblNom);
		pnlCentral.add(jtfNom);
		pnlCentral.add(lblPrenom);
		pnlCentral.add(jtfPrenom);
		pnlCentral.add(lblAdresse);
		pnlCentral.add(jtfAdresse);
		pnlCentral.add(lblDateNaissance);
		pnlCentral.add(jtfDateNaissance);
		pnlCentral.add(lblPrefession);
		pnlCentral.add(jtfProfession);
		// Ajout des composants à la fenetre
		this.add(lblTitreVue, BorderLayout.NORTH);
		this.add(pnlCentral, BorderLayout.CENTER);
		// Listeners
		jBtnCreation.addMouseListener(new Evenenements());
		this.repaint();
		fenetreAccueil.repaint();
		
	}

	/**
	 * EXECUTION REQUETE DE SELECTION de l'utilisateur sélectionné
	 * Récupération des données utilisateur
	 */
	private void executeSelectQuery() {
		String sqlQuery = "select * from COLLABORATEUR WHERE ID_MATRICULECOLLABORATEUR = " + idUtilisateur + " ;";
		System.out.println(sqlQuery);
		ArrayList<String> valuesArrayList = SqlUtilitaires.executeQueryEdition( connection , sqlQuery);
		//valuesArrayList.add("1 sysy 214");
		String row = valuesArrayList.get(0);
		String[] tab = row.split(" ");
		jtfNom.setText(tab[0]);
		jtfPrenom.setText(tab[1]);
		jtfProfession.setText(tab[2]);
	}

	// TRAITEMENT DES EVENEMENTS SOURIS
	class Evenenements extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
		}
	}
}

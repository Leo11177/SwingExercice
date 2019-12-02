package fr.leo.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VueCreationUtilisateur extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// ATTRIBUTS
	FenetreAccueil fenetreAccueil;
	private JButton btnCreation = new JButton("Creation");
	protected JTable jtbl_ListeDesUtilisateur;
	protected String sqlInsert;
	private Connection connection;
	private JPanel root;

	JPanel pnlCentral = new JPanel();
	JLabel lblNom = new JLabel("ID DROIT :");
	JLabel lblPrenom = new JLabel("LOGIN :");
	JLabel lblAdresse = new JLabel("MOT DE PASSE :");
	JLabel lblDateNaissance = new JLabel("Date naissance :");
	JLabel lblPrefession = new JLabel("Profession :");

	JTextField jtfNom = new JTextField();
	JTextField jtfPrenom = new JTextField();
	JTextField jtfAdresse = new JTextField();
	JTextField jtfDateNaissance = new JTextField();
	JTextField jtfProfession = new JTextField();
	JButton btnRetour = new JButton("Retour Accueil");

	// CONSTRUCTEUR
	public VueCreationUtilisateur(FenetreAccueil _fenetreAccueil, Connection connection, JPanel root) {
		this.connection = connection;
		this.fenetreAccueil = _fenetreAccueil;
		this.root = root;
		initialisationFenetreAccueil();
		initialisationVue();
	}

	// AFFECTATION NOUVEAU PANEL A LA FENETRE ACCUEIL
	private void initialisationFenetreAccueil() {
		fenetreAccueil.lblTitreVue.setText("CREATION NOUVEL ACCES");
		fenetreAccueil.setTitle("NOUVEL ACCES");
		fenetreAccueil.setContentPane(this);
		//fenetreAccueil.repaint();
	}

	// INITIALISATION DE LA VUE
	private void initialisationVue() {
		this.setLayout(new BorderLayout());
		pnlCentral.setLayout(new GridLayout(3, 2));
		//pnlCentral.setBackground(Color.yellow);
		pnlCentral.add(lblNom);
		pnlCentral.add(jtfNom);
		pnlCentral.add(lblPrenom);
		pnlCentral.add(jtfPrenom);
		pnlCentral.add(lblAdresse);
		pnlCentral.add(jtfAdresse);
//		pnlCentral.add(lblDateNaissance);
//		pnlCentral.add(jtfDateNaissance);
//		pnlCentral.add(lblPrefession);
//		pnlCentral.add(jtfProfession);
		// Ajout des composants à la fenetre
		this.add(pnlCentral, BorderLayout.CENTER);
		this.add(btnCreation, BorderLayout.SOUTH);
		this.add(btnRetour,BorderLayout.NORTH );
		// Listeners
		btnCreation.addMouseListener(new Evenenements());
		btnRetour.addMouseListener(new Evenenements());
		
		this.setVisible(true);
		//this.repaint();
	}

	// TRAITEMENT DES EVENEMENTS SOURIS
	class Evenenements extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == btnCreation) {
				if (executeInsertQuery()) {
					JOptionPane.showMessageDialog(fenetreAccueil, "Nouvel accés créé avec succés !");
				}
			}else if ((e.getSource() == btnRetour) ){
				System.out.println("retour ...");
				Close();
			}
		}
	}
	
	private void Close() {
		this.setVisible(false);
		root.setVisible(true);
		fenetreAccueil.fillAndDisplayJTable();
		
	}

	/**
	 * Execution de la requete pour insertion du nouvel utilisateur
	 */
	private boolean executeInsertQuery() {
		try {
			Statement statement = connection.createStatement();
			Integer idDroit = Integer.parseInt(  jtfNom.getText());
			String login = jtfPrenom.getText();
			String password = jtfAdresse.getText();
			String sqlInsert = "insert into ACCES (ID_DROIT, STR_LOGIN, STR_MDP)  values (" + idDroit + ", " + "'" + login +"'" + ", " + "'"+ password + "'" +") ;";
			statement.executeUpdate(sqlInsert);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}

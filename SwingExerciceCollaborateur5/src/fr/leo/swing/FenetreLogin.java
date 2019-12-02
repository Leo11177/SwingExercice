package fr.leo.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FenetreLogin extends JFrame{

	private static final long serialVersionUID = 1L;
	
		// Liste des composants
		JPanel pnlButtonsNom = new JPanel();	
		JPanel pnlButtonsPrenom = new JPanel();
		JPanel pnlButtonsPrenomNom = new JPanel();
		JLabel lblNom = new JLabel("Login : ");
		JTextField jtfLogin = new JTextField("sa");
		JLabel lblPrenom = new JLabel("Password : ");
		JTextField jtfPassword = new JTextField("titi");
		JButton bntOk = new JButton("Ok");
		Connection connection;
		
		// Constructeur
		public FenetreLogin() {
				init();			
		}		
		
		// Initialisation de la fenetre et ajouts des listener
		private void init() {
			
			setTitle("Fenetre Login");			
			
			// Panneau de login
			jtfLogin.setColumns(10);
			jtfPassword.setColumns(10);
			pnlButtonsNom.setLayout(new FlowLayout());
			pnlButtonsNom.add(lblNom);
			pnlButtonsNom.add(jtfLogin);
			//pnlButtonsNom.setBackground(Color.YELLOW);
			pnlButtonsPrenom.setLayout(new FlowLayout());
			pnlButtonsPrenom.add(lblPrenom);
			pnlButtonsPrenom.add(jtfPassword);
			//pnlButtonsPrenom.setBackground(Color.YELLOW);
			pnlButtonsPrenomNom.add(pnlButtonsNom);
			pnlButtonsPrenomNom.add(pnlButtonsPrenom);
			pnlButtonsPrenomNom.setLayout(new BoxLayout(pnlButtonsPrenomNom, BoxLayout.Y_AXIS));			
			pnlButtonsPrenomNom.setBackground(Color.ORANGE);
			
			// Bouton ok
			//bntOk.setBackground(Color.GREEN);
			bntOk.addActionListener(new Evenenements());
			
			JPanel root = (JPanel) getContentPane();
			root.add(pnlButtonsPrenomNom, BorderLayout.NORTH);
			root.add(bntOk, BorderLayout.CENTER);
			
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationByPlatform(true);
			setSize(1200,490);
			setVisible(true);
		}
	
	 // Traitement des evenements
	 class Evenenements implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bntOk) { // Ouverture de la fenetre qui liste les utilisateurs
				if  ( (connection = SqlUtilitaires.connectTo(jtfLogin, jtfPassword)) != null) {
				//if (1 == 1) {
					System.out.println("Connexion reussie");
					setVisible(false);
					new FenetreAccueil(connection);		
				}else {
					System.out.println("connexion pbe !");
				}
			}
		}		 
	 }	 
}


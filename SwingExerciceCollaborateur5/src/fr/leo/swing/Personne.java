package fr.leo.swing;

public class Personne {

	String nom;
	String prenom;
	int age;
	public Personne(String nom, String prenom, int age) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
	}
	
	@Override
	public String toString()
	{
		return nom + " " + prenom + " " + age;
	}			
}

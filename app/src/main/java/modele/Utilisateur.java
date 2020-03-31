package modele;

public class Utilisateur {
    private int idUtilisateur;
    private String identifiant;
    private String prenom;
    private String nom;
    private String motdepasse;

    public Utilisateur(int idUtilisateur, String identifiant, String prenom, String nom, String motdepasse) {
        this.idUtilisateur = idUtilisateur;
        this.identifiant = identifiant;
        this.prenom = prenom;
        this.nom = nom;
        this.motdepasse = motdepasse;
    }

    public Utilisateur(int idUtilisateur, String identifiant, String prenom, String nom) {
        this.idUtilisateur = idUtilisateur;
        this.identifiant = identifiant;
        this.prenom = prenom;
        this.nom = nom;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
}

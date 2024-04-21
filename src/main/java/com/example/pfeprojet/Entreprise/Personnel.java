package com.example.pfeprojet.Entreprise;

public class Personnel {

    private String cin, nom, prenom, mail, motPass ;

    public Personnel(String cin, String nom, String prenom, String mail, String motPass) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motPass = motPass;
    }

    public String getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMotPass() {
        return motPass;
    }
}

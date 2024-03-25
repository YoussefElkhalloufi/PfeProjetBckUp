package com.example.pfeprojet.Entreprise;

public class Directeur {

    private String nom, prenom, mail ,motDePasse ;

    public Directeur(String nom, String prenom, String mail,String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.mail = mail;
    }

    public String getNom() {return nom;}
    public String getPrenom() {return prenom;}
    public String getMotDePasse() {return motDePasse;}
    public String getMail() {return mail;}


    public void setNom(String nom) {this.nom = nom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}
    public void setMotDePasse(String motDePasse) {this.motDePasse = motDePasse;}
    public void setMail(String mail) {this.mail = mail;}
}

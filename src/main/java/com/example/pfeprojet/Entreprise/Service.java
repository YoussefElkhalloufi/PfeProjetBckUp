package com.example.pfeprojet.Entreprise;

public class Service {

    private String designation;
    private int nombreHeure;
    private double prix;
    private double totalHT ;

    public Service(String designation, int nbHeure, double prix) {
        this.designation = designation;
        this.nombreHeure = nbHeure;
        this.prix = prix;
        this.totalHT = nombreHeure * prix;
    }

    public String getDesignation() {
        return designation;
    }

    public int getNombreHeure() {
        return nombreHeure;
    }

    public double getPrix() {
        return prix;
    }

    public double getTotal() {
        return totalHT;
    }
}

package com.example.pfeprojet.Entreprise;

public class Produit {

        private String designation;
        private int quantite;
        private double prix;
        private double totalHT ;

        public Produit(String designation, int quantite, double prix) {
            this.designation = designation;
            this.quantite = quantite;
            this.prix = prix;
            this.totalHT = quantite * prix;
        }

        public String getDesignation() {
            return designation;
        }

        public int getQuantite() {
            return quantite;
        }

        public double getPrix() {
            return prix;
        }

        public double getTotal() {
            return totalHT;
        }
}

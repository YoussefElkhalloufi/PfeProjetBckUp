package com.example.pfeprojet.Entreprise;

import com.example.pfeprojet.Connexion;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Entreprise {
    private String nomEntreprise, adresseMail, localisation, numeroDeFax, secteurDactivite, identificationFiscale;
    private BigDecimal chiffreAffaireTotal = null  ;
    private int nbrClients = 0, nbPersonnel = 0;
    private Connexion cn, cn1;

    public Entreprise(String nomEntreprise){
        this.nomEntreprise = nomEntreprise;
        cn = new Connexion("jdbc:mysql://localhost:3306/"+nomEntreprise+"?user=root");
        cn1 = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");
    }

    public Entreprise(){
    }

    public Connexion getCn() {return cn;}

    public String getAdresseMail() {
        return adresseMail;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getNumeroDeFax() {
        return numeroDeFax;
    }

    public String getSecteurDactivite() {
        return secteurDactivite;
    }

    public String getIdentificationFiscale() {
        return identificationFiscale;
    }

    @Override
    public String toString() {
        return "Entreprise{" +
                "nomEntreprise='" + nomEntreprise + '\'' +
                ", adresseMail='" + adresseMail + '\'' +
                ", localisation='" + localisation + '\'' +
                ", numeroDeFax='" + numeroDeFax + '\'' +
                ", secteurDactivite='" + secteurDactivite + '\'' +
                ", identificationFiscale='" + identificationFiscale + '\'' +
                '}';
    }

    public void setInfosEntreprise(){
        try{
            ResultSet rs = cn1.lire("Select nomEntreprise, AdresseMail, Localisation, NumeroDeFax, secteurDactivite, identificationFiscale from infosEntreprises where nomEntreprise = '" +nomEntreprise+"'");
            if(rs.next()){
                nomEntreprise = rs.getString(1).replaceAll("_", " ");
                adresseMail = rs.getString(2);
                localisation = rs.getString(3);
                numeroDeFax = rs.getString(4);
                secteurDactivite = rs.getString(5);
                identificationFiscale = rs.getString(6);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public BigDecimal getChiffreAffaireTotal() {
        calculChiffreAffaire();
        return chiffreAffaireTotal;
    }

    public int getNbrClients() {
        calculNbrClient();
        return nbrClients;
    }

    public int getNbPersonnel() {
        calculNbrPersonnel();
        return nbPersonnel;
    }

    public void calculChiffreAffaire(){
        try{
            ResultSet rs =cn.lire("select sum(Total_TTC) from facture");
            if(rs.next()){
                chiffreAffaireTotal = rs.getBigDecimal(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public double getChiffreAffaireMois(int mois){
        try{
            ResultSet rs = cn.lire("SELECT somme_total_factures_par_mois(?)", String.valueOf(mois));
            if(rs.next()){
                return rs.getDouble(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public double getChiffreAffaireAnnee(int year){
        try{
            ResultSet rs = cn.lire("SELECT somme_total_factures_par_Annee(?)", String.valueOf(year));
            if(rs.next()){
                return rs.getDouble(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void calculNbrClient(){
        try{
            ResultSet rs = cn.lire("Select count(*) from client");
            if(rs.next()){
                nbrClients = rs.getInt(1);
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }

    public void calculNbrPersonnel(){
        try{
            ResultSet rs, rs1, rs2 ;
            rs = cn.lire("select count(*) from gestionnaires");
            rs1 = cn.lire("select count(*) from vendeurs");
            rs2 = cn.lire("select count(*) from responsables");
            if(rs.next() && rs1.next() && rs2.next()){
                nbPersonnel = rs.getInt(1) + rs1.getInt(1) + rs2.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }



    public double CalculTotalTTC(double totalHT, double remise, double tva){
        double totalTTC = 0;
        if(totalHT <= 0 || remise <0 || tva < 0){
            System.out.println("Erreur");
            return totalTTC;
        }else {
            if(remise == 0 && tva == 0){
                totalTTC = totalHT ;
            }else if(remise == 0 && tva > 0){
                totalTTC = totalHT+ ((totalHT * tva) / 100);
            }else if(remise > 0 && tva == 0){
                totalTTC = totalHT - (totalHT * remise)/100 ;
            }else{
                double netCommercial = totalHT -((totalHT * remise)/100);
                totalTTC = netCommercial +(netCommercial * tva) /100;
            }
        }
        return totalTTC;
    }
}

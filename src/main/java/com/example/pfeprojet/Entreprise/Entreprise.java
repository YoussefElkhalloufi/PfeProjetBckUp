package com.example.pfeprojet.Entreprise;

import com.example.pfeprojet.Connexion;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Entreprise {
    private String nomEntreprise, adresseMail,motdepasse, localisation, numeroDeFax, secteurDactivite, identificationFiscale;
    private BigDecimal chiffreAffaireTotal = null  ;
    private int nbrClients = 0, nbPersonnel = 0;
    private Connexion cn, cn1;

    public Entreprise(String nomEntreprise){
        this.nomEntreprise = nomEntreprise;
        cn = new Connexion("jdbc:mysql://localhost:3306/"+nomEntreprise+"?user=root");
        cn1 = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");
    }

    public Entreprise(){}

    public void setMotdepasse(String motdepasse){this.motdepasse = motdepasse;}

    public Connexion getCn() {return cn;}

    public String getAdresseMail() {
        return adresseMail;
    }

    public String getMotdepasse() {return motdepasse;}

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
            ResultSet rs = cn1.lire("Select nomEntreprise, AdresseMail, motdepasse, Localisation, NumeroDeFax, secteurDactivite, identificationFiscale from infosEntreprises where nomEntreprise = '" +nomEntreprise+"'");
            if(rs.next()){
                nomEntreprise = rs.getString(1);
                adresseMail = rs.getString(2);
                motdepasse = rs.getString(3);
                localisation = rs.getString(4);
                numeroDeFax = rs.getString(5);
                secteurDactivite = rs.getString(6);
                identificationFiscale = rs.getString(7);
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
    public ResultSet getResponsables(){
        return cn.lire("select * from responsables");
    }
    public ResultSet getGestionnaires(){
        return cn.lire("select * from gestionnaires");
    }
    public ResultSet getVendeurs(){
        return cn.lire("select * from vendeurs");
    }
    public boolean insererPersonnel(String typePerso,String cin, String nom, String prenom, String mail, String pwd){
        String req = "insert into "+typePerso+" values(?,?,?,?,?)";
        return cn.miseAjour(req, cin, nom, prenom, mail, pwd);
    }
    public boolean supprimerPersonnel(String typePerso, String cin){
        String req = "delete from "+typePerso+" where cin = ?";
        return cn.miseAjour(req,cin);
    }
    public boolean modifierPersonnel(String typePerso,String cinNv, String nom, String prenom, String mail, String pwd,String cinAncien){
        String req = "UPDATE `"+typePerso+"` SET `CIN`=?,`Nom`=?,`Prenom`=?," +
                "`AdresseMail`=?,`motdePasse`=? WHERE cin = ?";
        return cn.miseAjour(req, cinNv, nom, prenom, mail, pwd, cinAncien);
    }
    public ResultSet afficherPersonnel(String typePerso, String cin ){
        String req = "select * from " + typePerso +" where cin = ?";
        return cn.lire(req, cin);
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
    public void populateTableViewWithSelectionHandler(ResultSet rs, TableView<Object[]> tableView, TextField cinTextField, TextField nomTextField, TextField prenomTextField, TextField mailTextField, TextField passwordTextField) {
        try {
            // Clear existing items in the TableView
            tableView.getItems().clear();
            tableView.setStyle("-fx-border-color: black");

            // Get metadata about the ResultSet
            ResultSetMetaData rsm = rs.getMetaData();
            int columnCount = rsm.getColumnCount();

            // Create TableColumn objects for each column in the ResultSet
            tableView.getColumns().clear(); // Clear existing columns
            for (int i = 1; i <= columnCount; i++) {
                final int columnIndex = i;
                TableColumn<Object[], Object> column = new TableColumn<>(rsm.getColumnName(i));
                column.setCellValueFactory(cellData -> {
                    Object[] row = cellData.getValue();
                    return new SimpleObjectProperty<>(row[columnIndex - 1]); // Note: columnIndex is 1-based
                });
                tableView.getColumns().add(column);
            }

            // Add selection handler to populate text fields with selected row's data
            tableView.setOnMouseClicked(event -> {
                Object[] rowData = tableView.getSelectionModel().getSelectedItem();
                if (rowData != null) {
                    cinTextField.setText(rowData[0].toString()); // Assuming CIN is in the first column
                    nomTextField.setText(rowData[1].toString()); // Assuming Nom is in the second column
                    prenomTextField.setText(rowData[2].toString()); // Assuming Prenom is in the third column
                    mailTextField.setText(rowData[3].toString()); // Assuming Mail is in the fourth column
                    passwordTextField.setText(rowData[4].toString()); // Assuming Mot de Passe is in the fifth column
                }
            });

            // Iterate through the ResultSet and add data to the TableView
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i + 1); // Note: ResultSet index is 1-based
                }
                tableView.getItems().add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

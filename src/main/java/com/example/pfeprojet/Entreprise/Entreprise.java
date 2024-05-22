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
import java.util.ArrayList;
import java.util.List;


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

    public ArrayList<String> getCinClients()  {
        try {
            ArrayList<String> cinClients = new ArrayList<>();
            ResultSet rs = cn.lire("Select cinClient from client");
            while(rs.next()){
                cinClients.add(rs.getString(1));
            }
            return cinClients;
        }catch (SQLException e) {
            e.printStackTrace();
            return null ;
        }
    }

    public ArrayList<Integer> getIdProduits(){
        try{
            ArrayList<Integer> produits = new ArrayList<>();
            ResultSet rs = cn.lire("Select idProduit from produit");
            while(rs.next()){
                produits.add(rs.getInt(1));
            }
            return produits ;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Integer> getIdServices(){
        try{
            ArrayList<Integer> services = new ArrayList<>();
            ResultSet rs = cn.lire("Select idService from service");
            while(rs.next()){
                services.add(rs.getInt(1));
            }
            return services ;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String[] getInfosProduit(int idProduit){
        try{
            String[] infosProduits = new String[3];
            if(getColonnesTable("Produit").contains("libelleProduit")){
                ResultSet rs = cn.lire("select libelleProduit, prixUnitaire, stock from produit where idProduit = " + idProduit);
                while(rs.next()){
                    infosProduits[0] = rs.getString(1);
                    infosProduits[1] = String.valueOf(rs.getDouble(2));
                    infosProduits[2] = String.valueOf(rs.getInt(3));
                }
                return infosProduits;
            }else{
                ResultSet rs = cn.lire("select prixUnitaire, stock from produit where idProduit = " + idProduit);
                while(rs.next()){
                    infosProduits[0] = " ";
                    infosProduits[1] = String.valueOf(rs.getDouble(1));
                    infosProduits[2] = String.valueOf(rs.getInt(2));
                }
                return infosProduits;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String[] getInfosService(int idService){
        try{
            String[] infosProduits = new String[2];
            if(getColonnesTable("Service").contains("LibelleService")){
                ResultSet rs = cn.lire("select libelleService, cout_heure from service where idService = " + idService);
                while(rs.next()){
                    infosProduits[0] = rs.getString(1);
                    infosProduits[1] = rs.getString(2);
                }
                return infosProduits;
            }else{
                ResultSet rs = cn.lire("select cout_heure from service where idService = " + idService);
                while(rs.next()){
                    infosProduits[0] = " ";
                    infosProduits[1] = rs.getString(1);
                }
                return infosProduits;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public boolean insererFactureProduit(int numFacture, String cinClient, int idProduit, int qte){
       try{
           ResultSet rs = cn.lire("select * from facture_produit where numeroFacture = "+numFacture+" and idProduit = "+idProduit);
           if(rs.next()){
               return false;
           }else{
               return cn.miseAjour("{CALL inserer_facture("+numFacture+",'"+cinClient+"',"+idProduit+","+qte+")}");
           }
       }catch(SQLException e){
           e.printStackTrace();
           return false ;
       }
    }
    public boolean insererFactureService(int numFacture, String cinClient, int idService, int nbrHeure){
        try{
            ResultSet rs = cn.lire("select * from facture_service where numerofacture = " +numFacture+" and idService = "+idService);
            if(rs.next()){
                return false;
            }else {
                return cn.miseAjour("{CALL inserer_facture_service("+numFacture+",'"+cinClient+"',"+idService+","+nbrHeure+")}");
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false ;
        }
    }
    public int typeInventaire(){
        if(cn.verificationTables() == 0) return 0 ; // Produit + Service
        else if(cn.verificationTables() == 1 ) return 1 ; // Produit
        else if(cn.verificationTables() == 2) return 2 ; // Service
        return -1 ;
    }

    public ArrayList<String> getColonnesTable(String table){
        try{
            ResultSet rs = cn.lire("SHOW columns from " +table);
            ArrayList<String> colonnes = new ArrayList<>();
            while(rs.next()){
                if(!rs.getString(1).equalsIgnoreCase("idProduit") &&
                        !rs.getString(1).equalsIgnoreCase("prixUnitaire")
                        && !rs.getString(1).equalsIgnoreCase("stock")
                        && !rs.getString(1).equalsIgnoreCase("idService")
                        && ! rs.getString(1).equalsIgnoreCase("Cout_heure")){
                    colonnes.add(rs.getString(1));
                }
            }
            return colonnes ;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean ajouterService(int idService, double cout_heure, String libelleService, String typeService, String description){
        // Create lists to store column names and values
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();

        // Add non-null columns and values to the lists
        if (!libelleService.equalsIgnoreCase("")) {
            columns.add("libelleService");
            values.add("'" + libelleService + "'");
        }
        if (!typeService.equalsIgnoreCase("")) {
            columns.add("typeService");
            values.add("'" + typeService + "'");
        }
        if (!description.equalsIgnoreCase("")) {
            columns.add("description");
            values.add("'" + description + "'");
        }
        // Check if any non-null columns were provided
        if (columns.isEmpty()) {
            // If no non-null columns were provided, return false without executing the query
            return false;
        }

        // Construct the SQL query
        StringBuilder req = new StringBuilder("INSERT INTO Service (idService, cout_heure");
        StringBuilder valuesStr = new StringBuilder(" VALUES (" + idService + "," + cout_heure );

        // Append non-null columns and values to the SQL query
        for (int i = 0; i < columns.size(); i++) {
            req.append(", ").append(columns.get(i));
            valuesStr.append(", ").append(values.get(i));
        }

        // Complete the SQL query
        req.append(")").append(valuesStr).append(")");

        // Execute the query
        return cn.miseAjour(req.toString());
    }
    public boolean modifierService(int idService, double cout_heure, String libelleService, String typeService, String description) {
        // Create lists to store column names and values
        List<String> updates = new ArrayList<>();

        // Add non-null columns and values to the lists
        if (!libelleService.equalsIgnoreCase("")) {
            updates.add("libelleService = '" + libelleService + "'");
        }
        if (!typeService.equalsIgnoreCase("")) {
            updates.add("typeService = '" + typeService + "'");
        }
        if (!description.equalsIgnoreCase("")) {
            updates.add("description = '" + description + "'");
        }

        // Check if any non-null columns were provided
        if (updates.isEmpty()) {
            // If no non-null columns were provided, return false without executing the query
            return false;
        }

        // Construct the SQL query
        StringBuilder req = new StringBuilder("UPDATE service SET cout_heure = " + cout_heure + ", ");
        for (int i = 0; i < updates.size(); i++) {
            req.append(updates.get(i));
            if (i < updates.size() - 1) {
                req.append(", ");
            }
        }
        req.append(" WHERE idService = ").append(idService);

        // Execute the query
        return cn.miseAjour(req.toString());
    }
    public boolean modifierProduit(int idProduit, double prixUnitaire, int stock, String libelleProduit, String dateEnregistrement, String description, String categorie) {
        // Create lists to store column names and values
        List<String> updates = new ArrayList<>();

        // Add non-null columns and values to the lists
        if (!libelleProduit.equalsIgnoreCase("")) {
            updates.add("libelleProduit = '" + libelleProduit + "'");
        }
        if (!dateEnregistrement.equalsIgnoreCase("")) {
            updates.add("dateEnregistrement = '" + dateEnregistrement + "'");
        }
        if (!description.equalsIgnoreCase("")) {
            updates.add("description = '" + description + "'");
        }
        if (!categorie.equalsIgnoreCase("")) {
            updates.add("categorie = '" + categorie + "'");
        }

        // Check if any non-null columns were provided
        if (updates.isEmpty()) {
            // If no non-null columns were provided, return false without executing the query
            return false;
        }

        // Construct the SQL query
        StringBuilder req = new StringBuilder("UPDATE produit SET prixUnitaire = " + prixUnitaire + ", stock = " +stock+", ");
        for (int i = 0; i < updates.size(); i++) {
            req.append(updates.get(i));
            if (i < updates.size() - 1) {
                req.append(", ");
            }
        }
        req.append(" WHERE idProduit = ").append(idProduit);

        // Execute the query
        return cn.miseAjour(req.toString());
    }

    public boolean ajouterProduit(int idProduit, double prixUnitaire, int stock, String libelleProduit, String dateEnregistrement, String description, String categorie) {
        // Create lists to store column names and values
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();

        // Add non-null columns and values to the lists
        if (!libelleProduit.equalsIgnoreCase("")) {
            columns.add("libelleProduit");
            values.add("'" + libelleProduit + "'");
        }
        if (!dateEnregistrement.equalsIgnoreCase("")) {
            columns.add("dateEnregistrement");
            values.add("'" + dateEnregistrement + "'");
        }
        if (!description.equalsIgnoreCase("")) {
            columns.add("description");
            values.add("'" + description + "'");
        }
        if (!categorie.equalsIgnoreCase("")) {
            columns.add("categorie");
            values.add("'" + categorie + "'");
        }

        // Check if any non-null columns were provided
        if (columns.isEmpty()) {
            // If no non-null columns were provided, return false without executing the query
            return false;
        }

        // Construct the SQL query
        StringBuilder req = new StringBuilder("INSERT INTO produit (idProduit, prixUnitaire, stock");
        StringBuilder valuesStr = new StringBuilder(" VALUES (" + idProduit + "," + prixUnitaire + "," + stock);

        // Append non-null columns and values to the SQL query
        for (int i = 0; i < columns.size(); i++) {
            req.append(", ").append(columns.get(i));
            valuesStr.append(", ").append(values.get(i));
        }

        // Complete the SQL query
        req.append(")").append(valuesStr).append(")");

        // Execute the query
        return cn.miseAjour(req.toString());
    }


    public boolean ajouterClient(String cinClient, String nomClient, String prenomClient, String telephoneClient, String adresseClient, String dateNaissanceClient, String emailClient) {
        // Create lists to store column names and values
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();

        // Add non-null columns and values to the lists
        if (nomClient != null && !nomClient.equalsIgnoreCase("")) {
            columns.add("nomClient");
            values.add("'" + nomClient + "'");
        }
        if (prenomClient != null && !prenomClient.equalsIgnoreCase("")) {
            columns.add("prenomClient");
            values.add("'" + prenomClient + "'");
        }
        if (telephoneClient != null && !telephoneClient.equalsIgnoreCase("")) {
            columns.add("telephoneClient");
            values.add("'" + telephoneClient + "'");
        }
        if (adresseClient != null && !adresseClient.equalsIgnoreCase("")) {
            columns.add("adresseClient");
            values.add("'" + adresseClient + "'");
        }
        if (dateNaissanceClient != null && !dateNaissanceClient.equalsIgnoreCase("")) {
            columns.add("dateNaissanceClient");
            values.add("'" + dateNaissanceClient + "'");
        }
        if (emailClient != null && !emailClient.equalsIgnoreCase("")) {
            columns.add("emailClient");
            values.add("'" + emailClient + "'");
        }

        // Check if any non-null columns were provided
        if (columns.isEmpty()) {
            // If no non-null columns were provided, return false without executing the query
            return false;
        }

        // Construct the SQL query
        StringBuilder req = new StringBuilder("INSERT INTO client (cinClient");
        StringBuilder valuesStr = new StringBuilder(" VALUES ('" + cinClient + "'");

        // Append non-null columns and values to the SQL query
        for (int i = 0; i < columns.size(); i++) {
            req.append(", ").append(columns.get(i));
            valuesStr.append(", ").append(values.get(i));
        }

        // Complete the SQL query
        req.append(")").append(valuesStr).append(")");

        // Execute the query
        return cn.miseAjour(req.toString());
    }


    public ResultSet afficherProduit(int idProduit){
        return cn.lire("Select * from produit where idProduit = " + idProduit);
    }
    public ResultSet afficherService(int idService){
        return cn.lire("select * from Service where idService = " + idService);
    }
    public boolean supprimerProduit(int idProduit) {
        return cn.miseAjour("delete from produit where idProduit = "+ idProduit);
    }
    public boolean supprimerService(int idService){
        return cn.miseAjour("delete from service where idService = " +idService);
    }
    public boolean supprimerClient(String cinClt){
        return cn.miseAjour("delete from client where cinClient = ?", cinClt);
    }


    public ResultSet getPersonnes(String typePerso){return cn.lire("select * from "+typePerso);}
    public ResultSet getInventaire(String typeInventaire) {return cn.lire("select * from "+typeInventaire);}
    public boolean insererPersonnel(String typePerso,String cin, String nom, String prenom, String mail, String pwd){
        String req = "insert into "+typePerso+" values(?,?,?,?,?)";
        return cn.miseAjour(req, cin, nom, prenom, mail, pwd);
    }
    public boolean supprimerPersonnel(String typePerso, String cin){
        String req = "delete from "+typePerso+" where cin = ?";
        return cn.miseAjour(req,cin);
    }
    public boolean modifierPersonnel(String typePerso, String nom, String prenom, String mail, String pwd,String cin){
        String req = "UPDATE `"+typePerso+"` SET `Nom`=?,`Prenom`=?," +
                "`AdresseMail`=?,`motdePasse`=? WHERE cin = ?";
        return cn.miseAjour(req, nom, prenom, mail, pwd, cin);
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

    public void populateTableView(ResultSet rs, TableView<Object[]> tableView, TextField id) {
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

            tableView.setOnMouseClicked(event -> {
                Object[] rowData = tableView.getSelectionModel().getSelectedItem();
                if (rowData != null) {
                    id.setText(rowData[0].toString()); // Assuming CIN is in the first column
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

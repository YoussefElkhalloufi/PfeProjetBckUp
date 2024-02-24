package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Connexion;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerDbCreation1 {


    String dbName = ControllerSignUp.getCmp();
    //String dbName = "testTables";
    mouseEvents me = new mouseEvents();

    @FXML
    private Button ExitButton;

    @FXML
    private Button NextButton;



    @FXML
    private CheckBox tableClient;
    @FXML
    private CheckBox cinClient;
    @FXML
    private CheckBox nomClient;
    @FXML
    private CheckBox prenomClient;
    @FXML
    private CheckBox telClient;
    @FXML
    private CheckBox adrClient;
    @FXML
    private CheckBox dateNaissClient;



    @FXML
    private CheckBox tableFacture;
    @FXML
    private CheckBox NumFacture;
    @FXML
    private CheckBox tva;
    @FXML
    private CheckBox remise;



    public void initialize() {

        //TODO : add a checkbox 'Check all'
        tableClient.setOnAction(event -> {
            if (tableClient.isSelected()) {
                cinClient.setOpacity(1.0);
                cinClient.setSelected(true);
                cinClient.setDisable(true);
                nomClient.setOpacity(1.0);
                nomClient.setDisable(false);
                prenomClient.setOpacity(1.0);
                prenomClient.setDisable(false);
                telClient.setOpacity(1.0);
                telClient.setDisable(false);
                adrClient.setOpacity(1.0);
                adrClient.setDisable(false);
                dateNaissClient.setOpacity(1.0);
                dateNaissClient.setDisable(false);
            } else {
                cinClient.setOpacity(0.5);
                cinClient.setSelected(false);
                cinClient.setDisable(true);
                nomClient.setOpacity(0.5);
                nomClient.setSelected(false);
                nomClient.setDisable(true);
                prenomClient.setOpacity(0.5);
                prenomClient.setSelected(false);
                prenomClient.setDisable(true);
                telClient.setOpacity(0.5);
                telClient.setSelected(false);
                telClient.setDisable(true);
                adrClient.setOpacity(0.5);
                adrClient.setSelected(false);
                adrClient.setDisable(true);
                dateNaissClient.setOpacity(0.5);
                dateNaissClient.setSelected(false);
                dateNaissClient.setDisable(true);
            }
        });

        tableFacture.setOnAction(event -> {
            if (tableFacture.isSelected()) {
                NumFacture.setOpacity(1.0);
                NumFacture.setSelected(true);
                NumFacture.setDisable(true);
                tva.setOpacity(1.0);
                tva.setDisable(false);
                remise.setOpacity(1.0);
                remise.setDisable(false);
            } else {
                NumFacture.setOpacity(0.5);
                NumFacture.setSelected(false);
                NumFacture.setDisable(true);
                tva.setOpacity(0.5);
                tva.setSelected(false);
                tva.setDisable(true);
                remise.setOpacity(0.5);
                remise.setSelected(false);
                remise.setDisable(true);
            }
        });
    }


    public void AnnulerBtn() {
        Alerts sa = new Alerts();

        boolean confirmed = sa.showConfirmationAlert("Confirmation", "Êtes-vous sûr de vouloir quitter l'application et supprimer votre base de données '"+dbName+"' ?");
        if (confirmed) {
            System.out.println("User clicked 'Yes'");
            Connexion c = new Connexion("jdbc:mysql://localhost:3306/" +dbName+ "?user=root");
            Connexion c1 = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");

            if(c.dropDatabase(dbName)&& c1.miseAjour("Delete from infosEntreprises where nomEntreprise = ?", dbName)){
                sa.showAlert("Suppression de la base de donnée","La suppression de votre base de données '"+dbName+"' a été effectuée avec succès.","/images/checked.png");
                Platform.exit();
            }else{
                sa.showAlert("Échec","Échec de la suppression de votre base de donnée '"+dbName+"'.","/images/checkFailed.png");
            }
        } else {
            // User clicked "Cancel", cancel the operation
            System.out.println("User clicked 'No'");
        }
    }

    private void createTable(String dbName, String tableName, ArrayList<String> columns) {
        // Create a Connexion object with the URL to the database
        Connexion cn = new Connexion("jdbc:mysql://localhost:3306/" +dbName+ "?user=root");

        // Call the createTable method with the selected columns
        boolean success = cn.createTable(dbName, tableName, columns);

        Alerts sa = new Alerts();
        // Check if the table creation was successful
        if (success) {
//            System.out.println("Table '" + tableName + "' created successfully with columns: " + String.join(", ", columns));
            sa.showAlert("Creation avec succes","la table '"+tableName+"' créée avec succès","/images/checked.png");
        } else {
//            System.out.println("Failed to create table '" + tableName + "'.");
            sa.showAlert("Échec", "Échec de la création de la table '" +tableName+"'.", "/images/checkFailed.png");
        }
    }

    public void tablesCreation(ActionEvent event) throws IOException {
        if (tableClient.isSelected() && tableFacture.isSelected()){
            createTable(dbName, "Client", getSelectedColumnsClient());
            createTable(dbName, "Facture", getSelectedColumnsFacture());
            ChangingWindows cw = new ChangingWindows();
            cw.switchWindow(event, "DbCreation2.fxml");

//            ChangingWindows cw = new ChangingWindows();
//            cw.switchWindow(event, "DbCreation1.fxml");
//        }else if (tableClient.isSelected()){
//            createTable(dbName, "Client", getSelectedColumnsClient());
//
//            ChangingWindows cw = new ChangingWindows();
//            cw.switchWindow(event, "DbCreation1.fxml");
//        }else if (tableFacture.isSelected()){
//            createTable(dbName, "Facture", getSelectedColumnsFacture());

//            ChangingWindows cw = new ChangingWindows();
//            cw.switchWindow(event, "DbCreation1.fxml");
        }else{
            Alerts sa = new Alerts();
            sa.showAlert2("ATTENTION","vous devez coché les deux tables !!!");
        }
    }

    private ArrayList<String> getSelectedColumnsClient() {
        // Check which checkboxes are selected and add their corresponding column names to the list
        ArrayList<String> selectedColumns = new ArrayList<>();

        if (cinClient.isSelected()) {
            selectedColumns.add("CinClient VARCHAR(20) PRIMARY KEY");
        }
        if (nomClient.isSelected()) {
            selectedColumns.add("NomClient VARCHAR(100)");
        }
        if (prenomClient.isSelected()) {
            selectedColumns.add("PrenomClient VARCHAR(100)");
        }
        if (telClient.isSelected()) {
            selectedColumns.add("TelephoneClient Varchar(12)");
        }
        if(adrClient.isSelected()){
            selectedColumns.add("AdresseClient VARCHAR(255)");
        }
        if(dateNaissClient.isSelected()){
            selectedColumns.add("DateNaissanceClient Date");
        }

        return selectedColumns;
    }

    private ArrayList<String> getSelectedColumnsFacture() {
        // Check which checkboxes are selected and add their corresponding column names to the list
        ArrayList<String> selectedColumns = new ArrayList<>();


        if (NumFacture.isSelected()) {
            selectedColumns.add("NumeroFacture INT PRIMARY KEY");
        }
        if (tva.isSelected()) {
            selectedColumns.add("TauxDeTva DECIMAL(10,2)");
        }
        if (remise.isSelected()) {
            selectedColumns.add("TauxDeRemise DECIMAL(10, 2)");
        }

        selectedColumns.add("CinClient VARCHAR(20)");
        selectedColumns.add("Constraint fk_Client_Facture foreign key (CinClient) references Client (CinClient)");

        Connexion cn = new Connexion("jdbc:mysql://localhost:3306/" +dbName+ "?user=root");

        ResultSet rs = cn.lire("SHOW TABLES");
        try {
            while (rs.next()){
                String tableName = rs.getString(1);
                if(tableName.equalsIgnoreCase("produit")){
                    selectedColumns.add("idProduit INT");
                    selectedColumns.add("Constraint fk_Produit_Facture foreign key (idProduit) references Produit (idProduit)");

                }if (tableName.equalsIgnoreCase("Service")){
                    selectedColumns.add("idService INT");
                    selectedColumns.add("Constraint fk_Service_Facture foreign key (idService) references Service (idService)");
                }
            }
        }catch(SQLException e ){
            e.printStackTrace();
            System.out.println("erreur dans la verification de lexistence des tables ");
        }finally{
            cn.closeResources();
        }
        // Add other columns as needed
        return selectedColumns;
    }



    public void onMouseEntered(javafx.scene.input.MouseEvent mouseEvent) {
        me.onMouseEntered(mouseEvent, ExitButton);
    }

    public void onMouseExited(javafx.scene.input.MouseEvent mouseEvent) {
        me.onMouseExited(mouseEvent, ExitButton);
    }


    public void onMouseEntered2(javafx.scene.input.MouseEvent mouseEvent) {
        me.onMouseEntered2(mouseEvent, NextButton);
    }

    public void onMouseExited2(javafx.scene.input.MouseEvent mouseEvent) {
        me.onMouseExited2(mouseEvent, NextButton);
    }
}

package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerDbCreation1 {


    ControllerDbCreation cdbc = new ControllerDbCreation();
    String dbName = ControllerSignUp.getCmp();
    //String dbName = "bbbbbbb";
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
    private CheckBox email;



    @FXML
    private CheckBox tableFacture;
    @FXML
    private CheckBox NumFacture;
    @FXML
    private CheckBox tva;
    @FXML
    private CheckBox remise;
    @FXML
    private CheckBox dateFacture;



    public void initialize() {

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
                email.setOpacity(1.0);
                email.setDisable(false);
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
                email.setOpacity(0.5);
                email.setSelected(false);
                email.setDisable(true);
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
                dateFacture.setOpacity(1.0);
                dateFacture.setDisable(true);
                dateFacture.setSelected(true);
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
                dateFacture.setOpacity(0.5);
                dateFacture.setSelected(false);
                dateFacture.setDisable(true);
            }
        });
    }


    public void AnnulerBtn() {
        ControllerDbCreation cdbc = new ControllerDbCreation();
        cdbc.AnnulerBtn();
    }

    private ArrayList<String> getColumnsFP(){

        ArrayList<String> fpList = new ArrayList<>();
        fpList.add("NumeroFacture INT");
        fpList.add("IDProduit INT ");
        fpList.add("Quantité INT");
        fpList.add("Total_HT DECIMAL(10,2)");
        fpList.add("CONSTRAINT facture_produit_PK PRIMARY KEY (NumeroFacture, IDProduit)");
        fpList.add("CONSTRAINT fk_numero_facture FOREIGN KEY (NumeroFacture) REFERENCES Facture(NumeroFacture)");
        fpList.add("CONSTRAINT fk_id_produit FOREIGN KEY (IDProduit) REFERENCES Produit(idProduit)");

        return fpList;

    }

    private ArrayList<String> getColumnsFS(){
        ArrayList<String> fsList = new ArrayList<>();
        fsList.add("NumeroFacture INT");
        fsList.add("idService INT");
        fsList.add("NombreHeure DECIMAL(10.2)");
        fsList.add("Total_Ht DECIMAL(10,2)");
        fsList.add(" FOREIGN KEY (numerofacture) REFERENCES facture(numeroFacture)");
        fsList.add("FOREIGN KEY (idService) REFERENCES service(idService)");
        fsList.add("PRIMARY KEY (numerofacture, idService)");

        return fsList ;
    }

    public void tablesCreation(ActionEvent event) throws IOException {
        if (tableClient.isSelected() && tableFacture.isSelected()){
            Connexion cn = new Connexion("jdbc:mysql://localhost:3306/" +dbName+ "?user=root");
            if(cn.verificationTables() != -1){
                cdbc.creationSuccessful("Client",cdbc.createTable(dbName, "Client", getSelectedColumnsClient()));
                cdbc.creationSuccessful("Facture", cdbc.createTable(dbName, "Facture", getSelectedColumnsFacture()));
                if(cn.verificationTables() == 1){
                    cdbc.createTable(dbName,"Facture_Produit", getColumnsFP());
                    ChangingWindows cw = new ChangingWindows();
                    cw.switchWindow(event, "DbCreation2.fxml");
                }else if(cn.verificationTables() == 2){
                    cdbc.createTable(dbName,"Facture_Service", getColumnsFS());
                    ChangingWindows cw = new ChangingWindows();
                    cw.switchWindow(event, "DbCreation2.fxml");
                }else if(cn.verificationTables() == 0){
                    cdbc.createTable(dbName,"Facture_Produit", getColumnsFP());
                    cdbc.createTable(dbName,"Facture_Service", getColumnsFS());
                    ChangingWindows cw = new ChangingWindows();
                    cw.switchWindow(event, "DbCreation2.fxml");
                }
            }else{
                Alerts sa = new Alerts();
                sa.showAlert2("Erreur","Une erreur s'est produite lors de la creation des tables, réessayer plus tard ");
            }

            cn.closeResources();
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
            selectedColumns.add("TelephoneClient VARCHAR(12)");
        }
        if(adrClient.isSelected()){
            selectedColumns.add("AdresseClient VARCHAR(255)");
        }
        if(dateNaissClient.isSelected()){
            selectedColumns.add("DateNaissanceClient Date");
        }
        if(email.isSelected()){
            selectedColumns.add("EmailClient VARCHAR(255)");
        }

        return selectedColumns;
    }

    private ArrayList<String> getSelectedColumnsFacture() {
        // Check which checkboxes are selected and add their corresponding column names to the list
        ArrayList<String> selectedColumns = new ArrayList<>();


        selectedColumns.add("NumeroFacture INT PRIMARY KEY");

        if (tva.isSelected()) {selectedColumns.add("TauxDeTva DECIMAL(10,2)");}

        if (remise.isSelected()) {selectedColumns.add("TauxDeRemise DECIMAL(10, 2)");}

        selectedColumns.add("DateFacture Date");
        selectedColumns.add("Total_TTC DECIMAL(10, 2)");
        selectedColumns.add("CinClient VARCHAR(20)");
        selectedColumns.add("Constraint fk_Client_Facture foreign key (CinClient) references Client (CinClient)");

        return selectedColumns;
    }


    public void onMouseEntered(javafx.scene.input.MouseEvent mouseEvent) {
        me.onMouseEntered(mouseEvent, ExitButton);
    }
    public void onMouseExited(javafx.scene.input.MouseEvent mouseEvent) {
        me.onMouseExited(mouseEvent, ExitButton);
    }
    public void onMouseEntered2(javafx.scene.input.MouseEvent mouseEvent) {me.onMouseEntered2(mouseEvent, NextButton);}
    public void onMouseExited2(javafx.scene.input.MouseEvent mouseEvent) {
        me.onMouseExited2(mouseEvent, NextButton);
    }
}

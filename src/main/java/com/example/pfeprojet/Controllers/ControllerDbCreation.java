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
import java.util.ArrayList;

public class ControllerDbCreation {

    //ControllerSignUp c = new ControllerSignUp();
    String dbName = ControllerSignUp.getCmp();
    //String dbName = "bbbbbbb";



    @FXML
    private Button ExitButton;
    @FXML
    private Button NextButton;


    @FXML
    private CheckBox tableProduit;
    @FXML
    private CheckBox idProduit;
    @FXML
    private CheckBox libelleProduit;
    @FXML
    private CheckBox prixUnitaire;
    @FXML
    private CheckBox categorie;
    @FXML
    private CheckBox dateEnregistrement;
    @FXML
    private CheckBox description;








    @FXML
    private CheckBox tableService;
    @FXML
    private CheckBox idService;
    @FXML
    private CheckBox typeService;
    @FXML
    private CheckBox cout_heure;
    @FXML
    private CheckBox personnel;
    @FXML
    private CheckBox libelleService;
    @FXML
    private CheckBox descriptionService;









    public void initialize() {


        tableProduit.setOnAction(event -> {
            if (tableProduit.isSelected()) {
                idProduit.setOpacity(1.0);
                idProduit.setDisable(true);
                idProduit.setSelected(true);
                libelleProduit.setOpacity(1.0);
                libelleProduit.setDisable(false);
                prixUnitaire.setOpacity(1.0);
                prixUnitaire.setDisable(true);
                prixUnitaire.setSelected(true);
                categorie.setOpacity(1.0);
                categorie.setDisable(false);
                dateEnregistrement.setOpacity(1.0);
                dateEnregistrement.setDisable(false);
                description.setOpacity(1.0);
                description.setDisable(false);
            } else {
                idProduit.setOpacity(0.5);
                idProduit.setSelected(false);
                idProduit.setDisable(true);
                libelleProduit.setOpacity(0.5);
                libelleProduit.setSelected(false);
                libelleProduit.setDisable(true);
                prixUnitaire.setOpacity(0.5);
                prixUnitaire.setSelected(false);
                prixUnitaire.setDisable(true);
                categorie.setOpacity(0.5);
                categorie.setSelected(false);
                categorie.setDisable(true);
                dateEnregistrement.setOpacity(0.5);
                dateEnregistrement.setSelected(false);
                dateEnregistrement.setDisable(true);
                description.setOpacity(0.5);
                description.setSelected(false);
                description.setDisable(true);
            }
        });

        tableService.setOnAction(event -> {
            if (tableService.isSelected()) {
                idService.setOpacity(1.0);
                idService.setDisable(true);
                idService.setSelected(true);
                typeService.setOpacity(1.0);
                typeService.setDisable(false);
                cout_heure.setOpacity(1.0);
                cout_heure.setDisable(true);
                cout_heure.setSelected(true);
                personnel.setOpacity(1.0);
                personnel.setDisable(false);
                libelleService.setOpacity(1.0);
                libelleService.setDisable(false);
                descriptionService.setOpacity(1.0);
                descriptionService.setDisable(false);
            } else {
                idService.setOpacity(0.5);
                idService.setSelected(false);
                idService.setDisable(true);
                typeService.setOpacity(0.5);
                typeService.setSelected(false);
                typeService.setDisable(true);
                cout_heure.setOpacity(0.5);
                cout_heure.setSelected(false);
                cout_heure.setDisable(true);
                personnel.setOpacity(0.5);
                personnel.setSelected(false);
                personnel.setDisable(true);
                libelleService.setOpacity(0.5);
                libelleService.setSelected(false);
                libelleService.setDisable(true);
                descriptionService.setOpacity(0.5);
                descriptionService.setSelected(false);
                descriptionService.setDisable(true);
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
            if(c.dropDatabase(dbName) && c1.miseAjour("Delete from infosEntreprises where nomEntreprise = ?", dbName)){
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
    public boolean createTable(String dbName, String tableName, ArrayList<String> columns) {
        // Create a Connexion object with the URL to the database
        Connexion cn = new Connexion("jdbc:mysql://localhost:3306/" +dbName+ "?user=root");

        // Call the createTable method with the selected columns
        boolean result = cn.createTable(dbName, tableName, columns);
        cn.closeResources();
        return result;
    }

    public void createTablePersonnelService(){
            ArrayList<String> personnelColumns = new ArrayList<>();
            personnelColumns.add("idPersonnel INT PRIMARY KEY");
            personnelColumns.add("nomPersonnel VARCHAR(50)");
            personnelColumns.add("prenomPersonnel VARCHAR(50)");
            createTable(dbName,"Personnel",personnelColumns);
            ArrayList<String> personnelServiceColumns = new ArrayList<>();
            personnelServiceColumns.add("idPersonnel INT");
            personnelServiceColumns.add("idService INT");
            personnelServiceColumns.add("Constraint fk_personnelService_personnel foreign key (idPersonnel) references Personnel (idPersonnel)");
            personnelServiceColumns.add("Constraint fk_personnelService_Service foreign key (idService) references Service (idService)");
            personnelServiceColumns.add("Constraint Pk_Personnel_Service primary key (idPersonnel, idService)");
            createTable(dbName,"Personnel_Service",personnelServiceColumns);

    }

    public void creationSuccessful(String tableName, boolean creation) {
        Alerts sa = new Alerts();
        if (creation) {
            sa.showAlert("Creation avec succes","la table '"+tableName+"' crée avec succès","/images/checked.png");
        } else {
            sa.showAlert("Échec", "Échec de la création de la table '" +tableName+"'.", "/images/checkFailed.png");
        }
    }

    public void tablesCreation(ActionEvent event) throws IOException {
        if (tableProduit.isSelected() && tableService.isSelected()){
            creationSuccessful("Produit", createTable(dbName, "produit", getSelectedColumnsProduit()));
            creationSuccessful("Service", createTable(dbName, "service", getSelectedColumnsService()));
            if (personnel.isSelected()) createTablePersonnelService();
            ChangingWindows cw = new ChangingWindows();
            cw.switchWindow(event, "DbCreation1.fxml");

        }else if (tableProduit.isSelected()){
            creationSuccessful("Produit", createTable(dbName, "produit", getSelectedColumnsProduit()));

            ChangingWindows cw = new ChangingWindows();
            cw.switchWindow(event, "DbCreation1.fxml");
        }else if (tableService.isSelected()){
            creationSuccessful("Service", createTable(dbName, "service", getSelectedColumnsService()));
            if (personnel.isSelected()) createTablePersonnelService();

            ChangingWindows cw = new ChangingWindows();
            cw.switchWindow(event, "DbCreation1.fxml");
        }else{
            Alerts sa = new Alerts();
            sa.showAlert2("ATTENTION","vous devez coché au minimum une table");
        }



    }


    private ArrayList<String> getSelectedColumnsProduit() {
        // Check which checkboxes are selected and add their corresponding column names to the list
        ArrayList<String> selectedColumns = new ArrayList<>();

        if (idProduit.isSelected()) {
            selectedColumns.add("idProduit INT AUTO_INCREMENT PRIMARY KEY");
        }
        if (libelleProduit.isSelected()) {
            selectedColumns.add("libelleProduit VARCHAR(80)");
        }
        if (prixUnitaire.isSelected()) {
            selectedColumns.add("prixUnitaire DECIMAL(10, 2)");
        }
        if (categorie.isSelected()) {
            ArrayList<String> categorieColumns = new ArrayList<>();
            categorieColumns.add("idCategorie INT AUTO_INCREMENT PRIMARY KEY");
            categorieColumns.add("libelleCategorie VARCHAR(255)");
            categorieColumns.add("Description VARCHAR(255)");
            createTable(dbName,"categorie",categorieColumns);
            selectedColumns.add("idCategorie INT");
            selectedColumns.add("Constraint fk_categorie_produit foreign key (idCategorie) references categorie (idCategorie) ");
        }
        if(dateEnregistrement.isSelected()){
            selectedColumns.add("date_enregistrement DATE");
        }
        if(description.isSelected()){
            selectedColumns.add("description VARCHAR(255)");
        }

        selectedColumns.add("stock INT");
        return selectedColumns;
    }


    private ArrayList<String> getSelectedColumnsService() {
        // Check which checkboxes are selected and add their corresponding column names to the list
        ArrayList<String> selectedColumns = new ArrayList<>();

        if (idService.isSelected()) {
            selectedColumns.add("idService INT AUTO_INCREMENT PRIMARY KEY");
        }
        if(libelleService.isSelected()){
            selectedColumns.add("LibelleService VARCHAR(100)");
        }
        if (typeService.isSelected()) {
//            ArrayList<String> serviceColumns = new ArrayList<>();
//            serviceColumns.add("idTypeService INT AUTO_INCREMENT PRIMARY KEY");
//            serviceColumns.add("libelleTypeService VARCHAR(255)");
//            serviceColumns.add("Description VARCHAR(255)");
//            createTable(dbName,"TypeService",serviceColumns);
//            selectedColumns.add("idTypeService INT");
//            selectedColumns.add("Constraint fk_TypeService_Service foreign key (idTypeService) references TypeService (idTypeService)");
            selectedColumns.add("TypeService VARCHAR(255)");
        }
        if (cout_heure.isSelected()) {
            selectedColumns.add("Cout_heure DECIMAL(10, 2)");
        }
        if(descriptionService.isSelected()){
            selectedColumns.add("Description VARCHAR(255)");
        }
        // Add other columns as needed
        return selectedColumns;
    }



    mouseEvents ms = new mouseEvents();

    public void onMouseEntered(javafx.scene.input.MouseEvent event) { ms.onMouseEntered(event, ExitButton);}

    public void onMouseExited(javafx.scene.input.MouseEvent event) {
        ms.onMouseExited(event, ExitButton);
    }


    public void onMouseEntered2(javafx.scene.input.MouseEvent event) {
        ms.onMouseEntered2(event, NextButton);
    }

    public void onMouseExited2(javafx.scene.input.MouseEvent event) {
        ms.onMouseExited2(event, NextButton);
    }

    /*public void switchToLogin(ActionEvent event) throws IOException {
        ChangingWindows ch = new ChangingWindows();
        ch.switchWindow(event, "FstWindow.fxml");
    }*/
}

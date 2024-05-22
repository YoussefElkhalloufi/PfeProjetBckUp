package com.example.pfeprojet.Controllers;
import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControllerClientsEntreprise {
    @FXML
    private TableView<Object[]> clientTableview;


    @FXML
    private TextField cinTextbox;
    @FXML
    private TextField cinTextboxRech;
    @FXML
    private Label nomLabel;
    @FXML
    private TextField nomTextbox;
    @FXML
    private Label prenomLabel;
    @FXML
    private TextField prenomTextbox;
    @FXML
    private Label telLabel;
    @FXML
    private TextField telTextbox;
    @FXML
    private Label adresseLabel;
    @FXML
    private TextField adresseTextbox;
    @FXML
    private Label dateNaissLabel;
    @FXML
    private DatePicker dateNaissDatePicker;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField emailTextbox;


    @FXML
    private Button afficherClient;
    @FXML
    private Button modifierClient;
    @FXML
    private Button supprimerClient;
    @FXML
    private Button ajouterClient;

    private Entreprise e = ControllerDashboardDirecteur.getEntreprise();
    Alerts sa = new Alerts();

    //TODO : finish MODIFIER
    public void initialize(){
        e.populateTableView(e.getPersonnes("client"),clientTableview,cinTextbox);
        checkColonnesClient();
    }
    @FXML
    void actualiser(ActionEvent event) {
        initialize();
    }

    private void checkColonnesClient(){
        ArrayList<String> colonnesClient = e.getColonnesTable("client");
        if(!ControllerInventaire.containsColonne(colonnesClient, "nomclient")){
            nomTextbox.setDisable(true);
            nomLabel.setDisable(true);
        }
        if(!ControllerInventaire.containsColonne(colonnesClient, "prenomclient")){
            prenomTextbox.setDisable(true);
            prenomLabel.setDisable(true);
        }
        if(!ControllerInventaire.containsColonne(colonnesClient, "TelephoneClient")){
            telTextbox.setDisable(true);
            telLabel.setDisable(true);
        }
        if(!ControllerInventaire.containsColonne(colonnesClient, "AdresseClient")){
            adresseTextbox.setDisable(true);
            adresseLabel.setDisable(true);
        }
        if(!ControllerInventaire.containsColonne(colonnesClient, "DateNaissanceClient")){
            dateNaissDatePicker.setDisable(true);
            dateNaissLabel.setDisable(true);
        }
        if(!ControllerInventaire.containsColonne(colonnesClient, "EmailClient")){
            emailTextbox.setDisable(true);
            emailLabel.setDisable(true);
        }
    }
    @FXML
    void afficherClient(ActionEvent event) {

    }

    @FXML
    void ajouterClient(ActionEvent event) {
        LocalDate selectedDate = dateNaissDatePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(cinTextbox.getText().trim().isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            String cin = cinTextbox.getText().trim();
            String nom = nomTextbox.getText().trim();
            String prenom = prenomTextbox.getText().trim();
            String tel = telTextbox.getText().trim();
            String adresse = adresseTextbox.getText().trim();
            String dateNaiss = "";
            if(selectedDate != null){
                dateNaiss = selectedDate.format(formatter);
            }
            String email = emailTextbox.getText().trim();

            if(e.ajouterClient(cin, nom, prenom,tel, adresse, dateNaiss, email)){
                e.populateTableView(e.getPersonnes("client"),clientTableview, cinTextbox);
                sa.showAlert("Ajout avec succes","Le Client est bien ajouté !","/images/checked.png");
            }else{
                sa.showAlert("Ajout Erroné", "Le Client existe deja !", "/images/annuler.png");
            }
        }
    }

    @FXML
    void modifierClient(ActionEvent event) {

    }
    @FXML
    void supprimerClient(ActionEvent event) {
        if(!cinTextbox.getText().trim().isEmpty()){
            if(sa.showConfirmationAlert("Confirmation de la suppression","Êtes-vous sûr de vouloir procéder à la suppression du client ?")) {
                if(e.supprimerClient(cinTextbox.getText())){
                    e.populateTableView(e.getPersonnes("client"),clientTableview, cinTextbox);
                    sa.showAlert("Suppression réussie","Le client a été supprimé avec succès.","/images/checked.png");
                }else{
                    sa.showAlert("Suppression échouée","Le cin du client saisi n'existe pas. Veuillez sélectionner un client valide dans le tableau avant de procéder à la suppression.", "/images/annuler.png");
                }
            }
        }else{
            sa.showWarning("Suppresion Erroné", "Veuillez sélectionner un client avant de procéder");
        }
    }
    @FXML
    void dashboardDirecteur(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        cw.switchWindow(event,"DirecteurDashboard.fxml");
    }


}

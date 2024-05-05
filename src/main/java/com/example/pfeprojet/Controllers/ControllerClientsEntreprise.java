package com.example.pfeprojet.Controllers;
import com.example.pfeprojet.ChangingWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ControllerClientsEntreprise {
    @FXML
    private TableView<Object[]> clientTableview;


    @FXML
    private Label cinLabel;
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
    private DatePicker dateNaissTextbox;
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


    @FXML
    void actualiser(ActionEvent event) {

    }

    @FXML
    void afficherClient(ActionEvent event) {

    }

    @FXML
    void ajouterClient(ActionEvent event) {

    }

    @FXML
    void dashboardDirecteur(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        cw.switchWindow(event,"DirecteurDashboard.fxml");
    }

    @FXML
    void modifierClient(ActionEvent event) {

    }

    @FXML
    void supprimerClient(ActionEvent event) {

    }
}

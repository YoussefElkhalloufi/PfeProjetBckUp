package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ControllerProfilEntreprise {



    @FXML
    private TextField emailTextField;

    @FXML
    private TextField idFiscaleTextField;

    @FXML
    private TextField localisationTextField;

    @FXML
    private TextField numeroFaxTextField;

    @FXML
    private TextField pwdTextField;

    @FXML
    private TextField secteurDacTextField;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField villeTextField;

    mouseEvents me = new mouseEvents();
    Entreprise e = ControllerDashboardDirecteur.getEntreprise();

    @FXML
    void dashboardDirecteur(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        cw.switchWindow(event,"DashboardDirecteur.fxml");
    }

    @FXML
    void onMouseEntered2(MouseEvent event) {
        me.onMouseEntered2(event, updateBtn);
    }

    @FXML
    void onMouseExited2(MouseEvent event) {
        me.onMouseExited2(event, updateBtn);
    }
    public void initialize(){
        e.setInfosEntreprise();
        emailTextField.setText(e.getAdresseMail());
        localisationTextField.setText(e.getLocalisation());
        villeTextField.setText(e.getVille());
        numeroFaxTextField.setText(e.getNumeroDeFax());
        secteurDacTextField.setText(e.getSecteurDactivite());
        idFiscaleTextField.setText(e.getIdentificationFiscale());
        pwdTextField.setText(e.getMotdepasse());
    }

    @FXML
    void update(ActionEvent event) {
        //TODO : code the button !!!
    }

}

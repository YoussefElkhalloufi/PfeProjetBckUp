package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import com.itextpdf.layout.element.Paragraph;
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
    private TextField confiPwdTextField;
    @FXML
    private TextField secteurDacTextField;
    @FXML
    private TextField villeTextField;
    @FXML
    private Button updateBtn;


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
    }

    @FXML
    void update(ActionEvent event) {
        Alerts alert = new Alerts();

        String mail = emailTextField.getText().trim();
        String localisation = localisationTextField.getText().trim();
        String ville = villeTextField.getText().trim();
        String numeroFax = numeroFaxTextField.getText().trim();
        String secteurDac = secteurDacTextField.getText().trim();
        String idFiscale = idFiscaleTextField.getText().trim();
        String pwd = pwdTextField.getText().trim();
        String pwdConfi = confiPwdTextField.getText().trim();

        if(mail.isEmpty() || localisation.isEmpty() || ville.isEmpty() || pwd.isEmpty() || pwdConfi.isEmpty()){
            alert.showWarning("Attention","Certains champs obligatoires sont vides.");
            return ;
        }
        if(!pwd.equalsIgnoreCase(pwdConfi)){
            alert.showWarning("Attention","Veuillez verifier le mot de passe et sa confirmation !!!");
            return ;
        }


        String pwdHashed = PassworManager.hashPassword(pwdTextField.getText().trim());
         if(e.updateEntreprise(e.getNomEntreprise(), mail,localisation,ville,numeroFax,secteurDac,idFiscale,pwdHashed)){
             alert.showAlert("Succès","Modification avec succès","/images/checked.png");
         }

    }

}

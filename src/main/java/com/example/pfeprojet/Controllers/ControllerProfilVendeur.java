package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import com.example.pfeprojet.Entreprise.Responsable;
import com.example.pfeprojet.Entreprise.Vendeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ControllerProfilVendeur {

    @FXML
    private TextField confiPwdTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField pwdTextField;

    @FXML
    private Button updateBtn;

    ChangingWindows cw = new ChangingWindows();
    mouseEvents me = new mouseEvents();
    Entreprise e = ControllerDashboardVendeur.getEntreprise();


    @FXML
    void dashboardVendeur(ActionEvent event) throws IOException {
        cw.switchWindow(event, "DashboardVendeur.fxml");
    }

    @FXML
    void onMouseEnteredModifier(MouseEvent event) {
        me.onMouseEntered2(event, updateBtn);
    }

    @FXML
    void onMouseExitedModifier(MouseEvent event) {
        me.onMouseExited2(event, updateBtn);
    }

    @FXML
    void update(ActionEvent event) {

        Alerts alert = new Alerts();
        String mail = emailTextField.getText().trim();
        String pwd = pwdTextField.getText().trim();
        String confiPwd = confiPwdTextField.getText().trim();


        if(mail.isEmpty() || pwd.isEmpty() || confiPwd.isEmpty()) {
            alert.showWarning("Attention","Certains champs obligatoires sont vides.");
            return ;
        }

        if(!pwd.equalsIgnoreCase(confiPwd)) {
            alert.showWarning("Attention","Veuillez verifier le mot de passe et sa confirmation.");
            return ;
        }

        Vendeur vendeur = ControllerDashboardVendeur.vendeur ;
        if(e.updatePersonnel("Vendeurs", vendeur.getNom(), vendeur.getPrenom(), mail, pwd)){
            alert.showAlert("Succès","Modification avec succès","/images/checked.png");
        }else{
            alert.showAlert("","","/images/annuler.png");
        }


    }

}

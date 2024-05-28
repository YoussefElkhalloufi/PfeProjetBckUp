package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerLoginVendeur extends ControllerLoginResponsable{
    //Entreprise e = new Entreprise(cmp);


    @FXML
    private AnchorPane anchorPaneVendeur;
    @FXML
    private TextField mailVendeur;
    @FXML
    private PasswordField pwdVendeur;

        public void initialize(){
            setLabel(anchorPaneVendeur);
        }
    @FXML
    void loginVendeur(ActionEvent event) throws SQLException {
        Alerts a = new Alerts();
        String mail = mailVendeur.getText().trim();
        String pwd = pwdVendeur.getText().trim();
        ResultSet rs =e.getPersonnes("Vendeurs");
        while(rs.next()){
            if(rs.getString(4).equalsIgnoreCase(mail)){
                if(rs.getString(5).equalsIgnoreCase(pwd)){
                    //TODO : vendeur dashboard
                    a.showAlert("","good","/images/checked.png");
                }else{
                    a.showAlert("Mot de passe erroné","Le mot de passe que vous avez saisi est incorrect. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
                }
            }else {
                a.showAlert("Email erroné","L'e-mail que vous avez saisi n'existe pas. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
            }
        }
    }

}

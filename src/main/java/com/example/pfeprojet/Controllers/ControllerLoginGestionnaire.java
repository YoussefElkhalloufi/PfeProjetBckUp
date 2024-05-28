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

public class ControllerLoginGestionnaire extends ControllerLoginResponsable{
    Entreprise e = new Entreprise(cmp);

    @FXML
    private AnchorPane anchorPaneGestio;
    @FXML
    private TextField mailGestio;
    @FXML
    private PasswordField pwdGestio;

    public void initialize(){
        setLabel(anchorPaneGestio);
    }

    @FXML
    void loginGestio(ActionEvent event) throws SQLException {
        Alerts a = new Alerts();
        String mail = mailGestio.getText().trim();
        String pwd = pwdGestio.getText().trim();
        ResultSet rs =e.getPersonnes("gestionnaires");
        while(rs.next()){
            if(rs.getString(4).equalsIgnoreCase(mail)){
                if(rs.getString(5).equalsIgnoreCase(pwd)){
                    //TODO : gestionnaire dashboard
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

package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerLoginResponsable {
    //TODO new project, test in it, la facturation

    @FXML
    private Text label;

    @FXML
    private TextField mailRespo;
    @FXML
    private PasswordField pwdRespo;

    Entreprise e = new Entreprise(cmp);


    @FXML
    private AnchorPane anchorPaneRespo;
    public static String cmp = ControllerFstWindow.getEntreprise().getNomEntreprise();

    public void initialize() throws SQLException {
        setLabel(anchorPaneRespo);
    }

    public void setLabel(AnchorPane an){
        label.setText(cmp);


        Platform.runLater(() -> {
            // Set initial X-coordinate to center the label
            double centerX = (an.getWidth() - label.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(label, centerX);
        });
    }

    @FXML
    void loginRespo(ActionEvent event) throws SQLException {
        Alerts a = new Alerts();
        String mail = mailRespo.getText().trim();
        String pwd = pwdRespo.getText().trim();
        ResultSet rs =e.getPersonnes("Responsables");
        while(rs.next()){
            if(rs.getString(4).equalsIgnoreCase(mail)){
                if(rs.getString(5).equalsIgnoreCase(pwd)){
                    //TODO : responsable dashboard
                    a.showAlert("","good","/images/checked.png");
                }else{
                    a.showAlert("Mot de passe erroné","Le mot de passe que vous avez saisi est incorrect. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
                }
            }else{
                a.showAlert("Email erroné","L'e-mail que vous avez saisi n'existe pas. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
            }
        }
    }
}

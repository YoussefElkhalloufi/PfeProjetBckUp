package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Connexion;
import com.example.pfeprojet.Entreprise.Entreprise;
import com.example.pfeprojet.Entreprise.Gestionnaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
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

    private static Gestionnaire gestio;
    public static Gestionnaire getGestionnaire (){return gestio;}

    public void initialize(){
        setLabel(anchorPaneGestio);
    }

    @FXML
    void loginGestio(ActionEvent event) throws SQLException, IOException {
        Alerts a = new Alerts();

        if(mailGestio.getText().isEmpty() || pwdGestio.getText().isEmpty()){
            System.out.println("certains champs sont null");
        }else{
            getGestio();
            if(gestio == null) {
                a.showAlert("Email erroné","L'e-mail que vous avez saisi n'existe pas. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
            }else {
                String inputMail = mailGestio.getText().trim();
                String inputPwd = pwdGestio.getText().trim();

                String mail = gestio.getMail();
                String pwd = gestio.getMotDePasse();

                if(inputMail.equalsIgnoreCase(mail)){
                    if(inputPwd.equals(pwd)){
                        ChangingWindows cw = new ChangingWindows();
                        System.out.println("Login success, Dashboard Gestionnaire !!!");
                        cw.switchWindow(event,"DashboardGestionnaire.fxml");
                    }else{
                        a.showAlert("Mot de passe erroné","Le mot de passe que vous avez saisi est incorrect. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
                    }
                }else{
                    a.showAlert("Email erroné","L'e-mail que vous avez saisi n'existe pas. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
                }
            }
        }
    }


    public void getGestio() {
        try{
            Connexion c = new Connexion("jdbc:mysql://localhost:3306/"+cmp+"?user=root");
            System.out.println("le nom de l'entreprise : " +cmp);
            ResultSet rs = c.lire("select nom, prenom, adresseMail, motdePasse from gestionnaires where adresseMail = ?", mailGestio.getText());
            if(rs.next()){
                gestio = new Gestionnaire(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                c.closeResources();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

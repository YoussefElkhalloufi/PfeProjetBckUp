package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Connexion;
import com.example.pfeprojet.Entreprise.Vendeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerLoginVendeur extends ControllerLoginResponsable{


    @FXML
    private AnchorPane anchorPaneVendeur;
    @FXML
    private TextField mailVendeur;
    @FXML
    private PasswordField pwdVendeur;

    private static Vendeur vendeur;
    public static Vendeur getVendeur(){return vendeur;}

    public void initialize(){
            setLabel(anchorPaneVendeur);
    }

    @FXML
    void loginVendeur(ActionEvent event) throws SQLException, IOException {
        Alerts a = new Alerts();

        if(mailVendeur.getText().isEmpty() || mailVendeur.getText().isEmpty()){
            System.out.println("Certains champs sont null");
        }else{
            getVend();
            if(vendeur == null){
                System.out.println("Aucun vendeur avec ce mail");
            }else{
                String inputMail = mailVendeur.getText();
                String inputPwd = pwdVendeur.getText();

                String mail = vendeur.getMail();
                String pwd = vendeur.getMotDePasse();

                if(inputMail.equalsIgnoreCase(mail)){
                    if(inputPwd.equals(pwd)){
                        ChangingWindows cw = new ChangingWindows();
                        cw.switchWindow(event, "DashboardVendeur.fxml");
                    }else{
                        a.showAlert("Mot de passe erroné","Le mot de passe que vous avez saisi est incorrect. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
                    }
                }else{
                    a.showAlert("Email erroné","L'e-mail que vous avez saisi n'existe pas. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
                }
            }
        }
    }





    public void getVend() {
        try{
            Connexion c = new Connexion("jdbc:mysql://localhost:3306/"+cmp+"?user=root");
            System.out.println("le nom de l'entreprise : " +cmp);
            ResultSet rs = c.lire("select nom, prenom, adresseMail, motdePasse from Vendeurs where adresseMail = ?", mailVendeur.getText());
            if(rs.next()){
                vendeur = new Vendeur(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                c.closeResources();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}

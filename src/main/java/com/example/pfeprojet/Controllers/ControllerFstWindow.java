package com.example.pfeprojet.Controllers;


import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Connexion;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ControllerFstWindow extends mouseEvents{


   //TODO : communication en adresse mail

   private static final double ENLARGE_FACTOR = 1.1;

   private int i = 3;

   @FXML
   private PasswordField pwd;
   @FXML
   private TextField userName;

   @FXML
   private Button LoginButton;

   @FXML
   private Button SignUpButton;
   @FXML
   private Button aide;

   private static Entreprise e ;
   public static Entreprise getEntreprise(){return e;}

   public void initialize(){
      Connexion c = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");

      c.miseAjour("update infosentreprises \n" +
              "set STATUS = 'Activé', dateHeureStatus = null\n" +
              "where STATUS = 'désactivé' \n" +
              "and dateHeureStatus <= DATE_SUB(NOW(), INTERVAL 24 HOUR);");
      c.closeResources();
   }

   private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
   private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

   public static boolean isValidEmail(String email) {
      Matcher matcher = pattern.matcher(email);
      return matcher.matches();
   }
   public void signUp(ActionEvent event){
      String mail = userName.getText();
      String pass = pwd.getText();
      Alerts a = new Alerts();

      if(mail.isEmpty() || pass.isEmpty()){
         a.showWarning("Attention", "Assurez-vous de remplir l'Identifiant ET le mot de pass.");
      }else{
         if(isValidEmail(mail)){
            Connexion c = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");

            ResultSet rs = c.lire("Select MotdePasse, nomEntreprise  from infosEntreprises where adresseMail = ?", mail);
            ResultSet rs1 = c.lire("Select status, dateHeureStatus from infosEntreprises where adresseMail = ?", mail);


             try {
                if(rs1.next()){
                   String status = rs1.getString(1);
                   String dateHeureStatus = rs1.getString(2);

                   if(status.equalsIgnoreCase("activé")){
                      if(rs.next()){
                         String DbPass = rs.getString(1);
                         String cmp = rs.getString(2);
                         if(PassworManager.verifyPassword(pass, DbPass)){
                            e = new Entreprise(cmp);
                            e.setInfosEntreprise();
                            e.setMotdepasse(pass);
                            ChangingWindows cw = new ChangingWindows();
                            cw.switchWindow(event,"roleSpecification.fxml");
                         }else{
                            i--;
                            if(i==1 || i==0){
                               a.showWarning("Mot de passe erroné","Mot de passe erroné, il vous reste "+i+" Essai");
                            }else{
                               a.showWarning("Mot de passe erroné","Mot de passe erroné, il vous reste "+i+" Essais");
                            }
                         }
                         if(i==0){
                            a.showWarning("ATTENTION","Vous avez depassé 3 essais");
                            c.miseAjour("update infosentreprises set status = 'désactivé', dateHeureStatus = NOW()  where nomEntreprise = ?", cmp );
                            a.showWarning("Compte désactivé","Votre compte a été désactivé pendant 24 heures.\nSi vous estimez que cette désactivation est injustifiée, \nVeuillez nous contacter à l'adresse e-mail suivante : factureasePFE@gmail.com ");
                         }
                      }
                   }else {
                      a.showWarning("Acces refusé","Votre compte a été desactivé pendant 24 Heures, à " +dateHeureStatus);
                   }
                }else{
                   a.showWarning("Compte introuvable","Votre compte n'existe pas dans l'application");
                }

               c.closeResources();
            } catch (SQLException |IOException e) {
               e.printStackTrace();
            }

         }else {
            a.showAlert("Format erroné","La forme d'identifiant entrer est erroné","/images/annuler.png");
         }


      }

   }

   ChangingWindows ch = new ChangingWindows();
   public void switchToSignUp(ActionEvent event) throws IOException {
      ch.switchWindow(event,"SignUp.fxml");
   }

   @FXML
   public void onMouseEntered(MouseEvent event) {
      LoginButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5em;");
      enlargeButtonMS(LoginButton);
   }

   @FXML
   public void onMouseExited(MouseEvent event) {
      LoginButton.setStyle("-fx-background-color: #59A8A4; -fx-background-radius: 5em;");
      restoreButtonSizeMS(LoginButton);
   }

   @FXML
   public void onMouseEntered2(MouseEvent event) {
       onMouseEntered2(event, SignUpButton);
   }
   @FXML
   public void onMouseExited2(MouseEvent event) { onMouseExited2(event, SignUpButton);}

   @FXML
   public void onMouseEntered3(MouseEvent event) {
       onMouseEntered2(event, aide);
   }
   @FXML
   public void onMouseExited3(MouseEvent event) { onMouseExited2(event, aide);}


   private void enlargeButton(Button button) {
      ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
      scaleTransition.setToX(ENLARGE_FACTOR);
      scaleTransition.setToY(ENLARGE_FACTOR);
      scaleTransition.play();
   }

   private void restoreButtonSize(Button button) {
      ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
      scaleTransition.setToX(1.0);
      scaleTransition.setToY(1.0);
      scaleTransition.play();
   }


   public void helpSection(ActionEvent event) throws IOException {
      ch.switchWindow(event, "help.fxml");
   }
}
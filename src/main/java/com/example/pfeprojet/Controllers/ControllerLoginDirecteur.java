package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Connexion;
import com.example.pfeprojet.Entreprise.Directeur;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerLoginDirecteur {
    @FXML
    private Button LoginButton;

    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField pwdtxt;

    @FXML
    private Text label;
    @FXML
    private Text directeur;
    @FXML
    private AnchorPane anchorPane;
    private static String cmp = ControllerFstWindow.getCmp();

    public ControllerLoginDirecteur() throws SQLException {
    }

    public static String getCmp(){
        return cmp;
    }


    public void initialize() throws SQLException {
        label.setText(cmp);
        directeur.setText(dr.getNom());


        Platform.runLater(() -> {
            // Set initial X-coordinate to center the label
            double centerX = (anchorPane.getWidth() - label.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(label, centerX);
        });
    }

    private static final Directeur dr = getDirecteur();

    public static Directeur getDir(){
        return dr ;
    }

    public void login(ActionEvent event) throws IOException {
        Alerts a = new Alerts();
        String inputEmail = emailTxt.getText();
        String inputPwd = pwdtxt.getText();

        String directeurEmail = dr.getMail();
        String directeurPwd = dr.getMotDePasse();

        if(ControllerFstWindow.isValidEmail(inputEmail)){
            if(inputEmail.equalsIgnoreCase(directeurEmail)){
                if(PassworManager.verifyPassword(inputPwd, directeurPwd)){
                    ChangingWindows cw = new ChangingWindows();
                    cw.switchWindow(event,"DirecteurDashboard.fxml");
                }else{
                    a.showAlert("Mot de passe erroné","Le mot de passe que vous avez saisi est incorrect. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
                }
            }else{
                a.showAlert("Email erroné","L'e-mail que vous avez saisi est incorrect. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
            }
        }else{
            a.showAlert("Format erroné","La forme d'Email entrer est erroné","/images/annuler.png");
        }
    }

    public static Directeur getDirecteur() {
        try{
            Connexion c = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");

            ResultSet rs = c.lire("select nomDirecteur, prenomDirecteur, adresseMailDirecteur, motdePasseDirecteur from infosentreprises where nomEntreprise = ?", cmp);

            if(rs.next()){
                Directeur dr1 = new Directeur(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                c.closeResources();
                return dr1;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null ;
    }


    @FXML
    public void onMouseEntered(MouseEvent event) {
        LoginButton.setStyle("-fx-background-color: #DFDFDF; -fx-text-fill: white; -fx-background-radius: 5em;");
        enlargeButton(LoginButton);
    }

    @FXML
    public void onMouseExited(MouseEvent event) {
        LoginButton.setStyle("-fx-background-color: white; -fx-background-radius: 5em;");
        restoreButtonSize(LoginButton);
    }



    private static final double ENLARGE_FACTOR = 1.1;
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



}

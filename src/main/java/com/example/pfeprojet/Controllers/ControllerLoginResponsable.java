package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Connexion;
import com.example.pfeprojet.Entreprise.Entreprise;
import com.example.pfeprojet.Entreprise.Responsable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerLoginResponsable {
    @FXML
    private AnchorPane anchorPaneRespo;
    @FXML
    private Text label;

    @FXML
    private  TextField mailRespo;
    @FXML
    private  PasswordField pwdRespo;

    Entreprise e = new Entreprise(cmp);
    static String cmp = ControllerFstWindow.getEntreprise().getNomEntreprise();
    private static Responsable respo ;

    public static Responsable getResponsable() {return respo;}


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
    void loginRespo(ActionEvent event) throws SQLException, IOException {
        Alerts a = new Alerts();

        if(mailRespo.getText().isEmpty() || pwdRespo.getText().isEmpty()) {
            System.out.println("certains champs sont null");
        }else{
            getRespo();
            if(respo == null){
                System.out.println("Aucun responsable avec ce mail");
            }else{
                String inputMail = mailRespo.getText().trim();
                String inputPwd = pwdRespo.getText().trim();

                String mail = respo.getMail();
                String pwd = respo.getMotDePasse();

                if(inputMail.equalsIgnoreCase(mail)){
                    if(inputPwd.equals(pwd)){
                        ChangingWindows cw = new ChangingWindows();
                        cw.switchWindow(event,"DashboardResponsable.fxml");
                    }else{
                        a.showAlert("Mot de passe erroné","Le mot de passe que vous avez saisi est incorrect. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
                    }
                }else{
                    a.showAlert("Email erroné","L'e-mail que vous avez saisi n'existe pas. Veuillez vérifier votre saisie et réessayer.","/images/annuler.png");
                }
            }
        }
    }

    public void getRespo() {
        try{
            Connexion c = new Connexion("jdbc:mysql://localhost:3306/"+cmp+"?user=root");
            System.out.println("le nom de l'entreprise : " +cmp);
            ResultSet rs = c.lire("select nom, prenom, adresseMail, motdePasse from responsables where adresseMail = ?", mailRespo.getText());
            if(rs.next()){
                respo = new Responsable(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                c.closeResources();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

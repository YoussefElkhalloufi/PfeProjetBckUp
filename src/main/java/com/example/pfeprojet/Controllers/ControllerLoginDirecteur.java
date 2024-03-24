package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Connexion;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerLoginDirecteur {
    @FXML
    private Button LoginButton;

    @FXML
    private Text label;
    @FXML
    private Text directeur;
    @FXML
    private AnchorPane anchorPane;
    private static String cmp = ControllerFstWindow.getCmp();

    public static String getCmp(){
        return cmp;
    }


    public void initialize() throws SQLException {
        label.setText(cmp);
        directeur.setText(getNomDirecteur());


        Platform.runLater(() -> {
            // Set initial X-coordinate to center the label
            double centerX = (anchorPane.getWidth() - label.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(label, centerX);
        });
    }


    public String getNomDirecteur() throws SQLException {
        Connexion c = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");

        ResultSet rs = c.lire("select nomDirecteur, prenomDirecteur from infosentreprises where nomEntreprise = ?", cmp);

        if(rs.next()){
            String nomDirecteur = rs.getString(1);
            System.out.println(nomDirecteur);
            c.closeResources();
            return nomDirecteur;
        }
        return null ;
    }


    @FXML
    public void onMouseEntered(MouseEvent event) {
        LoginButton.setStyle("-fx-background-color: #59A8A4; -fx-text-fill: white; -fx-background-radius: 5em;");
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

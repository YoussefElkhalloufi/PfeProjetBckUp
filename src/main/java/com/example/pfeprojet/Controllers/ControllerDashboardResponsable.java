package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import com.example.pfeprojet.Entreprise.Responsable;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class ControllerDashboardResponsable {
    private ChangingWindows cw = new ChangingWindows();
    private static final double ENLARGE_FACTOR = 1.05;

    @FXML
    private Text label;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Text chiffreAffaire;
    @FXML
    private Text nbrClt;
    @FXML
    private Text nbPersonnel;

    @FXML
    private AnchorPane parametresAnchor;
    @FXML
    private AnchorPane personnel;
    @FXML
    private AnchorPane client;
    @FXML
    private AnchorPane chiffreAffaireAnchorpane;

    public static Responsable respo = ControllerLoginResponsable.getResponsable();


    private static final Entreprise e = ControllerFstWindow.getEntreprise();
    public static Entreprise getEntreprise(){
        return e;
    }

    @FXML
    void helpAndSupport(ActionEvent event) throws IOException {
        cw.switchWindow(event, "helpAndSupport.fxml");
    }

    public void onMouseExitedMessagerie(){
        parametresAnchor.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(parametresAnchor);
    }
    public void onMouseEnteredMessagerie(){
        parametresAnchor.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(parametresAnchor);
    }
    public void onMouseExitedChiffreAffaire(){
        chiffreAffaireAnchorpane.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(chiffreAffaireAnchorpane);
    }
    public void onMouseEnteredChiffreAffaire(){
        chiffreAffaireAnchorpane.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(chiffreAffaireAnchorpane);
    }
    public void onMouseExitedClient(){
        client.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(client);
    }
    public void onMouseEnteredClient(){
        client.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(client);
    }
    public void onMouseExitedPersonnel(){
        personnel.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(personnel);
    }
    public void onMouseEnteredPersonnel(){
        personnel.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(personnel);
    }

    public void enlargeButton(AnchorPane anchor) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), anchor);
        scaleTransition.setToX(ENLARGE_FACTOR);
        scaleTransition.setToY(ENLARGE_FACTOR);
        scaleTransition.play();
    }

    public void restoreButtonSize(AnchorPane anchor) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), anchor);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    @FXML
    void switchToClient(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/clientsEntreprise.fxml");
    }
    @FXML
    void switchToCA(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/chiffreAffaire.fxml");
    }

    public void initialize(){
        label.setText("Responsable '" +respo.getNom() +"' de l'entreprise '" +e.getNomEntreprise()+"'");
        if(e.getChiffreAffaireTotal() != null){
            chiffreAffaire.setText(e.getChiffreAffaireTotal() +" DH");
        }else{
            chiffreAffaire.setText("000 000 DH");
        }

        nbrClt.setText(String.valueOf(e.getNbrClients()));

        nbPersonnel.setText(String.valueOf(e.getNbPersonnel()));

        Platform.runLater(() -> {
            double centerX = (anchorPane.getWidth() - label.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(label, centerX);
        });
    }

    @FXML
    void switchToPersonnel(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/personnel.fxml");
    }

    @FXML
    void switchToParametres(MouseEvent event) throws IOException {
        cw.switchWindowPane(event, "profilResponsable.fxml");
    }
}

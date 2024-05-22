package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Directeur;
import com.example.pfeprojet.Entreprise.Entreprise;
import com.example.pfeprojet.HelloApplication;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;



public class ControllerDashboardDirecteur {

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
    private AnchorPane messagerieAnchor;
    @FXML
    private AnchorPane personnel;
    @FXML
    private AnchorPane client;
    @FXML
    private AnchorPane entreprise;
    @FXML
    private AnchorPane facturation;
    @FXML
    private AnchorPane inventaire;
    @FXML
    private AnchorPane chiffreAffaireAnchorpane;
    @FXML
    private Button aideBtn;
    mouseEvents ms = new mouseEvents();
    public void onMouseExitedMessagerie(){
        messagerieAnchor.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(messagerieAnchor);
    }
    public void onMouseEnteredMessagerie(){
        messagerieAnchor.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(messagerieAnchor);
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
    public void onMouseExitedInventaire(){
        inventaire.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(inventaire);
    }
    public void onMouseEnteredInventaire(){
        inventaire.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(inventaire);
    }
    public void onMouseExitedFacturation(){
        facturation.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(facturation);
    }
    public void onMouseEnteredFacturation(){
        facturation.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(facturation);
    }
    public void onMouseExitedEntreprise(){
        entreprise.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(entreprise);
    }
    public void onMouseEnteredEntreprise(){
        entreprise.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(entreprise);
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

    private static final Entreprise e = ControllerFstWindow.getEntreprise();
    public static Entreprise getEntreprise(){
        return e;
    }
    private Directeur dr = ControllerLoginDirecteur.getDr();
    public void initialize(){
        label.setText("Directeur '" +dr.getNom() +"' de l'entreprise  '" +e.getNomEntreprise()+"'");


        if(e.getChiffreAffaireTotal() != null){
            chiffreAffaire.setText(e.getChiffreAffaireTotal() +" DH");
        }else{
            chiffreAffaire.setText("0");
        }

        nbrClt.setText(String.valueOf(e.getNbrClients()));

        nbPersonnel.setText(String.valueOf(e.getNbPersonnel()));

        Platform.runLater(() -> {
            double centerX = (anchorPane.getWidth() - label.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(label, centerX);
        });
    }


    public void helpAndSupport(ActionEvent event) throws IOException {
        cw.switchWindow(event, "helpAndSupport.fxml");
    }



    @FXML
    void switchToCA(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/chiffreAffaire.fxml");
    }

    @FXML
    void switchToPersonnel(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/personnel.fxml");
    }
    @FXML
    void switchToInventaire(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/inventaire.fxml");
    }
    @FXML
    void switchToFacturation(MouseEvent event) throws IOException {
        cw.switchWindowPane(event, "/com/example/pfeprojet/facturation.fxml");
    }
    @FXML
    void switchToClient(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/clientsEntreprise.fxml");
    }
    @FXML
    void restartApplication(ActionEvent event) {
        HelloApplication.restartApplication();
    }
}

//TODO : fix the deconnexion button !!!!

package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Connexion;
import com.example.pfeprojet.Entreprise.Directeur;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ControllerDashboardDirecteur {

    //TODO : creation des triggers en SQL qui se charge de la modification du stock (fact, produit, etc )

    //TODO : try CODE WITH ME
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
    @FXML
    public void onMouseEnteredaide(MouseEvent event) {
        ms.onMouseEntered2(event, aideBtn);
    }
    @FXML
    public void onMouseExitedaide(MouseEvent event) {ms.onMouseExited2(event, aideBtn);}

    public void onMouseExitedMessagerie(){
        messagerieAnchor.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(messagerieAnchor);
    }
    public void onMouseEnteredMessagerie(){
        messagerieAnchor.setStyle("-fx-background-color : #b7b7b7; -fx-background-radius: 25;");
        enlargeButton(messagerieAnchor);
    }
    public void onMouseExitedChiffreAffaire(){
        chiffreAffaireAnchorpane.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(chiffreAffaireAnchorpane);
    }
    public void onMouseEnteredChiffreAffaire(){
        chiffreAffaireAnchorpane.setStyle("-fx-background-color : #b7b7b7; -fx-background-radius: 25;");
        enlargeButton(chiffreAffaireAnchorpane);
    }
    public void onMouseExitedClient(){
        client.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(client);
    }
    public void onMouseEnteredClient(){
        client.setStyle("-fx-background-color : #b7b7b7; -fx-background-radius: 25;");
        enlargeButton(client);
    }
    public void onMouseExitedPersonnel(){
        personnel.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(personnel);
    }
    public void onMouseEnteredPersonnel(){
        personnel.setStyle("-fx-background-color : #b7b7b7; -fx-background-radius: 25;");
        enlargeButton(personnel);
    }
    public void onMouseExitedInventaire(){
        inventaire.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(inventaire);
    }
    public void onMouseEnteredInventaire(){
        inventaire.setStyle("-fx-background-color : #b7b7b7; -fx-background-radius: 25;");
        enlargeButton(inventaire);
    }
    public void onMouseExitedFacturation(){
        facturation.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(facturation);
    }
    public void onMouseEnteredFacturation(){
        facturation.setStyle("-fx-background-color : #b7b7b7; -fx-background-radius: 25;");
        enlargeButton(facturation);
    }
    public void onMouseExitedEntreprise(){
        entreprise.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(entreprise);
    }
    public void onMouseEnteredEntreprise(){
        entreprise.setStyle("-fx-background-color : #b7b7b7; -fx-background-radius: 25;");
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



    private final Connexion cn = new Connexion("jdbc:mysql://localhost:3306/"+ControllerLoginDirecteur.getCmp()+"?user=root");
    private Directeur dr = ControllerLoginDirecteur.getDirecteur();
    public void initialize(){
        label.setText("Directeur '" +dr.getNom() +"' de l'entreprise  '" +ControllerLoginDirecteur.getCmp()+"'");

        BigDecimal chiffreAff = chiffreAffaire();
        if(chiffreAff != null){
            chiffreAffaire.setText(chiffreAff +" DH");
        }else{
            chiffreAffaire.setText("0");
        }

        int nbrClients = nbrClient();
        if(nbrClients != 0){
            nbrClt.setText(String.valueOf(nbrClients));
        }else{
            nbrClt.setText("0");
        }

        int nbrPerso = nbrPersonnel();
        if(nbrPerso != 0){
            nbPersonnel.setText(String.valueOf(nbrPerso));
        }else{
            nbPersonnel.setText("0");
        }

        Platform.runLater(() -> {
            double centerX = (anchorPane.getWidth() - label.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(label, centerX);
        });
    }

    public BigDecimal chiffreAffaire(){
        try{
            ResultSet rs = cn.lire("select sum(Total_TTC) from facture");
            if(rs.next()){
                return rs.getBigDecimal(1);
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public  int nbrClient(){
        try{
            ResultSet rs = cn.lire("Select count(*) from client");
            if(rs.next()){
                return rs.getInt(1);
            }
            return 0 ;
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public int nbrPersonnel(){
        try{
            int nbPersonnel ;
            ResultSet rs, rs1, rs2 ;
            rs = cn.lire("select count(*) from gestionnaires");
            rs1 = cn.lire("select count(*) from vendeurs");
            rs2 = cn.lire("select count(*) from responsables");
            if(rs.next() && rs1.next() && rs2.next()){
                nbPersonnel = rs.getInt(1) + rs1.getInt(1) + rs2.getInt(1);
                return nbPersonnel;
            }
            return 0;
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void helpAndSupport(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        cw.switchWindow(event, "helpAndSupport.fxml");
    }
}

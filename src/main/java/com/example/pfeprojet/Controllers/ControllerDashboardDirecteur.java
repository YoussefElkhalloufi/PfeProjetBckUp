package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Connexion;
import com.example.pfeprojet.Entreprise.Directeur;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerDashboardDirecteur {

    //TODO : design des bouttons + LE NOM de directeur + L'ENTREPRISE

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
    private Button chiffreAffaireBtn;
    @FXML
    private Button clientBtn;
    @FXML
    private Button factureBtn;
    @FXML
    private Button inventaireBtn;
    @FXML
    private Button personnelBtn;
    @FXML
    private Button profilEntrepriseBtn;
    @FXML
    private Button messagerieBtn;


    private final Connexion cn = new Connexion("jdbc:mysql://localhost:3306/"+ControllerLoginDirecteur.getCmp()+"?user=root");
    private Directeur dr = ControllerLoginDirecteur.getDirecteur();
    public void initialize(){
        label.setText("Directeur '" +dr.getNom() +"' de l'entreprise  '" +ControllerLoginDirecteur.getCmp()+"'");

        BigDecimal chiffreAff = chiffreAffaire();
        if(chiffreAff != null){
            chiffreAffaire.setText(chiffreAff +" DH");
        }else{
            chiffreAffaire.setText("KHERZAT DOUAE");
            //TODO : khrzt douae
        }

        int nbrClients = nbrClient();
        if(nbrClients != 0){
            nbrClt.setText(String.valueOf(nbrClients));
        }else{
            nbrClt.setText("Kherzat douae again !!");
        }

        int nbrPerso = nbrPersonnel();
        if(nbrPerso != 0){
            nbPersonnel.setText(String.valueOf(nbrPerso));
        }else{
            nbPersonnel.setText("Kherzat douae !!!");
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
}

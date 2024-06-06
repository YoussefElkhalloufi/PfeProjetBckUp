package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;


public class ControllerFacturation {


    @FXML
    private TextField numFactureTxtBox;


    @FXML
    private AnchorPane anchoreProduit;
    @FXML
    private ComboBox<String> cmbCinClients;
    @FXML
    private ComboBox<Integer> cmbIdProduits;
    @FXML
    private Text designationLbl;
    @FXML
    private Text puLbl;
    @FXML
    private Text totalLbl;
    @FXML
    private TextField qteTxtBox;





    @FXML
    private AnchorPane anchorService;
    @FXML
    private ComboBox<Integer> cmbIdServices;
    @FXML
    private Text cphLbl;
    @FXML
    private Text designationServiceLbl;
    @FXML
    private TextField nbrHeure;
    @FXML
    private Text totalServiceLbl;


    @FXML
    private AnchorPane anchorPaneRemise;
    @FXML
    private AnchorPane anchorPaneTva;
    @FXML
    private TextField tvaTextbox;
    @FXML
    private TextField remiseTextbox;


    Alerts sa = new Alerts();

    private Entreprise e = ControllerDashboardDirecteur.getEntreprise();

    public void initialize() throws SQLException {
        if(e == null){
            e = ControllerDashboardVendeur.getEntreprise();
        }
        cmbCinClients.getItems().addAll(e.getCinClients());
        if(e.typeInventaire() == 0){
            cmbIdProduits.getItems().addAll(e.getIdProduits());
            cmbIdServices.getItems().addAll(e.getIdServices());
        }else if (e.typeInventaire() == 1){
            anchorService.setVisible(false);
            cmbIdProduits.getItems().addAll(e.getIdProduits());
        }else if (e.typeInventaire() == 2){
            anchoreProduit.setVisible(false);
            cmbIdServices.getItems().addAll(e.getIdServices());
        }

        check_Remise_Tva();
    }

    int checkTvaRemise = 2 ;
    private void check_Remise_Tva() throws SQLException {
        if(e.checkTva_Remise() == -1){
            anchorPaneTva.setVisible(false);
            anchorPaneRemise.setVisible(false);
            checkTvaRemise = 2 ;
        }else if(e.checkTva_Remise() == 0){
            anchorPaneTva.setVisible(false);
            checkTvaRemise = 0 ;
        }else if (e.checkTva_Remise() == 1){
            anchorPaneRemise.setVisible(false);
            checkTvaRemise = 1 ;
        }
    }
     String[] infosProduit ;
    @FXML
    void setProduitInfos(ActionEvent event) {
        infosProduit = e.getInfosProduit(cmbIdProduits.getValue());
        System.out.println(infosProduit[0]);
        System.out.println(infosProduit[1]);
        designationLbl.setText(infosProduit[0]);
        puLbl.setText(infosProduit[1]);
        totalLbl.setText("");
        checkQte(event);
    }

    @FXML
    void setServiceInfos(ActionEvent event) {
        int idSer = cmbIdServices.getValue();
        String[] infosService = e.getInfosService(idSer);
        designationServiceLbl.setText(infosService[0]);
        cphLbl.setText(infosService[1]);
    }

    @FXML
    void checkQte(ActionEvent event) {
        if(qteTxtBox.getText().isEmpty()){

        }else{
            int qte = Integer.parseInt(qteTxtBox.getText());
            if(qte>Integer.parseInt(infosProduit[2])){
                sa.showAlert("Quantité non disponible","La quantité choisi est non disponible au stock .","/images/annuler.png");
            }else{
                double total = Double.parseDouble(puLbl.getText()) * Integer.parseInt(qteTxtBox.getText());
                totalLbl.setText(total + " DH");
            }
        }
    }

    @FXML
    void ajouterProduitFacture(ActionEvent event) {
        if(numFactureTxtBox.getText().trim().isEmpty() || cmbCinClients.getValue() == null || qteTxtBox.getText().trim().isEmpty() || cmbIdProduits.getValue() == null || totalLbl.getText().trim().isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            int numFc = Integer.parseInt(numFactureTxtBox.getText().trim());
            String cinClt = cmbCinClients.getValue();
            int idPr = cmbIdProduits.getValue();
            int qte = Integer.parseInt(qteTxtBox.getText());

            if(e.insererFactureProduit(numFc,cinClt,idPr,qte)){
                sa.showAlert("Ajout avec succes","Produit ajouté avec succes a la facture Numero : " +numFactureTxtBox.getText(),"/images/checked.png");
                viderProduit();
            }else{
                sa.showWarning("Erreur","Produit existe déjà dans la facture");
            }
        }
    }

    @FXML
    void onKeyQte(KeyEvent event) {
        checkQte(new ActionEvent());
    }
    @FXML
    void onKeyNbrHeure(KeyEvent event) {
        setTotalServiceFacture(new ActionEvent());
    }

    @FXML
    void ajouterServiceFacture(ActionEvent event) {
        if(numFactureTxtBox.getText().trim().isEmpty() || cmbCinClients.getValue() == null || nbrHeure.getText().trim().isEmpty() || cmbIdServices.getValue() == null){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            int numFc = Integer.parseInt(numFactureTxtBox.getText().trim());
            String cinClt = cmbCinClients.getValue();
            int idSer = cmbIdServices.getValue() ;
            int nbrH = Integer.parseInt(nbrHeure.getText());

            if(e.insererFactureService(numFc, cinClt, idSer,nbrH)){
                sa.showAlert("Ajout avec succes","Service ajouté avec succes a la facture Numero : " +numFactureTxtBox.getText(),"/images/checked.png");
                viderService();
            }else{
                sa.showWarning("Erreur","Service existe déjà dans la facture");
            }
        }
    }

    @FXML
    void ajouterFacture(ActionEvent event) throws SQLException {
        int remise = 0 ;
        int tva = 0 ;
        if(!tvaTextbox.getText().trim().isEmpty()) tva = Integer.parseInt(tvaTextbox.getText());
        if(!remiseTextbox.getText().trim().isEmpty()) remise = Integer.parseInt(remiseTextbox.getText());

        if(numFactureTxtBox.getText().trim().isEmpty()){
            System.out.println("vous devez remplir le numero facture");
            sa.showWarning("Attention", "Assurez-vous de remplir le numero de la facture.");
        }else {
            int numFac = Integer.parseInt(numFactureTxtBox.getText()) ;
            e.ajouterFacture(remise,tva,numFac,e.CalculTotalTTC(e.getTotalFacture(numFac), remise, tva));
            sa.showAlert("Ajout avec succes","Facture numero :  " +numFactureTxtBox.getText()+" ajouté avec succes.","/images/checked.png");
        }

    }

    @FXML
    void setTotalServiceFacture(ActionEvent event) {
        double total = Double.parseDouble(cphLbl.getText()) * Double.parseDouble(nbrHeure.getText());
        totalServiceLbl.setText(total + " DH");
    }

    private void viderProduit(){
        designationLbl.setText("");
        puLbl.setText("");
        qteTxtBox.setText("");
        totalLbl.setText("");
    }
    private void viderService(){
        designationServiceLbl.setText("");
        cphLbl.setText("");
        nbrHeure.setText("");
        totalLbl.setText("");
    }

    @FXML
    void dashboardDirecteur(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        if(ControllerDashboardDirecteur.dr == null){
            cw.switchWindow(event,"DashboardVendeur.fxml");
        }else{
            cw.switchWindow(event,"DashboardDirecteur.fxml");
        }
    }
    //TODO : Affichage et ajout dune facture separés
    
}

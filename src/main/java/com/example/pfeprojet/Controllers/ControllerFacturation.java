package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class ControllerFacturation {


    @FXML
    private TextField numFactureTxtBox;


    @FXML
    private AnchorPane anchoreProduit;
    @FXML
    private ComboBox<String> cmbCinClients;
    @FXML
    private ComboBox<Integer> cmdIdProduits;
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
    private ComboBox<Integer> cmdIdServices;
    @FXML
    private Text cphLbl;
    @FXML
    private Text designationServiceLbl;

    Alerts sa = new Alerts();

    private Entreprise e = ControllerDashboardDirecteur.getEntreprise();

    public void initialize(){
        cmbCinClients.getItems().addAll(e.getCinClients());
        if(e.typeInventaire() == 0){
            cmdIdProduits.getItems().addAll(e.getIdProduits());
            cmdIdServices.getItems().addAll(e.getIdServices());
        }else if (e.typeInventaire() == 1){
            anchorService.setVisible(false);
            cmdIdProduits.getItems().addAll(e.getIdProduits());
        }else if (e.typeInventaire() == 2){
            anchoreProduit.setVisible(false);
            cmdIdServices.getItems().addAll(e.getIdServices());
        }

    }
     String[] infosProduit ;
    @FXML
    void setProduitInfos(ActionEvent event) {
        infosProduit = e.getInfosProduit(cmdIdProduits.getValue());
        System.out.println(infosProduit[0]);
        System.out.println(infosProduit[1]);
        designationLbl.setText(infosProduit[0]);
        puLbl.setText(infosProduit[1]);
    }

    @FXML
    void setServiceInfos(ActionEvent event) {
        int idSer = cmdIdServices.getValue();
        String[] infosService = e.getInfosService(idSer);
        designationServiceLbl.setText(infosService[0]);
        cphLbl.setText(infosService[1]);
    }

    @FXML
    void checkQte(ActionEvent event) {
        int qte = Integer.parseInt(qteTxtBox.getText());
        if(qte>Integer.parseInt(infosProduit[2])){
            sa.showAlert("Quantité non disponible","La quantité choisi est non disponible au stock .","/images/annuler.png");
        }else{
            double total = Double.parseDouble(puLbl.getText()) * Integer.parseInt(qteTxtBox.getText());
            totalLbl.setText(total + " DH");
        }
    }

    @FXML
    void ajouterProduitFacture(ActionEvent event) {
        if( numFactureTxtBox.getText().isEmpty() || cmbCinClients.getItems().isEmpty() || qteTxtBox.getText().trim().isEmpty() || cmdIdProduits.getItems().isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            int numFc = Integer.parseInt(numFactureTxtBox.getText().trim());
            String cinClt = cmbCinClients.getValue();
            int idPr = cmdIdProduits.getValue();
            int qte = Integer.parseInt(qteTxtBox.getText());

            if(e.insererFacture(numFc,cinClt,idPr,qte)){
                viderProduit();
            }else{
                System.out.println("erreur");
            }
        }
    }

    private void viderProduit(){
        designationServiceLbl.setText("");
        puLbl.setText("");
        qteTxtBox.setText("");
    }

}

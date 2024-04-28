package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControllerInventaire {

    @FXML
    private TableView<Object[]> produitTableView;
    @FXML
    private Label categorieText;
    @FXML
    private TextField categorieTextBox;
    @FXML
    private Label dateEnregistrementText;
    @FXML
    private DatePicker dateEnregistrementTextBox;
    @FXML
    private Label descriptionText;
    @FXML
    private TextField descriptionTextBox;
    @FXML
    private TextField idProduit;
    @FXML
    private Label libelleText;
    @FXML
    private TextField libelleTextbox;
    @FXML
    private TextField prixUnitaireProduit;
    @FXML
    private TextField stockProduit;
    @FXML
    private Button afficherProduit;
    @FXML
    private Button ajouterProduit;
    @FXML
    private Button supprimerProduit;
    @FXML
    private Button modifierProduit;
    @FXML
    private TextField idPrAfficher;



    @FXML
    private TableView<Object[]> serviceTableView;
    @FXML
    private Label libelleServiceText;
    @FXML
    private TextField libelleServiceTextBox;
    @FXML
    private Label typeServiceText;
    @FXML
    private TextField typeServiceTextBox;
    @FXML
    private Label descriptionServiceText;
    @FXML
    private TextField descriptionServiceTextBox;
    @FXML
    private TextField idService;
    @FXML
    private TextField coutPheure;
    @FXML
    private Button supprimerService;
    @FXML
    private Button modifierService;
    @FXML
    private Button ajouterService;
    @FXML
    private Button afficherService;
    @FXML
    private TextField idSerAfficher;

    @FXML
    private Tab produitPane;
    @FXML
    private Tab servicePane;

    Alerts sa = new Alerts();

    private Entreprise e = ControllerDashboardDirecteur.getEntreprise();

    public void initialize(){

        //System.out.println(e.getColonneProduit().toString());
        if (e.typeInventaire() == 0){
            e.populateTableView(e.getInventaire("Produit"),produitTableView, idProduit);
            e.populateTableView(e.getInventaire("Service"), serviceTableView,idService);
            checkColonnesProduit();
            checkColonnesService();
            buttonsStyleProduit();
            buttonsStyleService();
        }else if(e.typeInventaire() == 1){
            servicePane.setDisable(true);
            e.populateTableView(e.getInventaire("Produit"),produitTableView, idProduit);
            checkColonnesProduit();
            buttonsStyleProduit();
        }else if (e.typeInventaire() == 2){
            servicePane.getTabPane().getSelectionModel().select(servicePane);
            e.populateTableView(e.getInventaire("Service"),serviceTableView, idProduit);
            checkColonnesService();
            produitPane.setDisable(true);
            buttonsStyleService();
        }
    }
    private void buttonsStyleService() {
        afficherService.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(afficherService));
        afficherService.setOnMouseExited(event -> applyButtonStyleOnMouseExited(afficherService));
        ajouterService.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(ajouterService));
        ajouterService.setOnMouseExited(event -> applyButtonStyleOnMouseExited(ajouterService));
        modifierService.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(modifierService));
        modifierService.setOnMouseExited(event -> applyButtonStyleOnMouseExited(modifierService));
        supprimerService.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(supprimerService));
        supprimerService.setOnMouseExited(event -> applyButtonStyleOnMouseExited(supprimerService));
    }
    private void buttonsStyleProduit() {
        afficherProduit.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(afficherProduit));
        afficherProduit.setOnMouseExited(event -> applyButtonStyleOnMouseExited(afficherProduit));
        ajouterProduit.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(ajouterProduit));
        ajouterProduit.setOnMouseExited(event -> applyButtonStyleOnMouseExited(ajouterProduit));
        modifierProduit.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(modifierProduit));
        modifierProduit.setOnMouseExited(event -> applyButtonStyleOnMouseExited(modifierProduit));
        supprimerProduit.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(supprimerProduit));
        supprimerProduit.setOnMouseExited(event -> applyButtonStyleOnMouseExited(supprimerProduit));

    }
    private void applyButtonStyleOnMouseEntered(Button button) {
        button.setStyle("-fx-background-color:  #D5D5D5; -fx-text-fill: BLACK; -fx-background-radius: 5em;");
    }

    private void applyButtonStyleOnMouseExited(Button button){
        button.setStyle("-fx-background-color: white; -fx-background-radius: 5em;");
    }
        public boolean containsColonne(ArrayList<String> colonnes, String colonne){
        for(String s : colonnes){
            if(s.equalsIgnoreCase(colonne)){
                return true;
            }
        }
        return false ;
    }

    public void checkColonnesProduit(){

        ArrayList<String> colonnesProduit = e.getColonneInventaire("produit");
        if(!containsColonne(colonnesProduit, "libelleProduit")) {
            libelleText.setVisible(false);
            libelleTextbox.setVisible(false);
        }
        if(!containsColonne(colonnesProduit, "description")) {
            descriptionText.setVisible(false);
            descriptionTextBox.setVisible(false);
        }
        if(!containsColonne(colonnesProduit, "dateenregistrement")) {
            dateEnregistrementText.setVisible(false);
            dateEnregistrementTextBox.setVisible(false);
        }
        if(!containsColonne(colonnesProduit, "categorie")) {
            categorieText.setVisible(false);
            categorieTextBox.setVisible(false);
        }
    }

    public void checkColonnesService(){
        ArrayList<String> colonnesService = e.getColonneInventaire("service");
        if(!containsColonne(colonnesService, "libelleService")){
            libelleServiceText.setVisible(false);
            libelleServiceTextBox.setVisible(false);
        }
        if(!containsColonne(colonnesService, "Description")){
            descriptionServiceText.setVisible(false);
            descriptionServiceTextBox.setVisible(false);
        }
        if(!containsColonne(colonnesService, "typeservice")){
            typeServiceText.setVisible(false);
            typeServiceTextBox.setVisible(false);
        }
    }


    @FXML
    void actualiserPr(ActionEvent event) {
        initialize();
        viderProduit();
    }
    @FXML
    void actualiserSer(ActionEvent event) {
        initialize();
        viderProduit();
    }

    @FXML
    void ajouterProduit(ActionEvent event) {
        LocalDate selectedDate = dateEnregistrementTextBox.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        if(idProduit.getText().trim().isEmpty() || prixUnitaireProduit.getText().trim().isEmpty() || stockProduit.getText().trim().isEmpty() || libelleTextbox.getText().trim().isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            int idPr = Integer.parseInt(idProduit.getText().trim());
            double PU = Double.parseDouble(prixUnitaireProduit.getText().trim());
            int stck = Integer.parseInt(stockProduit.getText().trim());
            String libellePr = libelleTextbox.getText().trim();
            String dateEnregistrementPr = "";
            if(selectedDate != null){
                dateEnregistrementPr = selectedDate.format(formatter);
            }
            String descriptionPr = descriptionTextBox.getText().trim();
            String cat = categorieTextBox.getText().trim();

            if(e.ajouterProduit(idPr,PU,stck,libellePr, dateEnregistrementPr, descriptionPr,cat)){
                e.populateTableView(e.getInventaire("Produit"),produitTableView, idProduit);
                sa.showAlert("Ajout avec succes","Le Produit est bien ajouté ! ","/images/checked.png");
                viderProduit();
            }else{
                sa.showAlert("Ajout Erroné", "Le Produit existe deja ! ", "/images/annuler.png");
                viderProduit();
            }
        }
    }

    @FXML
    void ajouterService(ActionEvent event) {
        if(!idService.getText().isEmpty() || !coutPheure.getText().isEmpty()){
            int idSer = Integer.parseInt(idService.getText().trim());
            double coutParHeure = Double.parseDouble(coutPheure.getText().trim());
            String lib = libelleServiceTextBox.getText().trim();
            String typeSer = typeServiceTextBox.getText().trim();
            String descriptionSer = descriptionServiceTextBox.getText().trim();

            if(e.ajouterService(idSer,coutParHeure,lib,typeSer,descriptionSer)){
                e.populateTableView(e.getInventaire("Service"),serviceTableView, idService);
                sa.showAlert("Ajout avec succes","Le Service est bien ajouté ! ","/images/checked.png");
                viderService();
            }else{
                sa.showAlert("Ajout Erroné", "Le service existe deja ! ", "/images/annuler.png");
                viderService();
            }
        }else{
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }
    }
    @FXML
    void supprimerProduit(ActionEvent event) {
        if(!idProduit.getText().isEmpty()){
            int idPr = Integer.parseInt(idProduit.getText());
            if(sa.showConfirmationAlert("Confirmation de la suppression","Êtes-vous sûr de vouloir procéder à la suppression du responsable ?")) {
                if(e.supprimerProduit(idPr)){
                    e.populateTableView(e.getInventaire("Produit"),produitTableView, idProduit);
                    sa.showAlert("Suppression réussie","Le produit a été supprimé avec succès.","/images/checked.png");
                    viderProduit();
                }else{
                    sa.showAlert("Suppression échouée","Le ID de produit saisi n'existe pas. Veuillez sélectionner un produit valide dans le tableau avant de procéder à la suppression.", "/images/annuler.png");
                }
            }
        }else{
            sa.showWarning("Suppresion Erroné", "Veuillez sélectionner un produit avant de procéder");
        }
    }
    @FXML
    void supprimerService(ActionEvent event) {
        if(!idService.getText().trim().isEmpty()){
            int idSer = Integer.parseInt(idService.getText());
            if(sa.showConfirmationAlert("Confirmation de la suppression","Êtes-vous sûr de vouloir procéder à la suppression du Service ?")) {
                if(e.supprimerService(idSer)){
                    e.populateTableView(e.getInventaire("Service"),serviceTableView, idService);
                    sa.showAlert("Suppression réussie","Le service a été supprimé avec succès.","/images/checked.png");
                    viderService();
                }else{
                    sa.showAlert("Suppression échouée","Le ID de service saisi n'existe pas. Veuillez sélectionner un service valide dans le tableau avant de procéder à la suppression.", "/images/annuler.png");
                }
            }
        }else{
            sa.showWarning("Suppresion Erroné", "Veuillez sélectionner un service avant de procéder");
        }
    }
    @FXML
    void afficherProduit(ActionEvent event) {
        if(!idPrAfficher.getText().trim().isEmpty()){
            int idPr = Integer.parseInt(idPrAfficher.getText().trim());
            e.populateTableView(e.afficherProduit(idPr), produitTableView, idProduit);
        }else{
            sa.showWarning("Affichage échouée","Veuillez taper un Id de produit avant de procéder.");
        }
    }

    @FXML
    void afficherService(ActionEvent event) {

        if(!idSerAfficher.getText().trim().isEmpty()){
            int idSer = Integer.parseInt(idSerAfficher.getText().trim());
            e.populateTableView(e.afficherService(idSer), serviceTableView, idService);
        }else{
            sa.showWarning("Affichage échouée","Veuillez taper un Id de Service avant de procéder.");
        }
    }
    @FXML
    void modifierProduit(ActionEvent event) {
        LocalDate selectedDate = dateEnregistrementTextBox.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        if(idProduit.getText().trim().isEmpty() || prixUnitaireProduit.getText().trim().isEmpty() || stockProduit.getText().trim().isEmpty() || libelleTextbox.getText().isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            int idPr = Integer.parseInt(idProduit.getText().trim());
            double PU = Double.parseDouble(prixUnitaireProduit.getText().trim());
            int stck = Integer.parseInt(stockProduit.getText().trim());
            String libellePr = libelleTextbox.getText();
            String dateEnregistrementPr = "";
            if(selectedDate != null){
                dateEnregistrementPr = selectedDate.format(formatter);
            }
            String descriptionPr = descriptionTextBox.getText().trim();
            String cat = categorieTextBox.getText().trim();

            if(e.modifierProduit(idPr,PU,stck,libellePr, dateEnregistrementPr, descriptionPr,cat)){
                e.populateTableView(e.getInventaire("Produit"),produitTableView, idProduit);
                sa.showAlert("Modification avec succes","Le Produit est bien modifié ! ","/images/checked.png");
                viderProduit();
            }else{
                sa.showWarning("Modification Erroné", "Une erreur s'est produite lors de la modification du Produit\nNB : Vous n'avez pas le droit de modifier le ID du produit. \nSi vous souhaitez modifier le id, veuillez créer un nouveau Produit avec le nouveau ID. ");
                viderProduit();
            }
        }
    }

    @FXML
    void modifierService(ActionEvent event) {
        if(!idService.getText().isEmpty() || !coutPheure.getText().isEmpty()){
            int idSer = Integer.parseInt(idService.getText().trim());
            double coutParHeure = Double.parseDouble(coutPheure.getText().trim());
            String lib = libelleServiceTextBox.getText().trim();
            String typeSer = typeServiceTextBox.getText().trim();
            String descriptionSer = descriptionServiceTextBox.getText().trim();

            if(e.modifierService(idSer,coutParHeure,lib,typeSer,descriptionSer)){
                e.populateTableView(e.getInventaire("Service"),serviceTableView, idService);
                sa.showAlert("Modification avec succes","Le Service est bien modifié ! ","/images/checked.png");
                viderService();
            }else{
                sa.showWarning("Modification Erroné", "Une erreur s'est produite lors de la modification du Service\nNB : Vous n'avez pas le droit de modifier le ID du Service. \nSi vous souhaitez modifier le id, veuillez créer un nouveau service avec le nouveau ID. ");
                viderService();
            }
        }else{
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }
    }

    public void viderService(){
        idService.setText("");
        coutPheure.setText("");
        libelleServiceTextBox.setText("");
        typeServiceTextBox.setText("");
        descriptionServiceTextBox.setText("");
        idSerAfficher.setText("");
    }

    public void viderProduit(){
        idProduit.setText("");
        prixUnitaireProduit.setText("");
        stockProduit.setText("");
        libelleTextbox.setText("");
        descriptionTextBox.setText("");
        categorieTextBox.setText("");
        dateEnregistrementTextBox.setValue(null);
        idPrAfficher.setText("");
    }

    @FXML
    void dashboardDirecteur(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        cw.switchWindow(event,"DirecteurDashboard.fxml");
    }
}

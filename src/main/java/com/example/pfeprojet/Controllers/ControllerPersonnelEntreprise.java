package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ControllerPersonnelEntreprise {

    @FXML
    private TableView<Object[]> responsableTableView;
    @FXML
    private TextField cinTextRespo;
    @FXML
    private TextField nomTextRespo;
    @FXML
    private TextField prenomTextRespo;
    @FXML
    private TextField mailTextRespo;
    @FXML
    private TextField pwdTextRespo;
    @FXML
    private Button afficherRespoBtn;
    @FXML
    private Button ajouterRespoBtn;
    @FXML
    private Button modifierRespoBtn;
    @FXML
    private Button supprimerRespoBtn;
    @FXML
    private TextField cinTextRespoRech;


    @FXML
    private TableView<Object[]> gestionnaireTableView;
    @FXML
    private TextField cinTextGestio;
    @FXML
    private TextField nomTextGestio;
    @FXML
    private TextField prenomTextGestio;
    @FXML
    private TextField mailTextGestio;
    @FXML
    private TextField pwdTextGestio;
    @FXML
    private Button afficherGestioBtn;
    @FXML
    private Button ajouterGestioBtn;
    @FXML
    private Button modifierGestioBtn;
    @FXML
    private Button supprimerGestioBtn;
    @FXML
    private TextField cinTextGestioRech;



    @FXML
    private TableView<Object[]> vendeurTableView;
    @FXML
    private TextField cinTextVendeur;
    @FXML
    private TextField nomTextVendeur;
    @FXML
    private TextField prenomTextVendeur;
    @FXML
    private TextField mailTextVendeur;
    @FXML
    private TextField pwdTextVendeur;
    @FXML
    private Button afficherVendeurBtn;
    @FXML
    private Button ajouterVendeurBtn;
    @FXML
    private Button modifierVendeurBtn;
    @FXML
    private Button supprimerVendeurBtn;
    @FXML
    private TextField cinTextVendeurRech;

    Alerts sa = new Alerts();
    mouseEvents me = new mouseEvents();
    private Entreprise e = ControllerDashboardDirecteur.getEntreprise();


    ArrayList<javafx.scene.control.TextField> textFields = new ArrayList<>();

    public void initialize(){
        e.populateTableViewWithSelectionHandler(e.getPersonnes("Responsables"),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
        e.populateTableViewWithSelectionHandler(e.getPersonnes("Gestionnaires"),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
        e.populateTableViewWithSelectionHandler(e.getPersonnes("Vendeurs"), vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
        buttonsStyle();
        setTextFields();
    }

    private void setTextFields(){
        textFields.add(nomTextGestio);
        textFields.add(prenomTextGestio);
        textFields.add(mailTextGestio);
        textFields.add(pwdTextGestio);
        textFields.add(cinTextGestio);

        textFields.add(nomTextRespo);
        textFields.add(prenomTextRespo);
        textFields.add(mailTextRespo);
        textFields.add(cinTextRespo);
        textFields.add(pwdTextRespo);

        textFields.add(nomTextVendeur);
        textFields.add(prenomTextVendeur);
        textFields.add(mailTextVendeur);
        textFields.add(pwdTextVendeur);
        textFields.add(cinTextVendeur);


        textFields.add(cinTextRespoRech);
        textFields.add(cinTextVendeurRech);
        textFields.add(cinTextGestioRech);
    }
    @FXML
    void actualiser(ActionEvent event) {
        initialize();
        viderTextFields(textFields);
    }
    private void buttonsStyle(){
        afficherRespoBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(afficherRespoBtn));
        afficherRespoBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(afficherRespoBtn));
        ajouterRespoBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(ajouterRespoBtn));
        ajouterRespoBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(ajouterRespoBtn));
        modifierRespoBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(modifierRespoBtn));
        modifierRespoBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(modifierRespoBtn));
        supprimerRespoBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(supprimerRespoBtn));
        supprimerRespoBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(supprimerRespoBtn));

        afficherGestioBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(afficherGestioBtn));
        afficherGestioBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(afficherGestioBtn));
        ajouterGestioBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(ajouterGestioBtn));
        ajouterGestioBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(ajouterGestioBtn));
        modifierGestioBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(modifierGestioBtn));
        modifierGestioBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(modifierGestioBtn));
        supprimerGestioBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(supprimerGestioBtn));
        supprimerGestioBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(supprimerGestioBtn));

        afficherVendeurBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(afficherVendeurBtn));
        afficherVendeurBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(afficherVendeurBtn));
        ajouterVendeurBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(ajouterVendeurBtn));
        ajouterVendeurBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(ajouterVendeurBtn));
        modifierVendeurBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(modifierVendeurBtn));
        modifierVendeurBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(modifierVendeurBtn));
        supprimerVendeurBtn.setOnMouseEntered(event -> applyButtonStyleOnMouseEntered(supprimerVendeurBtn));
        supprimerVendeurBtn.setOnMouseExited(event -> applyButtonStyleOnMouseExited(supprimerVendeurBtn));

    }
    private void applyButtonStyleOnMouseEntered(Button button) {
        button.setStyle("-fx-background-color:  #D5D5D5; -fx-text-fill: BLACK; -fx-background-radius: 5em;");
    }

    private void applyButtonStyleOnMouseExited(Button button){
        button.setStyle("-fx-background-color: white; -fx-background-radius: 5em;");
    }
    @FXML
    void ajouterRespo(ActionEvent event) {
        String cin = cinTextRespo.getText().trim();
        String nom = nomTextRespo.getText().trim();
        String prenom = prenomTextRespo.getText().trim();
        String mail = mailTextRespo.getText().trim();
        String pwd = pwdTextRespo.getText().trim();


        if(cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            if(EmailSender.isValidEmailAddress(mail)){
                if(e.insererPersonnel("Responsables",cin, nom, prenom, mail, pwd)){
                    sa.showAlert("Ajout avec succes", "Le compte du responsable est bien ajouté ! ", "/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getPersonnes("Responsables"),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
                    sendMail("Responsable",nom.trim(),prenom.trim(),mail.trim(),pwd);
                }else{
                    sa.showAlert("Ajout Erroné", "Le compte du responsable existe deja ! ", "/images/annuler.png");
                }
            }else{
                if(sa.showConfirmationAlert("Confirmation","Voulez-vous quand même ajouter le compte du responsable avec cette adresse e-mail invalide ? \nNB : Sinon, veuillez entrer une adresse e-mail valide au format 'xyz@gmail.com'. Si vous utilisez une adresse e-mail Gmail valide, vous pourrez contacter le personnel de l'entreprise directement depuis l'application.")){
                    if(e.insererPersonnel("Responsables",cin, nom, prenom, mail, pwd)){
                        sa.showAlert("Ajout avec succes", "Le compte du responsable est bien ajouté ! ", "/images/checked.png");
                        e.populateTableViewWithSelectionHandler(e.getPersonnes("Responsables"),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
                    }else{
                        sa.showAlert("Ajout Erroné", "Le compte du responsable existe deja ! ", "/images/annuler.png");
                    }
                }
            }
            viderTextFields(textFields);
        }
    }


    @FXML
    void ajouterGestio(ActionEvent event) {
        String cin = cinTextGestio.getText().trim();
        String nom = nomTextGestio.getText().trim();
        String prenom = prenomTextGestio.getText().trim();
        String mail = mailTextGestio.getText().trim();
        String pwd = pwdTextGestio.getText().trim();

        if(cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            if(EmailSender.isValidEmailAddress(mail)){
                if(e.insererPersonnel("Gestionnaires",cin, nom, prenom, mail, pwd)){
                    sa.showAlert("Ajout avec succes", "Le compte du gestionnaire est bien ajouté ! ", "/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getPersonnes("Gestionnaires"),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
                    sendMail("Gestionnaire",nom.trim(),prenom.trim(),mail.trim(),pwd);
                }else{
                    sa.showAlert("Ajout Erroné", "Le compte du gestionnaire existe deja ! ", "/images/annuler.png");
                }
            }else{
                if(sa.showConfirmationAlert("Confirmation","Voulez-vous quand même ajouter le compte du gestionnaire avec cette adresse e-mail invalide ? \nNB : Sinon, veuillez entrer une adresse e-mail valide au format 'xyz@gmail.com'. Si vous utilisez une adresse e-mail Gmail valide, vous pourrez contacter le personnel de l'entreprise directement depuis l'application.")){
                    if(e.insererPersonnel("Gestionnaires",cin, nom, prenom, mail, pwd)){
                        sa.showAlert("Ajout avec succes", "Le compte du gestionnaire est bien ajouté ! ", "/images/checked.png");
                        e.populateTableViewWithSelectionHandler(e.getPersonnes("Gestionnaires"),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
                    }else{
                        sa.showAlert("Ajout Erroné", "Le compte du gestionnaire existe deja ! ", "/images/annuler.png");
                    }
                }
            }
            viderTextFields(textFields);
        }
    }

    @FXML
    void ajouterVendeur(ActionEvent event) {
        String cin = cinTextVendeur.getText().trim();
        String nom = nomTextVendeur.getText().trim();
        String prenom = prenomTextVendeur.getText().trim();
        String mail = mailTextVendeur.getText().trim();
        String pwd = pwdTextVendeur.getText().trim();


        if(cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            if(EmailSender.isValidEmailAddress(mail)){
                if(e.insererPersonnel("Vendeurs",cin, nom, prenom, mail, pwd)){
                    sa.showAlert("Ajout avec succes", "Le compte du Vendeur est bien ajouté ! ", "/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getPersonnes("Vendeurs"),vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
                    sendMail("Vendeur",nom.trim(),prenom.trim(),mail.trim(),pwd);
                }else{
                    sa.showAlert("Ajout Erroné", "Le compte du Vendeur existe deja ! ", "/images/annuler.png");
                }
            }else{
                if(sa.showConfirmationAlert("Confirmation","Voulez-vous quand même ajouter le compte du vendeur avec cette adresse e-mail invalide ? \nNB : Sinon, veuillez entrer une adresse e-mail valide au format 'xyz@gmail.com'. Si vous utilisez une adresse e-mail Gmail valide, vous pourrez contacter le personnel de l'entreprise directement depuis l'application.")){
                    if(e.insererPersonnel("Vendeurs",cin, nom, prenom, mail, pwd)){
                        sa.showAlert("Ajout avec succes", "Le compte du Vendeur est bien ajouté ! ", "/images/checked.png");
                        e.populateTableViewWithSelectionHandler(e.getPersonnes("Vendeurs"),vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
                    }else{
                        sa.showAlert("Ajout Erroné", "Le compte du Vendeur existe deja ! ", "/images/annuler.png");
                    }
                }
            }
            viderTextFields(textFields);
        }
    }
    @FXML
    void supprimerRespo(ActionEvent event) {
        String cin = cinTextRespo.getText().trim();
        if(!cin.isEmpty()){
            if(sa.showConfirmationAlert("Confirmation de la suppression","Êtes-vous sûr de vouloir procéder à la suppression du responsable ?")){
                if(e.supprimerPersonnel("Responsables",cin)){
                    sa.showAlert("Suppression réussie","Le compte du responsable a été supprimé avec succès.","/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getPersonnes("Responsables"),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
                }else{
                    sa.showWarning("Suppression échouée","Le CIN saisi n'existe pas. Veuillez sélectionner un responsable valide dans le tableau avant de procéder à la suppression.");
                }
            }
            viderTextFields(textFields);
        }else{
            sa.showWarning("Suppression échouée","Veuillez sélectionner un responsable avant de procéder.");
        }
    }
    @FXML
    void supprimerGestio(ActionEvent event) {
        String cin = cinTextGestio.getText().trim();
        if(!cin.isEmpty()){
            if(sa.showConfirmationAlert("Confirmation de la suppression","Êtes-vous sûr de vouloir procéder à la suppression du Gestionnaire ?")){
                if(e.supprimerPersonnel("Gestionnaires",cin)){
                    sa.showAlert("Suppression réussie","Le compte du Gestionnaire a été supprimé avec succès.","/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getPersonnes("Gestionnaires"),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
                }else{
                    sa.showWarning("Suppression échouée","Le CIN saisi n'existe pas. Veuillez sélectionner un Gestionnaire valide dans le tableau avant de procéder à la suppression.");
                }
            }
            viderTextFields(textFields);
        }else{
            sa.showWarning("Suppression échouée","Veuillez sélectionner un Gestionnaire avant de procéder.");
        }
    }

    @FXML
    void supprimerVendeur(ActionEvent event) {
        String cin = cinTextVendeur.getText().trim();
        if(!cin.isEmpty()){
            if(sa.showConfirmationAlert("Confirmation de la suppression","Êtes-vous sûr de vouloir procéder à la suppression du Vendeur ?")){
                if(e.supprimerPersonnel("Vendeurs",cin)){
                    sa.showAlert("Suppression réussie","Le compte du Vendeur a été supprimé avec succès.","/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getPersonnes("Vendeurs"),vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
                }else{
                    sa.showWarning("Suppression échouée","Le CIN saisi n'existe pas. Veuillez sélectionner un Vendeur valide dans le tableau avant de procéder à la suppression.");
                }
            }
            viderTextFields(textFields);
        }else{
            sa.showWarning("Suppression échouée","Veuillez sélectionner un Vendeur avant de procéder.");
        }
    }

    @FXML
    void afficherRespo(ActionEvent event) {
        String cin = cinTextRespoRech.getText().trim();
        if(!cin.isEmpty()){
            ResultSet rs = e.afficherPersonnel("Responsables",cin);
            if(rs != null){
                e.populateTableViewWithSelectionHandler(rs,responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
            }else{
                sa.showWarning("Affichage échouée","Aucun Responsables avec ce CIN ");
            }
            viderTextFields(textFields);
        }else{
            sa.showWarning("Affichage échouée","Veuillez taper un cin avant de procéder.");
        }
    }
    @FXML
    void afficherGestio(ActionEvent event) {
        String cin = cinTextGestioRech.getText().trim();
        if(!cin.isEmpty()){
            ResultSet rs = e.afficherPersonnel("Gestionnaires", cin);
            if(rs != null){
                e.populateTableViewWithSelectionHandler(rs,gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
            }else{
                sa.showWarning("Affichage échouée","Aucun Gestionnaires avec ce CIN ");
            }
            viderTextFields(textFields);
        }else{
            sa.showWarning("Affichage échouée","Veuillez taper un cin avant de procéder.");
        }
    }

    @FXML
    void afficherVendeur(ActionEvent event) {
        String cin = cinTextVendeurRech.getText().trim();
        if (!cin.isEmpty()){
            ResultSet rs = e.afficherPersonnel("Vendeurs", cin);
            if(rs!= null){
                e.populateTableViewWithSelectionHandler(rs,vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
            }else{
                sa.showWarning("Affichage échouée","Aucun Vendeur avec ce CIN ");
            }
            viderTextFields(textFields);
        }else{
            sa.showWarning("Affichage échouée","Veuillez taper un cin avant de procéder.");
        }
    }
    @FXML
    void modifierRespo(ActionEvent event) {
            String cin = cinTextRespo.getText().trim();
            String nom = nomTextRespo.getText().trim();
            String prenom = prenomTextRespo.getText().trim();
            String mail = mailTextRespo.getText().trim();
            String pwd = pwdTextRespo.getText().trim();
            if(!cin.isEmpty()){
                if(e.modifierPersonnel("Responsables",nom,prenom,mail,pwd,cin)){
                    sa.showAlert("Modification réussie","Le compte du Responsable a été mis à jour avec succès.","/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getPersonnes("Responsables"),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
                }else{
                    sa.showWarning("Modification échouée","Une erreur s'est produite lors de la modification du compte du Responsable.\nNB : Vous n'avez pas le droit de modifier le CIN du compte du Responsable. \nSi vous souhaitez modifier le CIN, veuillez créer un nouveau compte avec le nouveau CIN.");
                }
                viderTextFields(textFields);
            }else{
                sa.showWarning("Modification échouée","Veuillez sélectionner un responsable et modifier ses informations ( SAUF LE CIN ) avant de procéder.");
            }
    }
    @FXML
    void modifierGestio(ActionEvent event) {
        String cin = cinTextGestio.getText().trim();
        String nom = nomTextGestio.getText().trim();
        String prenom = prenomTextGestio.getText().trim();
        String mail = mailTextGestio.getText().trim();
        String pwd = pwdTextGestio.getText().trim();
        if(!cin.isEmpty()){
            if(e.modifierPersonnel("Gestionnaires",nom,prenom,mail,pwd,cin)){
                sa.showAlert("Modification réussie","Le compte du Gestionnaire a été mis à jour avec succès.","/images/checked.png");
                e.populateTableViewWithSelectionHandler(e.getPersonnes("Gestionnaires"),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
            }else{
                sa.showWarning("Modification échouée","Une erreur s'est produite lors de la modification du compte du Gestionnaire.\nNB : Vous n'avez pas le droit de modifier le CIN du compte du Gestionnaire. \nSi vous souhaitez modifier le CIN, veuillez créer un nouveau compte avec le nouveau CIN.");
            }
            viderTextFields(textFields);
        }else{
            sa.showWarning("Modification échouée","Veuillez sélectionner un gestionnaire et modifier ses informations ( SAUF LE CIN ) avant de procéder.");
        }
    }
    @FXML
    void modifierVendeur(ActionEvent event) {
        String cin = cinTextVendeur.getText().trim();
        String nom = nomTextVendeur.getText().trim();
        String prenom = prenomTextVendeur.getText().trim();
        String mail = mailTextVendeur.getText().trim();
        String pwd = pwdTextVendeur.getText().trim();

        if(!cin.isEmpty()){
            if(e.modifierPersonnel("Vendeurs",nom,prenom,mail,pwd,cin)){
                sa.showAlert("Modification réussie","Le compte du Vendeur a été mis à jour avec succès.","/images/checked.png");
                e.populateTableViewWithSelectionHandler(e.getPersonnes("Vendeurs"),vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
            }else{
                sa.showWarning("Modification échouée","Une erreur s'est produite lors de la modification du compte du Vendeur.\nNB : Vous n'avez pas le droit de modifier le CIN du compte du Vendeur. \nSi vous souhaitez modifier le CIN, veuillez créer un nouveau compte avec le nouveau CIN.");
            }
            viderTextFields(textFields);
        }else{
            sa.showWarning("Modification échouée","Veuillez sélectionner un Vendeur et modifier ses informations ( SAUF LE CIN ) avant de procéder.");
        }
    }

    public void sendMail(String personnel, String nom, String prenom, String mailPersonnel, String pwdPersonnel){
            if(sa.showConfirmationAlert("Confirmation","Souhaitez-vous envoyer un e-mail au personnel contenant leurs informations d'authentification ?\nSi vous utilisez une adresse e-mail Gmail valide, vous pourrez contacter le personnel de l'entreprise directement depuis l'application.")){
                if(EmailSender.check()){
                    String objet = "Informations d'Authentification pour votre Compte sur l'application FacturEase";
                    String message = "Bonjour "+ nom+" "+prenom+",\n" +
                            "\n" +
                            "Nous sommes ravis de vous informer que votre compte pour le poste de" +
                            " ["+personnel+"] dans l'entreprise ["+e.getNomEntreprise()+"] a été créé avec succès. Veuillez trouver ci-dessous vos informations d'accès :\n\n"+
                            "1- Informations d'Authentification pour l'Application :\n" +
                            "Nom d'utilisateur : " + e.getAdresseMail()+"\n"+
                            "Mot de passe : " + e.getMotdepasse()+"\n" +
                            "\n\n" +
                            "2- Informations d'Authentification pour votre Compte Personnel :\n" +
                            "Nom d'utilisateur : " + mailPersonnel+"\n" +
                            "Mot de passe : "+pwdPersonnel+"\n\n"+
                            "Nous vous recommandons vivement de modifier votre mot de passe personnel dès votre première connexion pour des raisons de sécurité.\n" +
                            "\n" +
                            "N'hésitez pas à nous contacter en cas de questions ou de problèmes.\n" +
                            "\n" +
                            "Cordialement,\n" +
                            "L'équipe de FactureEase";
                    EmailSender.sendEmail(mailPersonnel, objet, message);
                    sa.showAlert("Envoi avec succes","L'email a été envoyé avec succes","/images/checked.png");
                }else{
                    sa.showWarning("Echec de l'envoi","Impossible d'envoyer l'e-mail au personnel. Veuillez vérifier votre connexion Internet et réessayer ultérieurement.");
                }
            }
    }

    @FXML
    void dashboardDirecteur(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        cw.switchWindow(event,"DirecteurDashboard.fxml");
    }


    public static void viderTextFields(ArrayList<TextField> textfields){
        for(javafx.scene.control.TextField textField : textfields){
            textField.setText("");
        }
    }
}

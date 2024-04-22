package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;

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
    private Button afficherRespo;



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


    Alerts sa = new Alerts();
    mouseEvents me = new mouseEvents();
    private Entreprise e = ControllerDashboardDirecteur.getEntreprise();


    public void initialize(){
        e.populateTableViewWithSelectionHandler(e.getResponsables(),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
        e.populateTableViewWithSelectionHandler(e.getGestionnaires(),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
        e.populateTableViewWithSelectionHandler(e.getVendeurs(), vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
    }
    //TODO : modifier(avec mail), afficher
    //TODO : finish onMouseEntered and exited
    //TODO : see WTF is going on with VendeurTableView
    @FXML
    void onMouseEnteredAfficherRespo(javafx.scene.input.MouseEvent event) {afficherRespo.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: white; -fx-background-radius: 5em;");}
    @FXML
    void onMouseExitedAfficherRespo(javafx.scene.input.MouseEvent event) {afficherRespo.setStyle("-fx-background-color: white; -fx-background-radius: 5em;");}
    @FXML
    void ajouterRespo(ActionEvent event) {
        String cin = cinTextRespo.getText();
        String nom = nomTextRespo.getText();
        String prenom = prenomTextRespo.getText();
        String mail = mailTextRespo.getText();
        String pwd = pwdTextRespo.getText();


        if(cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            if(EmailSender.isValidEmailAddress(mail)){
                if(e.insererRespo(cin, nom, prenom, mail, pwd)){
                    sa.showAlert("Ajout avec succes", "Le compte du responsable est bien ajouté ! ", "/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getResponsables(),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
                    sendMail("Responsable",nom.trim(),prenom.trim(),mail.trim(),pwd);
                }else{
                    sa.showAlert("Ajout Erroné", "Le compte du responsable existe deja ! ", "/images/annuler.png");
                }
            }else{
                if(sa.showConfirmationAlert("Confirmation","Voulez-vous quand même ajouter le compte du responsable avec cette adresse e-mail invalide ? \nNB : Sinon, veuillez entrer une adresse e-mail valide au format 'xyz@gmail.com'. Si vous utilisez une adresse e-mail Gmail valide, vous pourrez contacter le personnel de l'entreprise directement depuis l'application.")){
                    if(e.insererRespo(cin, nom, prenom, mail, pwd)){
                        sa.showAlert("Ajout avec succes", "Le compte du responsable est bien ajouté ! ", "/images/checked.png");
                        e.populateTableViewWithSelectionHandler(e.getResponsables(),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
                    }else{
                        sa.showAlert("Ajout Erroné", "Le compte du responsable existe deja ! ", "/images/annuler.png");
                    }
                }
            }
        }
    }


    @FXML
    void ajouterGestio(ActionEvent event) {
        String cin = cinTextGestio.getText();
        String nom = nomTextGestio.getText();
        String prenom = prenomTextGestio.getText();
        String mail = mailTextGestio.getText();
        String pwd = pwdTextGestio.getText();

        if(cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            if(EmailSender.isValidEmailAddress(mail)){
                if(e.insererGestio(cin, nom, prenom, mail, pwd)){
                    sa.showAlert("Ajout avec succes", "Le compte du gestionnaire est bien ajouté ! ", "/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getGestionnaires(),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
                    sendMail("Gestionnaire",nom.trim(),prenom.trim(),mail.trim(),pwd);
                }else{
                    sa.showAlert("Ajout Erroné", "Le compte du gestionnaire existe deja ! ", "/images/annuler.png");
                }
            }else{
                if(sa.showConfirmationAlert("Confirmation","Voulez-vous quand même ajouter le compte du gestionnaire avec cette adresse e-mail invalide ? \nNB : Sinon, veuillez entrer une adresse e-mail valide au format 'xyz@gmail.com'. Si vous utilisez une adresse e-mail Gmail valide, vous pourrez contacter le personnel de l'entreprise directement depuis l'application.")){
                    if(e.insererGestio(cin, nom, prenom, mail, pwd)){
                        sa.showAlert("Ajout avec succes", "Le compte du gestionnaire est bien ajouté ! ", "/images/checked.png");
                        e.populateTableViewWithSelectionHandler(e.getGestionnaires(),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
                    }else{
                        sa.showAlert("Ajout Erroné", "Le compte du gestionnaire existe deja ! ", "/images/annuler.png");
                    }
                }
            }
        }
    }

    @FXML
    void ajouterVendeur(ActionEvent event) {
        String cin = cinTextVendeur.getText();
        String nom = nomTextVendeur.getText();
        String prenom = prenomTextVendeur.getText();
        String mail = mailTextVendeur.getText();
        String pwd = pwdTextVendeur.getText();


        if(cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            if(EmailSender.isValidEmailAddress(mail)){
                if(e.insererVendeur(cin, nom, prenom, mail, pwd)){
                    sa.showAlert("Ajout avec succes", "Le compte du Vendeur est bien ajouté ! ", "/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getVendeurs(),vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
                    sendMail("Vendeur",nom.trim(),prenom.trim(),mail.trim(),pwd);
                }else{
                    sa.showAlert("Ajout Erroné", "Le compte du Vendeur existe deja ! ", "/images/annuler.png");
                }
            }else{
                if(sa.showConfirmationAlert("Confirmation","Voulez-vous quand même ajouter le compte du vendeur avec cette adresse e-mail invalide ? \nNB : Sinon, veuillez entrer une adresse e-mail valide au format 'xyz@gmail.com'. Si vous utilisez une adresse e-mail Gmail valide, vous pourrez contacter le personnel de l'entreprise directement depuis l'application.")){
                    if(e.insererVendeur(cin, nom, prenom, mail, pwd)){
                        sa.showAlert("Ajout avec succes", "Le compte du Vendeur est bien ajouté ! ", "/images/checked.png");
                        e.populateTableViewWithSelectionHandler(e.getVendeurs(),vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
                    }else{
                        sa.showAlert("Ajout Erroné", "Le compte du Vendeur existe deja ! ", "/images/annuler.png");
                    }
                }
            }
        }
    }

    @FXML
    void supprimerRespo(ActionEvent event) {
        String cin = cinTextRespo.getText();
        if(!cin.isEmpty()){
            if(sa.showConfirmationAlert("Confirmation de la suppression","Êtes-vous sûr de vouloir procéder à la suppression du responsable ?")){
                if(e.supprimerResponsable(cin)){
                    sa.showAlert("Suppression réussie","Le compte du responsable a été supprimé avec succès.","/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getResponsables(),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
                }else{
                    sa.showWarning("Suppression échouée","Le CIN saisi n'existe pas. Veuillez sélectionner un responsable valide dans le tableau avant de procéder à la suppression.");
                }
            }
        }else{
            sa.showWarning("Suppression échouée","Veuillez sélectionner un responsable avant de procéder.");
        }
    }
    @FXML
    void supprimerGestio(ActionEvent event) {
        String cin = cinTextGestio.getText();
        if(!cin.isEmpty()){
            if(sa.showConfirmationAlert("Confirmation de la suppression","Êtes-vous sûr de vouloir procéder à la suppression du Gestionnaire ?")){
                if(e.supprimerGestionnaire(cin)){
                    sa.showAlert("Suppression réussie","Le compte du Gestionnaire a été supprimé avec succès.","/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getGestionnaires(),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
                }else{
                    sa.showWarning("Suppression échouée","Le CIN saisi n'existe pas. Veuillez sélectionner un Gestionnaire valide dans le tableau avant de procéder à la suppression.");
                }
            }
        }else{
            sa.showWarning("Suppression échouée","Veuillez sélectionner un Gestionnaire avant de procéder.");
        }
    }

    @FXML
    void supprimerVendeur(ActionEvent event) {
        String cin = cinTextVendeur.getText();
        if(!cin.isEmpty()){
            if(sa.showConfirmationAlert("Confirmation de la suppression","Êtes-vous sûr de vouloir procéder à la suppression du Vendeur ?")){
                if(e.supprimerVendeur(cin)){
                    sa.showAlert("Suppression réussie","Le compte du Vendeur a été supprimé avec succès.","/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getVendeurs(),vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
                }else{
                    sa.showWarning("Suppression échouée","Le CIN saisi n'existe pas. Veuillez sélectionner un Vendeur valide dans le tableau avant de procéder à la suppression.");
                }
            }
        }else{
            sa.showWarning("Suppression échouée","Veuillez sélectionner un Vendeur avant de procéder.");
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
}

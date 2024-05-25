package com.example.pfeprojet.Entreprise;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EntrepriseTest {

    //TEST METHOD CALUCLTOTALTTC
        @Test
        void testNONtvaNONremise(){
            Entreprise e = new Entreprise();
            assertEquals(10,e.CalculTotalTTC(10,0,0));
        }
        @Test
        void testNONremiseOUItva(){
            Entreprise e = new Entreprise();
            assertEquals(12, e.CalculTotalTTC(10, 0, 20));
        }
        @Test
        void testNONtvaOUIremise(){
            Entreprise e = new Entreprise();
            assertEquals(8,e.CalculTotalTTC(10, 20, 0));
        }
        @Test
        void testOUItvaOUIremise(){
            Entreprise e = new Entreprise();
            assertEquals(8.4,e.CalculTotalTTC(10,30,20));
        }
        @Test
        void testRemiseNONVALIDE(){
            Entreprise e = new Entreprise();
            assertEquals(0,e.CalculTotalTTC(10,-1,20));
        }
        @Test
        void testTvaNONVALIDE(){
            Entreprise e = new Entreprise();
            assertEquals(0,e.CalculTotalTTC(10,30,-20));
        }
        @Test
        void testTotalNONVALIDE(){
            Entreprise e = new Entreprise();
            assertEquals(0,e.CalculTotalTTC(0,30,20));
            assertEquals(0,e.CalculTotalTTC(-10,30,20));
        }
        @Test
        void testTvaEtRemiseEtTotalNONVALIDE(){
            Entreprise e = new Entreprise();
            assertEquals(0,e.CalculTotalTTC(-10,-30,-20));
        }


    //TEST METHOD setInfosEntreprise
        @Test
        void testSetInfosEntreprise(){
            Entreprise e = new Entreprise("lisg");
            e.setInfosEntreprise();
            System.out.println(e);
            assertEquals("fdfkjdf@fdfji.fdf",e.getAdresseMail());
            assertEquals("lko", e.getLocalisation());
            assertEquals("0987654321", e.getNumeroDeFax());
            assertEquals("lkol", e.getSecteurDactivite());
            assertEquals("lko", e.getIdentificationFiscale());
        }



    //TEST METHOD getChiffreAffaireMois
        @Test
        void testGetChiffreAffaireMois1(){
            Entreprise e = new Entreprise("hennesy_liquor_store");
            assertEquals(32262.50,e.getChiffreAffaireMois(4));
        }
        @Test
        void testGetChiffreAffaireMois2(){
            Entreprise e = new Entreprise("hennesy_liquor_store");
            assertEquals(9850.87,e.getChiffreAffaireMois(3));
        }
        @Test
        void testGetChiffreAffaireMois3(){
            Entreprise e = new Entreprise("hennesy_liquor_store");
            assertEquals(0,e.getChiffreAffaireMois(2));
        }


    //TEST METHOD getChiffreAffaireAnnee
        @Test
        void testGetChiffreAffaireAnnee1(){
            Entreprise e = new Entreprise("hennesy_liquor_store");
            assertEquals(42113.37,e.getChiffreAffaireAnnee(2024));
        }
        @Test
        void testGetChiffreAffaireAnnee2(){
            Entreprise e = new Entreprise("hennesy_liquor_store");
            assertEquals(0,e.getChiffreAffaireAnnee(3));
        }
        @Test
        void testGetChiffreAffaireAnnee3(){
            Entreprise e = new Entreprise("hennesy_liquor_store");
            assertEquals(0,e.getChiffreAffaireAnnee(3));
        }


        //
    //TestInsertPersonnel
        @Test
        void testInsertPerso(){
            Entreprise e = new Entreprise("hennesy_liquor_store");
            e.insererPersonnel("Vendeurs","lll","lll","lll","lll","lll");
            e.insererPersonnel("Responsables","lll","lll","lll","lll","lll");
            e.insererPersonnel("Gestionnaires","lll","lll","lll","lll","lll");

        }
    //TestSupprPersonnel
        @Test
        void testSupprimerPerso(){
            Entreprise e = new Entreprise("hennesy_liquor_store");
            e.supprimerPersonnel("Responsables","lll");
            e.supprimerPersonnel("Gestionnaires","lll");
            e.supprimerPersonnel("Vendeurs","lll");
        }
    //TestModifierPersonnel
        @Test
        void testModifierPerso(){
            Entreprise e = new Entreprise("hennesy_liqour");
            e.modifierPersonnel("Responsables","kkk","kkk","kkk","kkk","fc9090");
            e.modifierPersonnel("Gestionnaires","kkk","kkk","kkk","kkk","12ZS");
            e.modifierPersonnel("Vendeurs","kkk","kkk","kkk","kkk","sdfd");

        }


    //TestAjouterProduit
        @Test
        void testAjouterProduit(){
            Entreprise e = new Entreprise("dolce");
            e.ajouterProduit(16,230.50,150,"chageur","2015-01-01","TEst","Electronique");
        }
        @Test
        void testAjouterProduit1(){
            Entreprise e = new Entreprise("hennesy_liqour");
            e.ajouterProduit(56,100,180,"Changeur","2024-08-29","","");
        }
        @Test
        void testAjouterProduit2(){
        Entreprise e = new Entreprise("maroc_telecom");
        e.ajouterProduit(4,100,180,"Changeur","","","");
    }

    //TestSupprimerProduit
        @Test
        void testSupprimerProduit(){
            Entreprise e = new Entreprise("hennesy_liqour");
            e.supprimerProduit(1);
        }

    //TestAjouterService
    @Test
    void testAjouterService(){
        Entreprise e = new Entreprise("maroc_telecom");
        e.ajouterService(1,230.50,"Testeur","","testeur");
    }

    //TestModifierProduit
    @Test
    void testModifierProduit(){
            Entreprise e = new Entreprise("hennesy_liqour");
            e.modifierProduit(4,200.90,15,"","2024-08-29","","");
    }

    //TestModifierService
    @Test
    void testModifierService(){
        Entreprise e = new Entreprise("maroc_telecom");
        e.modifierService(1,150," ","","testeurModifier");
    }


    //TestGetIdProduits
    @Test
    void testGetIdProduits(){
        Entreprise e = new Entreprise("hennesy_liqour");
        System.out.println(e.getIdProduits().toString());
    }

    //TestGetInfosProduits
    @Test
    void testGetInfosProduits(){
        Entreprise e = new Entreprise("maroc_telecom");
        System.out.println("Colonnes de la table service sont : " +e.getColonnesTable("service"));
        if(e.getColonnesTable("Service").contains("LibelleService")){
            System.out.println("hennesy_liqour.produit has column libelleService in it");
        }else{
            System.out.println("hennesy_liqour.produit hasnt column libelleService in it");
        }
    }
    @Test
    void testCheckQuantite(){
        Entreprise e = new Entreprise("dolce");
        String[] infosProduit = e.getInfosProduit(4);
        System.out.println("Prix unitaire du produit est : " + infosProduit[1]);
        if(13>Integer.parseInt(infosProduit[2])){
            System.out.println("Quantité non disponible, Quantite maximal disponible est : " + infosProduit[2]);
        }else{
            System.out.println("quantite disponible");
        }
    }

    @Test
    void testInsererFactureProduit(){
        Entreprise e = new Entreprise("WINSTON");
        if(e.insererFactureProduit(2,"FC59627",2,10)){
            System.out.println("Facture ajouté");
        }else{
            System.out.println("id existe deja dans la facture");
        }
    }

    @Test
    void testInsererFactureProduitService(){
        Entreprise e = new Entreprise("winston");
        if(e.insererFactureService(2,"FC59627",1,1)){
            System.out.println("Facture ajouté");
        }else{
            System.out.println("id existe deja dans la facture");
        }
    }

    //TESTCOLONNESCLIENT
    @Test
    void testGetColonnesClient(){
            Entreprise e = new Entreprise("iam_maroc_telecom");
            System.out.println(e.getColonnesTable("client"));
    }


    //TestCheckTvaRemise
    @Test
    void test_NON_Remise_Tva() throws SQLException {
        Entreprise e = new Entreprise("iam_maroc_telecom");
        assertEquals(-1, e.checkTva_Remise());
    }

    @Test
    void test_Check_Remise() throws SQLException {
        Entreprise e = new Entreprise("winston");
        assertEquals(0, e.checkTva_Remise());
    }

    @Test
    void testCheck_Remise_Tva() throws SQLException {
        Entreprise e = new Entreprise("winston");
        assertEquals(2, e.checkTva_Remise());
    }


    @Test
    void test_Check_Tva() throws SQLException {
        Entreprise e = new Entreprise("winston");
        assertEquals(1, e.checkTva_Remise());
    }



    //testAjouterFacture
    @Test
    void test_Ajouter_Facture_non_Tva_no_Remise() throws SQLException {
        Entreprise e = new Entreprise("iam_maroc_telecom");
        e.ajouterFacture(0,0,1, e.CalculTotalTTC(4000,0,0));
        }

    @Test
    void test_Ajouter_Facture_non_Tva_yes_Remise() throws SQLException {
        Entreprise e = new Entreprise("winston");
        e.ajouterFacture(50,20,1, e.CalculTotalTTC(e.getTotalFacture(1),50,20));
    }
}
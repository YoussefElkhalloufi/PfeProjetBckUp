package com.example.pfeprojet.Entreprise;

import org.junit.jupiter.api.Test;

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
}
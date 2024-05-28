package com.example.pfeprojet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnexionTest {


    @Test
    void test(){
        Connexion c = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");
        String query = "UPDATE infosentreprises" +
                " SET nomDirecteur = 'youssef'" +
                "where nomEntreprise = 'oujda'";
        boolean s = c.miseAjour(query);
        System.out.println(s);
        assertEquals(true,s);
    }

}
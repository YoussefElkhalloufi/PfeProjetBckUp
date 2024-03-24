package com.example.pfeprojet.Controllers;


import javafx.fxml.FXML;
import javafx.scene.shape.Arc;
import javafx.scene.shape.CubicCurve;

public class testController {

    @FXML
    private Arc arc;

    @FXML
    private CubicCurve curve;


    @FXML
    public void btn2(){
        curve.setVisible(true);
        arc.setVisible(false);
    }

    @FXML
    public void btn3(){
        curve.setVisible(false);
        arc.setVisible(true);
    }

}

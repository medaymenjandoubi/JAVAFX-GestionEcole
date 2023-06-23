/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.gui;

import com.esprit.entities.SessionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author abdel
 */
public class DetailsCandidatController implements Initializable {

    @FXML
    private Label lbNom;
    @FXML
    private Label lbPrenom;
    @FXML
    private Label lbTel;
    @FXML
    private Label lbMail;
    @FXML
    private Label lbDateNaissance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String username = SessionManager.getLoggedInUsername();
        String role = SessionManager.getLoggedInUserRole();
    }
    
    public void setLbNom(String nom) {
        lbNom.setText(nom);
    }
    
    public void setLbPrenom(String prenom) {
        lbPrenom.setText(prenom);
    }
     public void setLbDateNaissance(String date_de_naissance) {
        lbDateNaissance.setText(date_de_naissance);
    }
    
    public void setLbMail(String mail) {
        lbMail.setText(mail);
    }
     public void setLbTel(String tel) {
        lbTel.setText(tel);
    }
    
    
}

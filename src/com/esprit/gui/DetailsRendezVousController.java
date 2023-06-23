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
 * @author arabaoui
 */
public class DetailsRendezVousController implements Initializable {

  
    @FXML
    private Label lbdate_rdv;
    @FXML
    private Label lbresultat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String username = SessionManager.getLoggedInUsername();
        String role = SessionManager.getLoggedInUserRole();
        // TODO
         
        // TODO
    
    
    }
    
    
     public void setLbResultat(String resultat) {
        lbresultat.setText(resultat);
    }
     public void setLbDate_rdv(String date_rdv) {
        lbdate_rdv.setText(date_rdv);
    }
    
  
    
    
    
   
    
}

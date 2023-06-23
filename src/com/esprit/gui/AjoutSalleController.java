/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.gui;

import com.esprit.entities.SessionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author arabaoui
 */
public class AjoutSalleController implements Initializable {

    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfCapacite;
    @FXML
    private TextField tfType;
    @FXML
    private TableView<?> Table_Salle;
    @FXML
    private TableColumn<?, ?> c_numero;
    @FXML
    private TableColumn<?, ?> c_capacite;
    @FXML
    private TableColumn<?, ?> c_type;
    @FXML
    private TextField chercher;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String username = SessionManager.getLoggedInUsername();
        String role = SessionManager.getLoggedInUserRole();
    }    

    @FXML
    private void ajouterSalle(ActionEvent event) {
    }

    @FXML
    private void supprimer(ActionEvent event) {
    }

    @FXML
    private void modifier(ActionEvent event) {
    }

    @FXML
    private void chercher_salle(ActionEvent event) {
    }
    
}

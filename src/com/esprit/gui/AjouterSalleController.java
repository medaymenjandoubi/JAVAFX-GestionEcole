package com.esprit.gui;

import com.esprit.entities.Salle;
import com.esprit.entities.SessionManager;
import com.esprit.services.SalleService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AjouterSalleController implements Initializable {

    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfCapacite;
    @FXML
    private TextField tfType;
    @FXML
    private TableView<Salle> Table_Salle;
    @FXML
    private TableColumn<Salle, Integer> c_numero;
    @FXML
    private TableColumn<Salle, Integer> c_capacite;
    @FXML
    private TableColumn<Salle, String> c_type;
    @FXML
    private TextField chercher;

 
@Override
    public void initialize(URL url, ResourceBundle rb) {
        String username = SessionManager.getLoggedInUsername();
        String role = SessionManager.getLoggedInUserRole();
        c_numero.setCellValueFactory(new PropertyValueFactory<>("numero_salle"));
        c_capacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        c_type.setCellValueFactory(new PropertyValueFactory<>("type_salle"));

        SalleService salleService = new SalleService();
        List<Salle> salles = salleService.afficher();
        Table_Salle.getItems().addAll(salles);  

    }    

    @FXML//methode ajouter +controle saisi OK
    private void ajouterSalle(ActionEvent event) {
        
    String numeroText = tfNumero.getText();
    String capaciteText = tfCapacite.getText();
    String type = tfType.getText();

    if (numeroText.isEmpty() || capaciteText.isEmpty() || type.isEmpty()) {
        // Afficher un message d'erreur indiquant que tous les champs doivent être remplis
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs.");
        alert.showAndWait();
        return;
    }

    int numero;
    int capacite;
    try {
        numero = Integer.parseInt(numeroText);
        capacite = Integer.parseInt(capaciteText);
    } catch (NumberFormatException e) {
        // Afficher un message d'erreur indiquant que les champs numériques doivent être des entiers valides
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Les champs 'Numéro' et 'Capacité' doivent être des entiers valides.");
        alert.showAndWait();
        return;
    }

    // ... le reste du code pour l'ajout de la salle
        SalleService sp = new SalleService();
        sp.ajouter(new Salle(Integer.parseInt(tfNumero.getText()),Integer.parseInt(tfCapacite.getText()), tfType.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification réussie");
        alert.setHeaderText(null);
        alert.setContentText("La salle a été Ajouté avec succès !");
        alert.showAndWait();
    
         rafraichirTableView(); 
        clearFields();
    }

    @FXML //methode verfier ok
    private void supprimer(ActionEvent event) {
           Salle selectedSalle = Table_Salle.getSelectionModel().getSelectedItem();
    if (selectedSalle != null) {
        SalleService salleService = new SalleService();
        salleService.supprimer(selectedSalle);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression réussie");
        alert.setHeaderText(null);
        alert.setContentText("La salle a été supprimée avec succès !");
        alert.showAndWait();

        rafraichirTableView();
        clearFields();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une salle à supprimer.");
        alert.showAndWait();
    }

    }

    @FXML
    private void modifier(ActionEvent event) {
                int numero_salle = Integer.parseInt(tfNumero.getText());
                int capacite = Integer.parseInt(tfCapacite.getText());
                String type_salle = tfType.getText();

            SalleService salleService= new SalleService();

            Salle salleAModifier = new Salle (numero_salle, capacite, type_salle);
            salleService.modifier(salleAModifier);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("La salle a été modifiée avec succès !");
            alert.showAndWait();
            rafraichirTableView();
            clearFields();
          }

    @FXML
    private void chercher_salle(ActionEvent event) {
        
        Integer numero_salle = Integer.parseInt(chercher.getText());
        SalleService salleService = new SalleService();
        List<Salle> resultats = salleService.rechercherParNumero(numero_salle);
        Table_Salle.setItems(FXCollections.observableArrayList(resultats));
    }
 

    
    private void rafraichirTableView() {
            c_numero.setCellValueFactory(new PropertyValueFactory<>("numero_salle"));
            c_capacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
            c_type.setCellValueFactory(new PropertyValueFactory<>("type_salle"));

            SalleService salleService = new SalleService();
            List<Salle> salles = salleService.afficher();

            Table_Salle.getItems().clear(); // Supprimer les éléments existants dans la TableView
            Table_Salle.getItems().addAll(salles); // Ajouter les nouvelles classes à la TableView
    }
    
      
    private void clearFields() 
            {
            tfNumero.setText("");
            tfCapacite.setText("");
            tfType.setText("");
}
}
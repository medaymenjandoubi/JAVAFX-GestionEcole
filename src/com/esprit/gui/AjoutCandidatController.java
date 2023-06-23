package com.esprit.gui;



import com.esprit.entities.Candidat;
import com.esprit.services.ServiceCandidat;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import java.sql.Date;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;



public class AjoutCandidatController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private DatePicker tfDateNaissance;
    @FXML
    private TextField tfMail;
    @FXML
    private TextField tfTel;
    @FXML
    private TableView<Candidat> tableViewCandidats;
    @FXML
    private TableColumn<Candidat, String> columnNom;
    @FXML
    private TableColumn<Candidat, String> columnPrenom;
    @FXML
    private TableColumn<Candidat, Date> columnDateNaissance;
    @FXML
    private TableColumn<Candidat, String> columnMail;
    @FXML
    private TableColumn<Candidat, Integer> columnTel;

    private ServiceCandidat serviceCandidat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serviceCandidat = new ServiceCandidat();

        // Configure table columns
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnDateNaissance.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        columnMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        columnTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    

        // Load data into the table view
        chargerDonneesTableau();
    }

    private void chargerDonneesTableau() {
        try {
            // Retrieve the list of candidates from the service
            List<Candidat> candidats = serviceCandidat.afficher();

            // Create an ObservableList from the list of candidates
            ObservableList<Candidat> observableList = FXCollections.observableArrayList(candidats);

            // Display the data in the TableView
            tableViewCandidats.setItems(observableList);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle the exception
        }
    }
   
    
    



    @FXML
 private void ajouterCandidat(ActionEvent event) throws IOException {
    if (tfDateNaissance.getValue() != null) {
        LocalDate dateNaissance = tfDateNaissance.getValue();
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String mail = tfMail.getText();
        String tel = tfTel.getText();

        if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || tel.isEmpty()) {
            showAlert(AlertType.ERROR, "Veuillez remplir tous les champs !");
        } else if (!mail.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            showAlert(AlertType.ERROR, "L'adresse e-mail n'est pas valide !");
        } else if (!tel.matches("^[9524]\\d{7}$")) {
            showAlert(AlertType.ERROR, "Le numéro de téléphone n'est pas valide !");
        } else {
            serviceCandidat.ajouter(new Candidat(
                    nom,
                    prenom,
                    Date.valueOf(dateNaissance),
                    mail,
                    Integer.parseInt(tel)
            ));

            showAlert(AlertType.INFORMATION, "Candidat ajouté !");

            // Clear the text fields
            tfNom.clear();
            tfPrenom.clear();
            tfDateNaissance.setValue(null);
            tfMail.clear();
            tfTel.clear();

            // Refresh the table view
            chargerDonneesTableau();
        }
    } else {
        showAlert(AlertType.ERROR, "Veuillez sélectionner une date de naissance !");
    }
}

  
    @FXML
private void modifierCandidat(ActionEvent event) {
    Candidat candidat = tableViewCandidats.getSelectionModel().getSelectedItem();
    if (candidat != null) {
        candidat.setPrenom(tfPrenom.getText());
        candidat.setDate_de_naissance(Date.valueOf(tfDateNaissance.getValue()));
        candidat.setMail(tfMail.getText());
        candidat.setTel(Integer.parseInt(tfTel.getText()));
        serviceCandidat.modifier(candidat);
        showAlert(AlertType.INFORMATION, "Candidat modifié !");
        // Refresh the table view
        chargerDonneesTableau();
    } else {
        showAlert(AlertType.ERROR, "Aucun candidat sélectionné !");
    }
}


    @FXML
    private void rechercherCandidat(ActionEvent event) {
        String nom = tfNom.getText();
        Candidat candidat = serviceCandidat.rechercherParNom(nom);
        if (candidat != null) {
            tfPrenom.setText(candidat.getPrenom());
//            tfDateNaissance.setValueOf(candidat.getDate_de_naissance());
            tfMail.setText(candidat.getMail());
            tfTel.setText(String.valueOf(candidat.getTel()));
        } else {
            showAlert(AlertType.ERROR, "Candidat non trouvé !");
        }
    }

    @FXML
 
private void supprimerCandidat(ActionEvent event) {
    Candidat candidat = tableViewCandidats.getSelectionModel().getSelectedItem();
    if (candidat != null) {
        serviceCandidat.supprimer(candidat); // Correction de l'appel de méthode
        showAlert(AlertType.INFORMATION, "Candidat supprimé !");
        // Refresh the table view
        chargerDonneesTableau();
    } else {
        showAlert(AlertType.ERROR, "Aucun candidat sélectionné !");
    }
}


    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
   private void ajouterRendezvous(ActionEvent event) throws IOException {
        // Code pour ajouter un rendez-vous ici...

        // Exemple de redirection vers une autre interface graphique (AjoutRendezVous.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjoutRendezVous.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Gestion des rendez-vous");
        stage.show();
        
          
    }

    // Autres variables et méthodes existantes



    
    

    @FXML
    private void ajouterresultat(ActionEvent event) throws IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjoutRendezVous.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Gestion des rendez-vous");
        stage.show();

}
}
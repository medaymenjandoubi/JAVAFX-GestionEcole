/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.gui;

import com.esprit.entities.Administrateur;
import com.esprit.entities.Eleve;
import com.esprit.entities.Enseignant;
import com.esprit.entities.SessionManager;
import com.esprit.entities.User;
import static com.esprit.entities.User.u;
import com.esprit.services.ServiceUser;
import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

public class CrudUser1Controller implements Initializable {
   
    private ServiceUser su = new ServiceUser();
    
 
    @FXML
    private TextField TFNom;
    @FXML
    private TextField TFPrenom;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField TFTel;
    @FXML
    private TextField TFLogin;
    @FXML
    private TextField TFPassword;
    @FXML
    private TextField TFMatricule;
    @FXML
    private TextField TFNcarte;
    @FXML
    private TableView<User> TableUser;
    @FXML
    private TableColumn<User, String> NomCol;
    @FXML
    private TableColumn<User, String> PrenomCol;
    @FXML
    private TableColumn<User, Date> DateCol;
    @FXML
    private TableColumn<User, String> MailCol;
    @FXML
    private TableColumn<User, Integer> TelCol;
    @FXML
    private TableColumn<User, String> LoginCol;
    @FXML
    private TableColumn<User, String> PasswordCol;
    @FXML
    private TableColumn<User, String> RolCol;
    @FXML
    private TableColumn<Enseignant, Integer> MatCol;
    @FXML
    private TableColumn<Eleve, Integer> NCarteCol;
    @FXML
    private TextField tfemail;
    @FXML
    private ComboBox<String> cbrole;
    @FXML
    private TextField TFNomRecherche;
    @FXML
    private Button rechercherParNom;
    
        

    @FXML
    private Button BAjouterUser;
    @FXML
    private Button BModifierUser;
    @FXML
    private Button BSupprimerUser;
    
           
        @Override
    public void initialize(URL url, ResourceBundle rb) {
       String username = SessionManager.getLoggedInUsername();
       String role = SessionManager.getLoggedInUserRole();
                TFMatricule.setDisable(true);
        TFNcarte.setDisable(true);

        cbrole.getItems().addAll("Administrateur", "Enseignant", "Eleve");
   
   
        RefreshTable();
    }

    @FXML
    private void AjouterUser(ActionEvent event) {
        String nom = TFNom.getText();
        String prenom = TFPrenom.getText();
        Date date = Date.valueOf(datePicker.getValue());
        String mail = tfemail.getText();
        int tel = Integer.valueOf(TFTel.getText());
        String login = TFLogin.getText();
        String password = TFPassword.getText();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || TFTel.getText().isEmpty() || login.isEmpty() || password.isEmpty()) {
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        } else if (!mail.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {

            alert.setTitle("mail n'est pas valide !");
            alert.setHeaderText(null);
            alert.setContentText("L'adresse e-mail n'est pas valide !");
            alert.showAndWait();
            return;
        }
         // Vérification de l'unicité de l'adresse e-mail
            else if (emailExists(mail)) {
    alert.setTitle("Mail déjà utilisé !");
    alert.setHeaderText(null);
    alert.setContentText("L'adresse e-mail est déjà associée à un compte !");
    alert.showAndWait();
    return;
}
        else if (!TFTel.getText().matches("\\d{8}")) {

            alert.setTitle("Numéro de téléphone invalide !");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un numéro de téléphone valide composé de 8 chiffres.");
            alert.showAndWait();
            return;
        }
        // Vérifier le rôle sélectionné et créer l'utilisateur correspondant
        if (cbrole.getValue().equals("Administrateur")) {

            User u = new Administrateur(nom, prenom, date, mail, tel, login, password);
            u.setRole("Administrateur");
            su.ajouter(u);

        }

        if (cbrole.getValue().equals("Enseignant")) {

            TFMatricule.setDisable(false);

            if (TFMatricule.getText().isEmpty()) {
                alert.setTitle("Champs vides");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }
            if (!TFMatricule.getText().matches("\\d{4}")) {

                alert.setTitle("Matricule invalide !");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un numéro de Matricule valide composé de 4 chiffres.");
                alert.showAndWait();
                return;
            }

            int mat = Integer.valueOf(TFMatricule.getText());
            User u = new Enseignant(mat, nom, prenom, date, mail, tel, login, password);
            u.setRole("Enseignant");

            su.ajouter(u);
            TFMatricule.clear();

            TFMatricule.setDisable(true);

            RefreshTable();

        }

        if (cbrole.getValue().equals("Eleve")) {
            TFNcarte.setDisable(false);

            if (TFNcarte.getText().isEmpty()) {
                alert.setTitle("Champs vides");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }
            if (!TFNcarte.getText().matches("\\d{4}")) {

                alert.setTitle("N°Carte invalide !");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un numéro de N°Carte valide composé de 4 chiffres.");
                alert.showAndWait();
                return;
            }

            int ncart = Integer.valueOf(TFNcarte.getText());
            User u = new Eleve(ncart, nom, prenom, date, mail, tel, login, password);
            u.setRole("Eleve");
            su.ajouter(u);
            TFNcarte.clear();

            TFNcarte.setDisable(true);

            RefreshTable();

        }

        RefreshTable();
    }

    @FXML

    private void SupprimerUser(ActionEvent event) {
        User selectedUser = TableUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int id = selectedUser.getId_user();
            su.supprime1(id);

            RefreshTable();
        }
    }

    @FXML
    private void ModifierUser(ActionEvent event) {
        User selectedUser = TableUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String nom = TFNom.getText();
            String prenom = TFPrenom.getText();
            Date date = Date.valueOf(datePicker.getValue());
            String mail = tfemail.getText();
            int tel = Integer.valueOf(TFTel.getText());
            String login = TFLogin.getText();
            String password = TFPassword.getText();
            String role = cbrole.getValue();

            selectedUser.setNom(nom);
            selectedUser.setPrenom(prenom);
            selectedUser.setDateNaissance(date);
            selectedUser.setMail(mail);
            selectedUser.setTel(tel);
            selectedUser.setLogin(login);
            selectedUser.setPassword(password);
            selectedUser.setRole(role);
     
        
            su.modifier(selectedUser);
        
            RefreshTable();
            clearFields();
            
        }
    }

    @FXML
    private void rechercherParNom(ActionEvent event) {
        String nom = TFNomRecherche.getText();
        ObservableList<User> userList = FXCollections.observableArrayList(su.rechercherParNom(nom));
        TableUser.setItems(userList);
    }

    @FXML
    private void getselected(MouseEvent event) {
        User selectedUser = TableUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            TFNom.setText(selectedUser.getNom());
            TFPrenom.setText(selectedUser.getPrenom());
            datePicker.setValue(selectedUser.getDateNaissance().toLocalDate());
            tfemail.setText(selectedUser.getMail());
            TFTel.setText(String.valueOf(selectedUser.getTel()));
            TFLogin.setText(selectedUser.getLogin());
            TFPassword.setText(selectedUser.getPassword());
            cbrole.setValue(selectedUser.getRole());
        }
    }

    private void RefreshTable() {
        ObservableList<User> userList = FXCollections.observableArrayList(su.afficher());
        TableUser.setItems(userList);

        NomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        MailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        TelCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
        LoginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        PasswordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        RolCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        MatCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        NCarteCol.setCellValueFactory(new PropertyValueFactory<>("n_carte"));
    }

    private void clearFields() {
        TFNom.clear();
        TFPrenom.clear();
        datePicker.setValue(null);
        tfemail.clear();
        TFTel.clear();
        TFLogin.clear();
        TFPassword.clear();
        cbrole.getSelectionModel().clearSelection();
    }

    @FXML
    private void ButtonAccueil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/Accueil.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Accueil");
        stage.show();

    }

public boolean emailExists(String mail) {
       List<User> listUser = new ArrayList<>(su.afficher());
        // Ici, tu devrais implémenter la logique pour vérifier l'existence de l'adresse e-mail
        // dans ta source de données, comme une base de données ou une liste d'utilisateurs en mémoire.

        // Par exemple, supposons que tu aies une liste d'utilisateurs dans ta classe User :
       // List<User> listUser = getUsers(); // Méthode pour obtenir la liste des utilisateurs

        // Parcours de la liste pour vérifier si l'e-mail existe déjà
        for (User user : listUser) {
            if (user.getMail().equals(mail)) {
                return true; // L'e-mail existe déjà
            }
        }

        return false; // L'e-mail n'existe pas
    }


}
   
    
    

        


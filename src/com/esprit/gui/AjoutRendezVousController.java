package com.esprit.gui;

import com.esprit.entities.RendezVous;
import com.esprit.entities.SessionManager;
import com.esprit.services.ServiceRendezVous;
import com.esprit.tests.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class AjoutRendezVousController implements Initializable {
    @FXML
    private Session session;
    @FXML
    private TextField tfdate_rdv;
    @FXML
    private TableView<RendezVous> tableViewRendezVous;
    @FXML
    private TableColumn<RendezVous, String> columnDate_rendez_vous;
    @FXML
    private TableColumn<RendezVous, String> columnresultat;
    private ServiceRendezVous serviceRendezVous;
    @FXML
    private TextField tfresultat;
    
    private Button envoyerRendezVousButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String username = SessionManager.getLoggedInUsername();
        String role = SessionManager.getLoggedInUserRole();
        serviceRendezVous = new ServiceRendezVous();
        // Configure table columns
        columnDate_rendez_vous.setCellValueFactory(new PropertyValueFactory<>("date_rdv"));
        columnresultat.setCellValueFactory(new PropertyValueFactory<>("resultat"));
        // Load data into the table view
        chargerDonneesTableau();
    }

    private void chargerDonneesTableau() {
        try {
            // Retrieve the list of rendezvous from the service
            List<RendezVous> rendezvous = serviceRendezVous.afficher();

            // Create an ObservableList from the list of rendezvous
            ObservableList<RendezVous> observableList = FXCollections.observableArrayList(rendezvous);

            // Display the data in the TableView
            tableViewRendezVous.setItems(observableList);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle the exception
        }
    }

    @FXML

private void ajouterRendezVous(ActionEvent event) throws IOException {
    String dateRdv = tfdate_rdv.getText();
    String resultat = tfresultat.getText();

    if (dateRdv.isEmpty() || resultat.isEmpty()) {
        showAlert(AlertType.ERROR, "Veuillez remplir tous les champs !");
    } else if (!dateRdv.matches("\\d{2}-\\d{2}-\\d{4}")) {
        showAlert(AlertType.ERROR, "Le format de la date doit être JJ-MM-AAAA !");
    } else if (!resultat.matches("(admis|refuse)")) {
        showAlert(AlertType.ERROR, "Le résultat doit être 'admis' ou 'refuse' !");
    } else {
        ServiceRendezVous sp = new ServiceRendezVous();
        sp.ajouter(new RendezVous(dateRdv, resultat));
        showAlert(AlertType.INFORMATION, "Rendez-vous ajouté !");

        tfdate_rdv.clear();
        tfresultat.clear();

        // Refresh the table view
        chargerDonneesTableau();
    }
}

@FXML
private void modifierRendezVous(ActionEvent event) {
    RendezVous rendezVous = tableViewRendezVous.getSelectionModel().getSelectedItem();
    if (rendezVous != null) {
        String dateRdv = tfdate_rdv.getText();
        String resultat = tfresultat.getText();

        if (dateRdv.isEmpty() || resultat.isEmpty()) {
            showAlert(AlertType.ERROR, "Veuillez remplir tous les champs !");
        } else if (!dateRdv.matches("\\d{2}-\\d{2}-\\d{4}")) {
            showAlert(AlertType.ERROR, "Le format de la date doit être JJ-MM-AAAA !");
        } else if (!resultat.matches("(admis|refuse)")) {
            showAlert(AlertType.ERROR, "Le résultat doit être 'admis' ou 'refuse' !");
        } else {
            rendezVous.setDate_rdv(dateRdv);
            rendezVous.setResultat(resultat);

            serviceRendezVous.modifier(rendezVous);
            showAlert(AlertType.INFORMATION, "Rendez-vous modifié !");

            // Refresh the table view
            chargerDonneesTableau();
        }
    } else {
        showAlert(AlertType.ERROR, "Aucun rendez-vous sélectionné !");
    }
}

    @FXML
    private void supprimerRendezVous(ActionEvent event) {
        RendezVous rendezVous = tableViewRendezVous.getSelectionModel().getSelectedItem();
        if (rendezVous != null) {
            // Delete the rendez-vous from the database
            serviceRendezVous.supprimer(rendezVous);
            showAlert(Alert.AlertType.INFORMATION, "Rendez-vous supprimé !");

            // Refresh the table view
            chargerDonneesTableau();
        } else {
            showAlert(Alert.AlertType.ERROR, "Aucun rendez-vous sélectionné !");
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
@FXML
private void rechercherRendezVous(ActionEvent event) {
    String date_rdv = tfdate_rdv.getText();
    RendezVous rendezvous = serviceRendezVous.rechercherParDate_rdv(date_rdv);
    if (rendezvous != null) {
        tfdate_rdv.setText(rendezvous.getDate_rdv());
        tfresultat.setText(rendezvous.getResultat());
    } else {
        showAlert(Alert.AlertType.ERROR, "Rendez Vous non trouvé !");
    }
}
 
    @FXML
        private void envoyerRendezVous(ActionEvent event) {
        final String from = "aministrateurecole@gmail.com";
        final String username = "aministrateurecole@gmail.com";
        final String password = "vgwbzqsheawddprf";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "username");
        props.put("mail.smtp.password", "password");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        String to = "Adel.rabawi@gmail.com";
        String subject = "Sujet de l'e-mail";
        String body = "Contenu de l'e-mail";

        try {
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    // Autres méthodes existantes
}
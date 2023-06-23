package com.esprit.gui;
import com.esprit.entities.Classe;
import com.esprit.entities.SessionManager;
import com.esprit.services.ClasseService;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import com.mysql.cj.xdevapi.Table;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.TablePosition;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.text.Document;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class AjouterClasseController implements Initializable {

    @FXML
    private TextField tfNomClasse;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNiveau;
    
    @FXML
    private TableColumn<Classe, String> C_nomclasse;
    @FXML
    private TableColumn<Classe, Integer> c_nombre;
    @FXML
    private TableColumn<Classe, String> c_niveau;
    @FXML
    private Button convertpdf;
    @FXML
    private TableView<Classe> TableClasse;
    @FXML
    private TextField tfsearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String username = SessionManager.getLoggedInUsername();
        String role = SessionManager.getLoggedInUserRole();
        initializeTableView();
//        ClasseService classeService = new ClasseService();
//        List<Classe> classes = classeService.afficher();
//        TableClasse.getItems().addAll(classes);
        
           
//            C_nomclasse.setCellFactory(TextFieldTableCell.forTableColumn());
//            C_nomclasse.setOnEditCommit(event -> {
//                Classe classe = event.getTableView().getItems().get(event.getTablePosition().getRow());
//                classe.setNom_classe(event.getNewValue());
//
//                classeService.modifier(classe);
//                rafraichirTableView();
//            });
//
//            c_nombre.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//            c_nombre.setOnEditCommit(event -> {
//                Classe classe = event.getTableView().getItems().get(event.getTablePosition().getRow());
//                classe.setNombre_eleve(event.getNewValue());
//
//                classeService.modifier(classe);
//                rafraichirTableView();
//            });
//
//            c_niveau.setCellFactory(TextFieldTableCell.forTableColumn());
//            c_niveau.setOnEditCommit(event -> {
//                Classe classe = event.getTableView().getItems().get(event.getTablePosition().getRow());
//                classe.setNiveau(event.getNewValue());
//
//                classeService.modifier(classe);
//                rafraichirTableView();
//            });
//
   }  
        
            private void initializeTableView() 
            {
            C_nomclasse.setCellValueFactory(new PropertyValueFactory<>("nom_classe"));
            c_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_eleve"));
            c_niveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));

            ClasseService classeService = new ClasseService();
            List<Classe> classes = classeService.afficher();
            ObservableList<Classe> observableList = FXCollections.observableArrayList(classes);
            TableClasse.setItems(observableList);
            }
    @FXML
    private void ajouterClasse(ActionEvent event)  throws IOException {
        ClasseService sp = new ClasseService();
        sp.ajouter(new Classe(tfNomClasse.getText(),Integer.parseInt(tfNombre.getText()), tfNiveau.getText()));
        JOptionPane.showMessageDialog(null, "Classe ajoutée !");
        rafraichirTableView(); 
        clearFields();
           
    }
    
    @FXML
            private void modifierClasse(ActionEvent event) {
               String nom_classe = tfNomClasse.getText();
               String nombre_eleveText = tfNombre.getText();
               String niveau = tfNiveau.getText();

               if (nom_classe.isEmpty() || nombre_eleveText.isEmpty() || niveau.isEmpty()) {
                   // Show an error message indicating that all fields must be filled
                   Alert alert = new Alert(AlertType.ERROR);
                   alert.setTitle("Erreur");
                   alert.setHeaderText(null);
                   alert.setContentText("Veuillez remplir tous les champs.");
                   alert.showAndWait();
                   return;
               }

               int nombre_eleve;
               try {
                   nombre_eleve = Integer.parseInt(nombre_eleveText);
               } catch (NumberFormatException e) {
                   // Show an error message indicating that the input is not a valid integer
                   Alert alert = new Alert(AlertType.ERROR);
                   alert.setTitle("Erreur");
                   alert.setHeaderText(null);
                   alert.setContentText("Le champ 'nombre d'élèves' doit être un entier valide.");
                   alert.showAndWait();
                   return;
               }

               ClasseService classeService = new ClasseService();
               Classe classeAModifier = new Classe(nom_classe, nombre_eleve, niveau);
               classeService.modifier(classeAModifier);

               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("Modification réussie");
               alert.setHeaderText(null);
               alert.setContentText("La classe a été modifiée avec succès !");
               alert.showAndWait();

               rafraichirTableView();
               clearFields();
           }


    
    @FXML
    private void supprimer(ActionEvent event) {

        Classe selectedClasse = TableClasse.getSelectionModel().getSelectedItem();
        ClasseService classeService = new ClasseService();
        if (selectedClasse != null) {
            
            classeService.supprimer(selectedClasse);// Remove the selected item from the data source

            rafraichirTableView();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Suppression réussie");
            alert.setHeaderText(null);
            alert.setContentText("La classe a été supprimée avec succès !");
            alert.showAndWait();
        } 
        else {// No item selected, show an error message
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une classe à supprimer.");
            alert.showAndWait();
        }
    }

  
    @FXML
        private void chercher(ActionEvent event) {

            String nom_classe = tfsearch.getText();
            ClasseService classeService = new ClasseService();
            List<Classe> resultats = classeService.rechercherParNom(nom_classe);
            TableClasse.setItems(FXCollections.observableArrayList(resultats));
        }

        private void rafraichirTableView() {
                C_nomclasse.setCellValueFactory(new PropertyValueFactory<>("nom_classe"));
                c_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_eleve"));
                c_niveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));

                ClasseService classeService = new ClasseService();
                List<Classe> classes = classeService.afficher();

                TableClasse.getItems().clear(); // Supprimer les éléments existants dans la TableView
                TableClasse.getItems().addAll(classes); // Ajouter les nouvelles classes à la TableView
        }
        private void clearFields() 
            {
                tfNomClasse.setText("");
                tfNombre.setText("");
                tfNiveau.setText("");
            }

    @FXML
    private void ajoutersalle(ActionEvent event) throws IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjouterSalle.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Gestion des salles ");
        stage.show();
    }
private void generatePDF(String filePath, TableView<Classe> TableClasse ) {
    try {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = yStart;
        float bottomMargin = 70;
        float tableHeight = (yStart - bottomMargin) - 300;

        int numberOfColumns = TableClasse.getColumns().size();
        int numberOfRows = TableClasse.getItems().size();
        float rowHeight = 20f;
        float tableRowHeight = 15f;

        // Dessine l'en-tête du tableau
    float tableYPosition = yPosition;
        for (int i = 0; i < numberOfColumns-1; i++) {
            String columnName = TableClasse.getColumns().get(i).getText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 12);// Utilize PDType1Font.HELVETICA_BOLD instead of BOLD
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(margin + (i * tableWidth / numberOfColumns), tableYPosition);
            contentStream.drawString(columnName);
            contentStream.endText();
        }
        tableYPosition -= tableRowHeight;
        // Dessine les données du tableau
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns-1; col++) {
                String cellValue = TableClasse.getColumns().get(col).getCellData(row).toString();
                System.out.println(TableClasse.getColumns()+"test1");
                System.out.println(TableClasse.getColumns().get(col)+"test2");
                System.out.println( TableClasse.getColumns().get(col).getCellData(row)+"test3");
                System.out.println(TableClasse.getColumns().get(col).getCellData(row).toString()+"test4");
                contentStream.setFont(PDType1Font.HELVETICA, 12); // Use PDType1Font.HELVETICA instead of REGULAR
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(margin + (col * tableWidth / numberOfColumns), tableYPosition);
                contentStream.drawString(cellValue);
                contentStream.endText();
            }
            tableYPosition -= tableRowHeight;
        }

        contentStream.close();

        // Enregistre le document PDF
        document.save(filePath);
        document.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
@FXML
private void convertToPDF(ActionEvent event) {
    // Crée une instance de FileChooser
    FileChooser fileChooser = new FileChooser();
    
    // Configure le FileChooser pour spécifier les extensions de fichiers autorisées
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
    fileChooser.getExtensionFilters().add(extFilter);
    
    // Affiche la boîte de dialogue de sélection de fichier et attend la sélection de l'utilisateur
    Stage stage = (Stage) convertpdf.getScene().getWindow(); // Récupère la fenêtre actuelle
    File file = fileChooser.showSaveDialog(stage);
    
    // Vérifie si un fichier a été sélectionné
    if (file != null) {
        // Génère le PDF en utilisant le chemin du fichier sélectionné
        generatePDF(file.getAbsolutePath(), TableClasse);
    }
}

}



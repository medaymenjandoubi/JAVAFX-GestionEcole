package com.esprit.services;

import com.esprit.entities.Candidat;
import com.esprit.utils.DataSource;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCandidat {

    private Connection cnx = DataSource.getInstance().getCnx();

    public void ajouter(Candidat c) {
        try {
            String req = "INSERT INTO Candidat(nom, prenom, date_de_naissance, mail, tel) VALUES (?,?,?,?,?);";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
pst.setDate(3, new java.sql.Date(c.getDate_de_naissance().getTime()));
            pst.setString(4, c.getMail());
            pst.setInt(5, c.getTel());
            pst.executeUpdate();
            System.out.println("Candidat ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Candidat c) {
        try {
            String req = "UPDATE Candidat SET nom=?, prenom=?, date_de_naissance=?, mail=?, tel=? WHERE id_Candidat=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
pst.setDate(3, new java.sql.Date(c.getDate_de_naissance().getTime()));
            pst.setString(4, c.getMail());
            pst.setInt(5, c.getTel());
            pst.setInt(6, c.getId_Candidat());
            pst.executeUpdate();
            System.out.println("Candidat modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimer(Candidat c) {
        try {
            String req = "DELETE FROM Candidat WHERE id_Candidat=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, c.getId_Candidat());
            pst.executeUpdate();
            System.out.println("Candidat supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Candidat> afficher() {
        List<Candidat> list = new ArrayList<>();

        String req = "SELECT * FROM Candidat";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Candidat(rs.getInt("id_Candidat"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getDate("date_de_naissance"), rs.getString("mail"), rs.getInt("tel")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public Candidat rechercherParNom(String recherche) {
        try {
            String req = "SELECT * FROM Candidat WHERE nom=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, recherche);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int idCandidat = rs.getInt("id_Candidat");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                Date dateNaissance = rs.getDate("date_de_naissance");
                String mail = rs.getString("mail");
                Integer tel = rs.getInt("tel");
                return new Candidat(idCandidat, nom, prenom, dateNaissance, mail, tel);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}

package com.esprit.services;

import com.esprit.entities.RendezVous;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceRendezVous {
    
    private Connection cnx = DataSource.getInstance().getCnx();
    
    public void ajouter(RendezVous p) {
        try {
            String req = "INSERT INTO rdv(date_rdv, resultat) VALUES (?,?);";
            PreparedStatement pst = cnx.prepareStatement(req, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, p.getDate_rdv());
            pst.setString(2, p.getResultat());
            pst.executeUpdate();
            
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                p.setId_rdv(id);
            }
            
            System.out.println("RendezVous ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void modifier(RendezVous p) {
        try {
            String req = "UPDATE rdv SET date_rdv=?, resultat=? WHERE id_rdv=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, p.getDate_rdv());
            pst.setString(2, p.getResultat());
            pst.setInt(3, p.getId_rdv());
            pst.executeUpdate();
            System.out.println("RendezVous modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void supprimer(RendezVous p) {
        try {
            String req = "DELETE from rdv WHERE id_rdv=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, p.getId_rdv());
            pst.executeUpdate();
            System.out.println("RendezVous supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<RendezVous> afficher() {
        List<RendezVous> list = new ArrayList<>();
        
        String req = "SELECT * FROM rdv";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                list.add(new RendezVous(rs.getInt("id_rdv"), rs.getString("date_rdv"), rs.getString("resultat")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
    public RendezVous rechercherParDate_rdv(String recherche) {
        try {
            String req = "SELECT * FROM rdv WHERE date_rdv=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, recherche);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int id_rdv = rs.getInt("id_rdv");
                String date_rdv = rs.getString("date_rdv");
                String resultat = rs.getString("resultat");
                
                return new RendezVous(id_rdv, date_rdv, resultat);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}


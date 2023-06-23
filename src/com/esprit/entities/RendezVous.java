/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;




public class RendezVous {

    private int id_rdv;
    private String date_rdv;
    private String resultat;
   

   public RendezVous(String date_rdv, String resultat) {
    this.date_rdv = date_rdv;
    this.resultat = resultat;
  
}

    public RendezVous(int id_rdv, String date_rdv, String resultat, String caninfo) {
        this.id_rdv = id_rdv;
        this.date_rdv = date_rdv;
        this.resultat = resultat;
       
    }

    public RendezVous(int id_rdv, String date_rdv, String resultat) {
        this.id_rdv = id_rdv;
        this.date_rdv = date_rdv;
        this.resultat = resultat;
    }

  

  
    public int getId_rdv() {
        return id_rdv;
    }

    @Override
    public String toString() {
        return "RendezVous{" + "id_rdv=" + id_rdv + ", date_rdv=" + date_rdv + ", resultat=" + resultat + '}';
    }

    public void setId_rdv(int id_rdv) {
        this.id_rdv = id_rdv;
    }

    public void setDate_rdv(String date_rdv) {
        this.date_rdv = date_rdv;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

  
    public String getDate_rdv() {
        return date_rdv;
    }

    public String getResultat() {
        return resultat;
    }

   

   
    }

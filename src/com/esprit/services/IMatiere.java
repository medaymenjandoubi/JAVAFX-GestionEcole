/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.esprit.services;


import com.esprit.entities.MatiereDetails;
import java.util.List;

/**
 *
 * @author 21628
 */
public interface IMatiere<T> {
    
    public void ajouter(T m);
    public void modifier(T m);
    public void supprimer(T m);
    public List<MatiereDetails> afficher();
}
package org.wadael.bourdirconnect.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Un porte-feuille peut contenir plusieurs comptes.
 * Tout est en chaines
 */
public class Portefeuille {

    private String date; // l'info "Au 17/05/2024 à 11:22"
    private List<Compte> comptes;

    public Portefeuille() {
        comptes = new ArrayList<>();
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public void ajouteCompte(Compte c) {
        getComptes().add(c);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Portefeuille{" +
                "date=" + getDate() +
                "comptes=" + comptes +
                '}';
    }
}

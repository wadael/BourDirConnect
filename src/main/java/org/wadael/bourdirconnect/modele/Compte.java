package org.wadael.bourdirconnect.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Un compte.
 * Trois types possibles:  Compte PEA, Compte PEA-PME , Compte Titre ordinaire
 */
public class Compte {
    private String id;  // tel qu'apparait dans la liste déroulante des comptes (plutôt long, avec le numéro , votre nom et le type de compte)

    private String evaluationTotale;
    private String valeurVeille;

    private String soldeEspeces;

    private String soldeLiquidationDate;
    private String soldeLiquidationValeur;

    private String deltaVeille;  // +/- values/Veille :
    private String deltaPRU;     // +/- values/PRU

    List<Position> positions = new ArrayList<>();

    public void ajoutePosition(Position position) {
        this.positions.add(position);
    }

    // ======================= ci-dessous, code généré ========================================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvaluationTotale() {
        return evaluationTotale;
    }

    public void setEvaluationTotale(String evaluationTotale) {
        this.evaluationTotale = evaluationTotale;
    }

    public String getValeurVeille() {
        return valeurVeille;
    }

    public void setValeurVeille(String valeurVeille) {
        this.valeurVeille = valeurVeille;
    }

    public String getSoldeEspeces() {
        return soldeEspeces;
    }

    public void setSoldeEspeces(String soldeEspeces) {
        this.soldeEspeces = soldeEspeces;
    }

    public String getSoldeLiquidationValeur() {
        return soldeLiquidationValeur;
    }

    public void setSoldeLiquidationValeur(String soldeLiquidationValeur) {
        this.soldeLiquidationValeur = soldeLiquidationValeur;
    }

    public String getDeltaVeille() {
        return deltaVeille;
    }

    public void setDeltaVeille(String deltaVeille) {
        this.deltaVeille = deltaVeille;
    }

    public String getDeltaPRU() {
        return deltaPRU;
    }

    public void setDeltaPRU(String deltaPRU) {
        this.deltaPRU = deltaPRU;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public String getSoldeLiquidationDate() {
        return soldeLiquidationDate;
    }

    public void setSoldeLiquidationDate(String soldeLiquidationDate) {
        this.soldeLiquidationDate = soldeLiquidationDate;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id='" + id + '\'' +
                ", evaluationTotale='" + evaluationTotale + '\'' +
                ", valeurVeille='" + valeurVeille + '\'' +
                ", soldeEspeces='" + soldeEspeces + '\'' +
                ", soldeLiquidationDate='" + soldeLiquidationDate + '\'' +
                ", soldeLiquidationValeur='" + soldeLiquidationValeur + '\'' +
                ", deltaVeille='" + deltaVeille + '\'' +
                ", deltaPRU='" + deltaPRU + '\'' +
                ", positions=" + positions +
                '}';
    }
}

package org.wadael.bourdirconnect.modele;

/**
 * Une position est une ligne qui apparait dans un compte.
 * <p>
 * Sur le site web, les colonnes sont :
 * Libellé 	Qté 	PRU 	Cours 	Valo 	+/- Val. 	var/PRU 	var/Veille 	%
 */
public record Position(String libelle, String quantite, String pru, String cours,
                       String valorisation,
                       String deltaValorisation, String variationPRU, String variationVeille, String pourcentage) {

    @Override
    public String toString() {
        return "Position{" +
                "libelle='" + libelle + '\'' +
                ", quantite='" + quantite + '\'' +
                ", pru='" + pru + '\'' +
                ", cours='" + cours + '\'' +
                ", valorisation='" + valorisation + '\'' +
                ", deltaValorisation='" + deltaValorisation + '\'' +
                ", variationPRU='" + variationPRU + '\'' +
                ", variationVeille='" + variationVeille + '\'' +
                ", pourcentage='" + pourcentage + '\'' +
                '}';
    }
}

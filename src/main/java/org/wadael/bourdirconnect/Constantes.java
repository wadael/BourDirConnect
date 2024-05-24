package org.wadael.bourdirconnect;

/**
 * Les constantes.
 * Recueillies avec les devtools du navigateur
 *
 * Verifications avec   https://www.freeformatter.com/xpath-tester.html
 */
public class Constantes {
    public static final String LOGIN_PAGE = "https://www.boursedirect.fr/fr/login";
    public static final String LOGIN_FIELD_ID = "#bd_auth_login_type_login";
    public static final String PASSWORD_FIELD_ID = "#bd_auth_login_type_password";

    public static final String MON_COMPTE_XPATH = "xpath=/html/body/header/div[1]/section/div[1]/div/nav/div/div[1]/a/div/div";
    public static final String SE_CONNECTER_BUTTON_XPATH = "xpath=/html/body/div[5]/div[3]/div[2]/div[2]/div/div/div[1]/div/div/div/div/form/div[3]/button";
    public static final String SE_CONNECTER_BUTTON_TEXT = "text='Se connecter'";

    public static final String PTF_URL = "https://www.boursedirect.fr/fr/page/portefeuille";

    // Popup
    public static final String PAS_CONFIANCE = "xpath=/html/body/div[13]/div[2]/main/section/div[2]/div/input[2]"; // "xpath=*[@id=\"distrusted\"]";

    /**
     * xpaths dans la frame (issue autre page php) plutôt que dans la page.
     */
    static class PTF {
        public static final String PTF_DATEHEURE_XPATH = "xpath=/html/body/div[1]/div/div/div/div/div/div/div[2]/div[2]/table/tbody/tr[1]/td"; // /div[2]/table/tbody/tr[1]/td  par rapport à portTR
        // Evaluation totale
        public static final String EVALUATION_TOTALE_XPATH = "xpath=/html/body/div[1]/div/div/div/div/div/div/div[2]/div[2]/table/tbody/tr[3]/td[2]"; // "xpath=/html/body/div[1]/div/div/div/div/div/div/div[2]/div[2]/table/tbody/tr[3]/td[2]";

        // Valeur veille
        public static final String VALEUR_VEILLE_XPATH = "xpath=/html/body/div[1]/div/div/div/div/div/div/div[2]/div[2]/table/tbody/tr[3]/td[4]";

        // +/- values/Veille :
        public static final String DELTA_VALEUR_VEILLE_XPATH = "xpath=/html/body/div[1]/div/div/div/div/div/div/div[2]/div[2]/table/tbody/tr[3]/td[6]";

        // Solde espèces
        public static final String SOLDE_ESPECES_XPATH =  "xpath=/html/body/div[1]/div/div/div/div/div/div/div[2]/div[2]/table/tbody/tr[4]/td[2]";

        // Solde Liquidation
        public static final String SOLDE_LIQUIDATION_DATE_XPATH =  "xpath=/html/body/div[1]/div/div/div/div/div/div/div[2]/div[2]/table/tbody/tr[4]/td[4]";
        public static final String SOLDE_LIQUIDATION_VALEUR_XPATH =  "xpath=/html/body/div[1]/div/div/div/div/div/div/div[2]/div[2]/table/tbody/tr[4]/td[5]";

        // +/- values/PRU :
        public static final String DELTA_PRU_XPATH = "xpath=/html/body/div[1]/div/div/div/div/div/div/div[2]/div[2]/table/tbody/tr[4]/td[7]";
    }

    /**
     * Contient les MODELES des XPATH pour chaque information d'une position.
     * Ils sont relatifs au tableau les contenant.
     *
     * On remplacera %LIGNE% par le numéro de ligne voulu.
     * NB: Seules les lignes impaires sont utiles
     */
    static class Positions{

        public static final String IDENTIFIANT_TABLEAU = "#tabPTR"; // "xpath=//table@id=tabPTR";

        public static final String  LIBELLE_XPATH ="xpath=./tbody[2]/tr[%LIGNE%]/td[1]/a";
        public static final String  QUANTITE_XPATH ="xpath=./tbody[2]/tr[%LIGNE%]/td[2]";
        public static final String  PRU_XPATH ="xpath=./tbody[2]/tr[%LIGNE%]/td[3]";
        public static final String  COURS_XPATH ="xpath=./tbody[2]/tr[%LIGNE%]/td[4]";
        public static final String  VALO_XPATH ="xpath=./tbody[2]/tr[%LIGNE%]/td[5]";
        public static final String  DELTA_VALO_XPATH ="xpath=./tbody[2]/tr[%LIGNE%]/td[6]";
        public static final String  VAR_PRU_XPATH ="xpath=./tbody[2]/tr[%LIGNE%]/td[7]";
        public static final String  VAR_VEILLE_XPATH ="xpath=./tbody[2]/tr[%LIGNE%]/td[8]";
        public static final String  POURCENTAGE_XPATH ="xpath=./tbody[2]/tr[%LIGNE%]/td[9]";
    }
}
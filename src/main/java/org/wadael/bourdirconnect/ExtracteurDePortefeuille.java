package org.wadael.bourdirconnect;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.wadael.bourdirconnect.modele.Compte;
import org.wadael.bourdirconnect.modele.Portefeuille;
import org.wadael.bourdirconnect.modele.Position;

import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * Point d'entrée de la librairie.
 * Permet de récupérer les informations du compte à un instant T.
 */
public class ExtracteurDePortefeuille {

    private static int NB_SCREENSHOT = 0;

    private static void getScreenshot(Page page) {
        System.out.println("Screenshot " + NB_SCREENSHOT);
        page.screenshot(new Page.ScreenshotOptions().setPath(
                Paths.get("videos/screenshot-" + NB_SCREENSHOT + ".png")));
        NB_SCREENSHOT++;
    }

    /**
     * Point d'entrée de la librairie.
     * Permet de récupérer les informations du compte à un instant T, sans montrer le navigateur, ni prendre de screenshots ou de vidéo.
     *
     * @param utilisateur
     * @param motDePasse
     * @param passcode    code pour l'authentification à deux facteurs, ou null pour ne pas l'utiliser.
     * @return un Portefeuille rempli
     */
    public Portefeuille extraitPortefeuille(String utilisateur, String motDePasse, String passcode) {
        return extraitPortefeuille(utilisateur, motDePasse, passcode, true, false, false);
    }

    /**
     * Methode avec tous les paramètres.
     *
     * @param utilisateur
     * @param motDePasse
     * @param passcode   la valeur obtenue sur votre application de second facteur
     * @param fenetreCachee voulez-vous garder le navigateur caché pendant l'execution
     * @param takeScreenshots utile pendant le développement
     * @param takeVideo utile pendant le développement
     * @return
     */
    public Portefeuille extraitPortefeuille(String utilisateur, String motDePasse, String passcode, boolean fenetreCachee,
                                            boolean takeScreenshots, boolean takeVideo) {
        Portefeuille ptf = new Portefeuille();

        Browser browser = null;
        try (Playwright playwright = Playwright.create()) {

            browser = playwright.firefox()
                    .launch(new BrowserType.LaunchOptions()
                            .setHeadless(fenetreCachee)
                            .setTimeout(2000)
                    );

            BrowserContext context;
            if (takeVideo) {
                context = browser.newContext(new Browser.NewContextOptions()
                        .setRecordVideoDir(Paths.get("videos/"))
                        .setJavaScriptEnabled(true)
                );
            } else {
                context = browser.newContext(new Browser.NewContextOptions().setJavaScriptEnabled(true));
            }

            Page page = context.newPage();

            if (takeScreenshots) getScreenshot(page);
            page.navigate("https://www.boursedirect.fr/fr/actualites");
            if (takeScreenshots) getScreenshot(page);
            page.getByLabel("Accepter & Fermer: Accepter").click();
            if (takeScreenshots) getScreenshot(page);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Accès Client & Membre")).click();
            page.getByTestId("input-username").click();
            page.getByTestId("input-username").fill(utilisateur);
            page.getByTestId("input-password").click();
            page.getByTestId("input-password").fill(motDePasse);
            page.getByTestId("button-submit").click();
            if (takeScreenshots) getScreenshot(page);

            // on saisit le code de la double auth
            for (int i = 0; i < 6; i++) {
                Locator lacase = page.locator("xpath=//input[@data-id='" + i + "']");
                lacase.click();
                lacase.fill("" + passcode.charAt(i));
                if (takeScreenshots) getScreenshot(page);
            }
            page.getByLabel("Non").check(); // faire confiance: non pour la v1
            if (takeScreenshots) getScreenshot(page);
            // appuyer btn confirmer

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continuer")).click();
            if (takeScreenshots) getScreenshot(page);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Mes comptes")).first().click();
            if (takeScreenshots) getScreenshot(page);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Portefeuille temps réel")).click();
            if (takeScreenshots) getScreenshot(page);

            page.waitForURL(Constantes.PTF_URL);
            if (takeScreenshots) getScreenshot(page);

            ptf.setDate(Constantes.PTF.PTF_DATEHEURE_XPATH);

            Locator nc = null;
            int nbFrames = page.frames().size();
            Frame f = null;
            f = getLaBonneFrameQuiContient(page, "<select id=\"nc\"");
            nc = f.locator("#nc");

            if (nc == null)
                return null;
                /* Astuce: il y a des cas où nc est revenu vide en phase de dev.
                    Il faut vider les caches d'IntelliJ et le redémarrer.
                */
            Locator options = nc.locator("option");

            String t = nc.textContent(); // recupere tous les noms de comptes, séparés par un saut de ligne
            String[] libelleComptes = t.substring(1, t.length() - 2).split("\\n");  // pour enlever les sauts de debut et fin.

            for (int j = 0; j < options.count(); j++) {
                nc.focus();
                nc.click();
                if (takeScreenshots) getScreenshot(page);
                nc.selectOption(new SelectOption().setIndex(j));
                Thread.sleep(700);
                if (takeScreenshots) getScreenshot(page);


                if (libelleComptes[j].trim().length() > 0) {
                    // Renseigne le compte
                    Compte c = new Compte();

                    c.setId(libelleComptes[j].trim());

                    c.setEvaluationTotale(f.locator(Constantes.PTF.EVALUATION_TOTALE_XPATH).innerText());

                    c.setValeurVeille(f.locator(Constantes.PTF.VALEUR_VEILLE_XPATH).innerText());
                    c.setDeltaVeille(f.locator(Constantes.PTF.DELTA_VALEUR_VEILLE_XPATH).innerText());
                    c.setSoldeEspeces(f.locator(Constantes.PTF.SOLDE_ESPECES_XPATH).innerText());
                    c.setSoldeLiquidationDate(f.locator(Constantes.PTF.SOLDE_LIQUIDATION_DATE_XPATH).innerText());
                    c.setSoldeLiquidationValeur(f.locator(Constantes.PTF.SOLDE_LIQUIDATION_VALEUR_XPATH).innerText());
                    c.setDeltaPRU(f.locator(Constantes.PTF.DELTA_PRU_XPATH).innerText());

                    Locator locatorTableauPositions = f.locator(Constantes.Positions.IDENTIFIANT_TABLEAU);

                    List<Locator> lignes = null;
                    try {
                        lignes = locatorTableauPositions.locator("tr").all(); // tous les tr de ce tableau

                        int nombrePositions = lignes.size();

                        // traite les lignes de positions
                        for (int i = 1; i <= nombrePositions; i += 2) {
                            // Libellé 	Qté 	PRU 	Cours 	Valo 	+/- Val. 	var/PRU 	var/Veille 	%
                            Position p = new Position(
                                    locatorTableauPositions.locator(Constantes.Positions.LIBELLE_XPATH.replace("%LIGNE%", "" + i)).innerText(),
                                    locatorTableauPositions.locator(Constantes.Positions.QUANTITE_XPATH.replace("%LIGNE%", "" + i)).innerText(),
                                    locatorTableauPositions.locator(Constantes.Positions.PRU_XPATH.replace("%LIGNE%", "" + i)).innerText(),
                                    locatorTableauPositions.locator(Constantes.Positions.COURS_XPATH.replace("%LIGNE%", "" + i)).innerText(),
                                    locatorTableauPositions.locator(Constantes.Positions.VALO_XPATH.replace("%LIGNE%", "" + i)).innerText(),
                                    locatorTableauPositions.locator(Constantes.Positions.DELTA_VALO_XPATH.replace("%LIGNE%", "" + i)).innerText(),
                                    locatorTableauPositions.locator(Constantes.Positions.VAR_PRU_XPATH.replace("%LIGNE%", "" + i)).innerText(),
                                    locatorTableauPositions.locator(Constantes.Positions.VAR_VEILLE_XPATH.replace("%LIGNE%", "" + i)).innerText(),
                                    locatorTableauPositions.locator(Constantes.Positions.POURCENTAGE_XPATH.replace("%LIGNE%", "" + i)).innerText()
                            );
                            c.ajoutePosition(p);
                        }
                    } catch (Exception e) {
                        // arrive quand le compte n'a pas de positions
                    }

                    if (takeScreenshots) getScreenshot(page);
                    ptf.ajouteCompte(c);
                }
            }

            if (takeScreenshots) getScreenshot(page);
            page.close();
        } catch (Exception nel) {
            nel.printStackTrace();
        }
        return ptf;
    }

    /**
     * Maaandieu mais pourquoi dois-je faire cela.
     *
     * @param page la page
     * @param stringRecherchee
     * @return la frame qui contient ce que vous cherchez. Un meilleur point de départ pour vos autres Locator
     */
    private Frame getLaBonneFrameQuiContient(Page page, String stringRecherchee) {
        Iterator<Frame> it = page.frames().iterator();
        Frame f = null;
        while (it.hasNext()) {
            f = it.next();
            if (f.content().contains(stringRecherchee)) {
                return f;
            }
        }
        return null;
    }

}

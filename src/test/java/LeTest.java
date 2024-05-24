import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.wadael.bourdirconnect.ExtracteurDePortefeuille;
import org.wadael.bourdirconnect.modele.Compte;
import org.wadael.bourdirconnect.modele.Portefeuille;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lire l'explication dans README.md
 */
public class LeTest {

    @BeforeAll
    public static void nettoyage(){
        File dir = new File("./videos");
        File[] files = dir.listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    @Test
    public void estCeQuilYADuContenu() {
        // On charge les propriétés
        Properties prop = new Properties();
        try (InputStream flux = LeTest.class.getResourceAsStream("bdc.properties")) {
            prop.load(flux);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        assertNotNull(prop.getProperty("utilisateur"));
        assertNotNull(prop.getProperty("mdp"));
        assertNotNull(prop.getProperty("passcode"));

        ExtracteurDePortefeuille ptfe = new ExtracteurDePortefeuille();
        Portefeuille ptf = ptfe.extraitPortefeuille(prop.getProperty("utilisateur"),
                prop.getProperty("mdp"),
                prop.getProperty("passcode"), true,true, true);

        assertNotNull(ptf);

        System.out.println(ptf.toString()); // TODO suppr apres dev

        assertNotEquals(0, ptf.getComptes().size());

        for (Compte c : ptf.getComptes()) {
            assertNotNull(c.getPositions());

            assertNotEquals(0,  c.getId().length() );

            // Tous les getters doivent renvoyer quelquechose, et par ?flemme?, j'utilise la reflect-ion
            Method[] lesMeth = c.getClass().getDeclaredMethods();
            for (Method m : lesMeth) {
                if (m.getName().startsWith("get")) {
                    try {
                        assertNotNull( m.invoke(c, null) );
                    } catch (Exception e) {
                        e.printStackTrace();
                        fail("/!\\ Fail sur " + m.getName());
                    }
                }
            }
        }

    }
}

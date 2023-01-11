package it.unisa.ilike;

import static org.junit.Assert.assertTrue;

import org.junit.*;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoProxyBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.recensioni.application.RecensioneImpl;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class PubblicazioneRecensioniTest {
    IscrittoBean iscritto;
    ContenutoBean contenuto;
    String testo;
    int valutazione;

    private static String getStringaMilleUnoCaratteri() {
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < 1001; i++) {
            str.append("a");
        }
        return str.toString();
    }

    private static String getStringaOk() {
        return "Uno dei film più riusciti e forse uno dei più belli della storia de cinema. " +
                "Undici Oscar per dire tutto questo, scene spettacolari, musiche bellissime del " +
                "compositore James Horner e battute azzeccate fino all'ultima. Come ha detto " +
                "Mymovies, nel film lavorarono più persone di quante se ne imbarcarono nel 1912 e" +
                " solo questo fa pensare a qualcosa di Straordinario.";
    }

    private static IscrittoBean getIscritto() {
        return new IscrittoProxyBean("testiscritto1@ilike.it", null, null, null, null, null);
    }

    private static ContenutoBean getContenuto() {
        ContenutoBean contenuto = new SerieTVBean();
        contenuto.setId(1);
        return contenuto;
    }


    public PubblicazioneRecensioniTest(IscrittoBean iscritto, ContenutoBean contenuto, String testo, int valutazione) {
        this.iscritto = iscritto;
        this.contenuto = contenuto;
        this.testo = testo;
        this.valutazione = valutazione;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {getIscritto(), getContenuto(), "Ab", 5}, {getIscritto(), getContenuto(), getStringaMilleUnoCaratteri(), 5},
                {getIscritto(), getContenuto(), getStringaOk(), 0}, {getIscritto(), getContenuto(), getStringaOk(), 5}
        });
    }


    /*@Before
    public final void setUp() {
        iscritto = new IscrittoProxyBean("testiscritto1@ilike.it", null, null, null, null, null);
        contenuto = new SerieTVBean();
        contenuto.setId(1);
    }*/




    @Test
    public void testCreaRecensione() throws Exception {
        System.out.println("Parameterized input is: " + testo + ", " + valutazione);
        assertTrue(new RecensioneImpl().creaRecensione(testo, valutazione, iscritto, contenuto));
    }
}

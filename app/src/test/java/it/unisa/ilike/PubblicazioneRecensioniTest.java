package it.unisa.ilike;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import org.junit.*;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoProxyBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTestoException;
import it.unisa.ilike.recensioni.application.exceptions.TestoTroppoBreveException;
import it.unisa.ilike.recensioni.application.exceptions.ValutazioneException;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;

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

    @Test
    public void testCreaRecensione() throws Exception {
        String testoInfo = testo.length() > 50 ? testo.substring(0, 10) + "... (" + testo.length()
                + " caratteri)" : testo;

        System.out.println("Parameterized input is: " + testoInfo + ", " + valutazione);

        if(testo.length() < 3 ){
            assertThrows(TestoTroppoBreveException.class, () -> {
                new RecensioneImpl().creaRecensione(testo, valutazione, iscritto, contenuto);
            });
        }
        else if(testo.length() > 1000) {
            assertThrows(InvalidTestoException.class, () -> {
                new RecensioneImpl().creaRecensione(testo, valutazione, iscritto, contenuto);
            });
        }
        else if(valutazione == 0) {
            assertThrows(ValutazioneException.class, () -> {
                new RecensioneImpl().creaRecensione(testo, valutazione, iscritto, contenuto);
            });
        }
        else {
            assertNotNull(new RecensioneImpl().creaRecensione(testo, valutazione, iscritto, contenuto));
        }
    }
}

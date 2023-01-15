package it.unisa.ilike.unittesting.testcases.cancellaRecensione;

import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.account.storage.GestoreDAO;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;
import it.unisa.ilike.segnalazioni.application.SegnalazioneImpl;
import it.unisa.ilike.segnalazioni.application.SegnalazioneService;
import it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;


/**
 * Questa classe di test fa riferimento al TC_3_1 in TCS.
 * Questa classe testa il metodo cancellaRecensione {@link SegnalazioneImpl}
 * @author Marta
 * @version 0.2
 */
@RunWith(Parameterized.class)
public class CancellaRecensioneTest_TC_3_1 {

    /**
     * Costruttore che riceve i parametri parametrizzati da passare al metodo cancellaRecensione.
     * @param segnalazione istanza di SegnalazioneBean
     * @param motivazione stringa inserita dal gestore che indica la motivazione della cancellazione
     * @param gestoreBean istanza di GestoreBean
     */
    public CancellaRecensioneTest_TC_3_1(SegnalazioneBean segnalazione, String motivazione, GestoreBean gestoreBean) {
        this.gestore = gestoreBean;
        this.motivazione = motivazione;
        this.segnalazione = segnalazione;
    }


    /**
     * Parametri parametrizzati
     * @return una collezione di oggetti da passare al costruttore
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {getSegnalazione(), "", getGestore()}
        });
    }


    /**
     * Questo metodo crea un'istanza di RecensioneBean
     * @return l'istanza di RecensioneBean
     */
    private static RecensioneBean getRecensione(){
        RecensioneBean recensioneBean = new RecensioneBean();
        recensioneBean.setId(100);
        recensioneBean.setCancellata(false);
        return recensioneBean;
    }


    /**
     * Questo metodo crea un'istanza di SegnalazioneBean
     * @return l'istanza di SegnalazioneBean
     */
    private static SegnalazioneBean getSegnalazione() {
        return new SegnalazioneBean(100, 1, null, false, null, getRecensione());
    }


    /**
     * Questo metodo crea un'istanza di GestoreBean
     * @return l'istanza di GestoreBean
     */
    private static GestoreBean getGestore() {
        return new GestoreBean("gestore2@ilike.it", null, 0);
    }


    /**
     * Questo metedo viene eseguito prima del test, ed esegue un'inizzializzazione
     * degli oggetti usati nel test.
     */
    @Before
    public void init(){
        // creazione dei mock
        mockGestoreDAO = Mockito.mock(GestoreDAO.class);
        mockRecensioneDAO = Mockito.mock(RecensioneDAO.class);

        boolean r = true;
        segnalazione = getSegnalazione();

        // proxy dei metodi usati da cancellaRecensione
        when(mockGestoreDAO.doUpdate(getGestore())).thenReturn(r);
        when(mockRecensioneDAO.doRetrieveByIdRecensione(100)).thenReturn(segnalazione.getRecensione());
        when(mockRecensioneDAO.cancellaRecensione(segnalazione.getRecensione())).thenReturn(r);

        segnalazioneService = new SegnalazioneImpl(mockGestoreDAO, mockRecensioneDAO);
    }


    /**
     * Questo metodo esegue il TC_3_1.
     * @throws Exception
     */
    @Test
    public void testCancellazioneRecensione() throws Exception{

        String motivazioneInfo = motivazione.length() > 50 ? motivazione.substring(0, 10) + "... (" + motivazione.length()
                + " caratteri)" : motivazione;

        System.out.println("Parameterized input is: " + motivazioneInfo);

        assertThrows(MotivazioneVuotaException.class, () -> {
            segnalazioneService.cancellaRecensione(segnalazione, motivazione, gestore);
        });
    }

    private GestoreBean gestore;
    private String motivazione;
    private SegnalazioneBean segnalazione;
    private GestoreDAO mockGestoreDAO;
    private RecensioneDAO mockRecensioneDAO;
    private SegnalazioneService segnalazioneService;
}

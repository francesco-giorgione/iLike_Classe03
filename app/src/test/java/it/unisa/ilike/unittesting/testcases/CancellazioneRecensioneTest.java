package it.unisa.ilike.unittesting.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.account.storage.GestoreDAO;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.RecensioneService;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;
import it.unisa.ilike.segnalazioni.application.SegnalazioneImpl;
import it.unisa.ilike.segnalazioni.application.SegnalazioneService;
import it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneDAO;

@RunWith(Parameterized.class)
public class CancellazioneRecensioneTest {

    public CancellazioneRecensioneTest(SegnalazioneBean segnalazione, String motivazione, GestoreBean gestoreBean) {
        this.gestore = gestoreBean;
        this.motivazione = motivazione;
        this.segnalazione = segnalazione;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {getSegnalazione(), "", getGestore()},
                {getSegnalazione(), getStringaTrecentouno(), getGestore()},
                {getSegnalazione(), getStringaOK(), getGestore()}
        });
    }

    private static RecensioneBean getRecensione(){
        RecensioneBean recensioneBean = new RecensioneBean();
        recensioneBean.setId(100);
        recensioneBean.setCancellata(false);
        return recensioneBean;
    }

    private static SegnalazioneBean getSegnalazione() {
        return new SegnalazioneBean(100, 1, null, false, null, getRecensione());
    }

    private static GestoreBean getGestore() {
        return new GestoreBean("gestore2@ilike.it", null, 0);
    }

    private static String getStringaOK() {
        return "Tale recensione sarà cancellata in " +
                "quanto effettua uno spoiler sul finale della stagione 1.";
    }

    private static String getStringaTrecentouno() {
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < 301; i++) {
            str.append("a");
        }
        return str.toString();
    }

    @Before
    public void init(){
        mockGestoreDAO = Mockito.mock(GestoreDAO.class);
        mockRecensioneDAO = Mockito.mock(RecensioneDAO.class);

        boolean r = true;
        segnalazione = getSegnalazione();

        when(mockGestoreDAO.doUpdate(getGestore())).thenReturn(r);
        when(mockRecensioneDAO.doRetrieveByIdRecensione(100)).thenReturn(segnalazione.getRecensione());
        when(mockRecensioneDAO.cancellaRecensione(segnalazione.getRecensione())).thenReturn(r);

        segnalazioneService = new SegnalazioneImpl(mockGestoreDAO, mockRecensioneDAO);
    }


    @Test
    public void testCancellazioneRecensione() throws Exception{
/*
        SegnalazioneService segnalazioneService = new SegnalazioneImpl();
        String motivazioneInfo = motivazione.length() > 50 ? motivazione.substring(0, 10) + "... (" + motivazione.length()
                + " caratteri)" : motivazione;

        System.out.println("Parameterized input is: " + motivazioneInfo);

        SegnalazioneService mockSegnalazioneService = Mockito.mock(SegnalazioneService.class);*/


        String motivazioneInfo = motivazione.length() > 50 ? motivazione.substring(0, 10) + "... (" + motivazione.length()
                + " caratteri)" : motivazione;

        System.out.println("Parameterized input is: " + motivazioneInfo);

        if(motivazione.length() < 1){
            assertThrows(MotivazioneVuotaException.class, () -> {
                segnalazioneService.cancellaRecensione(segnalazione, motivazione, gestore);
            });
        }else if (motivazione.length() > 300){
            assertThrows(InvalidMotivazioneException.class, () -> {
                segnalazioneService.cancellaRecensione(segnalazione, motivazione, gestore);
            });
        }else {
            assertTrue(segnalazioneService.cancellaRecensione(segnalazione, motivazione, gestore));
        }
    }


    private GestoreBean gestore;
    private String motivazione;
    private SegnalazioneBean segnalazione;
    private GestoreDAO mockGestoreDAO;
    private RecensioneDAO mockRecensioneDAO;
    private SegnalazioneService segnalazioneService;
}

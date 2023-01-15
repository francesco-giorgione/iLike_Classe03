package it.unisa.ilike.unittesting.testcases.creazioneListe;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoDAO;
import it.unisa.ilike.account.storage.IscrittoProxyBean;
import it.unisa.ilike.liste.application.ListaImpl;
import it.unisa.ilike.liste.application.exceptions.NomeVuotoException;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.liste.storage.ListaDAO;

import org.mockito.Mockito;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class CreazioneListeTest_TC_4_4 {

    private static IscrittoBean getIscritto() {
        return new IscrittoProxyBean("testiscritto1@ilike.it", null, null, null, null, null);
    }

    public CreazioneListeTest_TC_4_4(String nome, IscrittoBean iscritto, Boolean pubblica) {
        this.nome = nome;
        this.iscritto = iscritto;
        this.pubblica = pubblica;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"Pre-serata", getIscritto(), false}
        });
    }

    @Before
    public void init() {
        mockListaDAO = Mockito.mock(ListaDAO.class);

        boolean res = true;

        when(mockListaDAO.doSave(new ListaBean(nome, iscritto, pubblica))).thenReturn(res);

        listaService = new ListaImpl(mockListaDAO);
    }

    @Test
    public void testCreaLista() throws Exception {

        System.out.println("Parameterized input is: " + nome + ", " + pubblica);

        assertNotNull(listaService.creaLista(iscritto, nome, pubblica));

    }


    private String nome;
    private IscrittoBean iscritto;
    private Boolean pubblica;
    private ListaDAO mockListaDAO;
    private ListaImpl listaService;
}

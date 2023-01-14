package it.unisa.ilike.unittesting.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoProxyBean;
import it.unisa.ilike.liste.application.ListaImpl;
import it.unisa.ilike.liste.application.exceptions.InvalidNomeException;
import it.unisa.ilike.liste.application.exceptions.ListaGiaEsistenteException;
import it.unisa.ilike.liste.application.exceptions.NomeVuotoException;
import it.unisa.ilike.utils.Utils;

@RunWith(Parameterized.class)
public class CreazioneListeTest {

    private static String getStringaCinquantuno() {
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < 7; i++) {
            str.append("iLoveRock-");
        }
        return str.toString();
    }

    private static IscrittoBean getIscritto() {
        return new IscrittoProxyBean("testiscritto1@ilike.it", null, null, null, null, null);
    }

    public CreazioneListeTest(String nome, IscrittoBean iscritto, Boolean pubblica) {
        this.nome = nome;
        this.iscritto = iscritto;
        this.pubblica = pubblica;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"",getIscritto(), true},{getStringaCinquantuno(),getIscritto(),true},
                {getIscritto().getListe().get(0).getNome(), getIscritto(), true},
                {"Pre-serata", getIscritto(), false}
        });
    }


    @Test
    public void testCreaLista() throws Exception {

        System.out.println("Parameterized input is: " + nome + ", " + pubblica);

        if(nome.length() == 0 ){
            assertThrows(NomeVuotoException.class, () -> {
                new ListaImpl().creaLista(iscritto, nome, pubblica);
            });
        }
        else if(nome.length() > 50) {
            assertThrows(InvalidNomeException.class, () -> {
                new ListaImpl().creaLista(iscritto, nome, pubblica);
            });
        }
        else if(Utils.hasLista(iscritto, nome)) {
            assertThrows(ListaGiaEsistenteException.class, () -> {
                new ListaImpl().creaLista(iscritto, nome, pubblica);
            });
        }
        else {
            assertNotNull(new ListaImpl().creaLista(iscritto, nome, pubblica));
        }
    }

    private String nome;
    private IscrittoBean iscritto;
    private Boolean pubblica;
}

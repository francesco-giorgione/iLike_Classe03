package it.unisa.ilike.unittesting.testcases.login;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.account.storage.GestoreDAO;
import it.unisa.ilike.account.storage.IscrittoDAO;


/**
 * Implementa il testing di unità per TC_2_3 (Login)
 * @author LuiginaCostante
 */
public class LoginTest_TC_2_3 {
    String username;
    String password;


    /**
     * Costruttore che inizializza i parametri username e password previsti nel TC_2_3
     */
    public LoginTest_TC_2_3() {
        this.username = "gestore1@ilike.it";
        this.password = "gestore1";
    }


    /**
     * Eseguito prima del metodo annotato con <code>Test</code>, prepara i mock da usare per simulare
     * l'accesso al database dell'iscritto e del gestore.
     * @throws Exception relativa al metodo <code>getPasswordCrittografata()</code>
     */
    @Before
    public void init() throws Exception{
        mockGestoreDao= Mockito.mock(GestoreDAO.class);
        mockIscrittoDao= Mockito.mock(IscrittoDAO.class);
        service = new AccountImpl(mockGestoreDao, mockIscrittoDao);

        GestoreBean g= new GestoreBean("gestore1@ilike.it", "gestore1", -1);

        //MOCK GESTORE DAO
        when(mockGestoreDao.doRetrieveByEmailPassword(username,
                service.getPasswordCrittografata(password))).thenReturn(g);

        //MOCK ISCRITTO DAO
        when(mockIscrittoDao.doRetrieveByUsernamePassword(username, null,
                service.getPasswordCrittografata(password))).thenReturn(null);
        when(mockIscrittoDao.doRetrieveByUsernamePassword(null,username,
                service.getPasswordCrittografata(password))).thenReturn(null);
    }


    /**
     * Implementa il test della funzionalità Login
     * @throws Exception
     */
    @Test
    public void testLogin() throws Exception {

        System.out.println("Parameterized input is: " + username + ", " + password);
        assertNotNull(service.login(username, password));
    }

    GestoreDAO mockGestoreDao;
    IscrittoDAO mockIscrittoDao;
    AccountImpl service;
}

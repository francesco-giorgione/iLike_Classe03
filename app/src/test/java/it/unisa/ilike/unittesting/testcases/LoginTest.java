package it.unisa.ilike.unittesting.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.exceptions.CredenzialiErrateException;
import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.account.storage.GestoreDAO;
import it.unisa.ilike.account.storage.IscrittoDAO;


@RunWith(Parameterized.class)
public class LoginTest {

    String username;
    String password;

    public LoginTest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"gestore1@gmail.com", "gestore1"},
                {"gestore1@ilike.com", "gestore10"},
                {"gestore1@ilike.com", "gestore1"},
        });
    }

    @Before
    public void init() throws Exception{
        mockGestoreDao= Mockito.mock(GestoreDAO.class);
        mockIscrittoDao= Mockito.mock(IscrittoDAO.class);
        service = new AccountImpl();


        GestoreBean g= new GestoreBean("gestore1@ilike.it", "gestore1", -1);

        //MOCK GESTORE DAO
        when(mockGestoreDao.doRetrieveByEmailPassword("gestore1@gmail.com",
                service.getPasswordCrittografata("gestore1"))).thenReturn(null);

        when(mockGestoreDao.doRetrieveByEmailPassword("gestore1@ilike.com",
                service.getPasswordCrittografata("gestore10"))).thenReturn(null);

        when(mockGestoreDao.doRetrieveByEmailPassword("gestore1@ilike.com",
                service.getPasswordCrittografata("gestore1"))).thenReturn(g);



        //MOCK ISCRITTO DAO
        when(mockIscrittoDao.doRetrieveByUsernamePassword("gestore1@gmail.com", null,
                service.getPasswordCrittografata("gestore1"))).thenReturn(null);
        when(mockIscrittoDao.doRetrieveByUsernamePassword(null,"gestore1@gmail.com",
                service.getPasswordCrittografata("gestore1"))).thenReturn(null);

        when(mockIscrittoDao.doRetrieveByUsernamePassword(null, "gestore1@ilike.com",
                service.getPasswordCrittografata("gestore10"))).thenReturn(null);
        when(mockIscrittoDao.doRetrieveByUsernamePassword("gestore1@ilike.com",null,
                service.getPasswordCrittografata("gestore10"))).thenReturn(null);

        when(mockIscrittoDao.doRetrieveByUsernamePassword("gestore1@ilike.com",null,
                service.getPasswordCrittografata("gestore1"))).thenReturn(null);
        when(mockIscrittoDao.doRetrieveByUsernamePassword(null,"gestore1@ilike.com",
                service.getPasswordCrittografata("gestore1"))).thenReturn(null);
    }

    @Test
    public void testLogin() throws Exception {


        System.out.println("Parameterized input is: " + username + ", " + password);

        if (mockIscrittoDao.doRetrieveByUsernamePassword(null, username, password)==null){
            //credenziali iscritto (email e password) errate
            assertThrows(CredenzialiErrateException.class, () -> {
                service.login(username, password);
            });
        }
        else if (mockIscrittoDao.doRetrieveByUsernamePassword( username, null, password)==null){
            //credenziali iscritto (nickname e password) errate
            assertThrows(CredenzialiErrateException.class, () -> {
                service.login(username, password);
            });
        }
        else if (mockGestoreDao.doRetrieveByEmailPassword(username, password) == null) {
            //credenziali gestore (email e password) errate
            assertThrows(CredenzialiErrateException.class, () -> {
                service.login(username, password);
            });
        }
        else {
            //credenziali corrette
            assertNotNull(service.login(username, password));
        }
    }

    GestoreDAO mockGestoreDao;
    IscrittoDAO mockIscrittoDao;
    AccountImpl service;
}

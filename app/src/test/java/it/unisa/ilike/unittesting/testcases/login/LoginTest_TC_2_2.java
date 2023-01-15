package it.unisa.ilike.unittesting.testcases.login;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.exceptions.CredenzialiErrateException;
import it.unisa.ilike.account.storage.GestoreDAO;
import it.unisa.ilike.account.storage.IscrittoDAO;

public class LoginTest_TC_2_2 {
    String username;
    String password;

    public LoginTest_TC_2_2() {
        this.username = "gestore1@ilike.it";
        this.password = "gestore10";
    }


    @Before
    public void init() throws Exception{
        mockGestoreDao= Mockito.mock(GestoreDAO.class);
        mockIscrittoDao= Mockito.mock(IscrittoDAO.class);
        service = new AccountImpl(mockGestoreDao, mockIscrittoDao);


        //MOCK GESTORE DAO
        when(mockGestoreDao.doRetrieveByEmailPassword(username,
                service.getPasswordCrittografata(password))).thenReturn(null);

        //MOCK ISCRITTO DAO
        when(mockIscrittoDao.doRetrieveByUsernamePassword(username, null,
                service.getPasswordCrittografata(password))).thenReturn(null);
        when(mockIscrittoDao.doRetrieveByUsernamePassword(null,username,
                service.getPasswordCrittografata(password))).thenReturn(null);
    }

    @Test
    public void testLogin() throws Exception {

        System.out.println("Parameterized input is: " + username + ", " + password);
        assertThrows(CredenzialiErrateException.class, () -> {
            service.login(username, password);
        });
    }

    GestoreDAO mockGestoreDao;
    IscrittoDAO mockIscrittoDao;
    AccountImpl service;
}

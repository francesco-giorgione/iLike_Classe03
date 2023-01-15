package it.unisa.ilike.unittesting;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import it.unisa.ilike.unittesting.testcases.cancellaRecensione.CancellaRecensioneTest_TC_3_1;
import it.unisa.ilike.unittesting.testcases.cancellaRecensione.CancellaRecensioneTest_TC_3_2;
import it.unisa.ilike.unittesting.testcases.cancellaRecensione.CancellaRecensioneTest_TC_3_3;
import it.unisa.ilike.unittesting.testcases.creazioneListe.CreazioneListeTest_TC_4_1;
import it.unisa.ilike.unittesting.testcases.creazioneListe.CreazioneListeTest_TC_4_2;
import it.unisa.ilike.unittesting.testcases.creazioneListe.CreazioneListeTest_TC_4_3;
import it.unisa.ilike.unittesting.testcases.creazioneListe.CreazioneListeTest_TC_4_4;
import it.unisa.ilike.unittesting.testcases.login.LoginTest_TC_2_1;
import it.unisa.ilike.unittesting.testcases.login.LoginTest_TC_2_2;
import it.unisa.ilike.unittesting.testcases.login.LoginTest_TC_2_3;
import it.unisa.ilike.unittesting.testcases.pubblicazioneRecensioni.PubblicazioneRecensioniTest_TC_1_1;
import it.unisa.ilike.unittesting.testcases.pubblicazioneRecensioni.PubblicazioneRecensioniTest_TC_1_2;
import it.unisa.ilike.unittesting.testcases.pubblicazioneRecensioni.PubblicazioneRecensioniTest_TC_1_3;
import it.unisa.ilike.unittesting.testcases.pubblicazioneRecensioni.PubblicazioneRecensioniTest_TC_1_4;

public class TestRunner {
    @Test
    public void test() {
        new TestRunner().testMethod(JUnitCore.runClasses(PubblicazioneRecensioniTest_TC_1_1.class));
        new TestRunner().testMethod(JUnitCore.runClasses(PubblicazioneRecensioniTest_TC_1_2.class));
        new TestRunner().testMethod(JUnitCore.runClasses(PubblicazioneRecensioniTest_TC_1_3.class));
        new TestRunner().testMethod(JUnitCore.runClasses(PubblicazioneRecensioniTest_TC_1_4.class));
        new TestRunner().testMethod(JUnitCore.runClasses(LoginTest_TC_2_1.class));
        new TestRunner().testMethod(JUnitCore.runClasses(LoginTest_TC_2_2.class));
        new TestRunner().testMethod(JUnitCore.runClasses(LoginTest_TC_2_3.class));
        new TestRunner().testMethod(JUnitCore.runClasses(CancellaRecensioneTest_TC_3_1.class));
        new TestRunner().testMethod(JUnitCore.runClasses(CancellaRecensioneTest_TC_3_2.class));
        new TestRunner().testMethod(JUnitCore.runClasses(CancellaRecensioneTest_TC_3_3.class));
        new TestRunner().testMethod(JUnitCore.runClasses(CreazioneListeTest_TC_4_1.class));
        new TestRunner().testMethod(JUnitCore.runClasses(CreazioneListeTest_TC_4_2.class));
        new TestRunner().testMethod(JUnitCore.runClasses(CreazioneListeTest_TC_4_3.class));
        new TestRunner().testMethod(JUnitCore.runClasses(CreazioneListeTest_TC_4_4.class));
    }


    private void testMethod(Result result) {
        for(Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}

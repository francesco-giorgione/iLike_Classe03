package it.unisa.ilike.unittesting;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import it.unisa.ilike.unittesting.testcases.CancellazioneRecensioneTest;
import it.unisa.ilike.unittesting.testcases.CreazioneListeTest;
import it.unisa.ilike.unittesting.testcases.LoginTest;
import it.unisa.ilike.unittesting.testcases.PubblicazioneRecensioniTest;

public class TestRunner {
    @Test
    public void test() {
        new TestRunner().testMethod(JUnitCore.runClasses(PubblicazioneRecensioniTest.class));
        new TestRunner().testMethod(JUnitCore.runClasses(LoginTest.class));
        new TestRunner().testMethod(JUnitCore.runClasses(CancellazioneRecensioneTest.class));
        new TestRunner().testMethod(JUnitCore.runClasses(CreazioneListeTest.class));
    }


    private void testMethod(Result result) {
        for(Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}

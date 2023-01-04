package it.unisa.ilike;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Costituisce un'interfaccia al DB. Invia le query sotto forma di richieste HTTP a script php
 * memorizzati sul server dell'applicazione.
 */
public class QueryManager {
    /**
     * Esegue il fetch di dati (select) dal database.
     * @param query query in linguaggio SQL da eseguire
     * @return risultato dell'interrogazione in formato JSON
     */
    public String select(String query) {
        String urlServer = "https://ilike-dbinterface.azurewebsites.net/select.php";
        return executeSql(query, urlServer);
    }

    /**
     * Esegue un aggiornamento di dati (insert, delete, update) nel database.
     * @param query query in linguaggio SQL da eseguire
     * @return valore booleano che descrive l'esito dell'operazione
     */
    public boolean update(String query) {
        String urlServer = "https://ilike-dbinterface.azurewebsites.net/update.php";
        String reqRes = executeSql(query, urlServer);
        return reqRes.equals("true");
    }

    /**
     * Esegue uno statement sql nel database.
     * @param query query in linguaggio SQL da eseguire
     * @return stringa restituita dallo script php
     */
    private String executeSql(String query, String serverUrl) {
        StringBuilder res = new StringBuilder();

        try {
            URL url = new URL(serverUrl);
            String postData = "query=" + query;

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.writeBytes(postData);
            }

            try (BufferedReader bf = new BufferedReader(new InputStreamReader
                    (conn.getInputStream()))) {
                String line;

                while ((line = bf.readLine()) != null) {
                    res.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res.toString();
    }
}

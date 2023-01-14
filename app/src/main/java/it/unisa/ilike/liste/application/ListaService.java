package it.unisa.ilike.liste.application;

import java.util.List;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.application.exceptions.ContenutoGiaPresenteException;
import it.unisa.ilike.liste.application.exceptions.InvalidNomeException;
import it.unisa.ilike.liste.application.exceptions.ListaGiaEsistenteException;
import it.unisa.ilike.liste.application.exceptions.NomeVuotoException;
import it.unisa.ilike.liste.storage.ListaBean;

/**
 * L'interfaccia espone i servizi relativi alla gestione delle liste.
 *  @version 0.3
 *  @author FrancescoGiorgione
 */
public interface ListaService {
    /**
     * Il metodo crea una nuova lista per un dato iscritto.
     * @param i è l'iscritto a cui associare la lista che si vuole creare.
     * @param nome è il nome della lista che si vuole creare.
     * @param pubblica stabilisce se la lista creata deve essere pubblica o privata.
     * @pre <code>1<=nome.lenght()<=50 AND NOT hasLista(i, nome)</code>
     * @post <code>i.hasLista(nome) AND i.getLista(nome) =  [lista vuota]</code>
     * @return l'oggetto della classe <code>IscrittoBean</code> aggiornato con la nuova lista
     * @throws NomeVuotoException se 'nome' è una stringa vuota.
     * @throws InvalidNomeException se 'nome' ha un numero di caratteri maggiore di 50.
     * @throws ListaGiaEsistenteException se l'iscritto ha già una lista di nome 'nome'.
     */
    IscrittoBean creaLista(IscrittoBean i, String nome, boolean pubblica) throws
            NomeVuotoException, InvalidNomeException, ListaGiaEsistenteException;

    /**
     * Il metodo aggiunge un contenuto ad una lista già esistente.
     * @param l è la lista a cui si vuile aggiungere il contenuto.
     * @param c è il contenuto che si vuole aggiungere alla lista.
     * @pre <code>NOT l.contains(c)</code>
     * @post <code>l'=l+[c]</code>
     * @return un booleano che descrive l'esito dell'operazione.
     * @throws ContenutoGiaPresenteException se il contenuto 'c' è già presente nella lista 'l'.
     */
    boolean aggiungiContenuto(ListaBean l, ContenutoBean c) throws ContenutoGiaPresenteException;

    /**
     * Questo metodo permette di ottenere un oggetto Lista identificato con l'email
     * dell'iscritto e il nome della lista
     * @param nome è il nome della lista
     * @param emailIscritto è l'email dell'iscritto a cui è associata la lista
     * @return un oggetto Lista se esitente, null altrimenti.
     */
    ListaBean getLista(String nome, String emailIscritto);

}

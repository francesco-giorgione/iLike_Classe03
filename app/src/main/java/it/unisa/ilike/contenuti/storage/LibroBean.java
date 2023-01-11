package it.unisa.ilike.contenuti.storage;

import java.io.Serializable;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi ai libri
 * @author Simona Lo Conte
 * @version 0.1
 */
public class LibroBean extends ContenutoBean implements Serializable {

    /**
     * Costruttore senza parametri
     */
    public LibroBean() {
    }

    /**
     * Costruttore con parametri
     * @param id è l'id del libro.
     * @param titolo è il titolo del libro.
     * @param descrizione è la descrizione del libro.
     * @param categoria è la categoria del libro.
     * @param autore è l'autore del libro.
     * @param isbn è l'isbn del libro.
     * @param numPagine è il numero totale di pagine del libro.
     */
    public LibroBean(int id, String titolo, String descrizione, String categoria, String autore, String isbn, int numPagine) {
        super(id, titolo, descrizione, categoria,0);
        this.autore = autore;
        this.isbn = isbn;
        this.numPagine = numPagine;
    }

    /**
     * Questo metodo restituisce l'autore del libro
     * @return autore del libro
     */
    public String getAutore() {
        return autore;
    }

    /**
     * Questo metodo imposta l'autore del libro
     * @param autore
     */
    public void setAutore(String autore) {
        this.autore = autore;
    }

    /**
     * Questo metodo restituisce l'isbn del libro
     * @return isbn del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Questo metodo imposta l'isbn del libro
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Questo metodo restituisce il numero di pagine del libro
     * @return numero di pagine del libro
     */
    public int getNumPagine() {
        return numPagine;
    }

    /**
     * Questo metodo imposta il numero di pagine del libro
     * @param numPagine
     */
    public void setNumPagine(int numPagine) {
        this.numPagine = numPagine;
    }

    private String autore;
    private String isbn;
    private int numPagine;

}

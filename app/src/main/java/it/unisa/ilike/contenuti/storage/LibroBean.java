package it.unisa.ilike.contenuti.storage;

public class LibroBean extends ContenutoBean{

    public LibroBean() {
    }

    public LibroBean(int id, String titolo, String descrizione, String categoria, String autore, String isbn, int num_pagine) {
        super(id, titolo, descrizione, categoria);
        this.autore = autore;
        this.isbn = isbn;
        this.num_pagine = num_pagine;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNum_pagine() {
        return num_pagine;
    }

    public void setNum_pagine(int num_pagine) {
        this.num_pagine = num_pagine;
    }

    private String autore;
    private String isbn;
    private int num_pagine;

}

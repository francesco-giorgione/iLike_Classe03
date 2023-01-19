
import pandas as pd


# path per aprire il file .csv
filmPath = "dataset/film.csv"
serieTvPath = "dataset/serietv.csv"
albumPath = "dataset/album.csv"
libriPath = "dataset/libri.csv"
iscrittiPath = "dataset/iscritto.csv"

# lista di attributi da settare per la tabella Contenuti
contenutiAttributi = ["id", "titolo", "descrizione", "categoria"]


def get_film_frame():
    # --------- FILM ---------
    # print("\n\nFILM: ")
    # Dizionario dove: key = nome attributo in dataset,  value = nome attributo in database
    filmAttributiDataset = {"titolo_originale": "titolo", "descrizione": "descrizione", "genere": "categoria",
                            "anno": "anno_rilascio", "durata": "durata", "registi": "regista", "attori": "attori", "paese": "paese"}

    # inizializzazione della tabella film
    filmTable = pd.DataFrame()

    # apertura filmDaKaggle.csv
    table = pd.read_csv(filmPath, sep=',')

    # copia delle colonne di nostro interesse
    numCol = 0
    for col in table:
        if col in filmAttributiDataset.keys():
            filmTable.insert(numCol, filmAttributiDataset[col], table[col])
            numCol += 1


    # numero null in ogni colonna
    # print(filmTable.isnull().sum())
    # togliere righe con valori = null
    filmTable.dropna(subset=["regista", "attori", "regista", "descrizione", "categoria"], inplace=True)

    # print(filmTable.isnull().sum())
    # print(filmTable.head(0))

    return filmTable


def get_serie_tv_frame():
    # --------- SERIE TV ---------
    # print("\n\nSERIE TV: ")
    serieTvAttributiDataset = {"Series Title": "titolo", "Description": "descrizione", "Genre": "categoria",
                               "Year Released": "anno_rilascio", "No of Seasons": "num_stagioni"}

    serieTvTable = pd.DataFrame()
    table = pd.read_csv(serieTvPath, sep=',')
    numCol = 0
    for col in table:
        if col in serieTvAttributiDataset.keys():
            serieTvTable.insert(numCol, serieTvAttributiDataset[col], table[col])
            numCol += 1

    # print(serieTvTable.isnull().sum())
    # print(serieTvTable.head(0))
    # print(serieTvTable)

    return serieTvTable


def get_libri_frame():
    # --------- LIBRI ---------
    # print("\n\nLIBRI: ")
    LibriAttributiDataset = {"title": "titolo", "desc": "descrizione", "genre": "categoria",
                             "pages": "num_pagine", "isbn": "ISBN", "author": "autore"}

    libriTable = pd.DataFrame()
    table = pd.read_csv(libriPath, sep=',')
    table.dropna(subset=["bookformat", "desc", "genre", "img", "isbn", "isbn13"], inplace=True)

    daEliminare = []

    for i, x in enumerate(table["genre"]):
        if "Nonfiction" in str(x) or "Esoterica" in str(x):
            daEliminare.append(i)

    table.drop(table.index[daEliminare], inplace=True)

    numCol = 0
    for col in table:
        if col in LibriAttributiDataset.keys():
            libriTable.insert(numCol, LibriAttributiDataset[col], table[col])
            numCol += 1


    # print(libriTable.isnull().sum())
    libriTable.dropna(subset=["titolo", "descrizione", "categoria"], inplace=True)
    # print(libriTable.isnull().sum())
    # print(libriTable)
    # print(libriTable.head(0))

    return libriTable


def get_album_frame():
    # --------- ALBUM ---------
    # print("\n\nALBUM: ")
    AlbumAttributiDataset = {"album": "titolo", "desc": "descrizione", "gens": "categoria",
                             "acousticness": "acustica", "rel_date": "data_rilascio", "ars_name": "artista",
                             "instrumentalness": "strumentalita", "tempo": "tempo", "valence": "valenza", "duration_ms": "durata"}


    albumTable = pd.DataFrame()
    table = pd.read_csv(albumPath, sep=',')
    numCol = 0
    for col in table:
        if col in AlbumAttributiDataset.keys():
            albumTable.insert(numCol, AlbumAttributiDataset[col], table[col])
            numCol += 1


    # print(albumTable.isnull().sum())
    albumTable.dropna(subset=["descrizione"], inplace=True)
    # print(albumTable.isnull().sum())
    # print(albumTable.head(0))

    return albumTable

def get_iscritti_frame():
    table = pd.read_csv(iscrittiPath, sep=";")
    return table


if __name__ == '__main__':
    print(get_iscritti_frame())



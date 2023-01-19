import numpy as np
import pandas as pd
import seaborn as sns
from matplotlib import pyplot as plt
from sklearn import preprocessing
from sklearn.feature_selection import VarianceThreshold

filmPath = "dataset/filmDaKaggle.csv"

table = pd.read_csv(filmPath, sep=',')
print(table.info())

#       -------- DATA CLEANING --------

# conto il numero di valori null
print(table.isnull().sum())
# Numero di righe
print("Numero di righe:", table.index.stop)


"""
    ELIMINAZIONE DELLE COLONNE
        voto_critica, voto_pubblico, note, attori
"""
print("\n\nEffettuo eliminazione per colonne: ")
table.drop(columns=['voto_critica', 'voto_pubblico', "note", "attori"], inplace=True)
print(table.isnull().sum())
print("Numero di righe:", table.index.stop)


"""
    ELIMINAZIONE DELLE RIGHE CON VALORI NULLI SU
        registi, descrizione, genere, paese
"""
print("\n\nEffettuo eliminazione per righe: ")
table.dropna(subset=["registi", "descrizione", "genere", "paese"], inplace=True)
print(table.isnull().sum())
print("Numero di righe:", table.index)


"""
    ELIMINAZIONE DI GENERI
        Noir, Sperimentale, Mélo, Catastrofico, Gangster, Biblico, Sportivo, Cortometraggio
"""
print(table.value_counts(subset=table["genere"]))

condizioni = (table["genere"] == "Noir") | (table["genere"] == "Sperimentale") | (table["genere"] == "Mélo") | (table["genere"] == "Catastrofico") | (table["genere"] == "Gangster") | (table["genere"] == "Biblico") | (table["genere"] == "Sportivo") | (table["genere"] == "Cortometraggio")
table.drop(table.loc[condizioni].index, inplace=True)
# print(table.value_counts(subset=table["genere"]))


#       -------- FEATURE SCALING --------

print("\n\n")

# per ogni colonna della tabella mostra le statistiche
for col in table:
    print(col, "\n", table[col].describe(), "\n\n")

"""
    erotismo    -> range 0-4
    tensione    -> range 0-5
    impegno     -> range 0-5
    ritmo       -> range 0-5
    humor       -> range 0-5
    voti_totali -> range 1-1052
    voto_medio  -> range 1-10
    durata      -> range 41-924
"""

"""
    MIN-MAX NORMALIZZATION (range 0-1)
"""

# daNormalizzare = ["erotismo", "tensione", "impegno", "ritmo", "humor", "voti_totali", "voto_medio", "durata"]
min_max_scaler = preprocessing.MinMaxScaler()
# table[daNormalizzare] = min_max_scaler.fit_transform(table[daNormalizzare].to_numpy())
# print(table[daNormalizzare])


numericCol = table.select_dtypes(include=[np.number]).columns
table[numericCol] = min_max_scaler.fit_transform(table[numericCol].to_numpy())
print(table[numericCol])

# print("\n\n")
# # per ogni colonna della tabella mostra le statistiche
# for col in table:
#     print(col, "\n", table[col].describe(), "\n\n")


"""
    Z-SCORE NORMALIZZATION
"""

# numericCol = table.select_dtypes(include=[np.number]).columns
# table[numericCol] = zscore(table[numericCol], axis=1, ddof=1)
# print(table[numericCol])

# print("\n\n")
# # per ogni colonna della tabella mostra le statistiche
# for col in table:
#     print(col, "\n", table[col].describe(), "\n\n")


#       -------- FEATURE SELECTION --------

"""
    RIMOZIONE voto_medio e filmtv_id
"""

table.drop(columns=["voto_medio"], inplace=True)
table.drop(columns=["filmtv_id"], inplace=True)
# print(table.columns)

# print("\n\n")
# # per ogni colonna della tabella mostra le statistiche
# for col in table:
#     print(col, "\n", table[col].describe(), "\n\n")

"""
    ELIMINAZIONE DI FEACTURE CON BASSA VARIANZA 
    (eliminazione di feacture con varianza < 0.01)
"""

numericCol = table.select_dtypes(include=[np.number]).columns
# print(numericCol)
lowVariance = VarianceThreshold()
lowVariance.fit_transform(table[numericCol])
# print(lowVariance.variances_)

# eliminazione delle colonne con varianza < 0.01
for i, col in enumerate(numericCol):
    if lowVariance.variances_[i] < 0.01:
        table.drop(columns=col, inplace=True)

print(table.columns)



# --- Utile ---

"""
    CONTERE IL NUMERO DI FILM PER GENERE
print(table.value_counts(subset=table["genere"]))
"""


"""
    VISUALIZZARE IL GRAFICO DEL DATASET
plt.figure(figsize = (30, 8))
sns.countplot(x="genere" ,data=table)
plt.show()
"""

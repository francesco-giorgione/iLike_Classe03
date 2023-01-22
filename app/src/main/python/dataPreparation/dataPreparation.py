import numpy as np
import pandas as pd
import seaborn as sns
from matplotlib import pyplot as plt
from scipy.stats import zscore
from sklearn import preprocessing
from sklearn.feature_selection import VarianceThreshold
from sklearn.preprocessing import StandardScaler

filmPath = "filmDaKaggle.csv"

table = pd.read_csv(filmPath, sep=',')
print(table.info())


#       -------- DATA CLEANING --------

# conto il numero di valori null
print(table.isnull().sum())
# Numero di righe
print("Numero di righe:", table.index)


"""
    ELIMINAZIONE DELLE COLONNE
        voto_critica, voto_pubblico, note, attori
"""
print("\n\nEffettuo eliminazione per colonne: ")
table.drop(columns=['voto_critica', 'voto_pubblico', "note", "attori"], inplace=True)
print(table.isnull().sum())
print("Numero di righe:", table.index)


"""
    ELIMINAZIONE DELLE RIGHE CON VALORI NULLI SU
        registi, descrizione, genere, paese
"""
print("\n\nEffettuo eliminazione per righe: ")
table.dropna(subset=["registi", "descrizione", "genere", "paese"], inplace=True)
print(table.isnull().sum())
print("Numero di righe:", table.index)


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

# RIMOZIONE filmtv_id
table.drop(columns=["filmtv_id"], inplace=True)

"""
    Grafico prima del Feature Selection
"""
numericCol = table.select_dtypes(include=[np.number]).columns

plt.figure(figsize = (15,4))
plt.title("Box Plot prima del Feature Scaling")
sns.boxplot(data = table[numericCol], orient = "h")

"""
    MIN-MAX NORMALIZZATION (range 0-1)
"""
# min_max_scaler = preprocessing.MinMaxScaler()
#
# numericCol = table.select_dtypes(include=[np.number]).columns
# table[numericCol] = min_max_scaler.fit_transform(table[numericCol].to_numpy())
# print(table[numericCol])
#
# plt.figure(figsize = (15,4))
# plt.title("Box Plot con il Feature Scaling con Min-Max Normalization")
# sns.boxplot(data = table[numericCol], orient = "h")
# plt.show()
#

"""
    Z-SCORE NORMALIZZATION
"""

numericCol = table.select_dtypes(include=[np.number]).columns

scaler = StandardScaler()
table[numericCol] = scaler.fit_transform(table[numericCol])
# table[numericCol] = pd.DataFrame( scaled_array, columns = table[numericCol].columns)

print(table[numericCol])

plt.figure(figsize = (15,4))
plt.title("Box Plot con il Feature Scaling con Z-Score Normalization")
sns.boxplot(data = table[numericCol], orient = "h")
# plt.show()

# per ogni colonna della tabella mostra le statistiche
for col in table:
    print(col, "\n", table[col].describe(), "\n\n")


#       -------- FEATURE SELECTION --------

"""
    ELIMINAZIONE DI FEATURE CON BASSA VARIANZA
    (eliminazione di feature con varianza <= 0)
"""

numericCol = table.select_dtypes(include=[np.number]).columns

lowVariance = VarianceThreshold()
lowVariance.fit_transform(table[numericCol])

# eliminazione delle colonne con varianza <= 0
for i, col in enumerate(numericCol):
    if lowVariance.variances_[i] <= 0:
        table.drop(columns=col, inplace=True)

print(table.columns)


"""
    ELIMINAZIONE UNIVARIATA DI FEATURE
"""

numericCol = table.select_dtypes(include=[np.number]).columns

tableNum = table[numericCol]

#heatmap per la matrice di correlazione
plt.figure(figsize = (15,6))
plt.title("Matrice di correlazione tra le variabili numeriche")
sns.heatmap(tableNum.corr(), annot=True)


#Dopo un confronto abbiamo deciso di togliere anno e voto_medio perchè
#sono inversamente correlate con le altre variabili
"""
    RIMOZIONE voto_medio, anno e durata
"""
# la colonna non viene eliminata, ma non considerata come feature. Serve solo come chiave con 'titolo_italiano'
# table.drop(columns=["anno"], inplace=True)
table.drop(columns=["voto_medio"], inplace=True)
table.drop(columns=["durata"], inplace=True)

# numericCol = table.select_dtypes(include=[np.number]).columns
numericCol = ["erotismo", "tensione", "impegno", "ritmo", "humor", "voti_totali"]
tableNum = table[numericCol]

plt.figure(figsize = (15,6))
plt.title("Matrice di correlazione tra le variabili numeriche dopo la rimozione di anno, voto_medio e durata")
sns.heatmap(tableNum.corr(), annot=True)
plt.show()


"""
    Ricreare il file film.csv
"""
# table.to_csv("film.csv")

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

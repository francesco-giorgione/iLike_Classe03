from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.decomposition import PCA

# Caricamento del dataset
table = pd.read_csv('film.csv', sep=',')

numericCol = ["erotismo", "tensione", "impegno", "ritmo", "humor", "voti_totali"]
tableNum = table[numericCol]

pca = PCA()
# Transformazione dei dati data
df = pca.fit_transform(tableNum)

# Inizializzazione di una lista per memorizzare i valori di inerzia
inertias = []

#k-means su un numero crescente di cluster
for k in range(1, 11):
    kmeans = KMeans(n_clusters=k, random_state=0, n_init=10).fit(tableNum)
    inertias.append(kmeans.inertia_)

# Disegno del grafico dei valori di inerzia
plt.plot(range(1, 11), inertias, marker='o')
plt.xlabel('Numero di cluster')
plt.ylabel('Inerzia')
plt.show()

from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.decomposition import PCA

# Carica il dataset
table = pd.read_csv('film.csv', sep=',')

numericCol = ["erotismo", "tensione", "impegno", "ritmo", "humor", "voti_totali"]
tableNum = table[numericCol]

pca = PCA()
# Transformazione dei dati data
df = pca.fit_transform(tableNum)

# Inizializza una lista per memorizzare i valori di inerzia
inertias = []

# Esegui il k-means su un numero crescente di cluster
for k in range(2, 25):
    kmeans = KMeans(init='k-means++', algorithm='elkan', n_clusters=k, random_state=0, n_init='auto').fit(tableNum)
    inertias.append(kmeans.inertia_)

# Disegna un grafico dei valori di inerzia
plt.plot(range(2, 25), inertias, marker='o')
plt.xlabel('Numero di cluster')
plt.ylabel('Inerzia')
plt.show()

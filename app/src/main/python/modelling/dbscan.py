import matplotlib.pyplot as plt
from sklearn.cluster import DBSCAN
import pandas as pd
import numpy as np
from sklearn.decomposition import PCA
from sklearn.metrics import silhouette_samples

plt.rcParams["figure.figsize"] = (14, 10)

filmPath = 'film.csv'

#Aprire il file csv e ottenere un oggetto DataFrame
table = pd.read_csv(filmPath, sep=',')
# table.drop(columns=['Unnamed: 0'], inplace=True)

numericCol = ["erotismo", "tensione", "impegno", "ritmo", "humor", "voti_totali"]
tableNum = table[numericCol]

pca = PCA()

#Transform the data
X = pca.fit_transform(tableNum)

# istanzio la classe di clustering DBSCAN
dbscan = DBSCAN(eps=1, min_samples=6)
# eseguo fitting e predizione in una volta sola
y_dbscan = dbscan.fit_predict(X)
table['Cluster'] = dbscan.labels_

# print(table)

# visualizzo il risultato
# u_labels = np.unique(dbscan)
#
# for i in u_labels:
#     plt.scatter(X[dbscan == i , 0] , X[dbscan == i , 1] , label = i)

# plt.scatter(X[:, 0], X[:, 1], c=y_dbscan, cmap="viridis", s=300, edgecolors="black")
# plt.legend()
# plt.title("Clustering con DB-Scan")
# plt.show()

# table.to_csv("filmClusterDBScan.csv")


"""
    SILHOUETTE SCORE
"""

# crea due grafici su una riga
fig, ax = plt.subplots(1, 2, figsize=(15, 7))
# Calcolare il Silhouette score per ciascun campione.
silhouette_val = silhouette_samples(X, y_dbscan)
y_ticks = []
# per impostasre l'asse y
print(np.unique(y_dbscan))
y_lower = y_upper = 0
for i, cluster in enumerate(np.unique(y_dbscan)):
    # Prendiamo tutti gli elementi del cluster corrente
    cluster_silhouette_val = silhouette_val[y_dbscan == cluster]
    cluster_silhouette_val.sort()
    y_upper += len(cluster_silhouette_val)

    # Crea un grafico a barre orizzontale.
    ax[0].barh(range(y_lower, y_upper), cluster_silhouette_val, height=1)
    ax[0].text(-0.03, (y_lower + y_upper)/2, str(i-1))
    y_lower += len(cluster_silhouette_val)

    avg_score = np.mean(silhouette_val)
    ax[0].axvline(avg_score, linestyle='--', linewidth=2, color='red')
    ax[0].set_yticks([])
    ax[0].set_xlim([-0.1, 1])
    ax[0].set_xlabel('Coefficienti Silhoette Score')
    ax[0].set_ylabel('Cluster')

    plt.scatter(X[:, 0], X[:, 1], c=y_dbscan)
    ax[1].set_title("Clustering con DB-Scan: intorno = 1.2 e numero minimo di punti = 200 ")
    plt.tight_layout()

plt.show()
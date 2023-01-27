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
dbscan = DBSCAN(eps=1, min_samples=15)
# eseguo fitting e predizione in una volta sola
y_dbscan = dbscan.fit_predict(X)
table['Cluster'] = dbscan.labels_

# print(table)

# visualizzo il risultato
u_labels = np.unique(dbscan)

for i in u_labels:
    plt.scatter(X[dbscan == i , 0] , X[dbscan == i , 1] , label = i)

plt.scatter(X[:, 0], X[:, 1], c=y_dbscan, cmap="viridis", s=300, edgecolors="black")
plt.legend()
plt.title("Clustering con DB-Scan")
plt.show()

# table.to_csv("filmClusterDBScan.csv")



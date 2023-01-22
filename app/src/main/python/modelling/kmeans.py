
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA
from sklearn.cluster import KMeans

filmPath = 'film.csv'
# Aprire il file csv e ottenere un oggetto DataFrame
table = pd.read_csv(filmPath, sep=',')

numericCol = ["erotismo", "tensione", "impegno", "ritmo", "humor", "voti_totali"]
tableNum = table[numericCol]

# print(tableNum)

"""
    K-MEANS
"""

pca = PCA()
# Transformazione dei dati data
df = pca.fit_transform(tableNum)

"""
    considerazioni sulle scelte dei parametri della funzione KMeans
"""
kmeans = KMeans(init='k-means++', algorithm='lloyd', n_init='auto')
label = kmeans.fit_predict(df)

# salvo per ogni elemento il cluster di appartenenza
table['Cluster'] = kmeans.labels_

"""
    NUMERO DI ITERAZIONI
    print(kmeans.n_iter_)
"""

"""
    Visualizazione del clustering con K-Means
"""
# visualizzazione dei centroidi
u_labels = np.unique(label)

for i in u_labels:
    plt.scatter(df[label == i , 0] , df[label == i , 1] , label = i)

centroids = kmeans.cluster_centers_
plt.scatter(centroids[:,0] , centroids[:,1] , s = 80, color = 'k')

plt.legend()
plt.title("Clustering con K-means")
plt.show()

# salvataggio del dataset con i cluster
# table.to_csv("filmClusterKMeans.csv")
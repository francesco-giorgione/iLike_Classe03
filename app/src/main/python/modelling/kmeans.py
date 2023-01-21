import random

import numpy as np  # For data management
import pandas as pd  # For data management

import seaborn as sns
import matplotlib.pyplot as plt  # For data visualization
from numpy import mean
from pandas import DataFrame

from sklearn.decomposition import PCA
from sklearn.cluster import KMeans  # To instantiate, train and use model
from sklearn.metrics import DistanceMetric

filmPath = 'film.csv'

# Aprire il file csv e ottenere un oggetto DataFrame
table = pd.read_csv(filmPath, sep=',')
table.drop(columns=['Unnamed: 0'], inplace=True)

numericCol = table.select_dtypes(include=[np.number]).columns

tableNum = table[numericCol]
table["index"] = table.index


# Initialize the class object


# predict the labels of clusters.


# Getting unique labels


# plotting the results:
# for i in u_labels:
#     plt.scatter(df[label == i , 0] , df[label == i , 1] , label = i)
#
# plt.legend()
#
# centroids = kmeans.cluster_centers_
# plt.scatter(centroids[:,0] , centroids[:,1] , s = 80, color = 'k')
#
# plt.show()


# inizio addestramento



# print(a)


"""
    K-MEANS
"""

pca = PCA()

# Transform the data
df = pca.fit_transform(tableNum)

# considerazioni sulle scelte dei parametri della funzione KMeans
kmeans = KMeans(init='k-means++', algorithm='lloyd', n_init='auto')
label = kmeans.fit_predict(df)
table['Cluster'] = kmeans.labels_

# print(kmeans.n_iter_)
u_labels = np.unique(label)

# importare lista da Java
a = []

for i in range(0, 10):
    x = int(random.uniform(0, len(table)))
    print(table.loc[x])
    a.append(table.loc[x])

# print(a)

# creare un  dizionario (chiave, valore) con (titolo, cluster)
# array da 0 a 9 contenente per ogni cella il numero di contenuti della lista appartenenti
# a quel determinato cluster
d = dict()
list = [0, 0, 0, 0, 0, 0, 0, 0]

# per ogni elemento della lista, trovare il cluster di appartenenza
for i in range(len(table)):
    for elemento in a:
        if elemento['titolo_italiano'] == table['titolo_italiano'].loc[i]:
            d[elemento['titolo_italiano']] = table['Cluster'].loc[i]
            list[table['Cluster'].loc[i]] += 1


print(d)
print(list)


# prendere max dell'array = cluster di riferimento
# ricavare l'indice del max dell'array
max = list[0]
cluster = 0
for i, x in enumerate(list):
    if x > max:
        max = x
        cluster = i


print(max)
print(cluster)
# print(a)

# per ogni elemento nel dizionario avente cluster == indice del max dell'array
newList = []
for elemento in a:
    if elemento['titolo_italiano'] in d.keys() and d[elemento['titolo_italiano']] == cluster:
        newList.append([elemento['durata'], elemento['voti_totali'], elemento['humor'], elemento['ritmo'],
                        elemento['impegno'], elemento['tensione'], elemento['erotismo']])


tableCluster = DataFrame(columns=['durata', 'voti_totali', 'humor', 'ritmo', 'impegno',
                                  'tensione', 'erotismo'])
y = 0
for i in range(len(table)):
    if table['Cluster'].loc[i] == cluster:
        tableCluster.loc[y] = [table['durata'].loc[i], table['voti_totali'].loc[i],table['humor'].loc[i],
                               table['ritmo'].loc[i], table['impegno'].loc[i],table['tensione'].loc[i],
                               table['erotismo'].loc[i]]
        y += 1


print(tableCluster)

# fare la media tra le distanze metriche degli elementi della lista
# recupera l'elemento con distanza minima
"""
    DISTANCE METRIC
"""

dist = DistanceMetric.get_metric('euclidean')
distanze = dist.pairwise(newList, tableCluster)

min = None
for i, x in enumerate(distanze):
    media = mean(x)
    if min is None or media < min[0]:
        min = (media, i)

print(min)
print(tableCluster.loc[min[1]])




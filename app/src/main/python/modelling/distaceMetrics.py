import random
from statistics import mean

import pandas as pd
from pandas import DataFrame
from sklearn.metrics import DistanceMetric

# importare lista da Java
a = []

filmPath = 'filmClusterKMeans.csv'
# Aprire il file csv e ottenere un oggetto DataFrame
table = pd.read_csv(filmPath, sep=',')

numericCol = ["erotismo", "tensione", "impegno", "ritmo", "humor", "voti_totali"]
tableNum = table[numericCol]

for i in range(0, 10):
    x = int(random.uniform(0, len(table)))
    # print(table.loc[x])
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
        if elemento['titolo_italiano'] == table['titolo_italiano'].loc[i] and elemento['anno'] == table['anno'].loc[i]:
            key = elemento['titolo_italiano'] + '--' + str(elemento['anno'])
            d[key] = table['Cluster'].loc[i]
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


# salvo la lista che hanno nel dizionario il cluster == indice del max dell'array
newList = []
for elemento in a:
    key = elemento['titolo_italiano'] + '--' + str(elemento['anno'])
    if d[key] == cluster:
        newList.append([elemento['voti_totali'], elemento['humor'], elemento['ritmo'],
                        elemento['impegno'], elemento['tensione'], elemento['erotismo']])


tableCluster = DataFrame(columns=['voti_totali', 'humor', 'ritmo',
                                  'impegno', 'tensione', 'erotismo',
                                  'titolo_italiano', 'anno'])
y = 0
for i in range(len(table)):
    if table['Cluster'].loc[i] == cluster:
        tableCluster.loc[y] = [table['voti_totali'].loc[i], table['humor'].loc[i], table['ritmo'].loc[i],
                               table['impegno'].loc[i], table['tensione'].loc[i], table['erotismo'].loc[i],
                               table['titolo_italiano'].loc[i], table['anno'].loc[i]]
        y += 1


print(tableCluster)


"""
    DISTANCE METRIC
"""
# calcoli la media tra le distanze metriche degli elementi della lista e tutti gli altri cluster
print(newList)
dist = DistanceMetric.get_metric('euclidean')
numTableCluster = ['voti_totali', 'humor', 'ritmo',
                    'impegno', 'tensione', 'erotismo']
distanze = dist.pairwise(newList, tableCluster[numericCol])


# recuperiamo l'elemento con distanza minima
min = None
for i, x in enumerate(distanze):
    print(len(x))
    media = mean(x)
    if min is None or media < min[0]:
        min = (media, i)


print(distanze)
print(min)
print("Il film selezionionato è -> ")
print(tableCluster.loc[min[1]])

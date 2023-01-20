import random

import numpy as np                                # For data management
import pandas as pd                               # For data management

import matplotlib.pyplot as plt                   # For data visualization

from sklearn.decomposition import PCA
from sklearn.cluster import KMeans                # To instantiate, train and use model
filmPath = 'film.csv'

#Aprire il file csv e ottenere un oggetto DataFrame
table = pd.read_csv(filmPath, sep=',')
table.drop(columns=['Unnamed: 0'], inplace=True)

numericCol = table.select_dtypes(include=[np.number]).columns

tableNum = table[numericCol]

pca = PCA(2)

#Transform the data
df = pca.fit_transform(tableNum)

#Initialize the class object
#considerazioni sulle scelte dei parametri della funzione KMeans
kmeans = KMeans(init='k-means++', n_clusters=10, algorithm='lloyd', n_init='auto')

#predict the labels of clusters.
label = kmeans.fit_predict(df)
print(label)

print(kmeans.n_iter_)

#Getting unique labels
u_labels = np.unique(label)

#plotting the results:
for i in u_labels:
    plt.scatter(df[label == i , 0] , df[label == i , 1] , label = i)

plt.legend()

centroids = kmeans.cluster_centers_
plt.scatter(centroids[:,0] , centroids[:,1] , s = 80, color = 'k')

#TOGLIERE IL COMMENTO
# plt.show()

table["index"] = table.index

print(table)

a = []

for i in range(0,10):
    a.append(table.loc(int (random.uniform(0,len(table)))))

print(a)



#importare lista da Java
#per ogni elemento della lista, trovare il cluster di appartenenza
 #creare un  dizionario (chiave, valore) con (titolo, cluster)
 #array da 0 a 9 contenente per ogni cella il numero di contenuti della lista appartenenti
 #a quel determinato cluster
#prendere max dell'array
#ricavare l'indice del max dell'array

#per ogni elemento nel dizionario avente cluster == indice del max dell'array
 #fare la media tra le distanze metriche degli elementi della lista

#media della somma calcolata nel ciclo


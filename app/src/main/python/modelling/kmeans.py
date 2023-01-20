import pandas as pd
import numpy as np
import seaborn as sns
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA

# See PyCharm help at https://www.jetbrains.com/help/pycharm/

filmPath = 'film.csv'

#Aprire il file csv e ottenere un oggetto DataFrame
table = pd.read_csv(filmPath, sep=',')
table.drop(columns=['Unnamed: 0'], inplace=True)

kmeans = KMeans(n_clusters=3, init='random', n_init=10, max_iter=300, random_state=0)
#bench_k_means(kmeans=kmeans, name="random", data=table, labels=labels)

numericCol = table.select_dtypes(include=[np.number]).columns
kmeans.fit(table[numericCol])
# assegno una classe ad ogni esempio del dataset
k = kmeans.predict(table[numericCol])

print(k)

centroids = kmeans.cluster_centers_
print(centroids)

table['Clusters'] = kmeans.labels_

plt.figure(figsize = (20, 8))
sns.scatterplot(data=table)
plt.xlim(-10,10)
plt.ylim(-10,10)
plt.show()
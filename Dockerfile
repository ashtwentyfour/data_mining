FROM ashtwentyfour/java-node

RUN mkdir -p /code

COPY apriori_algorithm /code/apriori_algorithm

COPY fp_growth /code/fp_growth

COPY id3_decision_tree /code/id3_decision_tree

COPY k_means_clustering /code/k_means_clustering

COPY package.json /code/package.json

COPY server.js /code/server.js

COPY run_apriori.sh /code/run_apriori.sh

COPY run_id3.sh /code/run_id3.sh

COPY run_fpgrowth.sh /code/run_fpgrowth.sh

COPY run_kmeans.sh /code/run_kmeans.sh

COPY index.html /code/index.html

WORKDIR /code

RUN npm install

EXPOSE 8081

ENTRYPOINT ["npm", "start"]


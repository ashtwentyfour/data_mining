FP Growth algorithm for mining frequent itemsets
=============================

This package can be used to extract frequent itemsets from a set of transactions by using the FP Growth algorithm
---------------------------------

**INSTRUCTIONS (to use object of class FP_Growth)**

- create an object of class FP_Growth: FP_Growth fp = new FP_Growth()
- call function 'fp_algorithm' on the object and specify the minimum support and the input file path
- example provided in Driver.java
- minimum support > 0

**TO BUILD DOCKER IMAGE**

- run 'docker build -t <image_name> .'
- deploy container 'docker run image_name'
- the change the input to the image, change the input file in test_cases and build the image once again 

**TO RUN USING docker-compose**

- run 'docker-compose up'




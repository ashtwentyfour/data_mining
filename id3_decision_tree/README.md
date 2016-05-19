ID3 Classification Algorithm 
====================

This package can be imported and used for decision making. The package implements the ID3 classification algorithm
-----------------------------

**INSTRUCTIONS (to use the ID3 class)**

- declare an object of class ID3: ID3 decision = new ID3()
- invoke the function 'train_data' on this object with the input data file and the decision attribute as arguments: decision.train_data(file , data)
- predict the outcome for a new tuple by calling the 'predict_value' function: decision.predict_value(attr , vals) - where attr is the list of attributes of the tuple and vals, the corresponding list of values
- follow Driver.java for steps

**TO BUILD A DOCKER IMAGE**
- run 'docker build .'
- locate the image code (by running 'docker images') and run 'docker run <Image ID>)

**TO RUN USING docker-compose**
- run 'docker-compose up'


 




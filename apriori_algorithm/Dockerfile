FROM java:8

MAINTAINER ashtwentyfour

WORKDIR /code

ADD src /code/src
ADD bin /code/bin
ADD test_cases /code/test_cases

RUN javac -d /code/bin -sourcepath /code/src /code/src/apriori_algo/*.java
CMD ["java", "-cp", "/code/bin", "apriori_algo.Driver", "/code/test_cases/test_input.txt"]

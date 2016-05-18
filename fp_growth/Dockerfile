FROM java:8

MAINTAINER ashtwentyfour

WORKDIR /code

ADD src /code/src
ADD bin /code/bin
ADD test_cases /code/test_cases

RUN javac -d /code/bin -sourcepath /code/src/fp_growth/*.java
CMD ["java", "-cp", "/code/bin", "fp_growth.Driver", "/code/test_cases/test_input.txt"]

FROM java:8

WORKDIR /code

ADD src /code/src
ADD bin /code/bin
ADD test_cases /code/test_cases

RUN javac -d bin -sourcepath /code/src/fp_growth/*.java
CMD ["java", "-cp", "bin", "fp_growth.Driver", "/code/test_cases/test_input.txt"]

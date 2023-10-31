# kotlin-csvParser

kotlin-csvParser is a Kotlin project used to parse political speech data in CSV format and generate speech statistics. The statistics contain answers to the following questions:

1. Which politician gave the most speeches in 2013?
2. Which politician gave the most speeches on "homeland security"?
3. Which politician spoke the fewest words overall?

## Installation

To start the project please run

```bash
mvn clean compile spring-boot:run
```

or you can build Docker image and run as a Docker container

```bash
docker build  --build-arg JAR_FILE="target/political-speech-0.0.1-SNAPSHOT.jar" -t tugra/political-speech  .

docker run  -p 8081:8081 --name speech-processor tugra/political-speech
```

## Usage

Using your browser, go to the links below

http://localhost:8081/speech/evaluation?url1=http://localhost:8081/testCsv.csv

http://localhost:8081/speech/evaluation?url1=http://localhost:8081/testCsv.csv&url2=http://localhost:8081/testCsv2.csv

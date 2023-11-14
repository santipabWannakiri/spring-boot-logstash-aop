# spring-boot-logstash-aop
## Introduction
Expanding on the earlier article's exploration of centralized logging with AOP, the focus now turns to a thorough examination of log management strategies. 

Moving beyond the basic act of coding to record logs in files, the emphasis is on envisioning how logs can actively contribute to investigating problems and, ideally, be utilized for predictive analysis to anticipate issues in the future.\
One of the most recognized tech stacks for log management is the ELK Stack (Elasticsearch, Logstash, and Kibana). Renowned for its potency, the ELK Stack excels in preserving, filtering, transforming, and searching logs in near real-time.\
However, this article will not utilize the entire ELK stack. Instead, specific components like Logstash will be employed and integrated with other tools. To facilitate understanding, refer to the illustration below, where I will delve into detailed explanations.

<img src="images/teach-stack-overview.JPG"  alt="image description" width="1000" height="180">

In the illustration, you're going to see that I try to setup "hybrid logging solution". This reflects the combination of on-premise components (Spring Boot, Filebeat, Logstash) and cloud-based storage (S3) for storing logs.\
Please take a look at the details for each one below.

`Spring Boot` is responsible for producing log messages related to your application's behavior. These logs could include information about transactions, errors, and other relevant events.

`Filebeat` is employed to read logs generated by Spring Boot. It efficiently harvests log data and sends it to Logstash for further processing. Filebeat also facilitates the filtering of log entries before reaching Logstash.

`Logstash` acts as an intermediary between Filebeat and S3. It processes incoming log entries, applies filters, and prepares the data for storage in S3. It can also enrich logs with additional information, if needed.

`S3` serves as the ultimate destination for log storage. Logstash uploads processed log files to S3, where they can be archived, analyzed, or accessed as needed. 

## Scenario
I would like to briefly describe the scenario that we will implement before we drill down into the configuration part.
According to the illustration below, it's an example of a log format that Spring Boot is going to write into a log file. And you might notice that the log has a pretty good format; this is because I implemented it using Elastic Common Schema (ECS).\
`The actual log from our application is going to be kept inside the "message"`
<img src="images/spring-boot-log.JPG"  alt="image description" width="1000" height="180">
Certainly! In our scenario, we need to achieve the following:

1.`Drop Fields`: Eliminate the className and methodName fields from the logs before they are sent to S3. We'll utilize `Filebeat` to handle this task.

2.`Mark Identification Number`: Apply the identification number "xxxxxxxxxxx" to the logs. This marking operation will be performed by `Logstash`.

By adopting this approach, we showcase the capabilities of both Filebeat and Logstash in our log processing pipeline. Filebeat takes care of dropping specific fields, while Logstash handles the marking process.

## Spring Boot configuration
The origin of the log files mentioned earlier is Spring Boot. To create log files, it's essential to set up and configure a log framework like logback within Spring Boot. Assuming this configuration is in place, the next step involves adapting the log file format to conform with the Elastic Common Schema (ECS). To achieve this, importing the co.elastic.logging dependency is necessary, and a few adjustments in the logback file are required. For detailed instructions, you can refer to the article.\
For additional information, please look at [ECS Logging Java](https://www.elastic.co/guide/en/ecs-logging/java/current/setup.html)

## Filebeat configuration
Configuration with Filebeat is an essential thing to do because Filebeat's going to monitor our log file and push new logs to the destination. However, aside from configuring the tallying log, we also need to configure Filbeat to drop some files as well.
I will provide an example of Filebeat below.

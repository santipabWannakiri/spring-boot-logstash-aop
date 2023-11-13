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

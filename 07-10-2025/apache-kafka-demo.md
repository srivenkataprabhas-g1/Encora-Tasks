# Apache Kafka Demo (07-10-2025)
## Setting Up Server
Follow these commands(terminal, path-go to kafka):
- For creation of Cluster ID(UUID)
<pre>.\bin\windows\kafka-storage.bat random-uuid</pre>
- For formatted Storage
<pre>.\bin\windows\kafka-storage.bat format -t <uuid which is generated> -c <location of broker.properties in config folder> </pre>
- For Server Start
<pre>.\bin\windows\kafka-server-start.bat <location of broker.properties></pre>
### Output
<img width="1354" height="677" alt="image" src="https://github.com/user-attachments/assets/afde2264-90e3-4f4e-938c-90b106b7c5a6" />

## Topic Related Cmds
- Create a Topic
<pre>.\bin\windows\kafka-topics.bat --create --topic test-demo --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1 </pre>
### Output
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/6454503e-8be1-4bc9-a0c3-ea5c6391fad2" />
- List Topics
<pre> .\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092 </pre>
### Output
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/9fdbc2d8-02f7-4e95-b7aa-1bf5f2ea73ff" />

## Producer and Consumer
- Consumer creation
<pre>.\bin\windows\kafka-console-consumer.bat --topic test-demo --from-beginning --bootstrap-server localhost:9092</pre>
- Producer creation
<pre>.\bin\windows\kafka-console-producer.bat --topic test-demo --bootstrap-server localhost:9092 --property "parse.key=true" --property "key.separator=:"</pre>
### Output
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/8b73ecbc-6c4f-44f7-ad98-972872e1b6c6" />

###Create a new topic into which we'll write and read some test messages

docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
--create \
--topic quickstart
package com.nordic;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class EventHubPublisherSSL {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        String j = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";";
        props.put("sasl.mechanism", "PLAIN");
        props.put("security.protocol", "SASL_SSL");
//        props.put("ssl.truststore.password", "elfi-kafka-uat");
        props.put("bootstrap.servers", "prod-elfi-ns.servicebus.windows.net:9093");
        props.put("ssl.endpoint.identification.algorithm", "");
        props.put("sasl.jaas.config", String.format(j, "$ConnectionString", "Endpoint=sb://prod-elfi-ns.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=EdpoUmkRAR7bpXa6rccIAkI7RxJ4jqqdzA0wtDSTZG0="));
//        props.put("ssl.truststore.location",
//                "/Users/mikael/Workspace/Fortress/rover.kafka/module.004/src/main/resources/kafka.truststore.uat.jks");

        List<Header> headers = List.of(new RecordHeader("applicationName", "sasl.test.publisher".getBytes()));
        Future<RecordMetadata> future = new KafkaProducer<>(props).send(
                new ProducerRecord<>
                        ("notification", 0, "mail"
                                , "{\n" +
                                "    \"data\": {\n" +
                                "        \"name\": \"Mike\",\n" +
                                "        \"score\": \"87\",\n" +
                                "        \"someOtherObject\": {\n" +
                                "            \"first\": \"second\"\n" +
                                "        }\n" +
                                "    },\n" +
                                "    \"attachments\": [\n" +
                                "        {\n" +
                                "            \"filename\": \"202207200121NotificationDetails.zip\",\n" +
                                "            \"directory\": \"uat/Result/2022_20_07\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "            \"filename\": \"11_39_44_EXPORT_BC_PP_details.txt\",\n" +
                                "            \"directory\": \"uat/Result/2022_28_07\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "            \"filename\": \"11_39_44_EXPORT_BC_PP_phoneFormat.csv\",\n" +
                                "            \"directory\": \"uat/Result/2022_28_07\"\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}", headers));

        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

package com.nordic;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Publisher {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        List<Header> headers = Arrays.asList(new RecordHeader("first_header", "sample_value".getBytes()),
                new RecordHeader("second_header", "sample_value".getBytes()));

        Future<RecordMetadata> future = new KafkaProducer<>(props).send(
                new ProducerRecord<>
                        ("notification", 0, "mail"
                                , "{\n" +
                                "    \"data\": [\n" +
                                "        {\n" +
                                "            \"key\": \"name\",\n" +
                                "            \"value\": \"Oli\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "            \"key\": \"score\",\n" +
                                "            \"value\": \"37\"\n" +
                                "        }\n" +
                                "    ],\n" +
                                "    \"directories\": [\n" +
                                "        {\n" +
                                "            \"name\": \"202207200121NotificationDetails.zip\",\n" +
                                "            \"directory\": \"uat/Result/2022_20_07\"\n" +
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

package com.example;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

public  class TextFileProcessor implements RequestHandler<S3Event, String> {

    private final S3Client s3Client = S3Client.create();
    private final DynamoDbClient dynamoDbClient = DynamoDbClient.create();
    private static final String TABLE_NAME = "FileProcessingResults";
    
    @Override
    public String handleRequest(S3Event event, Context context) {
        try {
            var record = event.getRecords().get(0);
            String bucket = record.getS3().getBucket().getName();
            String key = record.getS3().getObject().getKey();

           context.getLogger().log("Processing file: " + key);

            // Read file from S3
            GetObjectResponse s3Object = s3Client.getObject(
                GetObjectRequest.builder().bucket(bucket).key(key).build(),
                (response, inputStream) -> {
                    try {
                        return response;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            );

            InputStream inputStream = s3Client.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build());
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Count lines, words, characters
            long lineCount = content.lines().count();
            long wordCount = Arrays.stream(content.split("\\s+")).filter(s -> !s.isBlank()).count();
            long charCount = content.length();

            // Get preview
            String preview = content.length() > 100 ? content.substring(0, 100) : content;

            // Store results in DynamoDB
            Map<String, AttributeValue> item = new HashMap<>();
            item.put("fileName", AttributeValue.builder().s(key).build());
            item.put("lineCount", AttributeValue.builder().n(String.valueOf(lineCount)).build());
            item.put("wordCount", AttributeValue.builder().n(String.valueOf(wordCount)).build());
            item.put("charCount", AttributeValue.builder().n(String.valueOf(charCount)).build());
            item.put("preview", AttributeValue.builder().s(preview).build());
            item.put("processedDate", AttributeValue.builder().s(Instant.now().toString()).build());

            dynamoDbClient.putItem(PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(item)
                    .build());

            context.getLogger().log("File processed successfully: " + key);
            return "Success";

        } catch (Exception e) {
            context.getLogger().log("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

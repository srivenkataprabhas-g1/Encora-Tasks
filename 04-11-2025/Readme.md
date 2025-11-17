# Question 
Create a file processing system that automatically processes text files uploaded to an S3 bucket using a Java Lambda function.
## Requirements:
1. S3 Bucket
Create an S3 bucket: file-processing-bucket-<your-name>
Configure an event notification to trigger Lambda on .txt file uploads
2. Java Lambda Function
- Function Name:  TextFileProcessor
- Runtime: Java 17
- Trigger: S3 Event (ObjectCreated)
- Functionality: Read the uploaded text file from S3
- Count: lines, words, and characters
- Extract the first 100 characters as a preview Store processing results in a DynamoDB table
3. DynamoDB Table
- Table Name:  FileProcessingResults
- Partition Key:  fileName (String)
- Attributes:  lineCount, wordCount, charCount, preview, processedDate
- File Format to Process: Sample text file content with multiple lines for testing the processor
- Expected Processing Result: JSON
<pre>
{
 "fileName": "sample.txt",
 "lineCount": 3,
 "wordCount": 12,
 "charCount": 58,
 "preview": "Sample text file content with multiple lines for testing...",
 "processedDate": "2024-01-15T10:30:00Z"
}
</pre>
## Implementation Tasks:
- Create S3 bucket with event notification
- Create DynamoDB table
- Write Java Lambda function that:
  - Handles S3 trigger event
  - Reads the text file from S3
  - Processes the content
  - Saves results to DynamoDB
- Configure IAM roles with appropriate permissions
## Success Criteria:
- Upload a .txt file to S3
- Lambda automatically triggers and processes the file
- Processing results are stored in DynamoDB
- No errors in CloudWatch logs
## Deliverables:
- Java source code for Lambda function
- S3 event configuration
- IAM role policies
- Screenshots of:
 - Uploaded file in S3
 - Processed results in DynamoDB
 - Lambda execution logs
## Assessment Focus:
- S3 event configuration
- Java Lambda development
- S3 file reading in Java
- DynamoDB operations
- Error handling
# Solution
## Java Source Codes:
- TextFileProcessor Code:
 <pre>
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
    private static final String TABLE_NAME = "YOUR_TABLE_NAME";
    
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
 </pre>
- pom.xml:
```
 <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.encora</groupId>
  <artifactId>AWS-TextFileProcessor</artifactId>
  <version>1.0</version>
  <name>AWS-TextFileProcessor</name>
  
   <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <aws.sdk.version>2.25.12</aws.sdk.version>
    </properties>

    <dependencies>
        <!-- AWS SDK v2 for S3 -->
        <dependency>
            <groupId>software.amazon.awssdk</groupId>
            <artifactId>s3</artifactId>
            <version>${aws.sdk.version}</version>
        </dependency>

        <!-- AWS SDK v2 for DynamoDB -->
        <dependency>
            <groupId>software.amazon.awssdk</groupId>
            <artifactId>dynamodb</artifactId>
            <version>${aws.sdk.version}</version>
        </dependency>

        <!-- AWS Lambda Java Core -->
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-lambda-java-core</artifactId>
            <version>1.2.3</version>
        </dependency>

        <!-- AWS Lambda Java Events (for S3Event) -->
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-lambda-java-events</artifactId>
            <version>3.11.4</version>
        </dependency>

        <!-- Logging (optional but recommended) -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>2.0.13</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>

            <!-- Shade Plugin to create a fat JAR (required for Lambda deployment) -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.3</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <createDependencyReducedPom>false</createDependencyReducedPom>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.example.TextFileProcessor</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
- [Used this .jar file](https://github.com/srivenkataprabhas-g1/Encora-Tasks/blob/main/31-10-2025/Task%203/TextFileProcessor.jar)
## S3 Event Configuration

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/011fb72d-8c5f-4489-aec8-681b3aaa0b0a" />

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/34f6ab8c-f670-49e4-94f8-8f86d11a1919" />

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/a86b8b26-1bd4-4fee-bc14-0d58d1d37760" />

## IAM Role Policies

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/00008080-af3b-44fd-819e-7b79a763ff89" />

- AWSLambdaBasicExecutionRole
<pre>
 {
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "logs:CreateLogGroup",
                "logs:CreateLogStream",
                "logs:PutLogEvents"
            ],
            "Resource": "*"
        }
    ]
}
</pre>

- Task2AllowPolicy
<pre>
 {
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "S3ReadAccess",
            "Effect": "Allow",
            "Action": [
                "s3:GetObject",
                "s3:ListBucket"
            ],
            "Resource": [
                "arn:aws:s3:::file-processing-bucket-vpg",
                "arn:aws:s3:::file-processing-bucket-vpg/*"
            ]
        },
        {
            "Sid": "DynamoDBWriteAccess",
            "Effect": "Allow",
            "Action": [
                "dynamodb:PutItem",
                "dynamodb:UpdateItem"
            ],
            "Resource": "arn:aws:dynamodb:us-east-1:433980226768:table/FileProcessingResults"
        }
    ]
}
</pre>
# Screenshots
## Uploaded file in S3

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/1eb1c6c5-fc11-4051-b47c-69ccf4bb55f0" />

## Processed results in DynamoDB

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/44d61590-e87c-4061-9a05-d7d8258e0403" />

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/e930a009-9c1b-4661-b508-962655d72112" />

### Lambda execution logs
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/9142f4cc-8795-4910-80a7-dc289749d082" />

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/c678333f-8993-408b-b6ba-dde9c376493f" />

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/6d986347-1ba1-45d1-b871-ebed4de8834d" />

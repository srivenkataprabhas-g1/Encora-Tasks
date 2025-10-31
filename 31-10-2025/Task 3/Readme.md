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
- TextFileProcessor
<pre>
 package com.fileprocessor;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextFileProcessor implements RequestHandler<S3Event, String> {
    
    private static final Logger logger = LogManager.getLogger(TextFileProcessor.class);
    private final S3FileReader s3FileReader;
    private final FileProcessor fileProcessor;
    private final DynamoDBWriter dynamoDBWriter;

    public TextFileProcessor() {
        this.s3FileReader = new S3FileReader();
        this.fileProcessor = new FileProcessor();
        this.dynamoDBWriter = new DynamoDBWriter();
    }

    @Override
    public String handleRequest(S3Event event, Context context) {
        try {
            logger.info("Received S3 event: {}", event);
            
            // Extract S3 bucket and key from event
            S3EventNotification.S3EventNotificationRecord record = event.getRecords().get(0);
            String bucket = record.getS3().getBucket().getName();
            String key = record.getS3().getObject().getKey();
            
            logger.info("Processing file - Bucket: {}, Key: {}", bucket, key);
            
            // Validate file extension
            if (!key.toLowerCase().endsWith(".txt")) {
                logger.warn("Ignoring non-text file: {}", key);
                return "File is not a .txt file";
            }
            
            // Read file from S3
            String fileContent = s3FileReader.readFileFromS3(bucket, key);
            logger.info("File content read successfully, size: {} bytes", fileContent.length());
            
            // Process file content
            FileProcessingResult result = fileProcessor.processFileContent(key, fileContent);
            logger.info("File processed - Lines: {}, Words: {}, Characters: {}", 
                result.getLineCount(), result.getWordCount(), result.getCharCount());
            
            // Store result in DynamoDB
            dynamoDBWriter.writeResultToDynamoDB(result);
            logger.info("Result stored in DynamoDB for file: {}", key);
            
            return "Successfully processed file: " + key;
            
        } catch (Exception e) {
            logger.error("Error processing S3 event: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing file", e);
        }
    }
}
</pre>
- S3FileReader
<pre>
 package com.fileprocessor;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class S3FileReader {
    
    private static final Logger logger = LogManager.getLogger(S3FileReader.class);
    private final AmazonS3 s3Client;

    public S3FileReader() {
        this.s3Client = AmazonS3ClientBuilder.standard().build();
    }

    /**
     * Reads a text file from S3 and returns its content as a String
     *
     * @param bucketName S3 bucket name
     * @param objectKey   S3 object key (file path)
     * @return File content as String
     */
    public String readFileFromS3(String bucketName, String objectKey) {
        try {
            logger.info("Reading file from S3 - Bucket: {}, Key: {}", bucketName, objectKey);
            
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectKey);
            S3Object s3Object = s3Client.getObject(getObjectRequest);
            
            // Read the S3 object content
            String fileContent = new BufferedReader(
                    new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            
            logger.info("Successfully read file from S3. Size: {} characters", fileContent.length());
            return fileContent;
            
        } catch (Exception e) {
            logger.error("Error reading file from S3: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to read file from S3", e);
        }
    }
}
</pre>
- FileProcessor
<pre>
 package com.fileprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileProcessor {
    
    private static final Logger logger = LogManager.getLogger(FileProcessor.class);
    private static final int PREVIEW_LENGTH = 100;

    /**
     * Processes file content and extracts statistics
     *
     * @param fileName    Name of the file
     * @param fileContent Content of the file
     * @return FileProcessingResult with statistics
     */
    public FileProcessingResult processFileContent(String fileName, String fileContent) {
        try {
            logger.info("Starting file processing for: {}", fileName);
            
            // Count lines
            int lineCount = countLines(fileContent);
            
            // Count words
            int wordCount = countWords(fileContent);
            
            // Count characters (excluding newlines for accurate count)
            int charCount = fileContent.length();
            
            // Extract preview
            String preview = extractPreview(fileContent, PREVIEW_LENGTH);
            
            logger.info("File statistics - Lines: {}, Words: {}, Characters: {}", 
                lineCount, wordCount, charCount);
            
            FileProcessingResult result = new FileProcessingResult(
                fileName,
                lineCount,
                wordCount,
                charCount,
                preview
            );
            
            logger.info("File processing completed successfully");
            return result;
            
        } catch (Exception e) {
            logger.error("Error processing file content: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process file content", e);
        }
    }

    /**
     * Counts the number of lines in the content
     */
    private int countLines(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        return (int) content.lines().count();
    }

    /**
     * Counts the number of words in the content
     */
    private int countWords(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        
        // Split by whitespace and count non-empty tokens
        String[] words = content.trim().split("\\s+");
        int count = 0;
        
        for (String word : words) {
            if (!word.isEmpty()) {
                count++;
            }
        }
        
        return count;
    }

    /**
     * Extracts preview of the file content
     */
    private String extractPreview(String content, int length) {
        if (content == null) {
            return "";
        }
        
        if (content.length() <= length) {
            return content;
        }
        
        // Truncate and add ellipsis
        return content.substring(0, length) + "...";
    }
}
</pre>
- DynamoDBWriter
<pre>
 package com.fileprocessor;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DynamoDBWriter {
    
    private static final Logger logger = LogManager.getLogger(DynamoDBWriter.class);
    private static final String TABLE_NAME = "FileProcessingResults";
    private final AmazonDynamoDB dynamoDBClient;

    public DynamoDBWriter() {
        this.dynamoDBClient = AmazonDynamoDBClientBuilder.standard().build();
    }

    /**
     * Writes processing result to DynamoDB
     *
     * @param result FileProcessingResult to store
     */
    public void writeResultToDynamoDB(FileProcessingResult result) {
        try {
            logger.info("Writing result to DynamoDB for file: {}", result.getFileName());
            
            // Create item attributes
            Map<String, AttributeValue> item = new HashMap<>();
            
            // Add partition key
            item.put("fileName", new AttributeValue(result.getFileName()));
            
            // Add other attributes
            item.put("lineCount", new AttributeValue().withN(String.valueOf(result.getLineCount())));
            item.put("wordCount", new AttributeValue().withN(String.valueOf(result.getWordCount())));
            item.put("charCount", new AttributeValue().withN(String.valueOf(result.getCharCount())));
            item.put("preview", new AttributeValue(result.getPreview()));
            item.put("processedDate", new AttributeValue(result.getProcessedDate()));
            
            // Create PutItem request
            PutItemRequest putItemRequest = new PutItemRequest()
                    .withTableName(TABLE_NAME)
                    .withItem(item);
            
            // Execute put operation
            PutItemResult result_response = dynamoDBClient.putItem(putItemRequest);
            
            logger.info("Successfully written to DynamoDB. Response status code: {}", 
                result_response.getSdkHttpMetadata().getHttpStatusCode());
            
        } catch (Exception e) {
            logger.error("Error writing to DynamoDB: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to write result to DynamoDB", e);
        }
    }
}
</pre>
- FileProcessingResult
<pre>
 package com.fileprocessor;

import com.google.gson.annotations.SerializedName;
import java.time.Instant;

public class FileProcessingResult {
    
    @SerializedName("fileName")
    private String fileName;
    
    @SerializedName("lineCount")
    private int lineCount;
    
    @SerializedName("wordCount")
    private int wordCount;
    
    @SerializedName("charCount")
    private int charCount;
    
    @SerializedName("preview")
    private String preview;
    
    @SerializedName("processedDate")
    private String processedDate;

    // Default constructor for DynamoDB mapping
    public FileProcessingResult() {
        this.processedDate = Instant.now().toString();
    }

    // Constructor with parameters
    public FileProcessingResult(String fileName, int lineCount, int wordCount, 
                               int charCount, String preview) {
        this.fileName = fileName;
        this.lineCount = lineCount;
        this.wordCount = wordCount;
        this.charCount = charCount;
        this.preview = preview;
        this.processedDate = Instant.now().toString();
    }

    // Getters and Setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(String processedDate) {
        this.processedDate = processedDate;
    }

    @Override
    public String toString() {
        return "FileProcessingResult{" +
                "fileName='" + fileName + '\'' +
                ", lineCount=" + lineCount +
                ", wordCount=" + wordCount +
                ", charCount=" + charCount +
                ", preview='" + preview + '\'' +
                ", processedDate='" + processedDate + '\'' +
                '}';
    }
}
</pre>
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

# Question 
Create a file processing system that automatically processes text files uploaded to an S3 bucket using a Java Lambda function.
## Requirements:
1. S3 Bucket
Create an S3 bucket: file-processing-bucket-<your-name>
Configure an event notification to trigger Lambda on .txt file uploads
2. Java Lambda Function
Function Name:  TextFileProcessor
Runtime: Java 17
Trigger: S3 Event (ObjectCreated)
Functionality:
Read the uploaded text file from S3
Count: lines, words, and characters
Extract the first 100 characters as a preview
Store processing results in a DynamoDB table
3. DynamoDB Table
Table Name:  FileProcessingResults
Partition Key:  fileName (String)
Attributes:  lineCount, wordCount, charCount, preview, processedDate
File Format to Process:
Sample text file content
with multiple lines
for testing the processor
Expected Processing Result: JSON
{
 "fileName": "sample.txt",
 "lineCount": 3,
 "wordCount": 12,
 "charCount": 58,
 "preview": "Sample text file content with multiple lines for testing...",
 "processedDate": "2024-01-15T10:30:00Z"
}
Implementation Tasks:
Create S3 bucket with event notification
Create DynamoDB table
Write Java Lambda function that:
Handles S3 trigger event
Reads the text file from S3
Processes the content
Saves results to DynamoDB
Configure IAM roles with appropriate permissions
Success Criteria:
Upload a .txt file to S3
Lambda automatically triggers and processes the file
Processing results are stored in DynamoDB
No errors in CloudWatch logs
Deliverables:
Java source code for Lambda function
S3 event configuration
IAM role policies
Screenshots of:
Uploaded file in S3
Processed results in DynamoDB
Lambda execution logs
Assessment Focus:
S3 event configuration
Java Lambda development
S3 file reading in Java
DynamoDB operations
Error handling

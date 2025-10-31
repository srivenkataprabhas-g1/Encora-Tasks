# Question
Design and implement a real-time data processing pipeline for an e-commerce application that handles order events with the following requirements:
1. DynamoDB Streams: Configure a DynamoDB table Orders that streams all CRUD operations (INSERT, MODIFY, REMOVE)
2. EventBridge Pipes: Create an EventBridge Pipe that:
- Source: DynamoDB stream from the Orders table
- Filtering: Only process events where:
  - Order status is "pending" OR "shipped"
  - Order amount is greater than $100
  - Exclude test orders (customerEmail does not contain "test.com")
- Target: Lambda function for processing filtered orders
3. Advanced Filtering: Implement a second pipe that:
- Filters for high-value orders (amount > $1000)
- Routes to a different Lambda function for premium customer service
- Includes only MODIFY events where status changes from "pending" to "shipped"
4. Error Handling: Configure dead-letter queues for failed events and implement retry logic for transient failures

### Technical Requirements:
Use DynamoDB Streams with NEW_AND_OLD_IMAGES
Implement EventBridge Pipes with exact field matching and pattern matching
Create appropriate IAM roles for cross-service permissions
Handle batch processing and partial failures

### Evaluation Focus:
Proper configuration of DynamoDB streams
Effective use of EventBridge Pipes filtering capabilities
Efficient event pattern design
Error handling and reliability patterns
Cost optimization through appropriate filtering

### Deliverables:
CloudFormation/Terraform templates
Filter patterns and pipe configurations
Lambda function code for processing
Architecture diagram showing the complete pipeline

How would you design this solution to ensure only relevant order events are processed while maintaining scalability and cost-efficiency?
 

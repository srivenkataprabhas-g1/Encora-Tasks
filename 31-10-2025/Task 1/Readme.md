<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/a64b0ed2-f820-4920-92af-ed28b76e63f9" /># Question - DynamoDB, EC2, API Gateway and Lambda
### Assignment Overview
 Create a serverless web application that does CRUD  operation on data through an HTML form hosted on EC2, processes it via Lambda functions, and stores it in DynamoDB.
### Architecture Components
EC2 Instance: Hosts a static HTML form
API Gateway: REST API endpoint
Lambda Functions: Process form data
DynamoDB: Stores submitted form data
IAM Roles: Secure permissions between services
### Detailed Requirements
1. DynamoDB Table Design
Create a table named UserSubmissions with:
Partition Key: submissionId (String)
Attributes: name, email, message, submissionDate, status
2. Lambda Functions
Create two Lambda functions
Submission Lambda:
Triggered by API Gateway POST request
Validates input data
Generates unique submissionId
Stores data in DynamoDB
Returns success/error response
Query Lambda:
Triggered by API Gateway GET request
Retrieves submissions from DynamoDB
Supports querying by email or fetching all records
3. API Gateway
REST API with two resources:
POST /submit → Submission Lambda
GET /submissions → Query Lambda
Enable CORS for EC2 domain
4. EC2 Instance
Launch t2.micro instance with Amazon Linux
Install web server (Apache/Nginx)
Host static HTML form with fields:
Name (text, required)
Email (email, required)
Message (textarea, required)
JavaScript to handle form submission to API Gateway
5. IAM Roles
Lambda execution role with DynamoDB read/write permissions
EC2 instance profile (if needed)
Note: The HTML form should use CSS and Bootstrap.
# Solution
### DynamoDB
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/94b8c4b7-3eeb-45fa-a8d6-744f9f5d7534" />
## Lambda 
- Submission Lambda
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/e4915754-8958-48c3-95b9-554e41d0e838" />
<strong>Code:</strong>
<pre>import json
import boto3
import uuid
from datetime import datetime
from botocore.exceptions import ClientError

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('UserSubmissions')

def lambda_handler(event, context):
    # Log the incoming event for debugging
    print("Received event:", json.dumps(event))

    try:
        # Parse the body if present as JSON string else take event as form data
        if 'body' in event and isinstance(event['body'], str):
            body = json.loads(event['body'])
        else:
            body = event

        print("Parsed body:", body)

        # Extract and validate form data
        name = body.get('name', '').strip()
        email = body.get('email', '').strip()
        message = body.get('message', '').strip()

        # Validation
        validation_errors = []

        if not name:
            validation_errors.append("Name is required")
        if not email or '@' not in email:
            validation_errors.append("Valid email is required")
        if not message:
            validation_errors.append("Message is required")

        if validation_errors:
            return {
                'statusCode': 400,
                'headers': {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                'body': json.dumps({
                    'success': False,
                    'errors': validation_errors
                })
            }

        # Generate unique submission ID
        submission_id = str(uuid.uuid4())
        submission_date = datetime.utcnow().isoformat() + 'Z'

        # Prepare item for DynamoDB
        item = {
            'submissionId': submission_id,
            'name': name,
            'email': email,
            'message': message,
            'submissionDate': submission_date,
            'status': 'received'
        }

        # Store in DynamoDB
        table.put_item(Item=item)

        return {
            'statusCode': 201,
            'headers': {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            'body': json.dumps({
                'success': True,
                'submissionId': submission_id,
                'message': 'Submission received successfully'
            })
        }

    except ClientError as e:
        print(f"DynamoDB Error: {e}")
        return {
            'statusCode': 500,
            'headers': {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            'body': json.dumps({
                'success': False,
                'error': 'Database error occurred'
            })
        }

    except Exception as e:
        print(f"Unexpected Error: {e}")
        return {
            'statusCode': 500,
            'headers': {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            'body': json.dumps({
                'success': False,
                'error': 'An unexpected error occurred'
            })
        }</pre>


# Question - DynamoDB, EC2, API Gateway and Lambda
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
## Query Lambda
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/6a416dd2-b169-4c36-b0dc-a55db62f454c" />
<strong>Code:</strong>
<pre>
 import json
import boto3
from botocore.exceptions import ClientError

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('UserSubmissions')

def lambda_handler(event, context):
    """
    Retrieves submissions from DynamoDB
    Supports filtering by email or fetching all records
    """
    
    try:
        # Get query parameters
        query_params = event.get('queryStringParameters', {}) or {}
        email = query_params.get('email')
        
        if email:
            # Query by email using GSI (if available)
            # Falls back to scan with filter if GSI not configured
            try:
                response = table.query(
                    IndexName='EmailIndex',
                    KeyConditionExpression='email = :email',
                    ExpressionAttributeValues={
                        ':email': email
                    }
                )
            except:
                # Fallback to scan with filter
                response = table.scan(
                    FilterExpression='email = :email',
                    ExpressionAttributeValues={
                        ':email': email
                    }
                )
        else:
            # Get all submissions (with limit for performance)
            response = table.scan(Limit=100)
        
        # Format items for response
        items = response.get('Items', [])
        
        # Sort by submission date (newest first)
        items.sort(
            key=lambda x: x.get('submissionDate', ''),
            reverse=True
        )
        
        return {
            'statusCode': 200,
            'headers': {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            'body': json.dumps({
                'success': True,
                'count': len(items),
                'submissions': items
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
                'error': 'Database query failed'
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
        }
</pre>
# API Gateway
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/0cf8c6ac-c6eb-439c-b858-5800b474cfdc" />
# EC2 Instance
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/d4e417cb-42fb-49b6-bc72-321c97db1916" />
<strong>Server Code</strong>
<pre>
 <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Serverless Submission Form</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        .container-form {
            background: white;
            border-radius: 10px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.3);
            padding: 40px;
            max-width: 500px;
            width: 100%;
        }
        .form-title {
            color: #333;
            margin-bottom: 30px;
            text-align: center;
            font-weight: 600;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-label {
            color: #555;
            font-weight: 500;
            margin-bottom: 8px;
        }
        .form-control {
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            padding: 10px 15px;
            font-size: 14px;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        textarea.form-control {
            resize: vertical;
            min-height: 120px;
        }
        .btn-submit {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
            font-weight: 600;
            padding: 12px 30px;
            border-radius: 5px;
            width: 100%;
            cursor: pointer;
            transition: transform 0.2s;
        }
        .btn-submit:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }
        .btn-submit:disabled {
            opacity: 0.6;
            cursor: not-allowed;
            transform: none;
        }
        .alert {
            margin-bottom: 20px;
            border-radius: 5px;
        }
        .spinner-border {
            display: none;
            margin-left: 10px;
        }
        .submissions-section {
            margin-top: 40px;
            border-top: 2px solid #e0e0e0;
            padding-top: 30px;
        }
        .submissions-title {
            color: #333;
            font-weight: 600;
            margin-bottom: 20px;
        }
        .submission-item {
            background: #f8f9fa;
            border-left: 4px solid #667eea;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 5px;
        }
        .submission-name {
            font-weight: 600;
            color: #333;
        }
        .submission-email {
            color: #666;
            font-size: 14px;
        }
        .submission-message {
            color: #555;
            margin-top: 10px;
            font-style: italic;
        }
        .submission-date {
            color: #999;
            font-size: 12px;
            margin-top: 8px;
        }
    </style>
</head>
<body>
    <div class="container-form">
        <!-- Alert Messages -->
        <div id="alertContainer"></div>

        <!-- Form Section -->
        <div id="formSection">
            <h2 class="form-title">📝 Submit Your Information</h2>
            <form id="submissionForm">
                <div class="form-group">
                    <label for="name" class="form-label">Full Name *</label>
                    <input 
                        type="text" 
                        class="form-control" 
                        id="name" 
                        name="name"
                        required
                        placeholder="Enter your full name"
                    >
                </div>

                <div class="form-group">
                    <label for="email" class="form-label">Email Address *</label>
                    <input 
                        type="email" 
                        class="form-control" 
                        id="email" 
                        name="email"
                        required
                        placeholder="example@domain.com"
                    >
                </div>

                <div class="form-group">
                    <label for="message" class="form-label">Message *</label>
                    <textarea 
                        class="form-control" 
                        id="message" 
                        name="message"
                        required
                        placeholder="Enter your message here..."
                    ></textarea>
                </div>

                <button 
                    type="submit" 
                    class="btn-submit"
                    id="submitBtn"
                >
                    Submit
                    <span class="spinner-border spinner-border-sm" id="spinner" role="status" aria-hidden="true"></span>
                </button>
            </form>
        </div>

        <!-- Submissions Section -->
        <div class="submissions-section">
            <h3 class="submissions-title">📋 Recent Submissions</h3>
            <div id="submissionsList">
                <p class="text-muted">Loading submissions...</p>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // UPDATE THIS WITH YOUR API GATEWAY URL
        const API_URL = 'https://kzys2ykar8.execute-api.us-east-1.amazonaws.com/prod';
        const SUBMIT_ENDPOINT = '${API_URL}/submit';
        const QUERY_ENDPOINT = '${API_URL}/submissions';

        // Show alert message
        function showAlert(message, type = 'info') {
            const alertContainer = document.getElementById('alertContainer');
            const alertHtml = `
                <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                    ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            `;
            alertContainer.innerHTML = alertHtml;
            
            // Auto-dismiss after 5 seconds
            setTimeout(() => {
                alertContainer.innerHTML = '';
            }, 5000);
        }

        // Handle form submission
        document.getElementById('submissionForm').addEventListener('submit', async (e) => {
            e.preventDefault();

            const name = document.getElementById('name').value.trim();
            const email = document.getElementById('email').value.trim();
            const message = document.getElementById('message').value.trim();

            // Disable button and show spinner
            const submitBtn = document.getElementById('submitBtn');
            const spinner = document.getElementById('spinner');
            submitBtn.disabled = true;
            spinner.style.display = 'inline-block';

            try {
                const response = await fetch(SUBMIT_ENDPOINT, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ name, email, message })
                });

                const data = await response.json();

                if (response.ok) {
                    showAlert('✅ Submission successful! Thank you.', 'success');
                    document.getElementById('submissionForm').reset();
                    loadSubmissions(); // Refresh the submissions list
                } else {
                    const errorMsg = data.errors ? data.errors.join(', ') : data.error || 'Submission failed';
                    showAlert(`❌ ${errorMsg}`, 'danger');
                }
            } catch (error) {
                console.error('Error:', error);
                showAlert('❌ Network error. Please try again.', 'danger');
            } finally {
                submitBtn.disabled = false;
                spinner.style.display = 'none';
            }
        });

        // Load and display submissions
        async function loadSubmissions() {
            try {
                const response = await fetch(QUERY_ENDPOINT);
                const data = await response.json();

                const submissionsList = document.getElementById('submissionsList');

                if (!data.success || !data.submissions || data.submissions.length === 0) {
                    submissionsList.innerHTML = '<p class="text-muted">No submissions yet.</p>';
                    return;
                }

                let html = '';
                data.submissions.forEach(submission => {
                    const date = new Date(submission.submissionDate).toLocaleString();
                    html += `
                        <div class="submission-item">
                            <div class="submission-name">${escapeHtml(submission.name)}</div>
                            <div class="submission-email">${escapeHtml(submission.email)}</div>
                            <div class="submission-message">"${escapeHtml(submission.message)}"</div>
                            <div class="submission-date">${date}</div>
                        </div>
                    `;
                });

                submissionsList.innerHTML = html;
            } catch (error) {
                console.error('Error loading submissions:', error);
                document.getElementById('submissionsList').innerHTML = 
                    '<p class="text-danger">Error loading submissions.</p>';
            }
        }

        // Escape HTML to prevent XSS
        function escapeHtml(text) {
            const map = {
                '&': '&amp;',
                '<': '&lt;',
                '>': '&gt;',
                '"': '&quot;',
                "'": '&#039;'
            };
            return text.replace(/[&<>"']/g, m => map[m]);
        }

        // Load submissions on page load
        loadSubmissions();

        // Refresh submissions every 10 seconds
        setInterval(loadSubmissions, 10000);
    </script>
</body>
</html>
</body>
# IAM
### LambdaDynamoDbrole
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/cda9f5c7-73f4-4c52-944b-0b79cd91497f" />
### Submission Role
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/ea2ffefa-e667-4e00-aa58-aa1efc497144" />
### Query Role
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/fdf86e3c-c33e-401f-b641-458e57704010" />

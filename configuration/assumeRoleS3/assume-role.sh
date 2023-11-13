#!/bin/bash

# AWS CLI profile for AWS SSO
AWS_SSO_PROFILE="default"

# Role to assume
ROLE_TO_ASSUME="arn:aws:iam::xxxxxxxxxxxxx"

# Session name
SESSION_NAME="log-stash01"

# Run the AWS CLI command to assume the role
result=$(aws sts assume-role \
  --profile "$AWS_SSO_PROFILE" \
  --role-arn "$ROLE_TO_ASSUME" \
  --role-session-name "$SESSION_NAME")

# Extract and set temporary credentials as environment variables
AWS_ACCESS_KEY_ID=$(echo "$result" | grep -o '"AccessKeyId": "[^"]*' | cut -d'"' -f4)
AWS_SECRET_ACCESS_KEY=$(echo "$result" | grep -o '"SecretAccessKey": "[^"]*' | cut -d'"' -f4)
AWS_SESSION_TOKEN=$(echo "$result" | grep -o '"SessionToken": "[^"]*' | cut -d'"' -f4)

# Export the temporary credentials as environment variables
export AWS_ACCESS_KEY_ID
export AWS_SECRET_ACCESS_KEY
export AWS_SESSION_TOKEN

# Print the temporary credentials for verification
echo "Temporary credentials obtained:"
echo "AWS_ACCESS_KEY_ID: $AWS_ACCESS_KEY_ID"
echo "AWS_SECRET_ACCESS_KEY: $AWS_SECRET_ACCESS_KEY"
echo "AWS_SESSION_TOKEN: $AWS_SESSION_TOKEN"

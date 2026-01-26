#!/bin/bash

set -a        # automatically export all variables
source .env
set +a

echo "Environment variables loaded:"
echo "JWT_KEY=$Jwt_Key"
echo "JWT_EXPIRATION=$Jwt_Experation"

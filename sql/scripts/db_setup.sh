#!/bin/bash

DB_NAME="practice_project"
DB_USER="root"
DB_PASSWORD="poorva@112"
DDL_FILE="ddl.sql"

# MySQL commands
MYSQL="mysql -u$DB_USER -p$DB_PASSWORD"

# Create database
$MYSQL -e "CREATE DATABASE IF NOT EXISTS $DB_NAME;"

# Execute DDL script
$MYSQL $DB_NAME < $DDL_FILE

echo "Database and tables created successfully."

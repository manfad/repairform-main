#!/bin/bash

# Create QR codes directory in /tmp
mkdir -p /tmp/qrcodes

# Set permissions (readable and writable by everyone)
chmod 777 /tmp/qrcodes

# If running as root, change ownership to the application user
# Replace tomcat:tomcat with your actual application user:group
# chown tomcat:tomcat /tmp/qrcodes

echo "QR codes directory setup complete at /tmp/qrcodes" 
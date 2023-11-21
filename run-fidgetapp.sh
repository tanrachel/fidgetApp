#!/bin/bash

# Your setup steps go here
echo "Setting up FidgetApp..."

# Example: Compile the Java code using Maven
mvn clean compile

# Example: Set up an alias for run-fidgetapp
echo 'alias run-fidgetapp="mvn exec:java -Dexec.mainClass=org.example.Main"' >> ~/.zshrc

# Reload the shell configuration to apply the alias
source ~/.zshrc


echo "FidgetApp setup complete. You can now use 'run-fidgetapp' to execute the program."

# FidgetApp
## Description
FidgetApp is a desktop application that allows users to view the latest reddit, facts, news and weather.
The goal of the app is to help with context switching during development, the idea 
is to only provide bitesize content that is limited so that users don't become distracted scrolling on 
social media and get carried away from their tasks. 

## How to use
### 1. Git pull repository to local 
### 2. Set up environment variables
#### 2.1 create .env file in root directory 
#### 2.2 add the following variables to the .env file
```
NEWS_API_KEY={news api key: https://newsapi.org/}
W_API_KEY={weatheropenapi key: https://openweathermap.org/api}
```
### 3. Ensure you have maven installed
```
brew install maven
```
### 4. Run the following command in the root directory
```
*ensure you have set it executable: chmod +x run-fidgetapp.sh* 

./run-fidgetapp.sh
```
### 5. Enjoy the app!
You should be able to trigger the app to launch using 
```
run-fidgetapp
```
# UML Diagrams
[![Diagram Image Link](https://tinyurl.com/yta248cx)](https://tinyurl.com/yta248cx)<!--![Diagram Image Link](./uml.puml)-->
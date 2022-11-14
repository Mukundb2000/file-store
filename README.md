# File-store

## Project Title
File store service (HTTP server and a command line client)

## Description
A client-server application, comprising of a Spring Boot web application which includes an embedded web server as a file server and NodeJS for a client. A containerized form of the Web server can be created from this source code and deployed in Docker and Kubernetes. 
> There are various file operations that can be carried out, and comes with a great set of file-validations such as preventing file-content and file-name duplication, concurrent file upload control and network interrupt moderation.

## Getting Started
The project is structured under 4 folders:

* file-store-server : Contains source code for the Spring Boot File server application that acts as a Web API server.
* file-store-client/file-store-cmd : Contains a NodeJS Application for commandline interface and contains few sample text files.
* file-store-ui: A ReactJS Application for graphical user interface and contains few sample text files.  
* Dockerfile : Contains source code for the Docker image and facilitates Deployment and Service.

## Dependencies
* Apache Maven 3.8.5, openjdk 11.0.12, Spring Boot 2.7.5
* Docker version 20.10.21
* Node.js v16.13.2

## Installation
* For deploying this application in Docker environment, please follow the below steps:
* The Docker image can be found here, [mukundb2000/file-store-server](https://hub.docker.com/r/mukundb2000/file-store-server)
  * Create a Docker Image On a machine with Docker installed, run the below command:
    
    `docker build -t  mukundb2000/file-store-server . `
    
  * After the build is successful, run the below command:
  
    `docker run -it mukundb2000/file-store-server -p 8081:8081` 
    
  * Post these steps, the server will be up and running on localhost:8081
  
* For deploying this application on local machine, please follow the below steps:
  * Clone the repositry file-store.
  * Open the file  file-store-server in the Spring Boot IDE.
  * Build the jar using the below command:
  
  `mvn clean package install`
  
* For testing from the client end,
  * Local environment should have NodeJS installed
  * Please clone/download the file [commandline-client-implementation](https://github.com/Mukundb2000/file-store/tree/develop/file-store-client/file-store-cmd)
  * Run the below command to download all the dependencies:
  
    `npm install`
    
 ## Executing program
 
 `node store.js <cmd>`
 
 Can be one of the following values defined under Documentation
 
 ## Documentation
 The list of supported api calls are listed below:
 #Lists the existing files present on the server:
 
`node store.js ls` 

#Adds a new file file1.txt to the server

`node store.js add file1.txt`

#Prints a message that file1.txt already exists and adds file2.txt to the server

`node store.js add file1.txt file2.txt`

#If the file already exists and the content is same, it prints an appropriate message; else updates the content of the new file onto the file on the server

`node store.js update file1.txt`

#Deletes file1.txt on the server

`node store.js rm file1.txt`

#Prints the total number of words combined in all the files on the server

`node store.js wc`

#Prints the 10 most frequent words in all files on the server (default behavior)

`node store.js freq-words`

#Prints the 10 most frequent words in all files on the server

`node store.js freq-words --limit 10 --order dsc`

#Prints the 10 least frequent words in all files on the server

`node store.js freq-words --limit 10 --order asc`

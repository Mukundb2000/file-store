# File store service (HTTP server and a command line client)

## Description
A client-server application, comprising of a Spring Boot web application which includes an embedded web server as a file server and NodeJS for a client. A containerized form of the Web server can be created from this source code and deployed in Docker and Kubernetes. 
> There are various file operations that can be carried out, and comes with a great set of file-validations such as preventing file-content and file-name duplication, concurrent file upload control and network interrupt moderation.

## Getting Started
The project is structured under 3 folders:

* file-store-server : Contains source code for the Spring Boot File server application that acts as a Web API server.
  * Dockerfile: A file that builds a Docker image of the filestore server application. Can be used to run a Docker container / kubernetes pods.
* file-store-client/file-store-cmd : Contains a NodeJS Application for commandline interface and a few sample text files.
* file-store-ui: A ReactJS Application for graphical user interface and a few sample text files.  

## Dependencies
* Apache Maven 3.8.5, openjdk 11.0.12, Spring Boot 2.7.5
* Docker version 20.10.21
* Node.js v16.13.2

## Installation

For deploying this application in a Docker environment, please follow the below steps:
* You can either build a docker image locally or get the prebuilt Docker image from here- docker.io/mukundb2000/file-store-server
  * To build a Docker Image locally, run the below command:
    
    ```
    cd file-store-server
    docker build -t  file-store-server . 
    ```
    
  * After the build is successful, run the below command:
  
    ```
    docker run -it docker.io/mukundb2000/file-store-server -p 8081:8081
    ``` 
    OR
    ```
    docker run -it file-store-server -p 8081:8081`
    ```
    
  * Post these steps, the server will be up and running in a container image and is serving requests at localhost:8081
  
For deploying this application on local machine, please follow the below steps:
  * Clone the repositry file-store.
  * cd file-store-server
  * Build the jar using the below command:
  
  ```
  mvn clean package install
  ```
  
For testing from the client end,
  * Local environment should have NodeJS installed
  * cd file-store-client/file-store-cmd
  * Run the below command to download all the dependencies:
  
    ```
    npm install
    ```
    
  * Run the below command to run the application
 
    `node store.js <cmd>`
 

 ## Usage
 The list of supported api calls are listed below:
 #### List the existing files present on the server:
 
```
node store.js ls
``` 

#### Add a new file file1.txt to the server

```
node store.js add file1.txt
```

#### Add a duplicate file file1.txt and a new file file2.txt

```
node store.js add file1.txt file2.txt
```

`Prints a message that file1.txt already exists and adds file2.txt to the server`

#### Update a file with new contents

```
node store.js update file1.txt
```

`If the file already exists and the content is same, it prints an appropriate message; else updates the content of the new file onto the file on the server`

#### Delete file1.txt on the server

```
node store.js rm file1.txt
```

#### Print the total number of words combined from all the files on the server

```
node store.js wc
```

#### Print the 10 most frequent words from all the files on the server (default behavior)

```
node store.js freq-words
```

#### Print the 10 most frequent words from all files on the server

```
node store.js freq-words --limit 10 --order asc
```

#### Print the 10 least frequent words from all files on the server

```
node store.js freq-words --limit 10 --order dsc
```

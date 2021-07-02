# Getting Started

### Reference Documentation
This repository contains an RESTful Web Service to manage Tasks:
The REST API exposes the following endpoint with the different operations:
  
    GET /tasks  Retrieves the list of tasks. Additionally is possible to filter tasks by status appending the query parameter `status` to the URL
    POST /tasks Creates a new task
    PUT /tasks Edit existing tasks
    DELETE /tasks/{taskId} Deletes existing tasks

## Running Locally

You can either run the service directly via Gradle or from inside your favorite IDE.

### Via Gradle

Simply run the following command to build and start the application in the main project folder:

    ./gradlew bootRun

This will automatically start a H2 in-memory instance in the background.

## Disclaimer

For this exercise an in-memory database is used so data won't be persisted after closing the application.



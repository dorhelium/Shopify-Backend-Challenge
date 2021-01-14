# Summer 2021 Shopify-Backend-Challenge

Task: Build an image repository

This is a backend image repository server built with SpringBoot framework, integrating Spring Data JPA, Hibernate, PostgreSQL for data storage, and Spring Security for authorization and authentication.



Prerequisite: Java 1.8, maven, PostgreSQL are installed on the machine.

Database: (you need to create a database with following information before running the program)
* datasource url=jdbc:postgresql://localhost:5432/image_repo
* datasource username=postgres
* datasource password=postgres

<br>

# Overview

The architecture of this image repository is:

User ---owns---> ImageStore ---contains---> images

Each registered user owns the a image store. Each image store contain many images. The images can be public or private depending on what the image store owner provides.

* The users can add/delete/update/get the all the images from their own image store. These actions require authentication.

* Add/delete/update images from other user's image store is forbbiden. 

* Private images from other user's image store are not allowed to be retrieved. Public images can be retrived by all registered users.

* Bulk add/delete images are allowed. These APIs are **transactional** to ensure all or none during bulk add/delete.

<br>

# APIs

## - Only the user who owns the image store have access

- **POST   /image_store/{id}/addImages**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Add a list of new images to the image store with {id}. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Request Body: List of ImageDto

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
API Response: (success, code = 200, ImageStoreDto) or (Data Vialation, code = 401,  NOT_FOUND) or (Unauthorized, code = 403, FORBIDDEN)


-  **DELETE   /image_store/{id}/deleteImages**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Delete a list of existing images from the image store with {id}. 

-  **GET   /image_store/{id}**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Get a list of all images in the image store with {id}. 

-  **PUT   /image_store/{id}/image/{image_id}**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Update the image with {image_id} in the image store with {id}. 

-  **DELETE   /cancel_user/{username}**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Cancel/remove a registered user, along with ots image store and all the images in it.



## - All users with valid credentials can access

 -  **GET  /images**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Get a list all **public** images in the repository (from all image stores). 

-  **GET  /image_store/{id}/images**

/image_store/{id}/images
Description: Get a list all **public** images in the image store with {id}.


## - Everyone can access. No credentials needed

- **POST /new_user**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Register a new user and create an image store for the user. 

<br>


# Postman Demo









# Shopify-Backend-Challenge: Summer 2021 internship

Task: Build an image repository

This is a backend image repository server built with SpringBoot framework, integrating Spring Data JPA, Hibernate, PostgreSQL for data storage, and Spring Security for authorization and authentication.



Prerequisite: Java 1.8, maven, PostgreSQL are installed on the machine.

Database: (you need to create a database with following information before running the program)
* datasource url = jdbc:postgresql://localhost:5432/image_repo
* datasource username = postgres
* datasource password = postgres

<br>

# Overview

The architecture of this image repository is:

User ---owns---> ImageStore ---contains---> images

Each registered user owns the a image store. Each image store contain many images. The images can be public or private depending on what the image store owner provides.

* The users can add/delete/update/get the all the images from their own image store. These actions require authentication.

* Add/delete/update images from other user's image store are forbbiden. 

* All users can retrieve public images by image store id. Users can also retrive all public images in all image store. These actions require authentication.

* Private images from other user's image store are not allowed to be retrieved. 

* Bulk add/delete images from user's own image store are allowed. These APIs are **transactional** to ensure all or none during bulk add/delete. These actions require authentication.

* The images are accepted and returned as Base64 format. In the database, images are stored as byte arrays.

<br>

# APIs

## - Everyone can access. No credentials needed

- **POST /new_user**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Register a new user and create a new image store for the user. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Request Body: User

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Response Body: ImageStoreDto

## - Only the user who owns the image store has access

- **POST   /image_store/{id}/addImages**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Add a list of new images to the image store with {id}. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Request Body: List of ImageDto

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Response Body: ImageStoreDto


-  **DELETE   /image_store/{id}/deleteImages**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Delete a list of existing images from the image store with {id}. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Request Body: List of ImageDto

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Response Body: ImageStoreDto

-  **GET   /image_store/{id}**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Get a list of all images in the image store with {id}. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Response Body: ImageStoreDto

-  **PUT   /image_store/{id}/image/{image_id}**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Update the image with {image_id} in the image store with {id}. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Request Body: ImageDto

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Response Body: ImageStoreDto

-  **DELETE   /cancel_user/{username}**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Cancel/remove a registered user, along with ots image store and all the images in it.



## - All users with valid credentials can access

 -  **GET  /images**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Get a list all **public** images in the repository (from all image stores). 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Response Body: List of ImageDto

-  **GET  /image_store/{id}/images**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Description: Get a list all **public** images in the image store with {id}.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Response Body: List of ImageDto


<br>


## Postman Demo

Below is a Postman demo of a flow that includes all APIs. (Click on the images to view the details)

1. Register two users. Should return the ImageStoreDto owned by the registered user. User hello owns ImageStore-1. User doreen owns ImageStore with id-2
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104535685-a8ede700-55e4-11eb-9630-a4bb4b462f84.png">
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104535730-be631100-55e4-11eb-86da-b3f96a7786ea.png">

2.	User hello adds 2 images to the ImageStore-1 (owned by hello). The first image has public=true, the second image has public=false. Should return ImageStoreDto whitch contains 2 images.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104535994-34677800-55e5-11eb-94a3-4f1de8e93af4.png">
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104535962-21ed3e80-55e5-11eb-88f5-2dd4f31885e7.png">

3.	User doreen tries to add 2 images to the ImageStore-1 (owned by hello). 403 forbidden.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104536122-72649c00-55e5-11eb-918c-4a3ab4ea878a.png">

4.	User hello gets the ImageStore-1 (owned by hello). Should return ImageStoreDto with 2 images we just added.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104536262-c8394400-55e5-11eb-8e15-43bcb2aa130b.png">

5.	User doreen gets all images from ImageStore-1 (owned by hello). Should only return the list of images that are public. In this case, we have only 1 public image in ImageStore-1.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104536356-f4ed5b80-55e5-11eb-8c6c-c22e3eac488e.png">

6.User hello update the Image-2 in its own ImageStore-1. Set it to public.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104536460-223a0980-55e6-11eb-89f0-b4e363f427f2.png">

7.	Now user doreen tries again to get all public images from ImageStore-1 (owned by hello), should return 2 images.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104536568-4eee2100-55e6-11eb-8de2-707f4eec660b.png">

8.	Now user hello deletes Image-1 and Image-2 from its own ImageStore-1. Should return ImageStoreDto containing no image.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104536692-7f35bf80-55e6-11eb-88dc-062addbe5f55.png">

9.	Now user hello adds 1 public image in its own ImageStore-1. User doreen adds 2 public images in her own ImageStore-2.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104536746-9d032480-55e6-11eb-9295-d15b94338e43.png">
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104536778-b60bd580-55e6-11eb-9e20-0450a2cad1b3.png">

10.	Now user hello gets all public images from all image stores. Should return list of 3 images in total.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104536850-db98df00-55e6-11eb-8768-5f83db7527bf.png">

11.	User doreen cancels/unregisters herself. Then user hello gets all public images, doreen's images are gone. Only 1 image from ImageStore-1 (owned by hello) should left.
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104537004-20bd1100-55e7-11eb-9c27-1d71eb3098c8.png">
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104537036-30d4f080-55e7-11eb-8f3c-069543cee5da.png">

12.	For all APIs that needs valid credentials. If the wrong credentials are provided (user not registered yet or wrong password), the API returns 401.
Examples:
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104537128-5b26ae00-55e7-11eb-80b9-6bcd65d747bb.png">
<img width="468" alt="image" src="https://user-images.githubusercontent.com/34765166/104537155-67127000-55e7-11eb-8a08-7e6b2b5f1520.png">















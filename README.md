# Clean Architecture. 
## Image Storage Service. The first iteration
The simplest and most straightforward monolith.

**Functional requirements:**</br>
1. Uploading and saving images.
2. Give the image by ID.

**Dependencies:**</br>
* Spring Boot
* Lombok

**Controllers:**</br>
* *ImageController*:</br>
  Retrieves the file and returns it by UUID

**Services**</br>
* *ImageService:*
  1. Getting the file and saving it to disk in the "Content" folder.
  2. Returns the file by ID.

**Postman**</br>
* Saving a file:</br>
![postman_1.png](docs/postman_1.png)

* Getting the file:</br>
![postman_1.png](docs/postman_2.png)


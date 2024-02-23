# Clean Architecture. 
## Image Storage Service. The second iteration

**NEW Functional requirements:**</br>

The service should be able to switch between uploading to disk and to the cloud at our request.</br>

**Solution:**</br>

We have created a FileService interface that can save a file and give it back.
And created 2 implementations: LocalImageService, YandexCloudService. </br>

An implementation for saving a file locally. This is the same as it was in the first iteration.
The second implementation is uploading and uploading to Yandex in the "cloud" profile.</br>

**Disadvantages:**</br>

* All our Jars, that is, those who want to interact only with Yandex, will be required to interact with Local, 
  store the code to save to the local file system and vice versa.
* If we add functionality to save videos or other files, then our service will grow to a huge size.

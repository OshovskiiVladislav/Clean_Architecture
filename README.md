# Clean Architecture. 
## Image Storage Service. The fourth iteration

The last step that does not allow the project to be called "Clean Architecture" 
is the dependence of business logic on Spring Framework.

All this is due to the dependence on MultipartFile.
Let's remove Spring from our business logic.

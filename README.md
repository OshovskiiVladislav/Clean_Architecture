# Clean Architecture. 
## Image Storage Service. The fourth iteration

The last step that does not allow the project to be called "Clean Architecture" 
is the dependence of business logic on Spring Framework.</br>

All this is due to the dependence on MultipartFile.
Let's remove Spring from our business logic.</br>

![block_diagram_2.png](docs/block_diagram_2.png)

All in one java file ! \
Using Lombok, Jpa/Hibernate, Spring-Data , Spring Rest-Controler, Spring Data-Rest 

All in one java file :

1 . Creation of 2 entities : Students and Laboratories with Lombok \
       (relationship oneToMany between Laboratories and Students)
       
2. Mapping Object/Relational : with Jpa/Hibernate 
       
3. Creation of interface Repositories on Students and Laboratories : with Spring-Data 

4. Creation of WebServices Rest on Students and Laboratories : with Spring Rest-Controler 

5. Creation of WebServices Rest on Students and Laboratories : with Spring-Data-Rest \
   Creation of a projection on Students

6. Fill the database using studentRepositorie and laboratoryRepository

Rest-controler :
http://localhost:8080/api/students
http://localhost:8080/api/laboratories

![image](https://user-images.githubusercontent.com/39586770/205640678-7b1336b1-5414-4a2c-897d-ce189ff7dc6c.png)

![image](https://user-images.githubusercontent.com/39586770/205640783-3282759e-f82e-4d0a-820b-fb7193ff3f7f.png)



SpringDataRest :
http://localhost:8080/laboratories
http://localhost:8080/students
http://localhost:8080/students/1?projection=p1

![image](https://user-images.githubusercontent.com/39586770/205640968-1af8a389-201f-4692-81c7-7e0d71a61fe3.png)

![image](https://user-images.githubusercontent.com/39586770/205641067-43f52084-dec1-4bb5-b268-893bdc251a6b.png)

![image](https://user-images.githubusercontent.com/39586770/205641155-b278c184-e27c-433c-963b-1c67dfb8d974.png)





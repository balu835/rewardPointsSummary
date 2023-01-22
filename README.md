# rewardPointsSummary
To Calculate Reward Points summary for a given customer

Assignment and Instructions:
----------------------------
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every
dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, calculate the reward points earned for
each customer per month and total.

Database tables details:
-----------------------
1) DT_CUST -- contains the customer details such as customer first name, last name, email, effective and end dates of the customer.
2) DT_PRDT -- contains the product details such as product name, product type, product price, effective and end dates of the product.
3) DT_ORDR -- contains the order details such as customer id, order amount, order date and comments.
4) DT_PRDT_ORDR -- contains the product order details such as order id, product id, quantity of products, total amount of the products.


Steps to deploy and test the  project
====================================
This is a maven project developed using spring boot 3.0.1, junit 5, h2 in memory database, thymeleaf, slf4j etc.,

Working with the IDE
--------------------
1)  Clone the source code from https://github.com/balu835/rewardPointsSummary.git using any IDE.
2)  Navigate to the project folder and run ./mvnw clean install command in the terminal tab as shown <img width="1280" alt="image" src="https://user-images.githubusercontent.com/58399070/213944938-cb36ccdd-083f-404c-8f68-afedcdeaaaee.png">
3)  Build should be success <img width="1280" alt="image" src="https://user-images.githubusercontent.com/58399070/213944965-640ad3ff-8d11-4e47-81f7-2c17334cac7d.png">
4)  If you are using IntelliJ please run the application using the run icon as shown in the image <img width="1280" alt="image" src="https://user-images.githubusercontent.com/58399070/213944730-1093f03c-6bd3-4247-9d82-ca18caf5532f.png">
5)  if you want to run it via terminal run ./mvnw spring-boot:run as shown <img width="1280" alt="image" src="https://user-images.githubusercontent.com/58399070/213945026-175cd755-a79b-40c5-ac2a-ebd7b8361e3a.png">
6) Application should be up and running after few seconds on port 8081

Working with command Prompt for mac laptops
-------------------------------------------
1)  Clone the source code from https://github.com/balu835/rewardPointsSummary.git to desktop folder.
2)  Navigate to the project folder and run ./mvnw clean install command in the terminal tab as shown <img width="1280" alt="image" src="https://user-images.githubusercontent.com/58399070/213945565-70ca4b4a-2c4c-4dac-bef2-ea100bf5183d.png">
3) Once the build is success run ./mvnw spring-boot:run command to start the application as shown <img width="1280" alt="image" src="https://user-images.githubusercontent.com/58399070/213945678-76d5a6a5-3a2d-4ede-8e52-550fbe29cf4c.png">
4) Application should be up and running after few seconds on port 8081

Working with command Prompt for windows laptops
-------------------------------------------
1)  Clone the source code from https://github.com/balu835/rewardPointsSummary.git to desktop folder.
2)  Navigate to the project folder and run mvnw.cmd clean install command in the command prompt.
3) Once the build is success run ./mvnw spring-boot:run command to start the application.
4) Application should be up and running after few seconds on port 8081

Testing the application
-----------------------
1) Open a browser tab and paste this url for testing http://localhost:8081/fetchRewardPoints/1001 <img width="1280" alt="image" src="https://user-images.githubusercontent.com/58399070/213945408-69170843-4d8e-4c7f-9e28-4e7bc50181e9.png">
2) if you just want view the details with basic UI configuration http://localhost:8081/rewardPointsView/1001 <img width="1280" alt="image" src="https://user-images.githubusercontent.com/58399070/213945428-b2b8aad9-916c-4c9f-96e8-2a9a5d58c1fa.png">

Test data currently configured
------------------------------
Currently configured test data with customerId's 1001,1002,1003,1004,1005.










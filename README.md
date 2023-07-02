# catarion_stack_overflow

    Solved Taskes:
    - page design with HTML and Bootstrap
    - API in ResourceAnswerController
    

## What I configured

  
🧩 **Docker** (dockerfile and docker compose)

🧩 **Liquibase** (connected it to database)

🧩 **DBRider** customization

Verified and resolved bug reports and issues


## Tasks I complited

#### ✅ Built All Questions page with **HTML** and **Bootstrap**
------

![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/5af8114d05fae37628bd7af0391f60a15caf9218/images/catarion_stack_overflow/AllQuestions.png)

#### ✅ Implemented API in ResourceAnswerController:
    POST api/user/question/{questionId}/answer/{id}/downVote.
    When User votes for an Answer, Reputation of the Answer's author looses 5 points.
    The API returns a Total Vote Count (sum of up- and downvotes) and should be documented. Only for authorized users. 

------
Here I begin by determining the path and description:  
![alt-текст]()
Then I add dependencies using constructor injection for
QuestionService / AnswerService / VoteAnswerService / ReputationService classes and check if:
- User is authorized
- Answer and Question exist
- User voted down already  
![alt-текст]()
The next step is update points value in Reputation and count how many votes (both up and down) has the Answer  
![alt-текст]()

Let us focus on the _.updateCountByDown_ that takes two parameters: a User who wrote the Answer and an Answer's ID:





#### ✅ Configurated and connected **Database Rider**
------
##### DBRider is a Java testing library that facilitates writing integration tests for database-related code

    <dependency>
          <groupId>com.github.database-rider</groupId>
          <artifactId>rider-core</artifactId>
          <version>1.35.0</version>
          <scope>test</scope>
    </dependency>

![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/5af8114d05fae37628bd7af0391f60a15caf9218/images/catarion_stack_overflow/dbRider_tree.png)

![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/5af8114d05fae37628bd7af0391f60a15caf9218/images/catarion_stack_overflow/dbrider_annotations.png "Annotations")

_@DataSet_ annotation is implemented to configure a DataSet (vs. DBUnit) and is specified with the following feature:  

- _strategy = SeedStrategy.INSERT_  meaning that DBUnit will insert data in tables present on provided data;  

- _skipCleaningFor_  allows to skip cleaning for  _"db_liquibase"_ to preserve data that is managed by an external tool - Liquibase;  

- _cleanAfter_  calls a cleaning process after test that will ensure any changes are rolled back to leave the database in its original state;  

- _tableOrder_  defines the order in which tables should be cleaned after the test to ensure child tables will be cleaned before their parents.

![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/5af8114d05fae37628bd7af0391f60a15caf9218/images/catarion_stack_overflow/dbrider_findByEmail.png "findByEmail_testMethod")

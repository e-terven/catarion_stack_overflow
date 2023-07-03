# catarion_stack_overflow

    - Git Version Control to track changes and deal with Git Conflict
    - Verified and resolved bug reports and issues
    - Frontend with HTML and Bootstrap
    - API implementation
    - Environment configuration
    

## What I configured

  
🧩 **Docker** (dockerfile and docker compose)

🧩 **Liquibase** (connected it to database)

🧩 **DBRider** customization


## Tasks I complited

#### ✅ Built All Questions page with **HTML** and **Bootstrap**
------

![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/5af8114d05fae37628bd7af0391f60a15caf9218/images/catarion_stack_overflow/AllQuestions.png)

#### ✅ Implemented API in ResourceAnswerController:
    POST api/user/question/{questionId}/answer/{id}/downVote.
    When User votes for an Answer, Reputation of the Answer's author looses 5 points.
    The API returns a Total Vote Count (sum of up- and downvotes) and should be documented. Only for authorized users. 

------
The interesting thing here is that there are conditions we have to consider before counting and post a total amount of votes. Such as:
1. User can vote down only once. Thus, I wrote method _voteAnswerExists_ the VoteAnswer.class to confirm that the object does not exist in database:
   in ResourceAnswerController:
![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/9390c2dc0decfd82bb74f2203c6681eabfced310/images/catarion_stack_overflow/VoteAnswer-1.png)
   in VoteAnswerService:
![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/9390c2dc0decfd82bb74f2203c6681eabfced310/images/catarion_stack_overflow/VoteAnswer-2.png)
   in VoteAnswerDao:
![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/9390c2dc0decfd82bb74f2203c6681eabfced310/images/catarion_stack_overflow/VoteAnswer-3.png)
- User cannot vote for own answer
- The author of the Answer has to be "granted" by -5 points that means his Reputation status should be updated
- To return the Total amount of votes we have to compute both down and up votes of the Answer.

Besides, we have to check if the answer and the question (related to the answer) exist. Here I use EnitityManager in Dao layer to query information from database.


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

Let us focus on the _**reputationService.updateCountByDown**_ that takes two parameters: a User who wrote the Answer and an Answer's ID:





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

# catarion_stack_overflow

    The RESTful API defines:
     - endpoints specification (URL)
    

## What I configured

  
🧩 **Docker** (dockerfile and docker compose)

🧩 **Liquibase** (connected it to database)

🧩 **DBRider** customization

Verified and resolved bug reports and issues


## Tasks 

✅ Built All Questions page with **HTML** and **Bootstrap**.

#### ✅ Database Rider

    <dependency>
          <groupId>com.github.database-rider</groupId>
          <artifactId>rider-core</artifactId>
          <version>1.35.0</version>
          <scope>test</scope>
    </dependency>

##### is a Java testing library that facilitates writing integration tests for database-related code
![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/5b7387ae1d6f4bb2b2e1336fc85c75abb873e689/dbrider_annotations.png "Annotations")

There is _@DataSet_ annotation is implemented to configure a DataSet (vs. DBUnit) and is specified with the following feature:  

- _strategy = SeedStrategy.INSERT_  meaning that DBUnit will insert data in tables present on provided data;  

- _skipCleaningFor_  allows to skip cleaning for  _"db_liquibase"_ to preserve data that is managed by an external tool - Liquibase;  

- _cleanAfter_  calls a cleaning process after test that will ensure any changes are rolled back to leave the database in its original state;  

- _tableOrder_  defines the order in which tables should be cleaned after the test to ensure child tables will be cleaned before their parents.

![alt-текст](https://github.com/e-terven/catarion_stack_overflow/blob/2e4e92dadea035ec9bcbd1570de0fa6a84a80af6/dbrider_findByEmail.png "findByEmail_testMethod")

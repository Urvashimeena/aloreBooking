# AloreBooking

1) There is an SQLDUMP folder. You just need to import that tables in DB (create aloreBooking).

2) Import postman collection from the given collection file into(alore.postman_collection.json) your POSTMAN.

3) Clone the complete project. Import Maven Dependency. Make sure that you have proper internet, so that the dependency which is not present in your local system will get import from internet.

4) Use the payload data present in Payload folder. And use that for update, insert, delete and selecting data.

4) There are four APIS <br/>
    a) /updateData <br/>
    b) /deleteData <br/>
    c) /insertData <br/>
    d) /selectData  <br/>
    
    i) The APIs are generic, you can use that from other system also. After doing the Bit changes in my code like providing           some authentication. <br/>
    ii) The APIs have payload which has 2 parameter query and values. We can write any query and enter values corresponding to         that. And after hiting that from postman. That will run. <br/>
    iii) In project there is an Application.Java which needs to be run to start the project. The port is 8085.
    
  
   

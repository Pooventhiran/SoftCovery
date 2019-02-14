# SoftCovery
![Logo of SoftCovery](https://github.com/Pooventhiran/SoftCovery/blob/master/code/SoftCovery/WebContent/images/Softcovery.png)

Hello, Software peeps!
Wanna find only software news, and that too on specific topics? SoftCovery is here for the rescue.

It is a news feed application powered by IBM Watson's Disocvery Learning API. Given a natural language query and the fields of interest, it will shoot you with the top 10 news related to the constraints. 

## Dependencies
- Eclipse Oxygen

I actually uploaded the complete project directory to help with the structure of the project. 

## Dataset
It uses the default IBM Watson's news dataset which contains rich information processed and ingested.

## Path to important codes
- The web contents (HTML and images) are in [SoftCovery/code/SoftCovery/WebContent](https://github.com/Pooventhiran/SoftCovery/tree/master/code/SoftCovery/WebContent)
- The servlet program is in [SoftCovery/code/SoftCovery/src/softcovery/servlet][servlet]

## Watson Account: Creation and Accessing
**1. Sign up for IBM Cloud**

![Create Account](https://github.com/Pooventhiran/SoftCovery/blob/master/images/creat_acc.jpg)

**2.  Create IBM Watson Discovery instance**

![Create instance](https://github.com/Pooventhiran/SoftCovery/blob/master/images/creat_instance.jpg)

**3.  Launch the instance**

![Launch instance](https://github.com/Pooventhiran/SoftCovery/blob/master/images/launch.jpg)

**4.  Select the Pre-enriched collection (Watson Discovery News)**

![Select collection](https://github.com/Pooventhiran/SoftCovery/blob/master/images/select_coll.jpg)

**5.  Enter the credentials**

The account can be accessed by specifying the configurations: environment-id, collection-id, end-point, username, and password. The end-point, username and password can be found in the launch page. 

![Credentials](https://github.com/Pooventhiran/SoftCovery/blob/master/images/cred.jpg)
    
Fill in these credentials in the servlet program named **AccessWatson.java** located [here][servlet]

#### Note

The Watson application should receive at least one call in two months. Otherwise, the instance will be disabled and you have to create a new instance for discovery learning.

[//]: # (Link definitions)

[servlet]: <https://github.com/Pooventhiran/SoftCovery/tree/master/code/SoftCovery/src/softcovery/servlet>
  
 


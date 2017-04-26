# TEACHERS PET

Teachers pet is a tool for professors in Universities and similar institutions to get feedback from students so that they can adapt their lectures to the needs of the students.

## Getting Started

### Prerequisites

* MYSQL-server: For testing purposes you need a local MySQL server running. For deployment you need one with a static ip. 
* IDE with Gradle

### Installing

Server
clone repository from <http://github.com/choffa/teachers-pet_backend>

#### MySQL setup
Start your Mysql service and create a database and a user. In the git repository you will find an sql file called teachersPetDatabaseSchema.sql

### Java setup
Change the url, usr, and pwd variable in ServerDatabaseConnection to url="jdbc:mysql://<your_IP_or_localhost_or_domain>/<your_database_name>" and usr and pwd to the database login.

Open in your preferred IDE, compile and run the ServerMain class.

Android APP
clone repository from <http://github.com/choffa/teachers-pet_app>

Open the project in Android Studio, change the ip in the Connection class to the ip of the machine running the server. Build and deploy.

## Deployment

To deploy the code you must copy the server class files or, package them to a runnable jar and copy the jar, to a machine with a static IP which runs java and a mysql server.
Run the installation instructions above. Then everything is ready. 


## Built With

* [GRADLE](https://gradle.org/ "Gradle homepage") - Dependency Management

## Authors

Christopher Gjøvåg, Eirik Osnes, Magnus Leikvoll, Mathias Lien

## License

The project is under the Creative Commons license CC3.0

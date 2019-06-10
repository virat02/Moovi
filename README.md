# About Project
This project was made as part of the Course CS5200 Database Management System at Northeastern University. The objective of the project was to make a Spring Boot application for interactive search for movies
as well as interact with the TMDb (The Movie Database) API to retrieve data. The application runs on localhost server and keeps user details about their features in a MySQL server.
# Project Description
Our project is to make a social web-based entertainment application that will provide users with customized features in their account. Users can browse the application anonymously, viewing a wide range of movies, with their reviews and ratings as well as now playing and popular movies. He/She can also register to get access to additional features. A user can register as a Fan, a Critic, a Theater Manager and an Advertising Company Recruiter. The fan account allows users to like or dislike a movie, follow their favorite actors, favorite critics and other fans in their social circle. The critic account allows users to review any number of movies, rate any number of movies and recommend movies to their followers. The theater manager account shows the now playing movies as well as upcoming movies which can help them manage and schedule their screenings in their theatres. The advertising company recruiter account allows its users to search for actors based on their popularity scores and genres so that they can recruit them for their company adverts.
An Admin can view all tables and has complete access to the database and can apply CRUD operations on them. An anonymous user, who has not registered with the website can browse movies and query about the same, but to gain additional features, he/she has to register as one of the users. A user can be registered with the first name, last name, username, password, email ID, date of birth. A fan account consists of description where he/she can write about themselves and their interests. Critic account consists of description field and their website URL. An Advertisement Recruiter account has a description of their company. A Theatre Manager manages one or more theatres where theatres have a name, number of screens and location. Every screen in the theatre can show a movie at a time. A movie consists of name, IMDb ID, overview, poster source, runtime, IMDb rating, release date, revenue and release status as its attributes. A movie can have multiple genres. The recruiter can recruit actors based on their popularity score. An actor can have a name, IMDb ID, date of birth, date of death (none if still alive) and biography. A critic can rate movies as excellent, superb, nice, fair and poor.

# **What API are we using?**
The API being used for implementing the project is The Movie Database (TMDb). TMDb provides a lot of information about movies and their cast. It lists information about movie details, poster, release date, runtime, and genre. Also, information like now playing, upcoming, top rated and popular movies are also provided which we are incorporating in our project. The API also provides us the review of any movies along with all the cast of that movie. To know more about the cast, there are different search options and well-defined focused searches like Movie Credit, Cast Description, etc. 

To fulfill our use case of Advertisement Company Recruiter, the API provides us with much-needed details for actors such as popularity scores and genres of movies they have acted in, which can help them in searching for actors based on their requirements. A fan can also get recommended movies and similar movies to the movies he/she likes through the application as the API has separate functions to get recommended and similar movies.

# **Setup Instructions**
* Clone this project repository in a local directory.
* Open Intellij IDEA (recommended) or STS.

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%206.59.50%20PM.png)

* Click on import project and select the _moovi_ folder.

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.00.32%20PM.png)

* Import this as a _Maven Project_. Click next.

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.00.44%20PM.png)

* Leave the default settings as it is. Click next.

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.00.56%20PM.png)

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.01.08%20PM.png)

* Click finish and the project now opens up, loading the dependencies.

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.01.30%20PM.png)

* Go to File>Project Structure>Modules and select the Dependencies tab on the right. Add the dependency for JSON parser as follows:

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.42.50%20PM.png)

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.43.17%20PM.png)

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.43.41%20PM.png)

* Select _json-simple-1.1.1.jar_ file which is inside the _moovi_ directory and add this JAR file to the project.

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.44.05%20PM.png)

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.44.24%20PM.png)
***
***
# **Schema Creation and Running**
* Open MySQL Workbench and on your localhost, create the schema named _**moovi**_.
* In the project, navigate to moovi>src>resources>application.properties and enter your localhost username and password to access the schema you created in the step before.

![](https://github.com/AkshatShukla/cs5200_spring2018_DBMS_Project/blob/master/Screenshots/Screen%20Shot%202018-04-22%20at%207.14.12%20PM.png)

* Run the MooviApplication file. This will populate the moovi schema in the localhost with all the required tables.
* Go to MySQL Workbench and refresh the schema. Create a new query:

INSERT INTO \`moovi\`.\`user\`
(`dtype`,
`dob`,
`email`,
`first_name`,
`last_name`,
`password`,
`username`)
VALUES
("Admin","22-04-2018","admin@gmail.com","admin","admin","admin","admin");

* Open your browser and enter URL as: 
> https://localhost:8080/
This opens the Moovi Application for you. Welcome!

## Create Users based on Roles
* Go to Registration tab from the Navigation Bar above. Create a Role1 User(Fan) as follows:
> First Name: Alice,
Last Name: Alice,
Username: alice,
Password: alice,
UserType: Fan,
EmailId: alice@gmail.com,
Date: 22-06-1887,
Description: i am a fan

After submission of the form, the login window appears. Again click on Registeration window and repeat these steps for the following users:
(For Role2 User(Critic))
> First Name: Bob,
Last Name: Bob,
Username: bob,
Password: bob,
UserType: Critic,
EmailId: bob@gmail.com,
Date: 12-09-1987,
Description: i am a critic
WebsiteURL: bobimcritic.com

(For Role3 User(Ad Recruiter))
> First Name: Charlie,
Last Name: Charlie,
Username: charlie,
Password: charlie,
UserType: Advertisement Recruiter,
EmailId: charlie@gmail.com,
Date: 09-05-2011,
Description: i am an ad recruiter

(For Role4 User(Theatre Manager))
> First Name: Dan,
Last Name: Dan,
Username: dan,
Password: dan,
UserType: Theatre Manager,
EmailId: dan@gmail.com,
Date: 31-03-1999,
Description: i am a theatre manager

### OR
* Run the following SQL queries in MySQL Workbench:
1. INSERT INTO \`moovi\`.\`user\`
    (`dtype`,
    `dob`,
    `email`,
    `first_name`,
    `last_name`,
    `password`,
    `username`,
    `fan_description`)
    VALUES
    ("Fan","22-06-1887","alice@gmail.com","alice","alice","alice","alice","i am a fan");
2. INSERT INTO \`moovi\`.\`user\`
(`dtype`,
`dob`,
`email`,
`first_name`,
`last_name`,
`password`,
`username`,
`critic_description`,
`website_url`)
VALUES
("Critic","12-09-1987","bob@gmail.com","bob","bob","bob","bob","i am a critic","bobimcritic.com");
3. INSERT INTO \`moovi\`.\`user\`
(`dtype`,
`dob`,
`email`,
`first_name`,
`last_name`,
`password`,
`username`,
`recruiter_description`)
VALUES
("AdRecruiter","09-05-2011","charlie@gmail.com","charlie","charlie","charlie","charlie","i am an ad recruiter");
4. INSERT INTO \`moovi\`.\`user\`
(`dtype`,
`dob`,
`email`,
`first_name`,
`last_name`,
`password`,
`username`)
VALUES
("TheatreManager","31-03-1999","dan@gmail.com","dan","dan","dan","dan","i am a theatre manager");

* After creating the above users, click on the _**Moovi**_ tab on the navigation bar to go to the homepage of the application.
***

## Exploring Moovi Application
#### Note:
> The Moovi Application is based on retrieving data from TMDb API (https://www.themoviedb.org/documentation/api) and clicking on the _Search Movie_ or _Search Actor_ tab again and again might not give any results if done in swift intervals because of _**Error Code 429**_ (Too many request calls to API). Please be patient while the initial data loads and kindly do not keep switching tabs between _Search Movie_ and _Search Actor_ swiftly as it will result in too many API calls and will retrieve no data. If the data is not loading, it is certainly because of _**Error Code 429**_ and **to fix this, just go back to the homepage by clicking on the _**Moovi**_ tab on the navigation bar and then try again**.
> In case of any loading problems, please clear browser cache or open the localhost URL in incognito page.

### Anonymous 
* Whenever any anonymous user clicks on the _Search Movie_ tab on the navigation bar, the page loads up with Now Playing movies by default. 
* Similarly, when anonymous user clicks on _Search Actor_ tab on the navigation bar, the page loads up showing the most popular actors.
***
> [DOMAIN OBJECT RELATED TO ANOTHER DOMAIN OBJECT]
* The MySQL database table `movie` and `actor` get populated and also their relationship in the table `movie_cast` gets populated accordingly.
***
* The role of the API is just to fetch and initially populate the tables of MySQL database based on queries by user.
* The anonymous user can freely browse Movies and Actors and even navigate to their official IMDb link provided corresponding to every movie and actor.
* The anonymous user can click on any movie title or actor name to view more details about them which is retrieved from the MySQL database as the movies and actors searched have already populated the database tables.
* If the anonymous user wants to recommend/review, follow another user/actor or like/dislike any movie or recruit an actor, he/she can navigate to the Login tab in the navigation bar and login as either Fan/Critic/Advertisement Recruiter/Theatre Manager.

### Fan
* Click on _Login_ tab and login as fan by entering username and password and "Fan" datatype. After logging in as the Fan, you will get navigated to the Home Page of the fan, with 6 cards having different functionalities. Initially, you might not see any data when you click on any card as the user data is empty. In order to view the data, you will need to populate the user data.
***
> [USER SEARCHES FOR A LIST OF DOMAIN OBJECTS THAT MATCH A CRITERIA]
* Click on _Search Movie_ tab. The page will be loaded with the Now Playing movies and you can search the movie by title too.
* Click on _Search Actor_ tab. The page will be loaded with the Most Popular actors and you can search the actor by name too.
***
> [USER RELATED TO A DOMAIN OBJECT]
* When you are inside the _Search Movie_ page, you can like or dislike any movie out of the ones already loaded or you can search for any movie by title and then like or dislike that movie.
* When you are inside the _Search Actor_ page, you can follow any actor out of the ones already loaded or you can search for an actor by name and follow him/her.
***
> [USER RELATED TO ANOTHER USER]
* If you have multiple registered users as Fans in your database, you will be able to see all of them in the _Search Fan_ tab and from there on, you can follow them too. Initially, only Alice is a fan, so no data is shown in _Search Fan_ tab.
* Third, click on _Search Critic_ tab to see the list of all the registered critics in the database. Initially, you will see Bob as a critic. You can follow Bob and any other critic shown in the list.
***
* Lastly, you can go to your _Home_ tab and click on any of the cards to see the above results reflected.
***
> [A USER VIEWS DETAILS OF A PARTICULAR DOMAIN OBJECT LISTED IN THE SEARCH RESULT]
* Fan can click on the movie title in the_Search Movie_ tab to see that particular movie's summary and its cast.
* Similarly, he/she can also click on an actors name in the _Search Actor_ tab to see their biography and the movies the actor was cast in.
***
> [USER VIEWS ALL DOMAIN OBJECTS RELATED TO USER]
* In each of them, you can revert the actions you took eg. you can unfollow an actor or a critic, or remove a liked movie or a disliked movie, etc.
***
> [A USER VIEWS ALL OTHER USERS RELATED TO THE USER]
* You can also see all the Fans you are following, all the Fans you are followed by and all the Critics you are following.
***
* Click on _Logout_ tab to continue as another user.

### Critic
* Click on _Login_ tab and login as critic by entering username and password and "Critic" datatype. After Logging in as Critic, you will be navigated to your Home Page which will show you 3 cards having different functionalities 
* Click on _Search Movie_ tab. After the page has loaded with the Now Playing movies, you can recommend or write reviews for any movie out of the ones already loaded or you can search for any movie and recommend or write reviews for the movie.
***
> [USER RELATED TO A DOMAIN OBJECT]
* Once you click on `Write Review` button, you will be navigated to a page with a textbox to write your review and a dropdown to assign a rating to that particular movie. Click on submit to save it to the database. You can even recommend the movie so that it will get added to you recommended movie list.
***
> [USER SEARCHES FOR A LIST OF DOMAIN OBJECTS THAT MATCH A CRITERIA]
* The critic can also search and view details of any actors by name or any movie by title.
***
> [USER VIEWS ALL DOMAIN OBJECTS RELATED TO USER]
* Then, navigate to _Home_ and when you click on `Movies Recommended` card, you can see the list of movies you have recommended and if you want you can remove it from the list. When you click on `Movies Reviewed` you can see all your reviews corresponding to all the movies you reviewed.
***
> [A USER VIEWS ALL OTHER USERS RELATED TO THE USER]
* You can also click on `Your Followers` to see all the fans who follow you.
***
* Click on _Logout_ tab to continue as another user.

### Advertisement Recruiter
* Click on _Login_ tab and login as Ad-recruiter by entering username and password and "Advertisement Recruiter" datatype. After logging in as Advertisement Recruiter, you will be navigated to your Home Page which will show you a card in which you can see the actors you have recruited, which is initially empty.
***
> [USER RELATED TO A DOMAIN OBJECT]
* When you click on _Search Actor_, you can recruit the already loaded popular actors by clicking the `Recruit` button or search for actors of your likings and recruit them.
***
> [USER SEARCHES FOR A LIST OF DOMAIN OBJECTS THAT MATCH A CRITERIA]
* Advertisement Recruiter can also search for movies and actors.
***
> [USER VIEWS ALL DOMAIN OBJECTS RELATED TO USER]
* Then, navigate to _Home_ tab and check out the `Actors Recruited` card to see all the actors you have recruited. If you want, you can remove any actor from this list too.
***
* Click on _Logout_ tab to continue as another user.

### Theatre Manager
* Click on _Login_ tab and login as theatre manager by entering username and password and "Theatre Manager" datatype.After logging in as Theatre Manager, you will be navigated to your Home Page which will show you a card in which you can see the theatres you are managing, which is initially empty.
***
> [USER RELATED TO A DOMAIN OBJECT]
* When you click on _Register a Theatre_, you will be given a form to fill up. You will be asked to enter the name of the theatre, location of the theatre, and the movie which will be showing in the theatre screen. The list of movies which you will be shown in the drop-down is the current now playing movies at this time.
***
> [DOMAIN OBJECT RELATED TO ANOTHER DOMAIN OBJECT]
* Theatre registered will contain a single screen. Also, you have functionality to assign a movie to that screen.
***
> [USER SEARCHES FOR A LIST OF DOMAIN OBJECTS THAT MATCH A CRITERIA]
* The Theatre Manager also go to _Search Movie_ tab and see the release status for any searched movie, so as to plan ahead. They can also search for any actor by clicking on _Search Actor_.
***
> [USER VIEWS ALL DOMAIN OBJECTS RELATED TO USER]
* After registering the theatre, you can go to your _Home_ tab and click on `Managed Theatres` and view all the theatres registered under you.
***
* Click on _Logout_ tab to logout and become an anonymous user again.

### Admin
* Now login as Admin user by entering admin username and password already in the database and selecting user type as "Admin". Once logged in, you will be presented with 8 cards with all access to every functionality in the Moovi Application.
* You can see list of fans, critics, ad-recruiters and theatre managers by clicking on the corresponding cards.
***
> [AN ADMIN LISTS ALL USERS]
* Once you click on any card, you can view the data in the tabular format.
***
> [ADMIN CREATES A USER]
* Once you click on the card to list all fans, critics, ad-recruiters and theatre managers, you will see text boxes for input beneath the headings of the table where you can enter data and right horizontally to click on "Add" button to create the new user with respective type. This will reload the view automatically after submission with the updated view of the user created.
***
> [ADMIN EDITS A PARTICULAR USER]
* Once you click on the card to list all fans, critics, ad-recruiters and theatre managers, you will see text boxes for input beneath the headings of the table where you can enter data and right horizontally to click on "Edit" button to edit the existing selected user with respective type. You will then see new text boxes beneath the headings of the table with a static ID field. You can then fill up the respective fields with new data and click on "Submit Changes" button by scrolling horizontally to the right. This will reload the view automatically after submission with the updated view of the user edited.
***
> [ADMIN REMOVES A USER]
* Once you click on the card to list all fans, critics, ad-recruiters and theatre managers, you will see text boxes for input beneath the headings of the table where you can enter data and right horizontally to click on "Delete" button to remove the existing selected user with respective type. This will delete the user from the database permanently and reload the view automatically.
***
* Additional functionalities for the Admin are that, he/she can view the list of all movies, actors, reviews, theatre and can Add/Delete/Update in the same fashion as he/she can with the Users. 
* Admin can also manipulate the relationships between User-User and User-Domain Object. Admin can click on any card that lists the Users and then click on any particular username. He/she will then be redirected to the Home page of that user and Admin can change the various relationships of the User by interacting with the cards on their page. For example, the Admin can list all fans, and click on username of Alice. This will open Alice's homepage for Admin, which will expose the relationship of Alice with other Users and Domain Objects to the Admin. Admin can then change any relationships such as unfollow a followed Actor for Alice.
***
***
## Cardinalities and Enumerations
#### Many-to-Many Relationship
1. `Fan` to `Movie` (for likes)
2. `Fan` to `Movie` (for dislikes)
3. `Fan` to `Actor`
4. `Fan` to `Critic`
5. `Fan` to `Fan`
6. `Ad-Recruiter` to `Actor`
7. `Movie` to `Actor`
8. `Critic` to `Movie`

#### One-to-Many Relationship
1. `Critic` to `Review`
2. `Movie` to `Review`
3. `Theatre Manager` to `Theatre`

#### Enumerations
1. `Rating` (Excellent, Superb, Nice, Fair, Poor)

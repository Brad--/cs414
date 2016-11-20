# cs414
CS414 repository for group A

Jar Dependencies:
Google web tool kit for clients and server
MySql connector.jar this needs to be put in the the Tomcat lib so they can talk.

Other Dependencies:
MySQL
TomCat 7.0 server

Using the system:
connect to javapoloy.site if you want to play it.
compiling through the command line is much more challenging we will try to walk you though it.
javac srcdir="/src"
cp /src/*.class /war/WEB-INF/classes/
java com.google.gwt.dev.Compiler (GWT SDK REQUIRED)


Pro:
 distributed is done extremely well.
 The GUI looks sweet lets not lie.
 Its super easy to run once you connect to the website
 All the data is stored dynamically and very easy to check to find errors

Con:
 Add Cons once we are done coding and Testing
 <ADD HERE>

Pattern:
We used the Mode View Presenter patter its a sub category of the MVC patter.

refactor:
we did a huge refactor to make this more MVP based then A4. We Had to go and rewrite a lot of the logic to use the database to make
the data persistent. We did a lot of code reviews we used git hubs pull request feature. Before we did the refactor we didn't have a database
so the program was mostly client side. 
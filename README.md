# Rate-touille
Ratetouille is the project submitted by Team 2 in the completion of the KPT Project.
## Developed by - Archit Jain
For Ratetouille Main Algorithm Follow Ratetouille_JAVA Folder
![website](
https://github.com/Archit-Jain/RestaurantReviews/blob/master/website.PNG)
![android app](https://github.com/Archit-Jain/RestaurantReviews/blob/master/android.PNG
)

### Steps to follow
Step 1 - Install JAVA, JDK version 8 or above(preferably the latest version), MySQL version 5.7 or version 8 on the computer.</br>
Step 2 - Use your preferable IDE(IntelliJ or jGrasp)</br>
Step 3 - Extract Ratetouille.zip</br>
Step 4 – Open the MySQL command line client.</br>
Step 5 – Run the command “CREATE DATABASE ratetouille;”</br>
Step 6 – This would create the database for the JAVA program.</br>
Step 7 – The tables within the database would be created directly in the program.</br>
Step 8 – Now on line 792 of the JAVA program change the user=root and password=password to user=yourusername and password=yourpassword within the URL.</br>
PS. yourusername is the username in MySQL server and yourpassword is the password in MySQL server.</br>
Step 9 - Build/compile and run the project file Ratetouille.java</br>
  
Step 10 - Add opencsv-3.8.jar and mysql-connector-java-8.0.19 file to jGRASP (goto JGRASP->settings->PATHS/CLASSPATH->workspace-->classpath>New>Browse-></br>
(\Ratetouille\lib\opencsv-3.8.jar\ -->Select -Ok) and (\Ratetouille\lib\ mysql-connector-java-8.0.19\ -->Select -Ok))
</br>
Outputs Explained – 
Initially the Stemmed Reviews for all the instances are shown in the array in the below format.
Stemmed Reviews</br>
5201 : R0128 ===>>> [tabl, book, quick, 5, squash, tabl, 4, waitress, inform, tabl, requir, back, finish, drink, bar, back, bar, book, expens, not, enjoy, felt, not, relax]</br>
This holds true for 1-10000 review entries.</br>
Weights and Counts</br>
Next the Positive TermList followed by Negative TermList and then the Neutral TermList is printed with their total frequencies in the reviews and the logarithmic weights are printed. </br>
Weights is calculated using the formula</br>
W = log(N/Term Frequency) </br>
Where N = total number of reviews.</br>
Score per review
Logarithmic Score Sum arranged according to the restaurant IDs.
Total Score and Star Rating
Total score shows the number of positive, negative or the neutral reviews overall to a restaurant and the star rating provided by the software.

For Ratetouille Website Follow Ratetouille_WEB Folder

**Running web service** </br>

1.) Download and install xampp application</br>
2.) Uncompress 2 folders mysql.zip and htdocs.zip</br>
3.) Replace the htdocs folder in directory xampp\htdocs (web application "ratetouille" is already placed in htdocs folder)</br>
4.) Replace the mysql folder in directory xampp\mysql</br>
5.) Launch xampp command line interface and click on "start" for below two services</br>
	5a.) "Apache" (Tomcat server for localhost)</br>
	5b.) "MySql" (Server for connecting to database)</br>

6.) Once both the service start go to the URL http://localhost/ratetouille/rate-toullie.php</br>

##WEB OUTPUT EXPLAINED
The webpage shows the list of restaurants with the ratings provided by our software in an interactive UI.</br>
For Ratetouille Android Application Follow Ratetouille_ANDROID Folder</br>
Steps to follow:</br>
Step 1: Install Android Studio from the link provides(https://developer.android.com/)</br>
Step 2: Go to settings -> Android SDK and install the necessary SDK platforms, SDK tools, and SDK updates sites.</br>
Step 3:Unzip the Ratetouille_Android folder</br>
Step 4: Go to File, Open Project on Android Studio and open the project file as a whole.</br>
Step 5: Select a virtual device from Android Virtual Device(AVD) manager given in the toolbar(Preferably mentioned in playstore).</br>
Step 6: In the project folder(My Application)->app->libs make sure mysql-connector-java-5.1.45-bin.jar is present. Right click and add as a library. </br>
Step 7: Run Build.gradle first and then run the code provided.</br>

##ANDROID OUTPUT EXPLAINED</br>
The Android Application shows the list of restaurants with the ratings provided by our software in an interactive UI.</br>




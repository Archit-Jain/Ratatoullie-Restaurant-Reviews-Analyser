import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Ratetouille {
    // Global Variable deceleration.
    ArrayList<Reviews> data1;
    ArrayList<Reviews> data = new ArrayList<>();
    ArrayList<Parse.pureReview> pure = new ArrayList<Parse.pureReview>();
    String[] positive = new String[0];
    String[] negative = new String[0];
    String[] neutral = new String[0];
    ArrayList<String> restID = new ArrayList<>();
    ArrayList<ArrayList<Double>> reviewRate = new ArrayList<ArrayList<Double>>();
    ArrayList<Integer> Average = new ArrayList<Integer>();
    ArrayList<Integer> starArray = new ArrayList<Integer>();
    // update for weight
    ArrayList<String> positiveTermList = new ArrayList<>();
    ArrayList <ArrayList<Integer>> positiveDocList = new ArrayList<>();
    ArrayList<String> negativeTermList = new ArrayList<>();
    ArrayList <ArrayList<Integer>> negativeDocList = new ArrayList<>();
    ArrayList<String> neutralTermList = new ArrayList<>();
    ArrayList <ArrayList<Integer>> neutralDocList = new ArrayList<>();
    Integer reviewCount = 0;
    ArrayList<weights> positiveWeights = new ArrayList<>();
    ArrayList<weights> negativeWeights = new ArrayList<>();
    ArrayList<weights> neutralWeights = new ArrayList<>();
    // update for weight ends

    // Global Variable deceleration ends.

    /**
     * readReview class used to create a review object read1
     * further read1.allTask will give us the reviews stored
     * in the csv file.
     * read1.printreviews is used to print the reviews;
     * @param -      String Filename pass the name of the file to be read
     */
    public void readReviews(String Filename) {
        // Local Variable deceleration.
        Reviews read1 = new Reviews();
        // Local Variable deceleration ends.
        data1 = read1.allTask(Filename);
        read1.printReviews(data1);
    }// end readReviews

    /**
     * Reviews class which contains two strings first
     * contains the restaurantID of the type "R0001,
     * R002, R0003,......, R0211 and the second the reviews.
     * The object contains data in the form R0001 - Review1,
     * R0001 - Review2, R0001 - Review3,...... R0001 - ReviewN,
     * R0002 - Review1........... R0211 - ReviewN.
     */
    public class Reviews {
        // Object deceleration.
        //update for weight added reviewID in object
        Integer reviewID;
        String restaurantID;
        String review;
        // Object deceleration ends.

        /**
         * allTask method in the Reviews class reads the CSV file
         * new.csv that contains the reviews in a comma delimited
         * format and then storing it in an ArrayList "data".
         * @param     -String Filename pass the name of the file to be read
         * @return -  ArrayList of type Reviews
         */
        public ArrayList<Reviews> allTask(String Filename) {
            // Local Variable deceleration.
            CSVReader reader = null;
            // Local Variable deceleration ends.
            try {
                // Local Variable deceleration.
                //parsing a CSV file into CSVReader class constructor
                reader = new CSVReader(new FileReader(Filename));
                String[] nextLine;
                int i = 1;
                // Local Variable deceleration ends.
                //reads one line at a time
                while ((nextLine = reader.readNext()) != null) {
                    Reviews re1 = new Reviews();
                    //update for weight added i in arguments
                    re1.setReviews(i,nextLine[0], nextLine[1]);
                    data.add(re1);
                    i= i + 1;
                    reviewCount = reviewCount + 1;
                }// end while
                System.out.println("Review Count = " + reviewCount );
            }// end try
            catch (Exception e) {
                e.printStackTrace();
            }// end catch
            return data;
        }// end allTask

        /**
         * setReviews method is used to store the read rid and
         * review which are passed into the method as arguments.
         * @param entryID - giving uniqueID to every review
         * @param rid        - Restaurant ID
         * @param restReview - Restaurant Review.
         */
        public void setReviews(int entryID,String rid, String restReview) {
            //update for weight added entryID in arguments
            reviewID = entryID;
            restaurantID = rid;
            review = restReview;
        }// end setReviews

        /**
         * Method call used to print all the restaurants ID and
         * their corresponding reviews.
         * @param data - ArrayList of the type Reviews which contains
         *             all the read reviews.
         */
        public void printReviews(ArrayList<Reviews> data) {
            // Local Variable deceleration.
            String restIdString = new String();
            // Local Variable deceleration ends.
            for (Reviews r : data) {
                restIdString = " " + r.reviewID + " : ";
                restIdString += "" + r.restaurantID + " : ";
                restIdString += r.review + " ";
                System.out.println(restIdString);
            }// end for
        }// end printReviews
    }// end Reviews

    /**
     * Method stemReviews is a go to method for all the tasks that need to be
     * performed in order to preprocess the reviews.
     * @param data1 -     Array list of the object type Reviews.
     */
    public void stemReviews(ArrayList<Reviews> data1) {
        // Local Variable deceleration.
        Parse p1 = new Parse();
        Reviews r1 = new Reviews();
        String filename = "stopwords.txt";
        String delimiter = "[ '.,&#?!:;$%+()\\-\\/*\"]+";
        // Local Variable deceleration ends.
        p1.getStopWords(filename);
        pure = p1.tokenize(delimiter, data1);
        p1.printPure(pure);
        System.out.println("Pure size = " + pure.size());
    }// end stemReviews

    /**
     * Class Parse used to preprocess, tokenize and stem the
     * read reviews.
     */
    public class Parse {
        // Global-Parse Variable deceleration.
        public String[] words = new String[0];
        // Global-Parse Variable deceleration ends.

        /**
         * Class pureReview which is created to store a
         * preprocessed version of the reviews with their
         * restaurant IDs. The String restaurantID contains
         * the ID and the ArrayList reviewArray contains the
         * Array that has words that are free of stop words
         * and is tokenized.
         */
        public class pureReview {
            // Object deceleration.
            //update for weight added reviewID in object
            Integer reviewID;
            String restaurantID;
            ArrayList<String> reviewArray;
            // Object deceleration ends.

            /**
             * Method setReviewArray is used to initialize the
             * values of an instance of the pureReview object
             * into the array.
             * @param entryID - unique ID for review
             * @param rid -         Restaurant ID
             * @param restReview -  Original Reviews read
             */
            public void setReviewArray(int entryID, String rid, ArrayList<String> restReview) {
                //update for weight added i in arguments
                reviewID = entryID;
                restaurantID = rid;
                reviewArray = restReview;
            }// setReviewArray ends.
        }//pureReview ends.

        /**
         * Method used to read the stop words from the txt
         * file and store it into a String[] words
         * @param fileName -    reads the name of file
         *                      containing the list of stop
         *                      words.
         */
        public void getStopWords(String fileName) {
            // Local Variable deceleration.
            String sw = new String();
            // Local Variable deceleration ends.
            try {
                String lines = "";
                Path path = Paths.get(fileName);
                lines = new String(Files.readAllBytes(path));
                lines = lines.toLowerCase();
                words = lines.split("\\n");
                Arrays.sort(words);
                for (int i = 0; i < words.length; i++) {
                    sw += words[i] + " ";
                }// for ends.
                System.out.println("Stopwords => " + sw);
            }// try ends.
            catch (Exception e) {
                e.printStackTrace();
            }// catch ends.
        }// getStopWords ends.

        /**
         *  tokenize function is used to split the sentences into word tokens
         *  and the these words are stemmed and stored in an ArrayList of the
         *  pureReview object type.
         * @param delimiter -    String containing the symbols used for tokenizing the reviews
         * @param data1 -        is an ArrayList of Strings containing the object type Reviews
         * @return -             ArrayList of String stemmedReview - containing the stemmed document
         *                       using the Porter's Stemmer Algorithm
         */
        public ArrayList<pureReview> tokenize(String delimiter, ArrayList<Reviews> data1) {
            // Local Variable deceleration.
            ArrayList<pureReview> stemmedReview = new ArrayList<pureReview>();
            // added for weight
            int idCounter = 1;
            // Local Variable deceleration ends.
            for (int i = 0; i < data1.size(); i++) {
                ArrayList<String> pureTokens = new ArrayList<String>();
                String[] tokens = new String[0];
                tokens = data1.get(i).review.toLowerCase().split(delimiter);
                for (String token : tokens) {
                    if (searchStopWord(token) == -1) {
                        pureTokens.add(token);
                    }// if ends.
                }// for ends.
                //stemming
                Stemmer st = new Stemmer();
                ArrayList<String> stemmed = new ArrayList<>();
                for (String token : pureTokens) {
                    st.add(token.toCharArray(), token.length());
                    st.stem();
                    stemmed.add(st.toString());
                    st = new Stemmer();
                }// for ends.
                pureReview r1 = new pureReview();
                //update for weight added idCounter in arguments
                r1.setReviewArray(idCounter,data1.get(i).restaurantID, stemmed);
                // added for weight
                idCounter = idCounter + 1;
                stemmedReview.add(r1);
            }// for ends
//         printPure(stemmedReview);
            return stemmedReview;
        }// tokenize ends.

        /**
         *  In the searchStopWord method the global variable the String is
         *  searched against words containing the stopwords using Binary Search.
         * @param key -     of string datatype
         * @return int -    returns -1 is the key is not found in the stop word list
         */
        public int searchStopWord(String key) {
            // Local Variable deceleration.
            int lo = 0;
            int hi = words.length - 1;
            // Local Variable deceleration ends.
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int result = key.compareTo(words[mid]);
                if (result < 0) hi = mid - 1;
                else if (result > 0) lo = mid + 1;
                else return mid;
            }// while ends.
            return -1;
        }// searchStopWord ends.

        /**
         * printPure function is used to print the ArrayList of the
         * object type pureReview.
         * @param pure -    An ArrayList of type pureReview which
         *                  contains Restaurant IDs and the tokenized
         *                  stemmed reviews.
         */
        public void printPure(ArrayList<pureReview> pure) {
            for (pureReview r : pure) {
                System.out.println(r.reviewID + " : " + r.restaurantID + " ===>>> " + r.reviewArray);
            }// for ends.
        }// printPure ends.
    }// Parse ends.

    /**
     * readWords method is the method which combines function call for
     * tokenizeWords for the 3 files containing positive, negative and
     * the neutral words and storing them in 3 different arrays.
     */
    public void readWords() {
        // Local Variable deceleration.
        String delimiter = ",";
        // Local Variable deceleration ends.
        positive = tokenizeWords(delimiter, "positive.txt");
        Arrays.sort(positive);
        for (String word : positive) {
            System.out.print(word + " ");
        }// for ends.
        System.out.println("\n");
        negative = tokenizeWords(delimiter, "negative.txt");
        Arrays.sort(negative);
        for (String word : negative) {
            System.out.print(word + " ");
        }// for ends.
        System.out.println("\n");
        neutral = tokenizeWords(delimiter, "neutral.txt");
        Arrays.sort(neutral);
        for (String word : neutral) {
            System.out.print(word + " ");
        }// for ends.
    }// readWords ends.

    /**
     * tokenizeWords is a method that stems the words reads from
     * the respective files.
     * @param delimiter -   String containing the symbols used for tokenizing the word list
     * @param fileName  -   filename to read the words from
     * @return String[] -   Array Storing the stemmed words
     */
    public String[] tokenizeWords(String delimiter, String fileName) {
        // Local Variable deceleration.
        String Data = "";
        // Local Variable deceleration ends.
        try {
            Path path = Paths.get(fileName);
            System.out.println(path);
            Data = new String(Files.readAllBytes(path));
            Data = Data.toLowerCase();
        }// try ends.
        catch (IOException error) {
            System.out.println(error);
        }// catch ends.
        String[] tokens = null;
        tokens = Data.split(delimiter);
        String[] stemmered = new String[tokens.length];
        int i = 0;
        //stemming
        Stemmer st = new Stemmer();
        for (String token : tokens) {
            st.add(token.toCharArray(), token.length());
            st.stem();
            stemmered[i] = st.toString();
            st = new Stemmer();
            i++;
        }// for ends
        return stemmered;
    }// tokenizeWords ends.

    /**
     * Core Algorithm start here
     * sentimentAnalysis method runs the calculateReviewRating
     * for all the reviews and creates a normalized data structure
     * storing the ratings of all the reviews for the restaurant by
     * their IDs.
     */
    public void sentimentAnalysis() {
        // Local Variable deceleration.
        ArrayList<Double> rate;
        // Local Variable deceleration ends.
        for (int i = 0; i < pure.size(); i++) {
            calculateWeightList(pure.get(i));}
        System.out.println("\n");
        System.out.println("positive term dictionary");
        for(int i=0; i< positiveTermList.size(); i++){
            System.out.println(positiveTermList.get(i) + " --> " + positiveDocList.get(i));
        }
        System.out.println("\n");
        System.out.println("negative term dictionary");
        for(int i=0; i< negativeTermList.size(); i++){
            System.out.println(negativeTermList.get(i) + " --> " + negativeDocList.get(i));
        }
        System.out.println("\n");

        System.out.println("neutral term dictionary");
        for(int i=0; i< neutralTermList.size(); i++){
            System.out.println(neutralTermList.get(i) + " --> " + neutralDocList.get(i));
        }
        System.out.println("\n");
        computeWeights();
        for (int i = 0; i<pure.size(); i++ ){
            String string = pure.get(i).restaurantID;
            double value = calculateReviewRating(pure.get(i));
            if (!restID.contains(string)) {
                restID.add(string);
                rate = new ArrayList<Double>();
                rate.add(value);
                reviewRate.add(rate);
            }//if ends.
            else {
                int index = restID.indexOf(string);
                rate = reviewRate.get(index);
                rate.add(value);
                reviewRate.set(index, rate);
            }// else ends.
        }// for ends.
        System.out.println("Stay out of this THis is the size of restaurant ID list " + restID.size());
        System.out.println("Stay out of this THis is the size of review rate " + reviewRate.size());
        String result = "";
        for (int i = 0; i< restID.size(); i++) {
            result += String.format("%-15s", restID.get(i));
            rate = reviewRate.get(i);
            for(int j = 0 ; j < rate.size(); j++)
            {
                result += rate.get(j) + "\t";
            }// for ends.
            result += "\n";
        }// for ends.
            System.out.println(result);

    }// sentimentAnalysis ends.

    /**
     * method calculateReviewRating checks if each term in
     * a review exists in either one of the lists and gives
     * them an integer value of 1 for positive, 0 for neutral
     * and -1 for negative.
     * @param entry -   holds a single instance of the pureReview
     * @return
     */
    public Double calculateReviewRating (Parse.pureReview entry){
        Double positiveCount = 0.0;
        Double negativeCount = 0.0;
        Double neutralCount = 0.0;
        Double netValue = 0.0;

        for (String word:entry.reviewArray){
            // if word is in ArrayList positiveWeights.term
            // add the weight
            if(positiveTermList.contains(word)){
                for (weights w :positiveWeights) {
                    if(w.term.contains(word)){
                        positiveCount = positiveCount + w.weight;
                    }
                }
            }
             // if word is in ArrayList positiveWeights.term
            // subtract the weight
            if(negativeTermList.contains(word)){
                for (weights w :negativeWeights) {
                    if(w.term.contains(word)){
                        negativeCount = negativeCount - w.weight;
                    }
                }
            }
            // if word is in ArrayList neutralWeights.term
            // add with zeroinPOsitive
            if(neutralTermList.contains(word)){
                for (weights w :neutralWeights) {
                    if(w.term.contains(word)){
                        neutralCount = neutralCount + 0;
                    }
                }
            }

        }
        netValue =positiveCount + negativeCount + neutralCount;
        return netValue;
    }
    //change the comments below.
    /**
     *
     * @param entry -   holds a single instance of the pureReview
     * @return
     */
    public void calculateWeightList (Parse.pureReview entry){
        // Local Variable deceleration.
        ArrayList<Integer> temp;

        // Local Variable deceleration ends.
        for (String word:entry.reviewArray) {
            if(isInPositive(word) != -1){
                // update for weight
                if (!positiveTermList.contains(word)) {
                    positiveTermList.add(word);
                    temp = new ArrayList<Integer>();
                    temp.add(entry.reviewID);
                    positiveDocList.add(temp);
                } else {
                    int index = positiveTermList.indexOf(word);
                    temp = positiveDocList.get(index);
                    if (!temp.contains(entry.reviewID)) {
                        temp.add(entry.reviewID);
                        positiveDocList.set(index, temp);
                    }
                }
                // update for weight ends
            }// if ends.
            else if(isInNegative(word) != -1){
                // update for weight
                if (!negativeTermList.contains(word)) {
                    negativeTermList.add(word);
                    temp = new ArrayList<Integer>();
                    temp.add(entry.reviewID);
                    negativeDocList.add(temp);
                }
                else {
                    int index = negativeTermList.indexOf(word);
                    temp = negativeDocList.get(index);
                    if (!temp.contains(entry.reviewID)) {
                        temp.add(entry.reviewID);
                        negativeDocList.set(index, temp);
                    }
                }
                // update for weight ends
            }// else if ends.
            else if(isInNeutral(word) != -1){
                // update for weight
                // update for weight ends
                if (!neutralTermList.contains(word)) {
                    neutralTermList.add(word);
                    temp = new ArrayList<Integer>();
                    temp.add(entry.reviewID);
                    neutralDocList.add(temp);
                } else {
                    int index = neutralTermList.indexOf(word);
                    temp = neutralDocList.get(index);
                    if (!temp.contains(entry.reviewID)) {
                        temp.add(entry.reviewID);
                        neutralDocList.set(index, temp);
                    }
                }
            }// else if ends.
            else{
                continue;
            }// else ends.
        }// for ends.
    }// calculateWeight ends.

    public class weights{
        String term;
        Integer countTerm;
        Double weight;

        public void setWeight(String t, Integer c, Double w){
            this.term = t;
            this.countTerm = c;
            this.weight = w;
        }
    }
    class SortByCount implements Comparator<weights>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(weights a, weights b)
        {
            return b.countTerm - a.countTerm;
        }
    }
    public void computeWeights(){

        for (int i=0 ; i<positiveTermList.size();i++){
            weights weight = new weights();
            Integer termFrequency = positiveDocList.get(i).size();
            System.out.println("N = " + reviewCount + " df = " + termFrequency);
            Double w = Math.log10(reviewCount/termFrequency);
            weight.setWeight(positiveTermList.get(i), termFrequency,w);
            positiveWeights.add(weight);
        }
        Collections.sort(positiveWeights, new SortByCount());
        for (int i=0 ; i<negativeTermList.size();i++){
            weights weight = new weights();
            Integer termFrequency = negativeDocList.get(i).size();
            Double w = Math.log10(reviewCount/termFrequency);
            weight.setWeight(negativeTermList.get(i), termFrequency,w);
            negativeWeights.add(weight);
        }
        Collections.sort(negativeWeights, new SortByCount());
        for (int i=0 ; i<neutralTermList.size();i++){
            weights weight = new weights();
            Integer termFrequency = neutralDocList.get(i).size();
            Double w = Math.log10(reviewCount/termFrequency);
            weight.setWeight(neutralTermList.get(i), termFrequency,w);
            neutralWeights.add(weight);
        }
        Collections.sort(neutralWeights, new SortByCount());
        for (weights p : positiveWeights) {
            System.out.println(p.term + " " + p.countTerm + " " + p.weight );
        }
        System.out.println();
        for (weights n : negativeWeights) {
            System.out.println(n.term + " " + n.countTerm + " " + n.weight );
        }
    }


    /**
     *  isInPositive Method checks if the given key word exists in the
     *  positive list
     * @param key -     The term to be searched
     * @return  -       return -1 when the word is not in the list
     */
    public int isInPositive(String key) {
        // Local Variable deceleration.
        int lo = 0;
        int hi = positive.length - 1;
        // Local Variable deceleration ends.
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int result = key.compareTo(positive[mid]);
            if (result < 0) {
                hi = mid - 1;
            }// if ends.
            else if (result > 0) {
                lo = mid + 1;
            }// else if ends.
            else {
                return mid;
            }// else ends.
        }// while ends.
        return -1;
    }//isInPositive ends.

    /**
     *  isInNegative Method checks if the given key word exists in the
     *  negative list
     * @param key -     The term to be searched
     * @return  -       return -1 when the word is not in the list
     */
    public int isInNegative(String key) {
        // Local Variable deceleration.
        int lo = 0;
        int hi = negative.length - 1;
        // Local Variable deceleration ends.
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int result = key.compareTo(negative[mid]);
            if (result < 0) {
                hi = mid - 1;
            }//if ends.
            else if (result > 0) {
                lo = mid + 1;
            }// else if ends.
            else {
                return mid;
            }// else ends.
        }// while ends.
        return -1;
    }// isInNegative ends.

    /**
     *  isInNeutral Method checks if the given key word exists in the
     *  neutral list
     * @param key -     The term to be searched
     * @return  -       return -1 when the word is not in the list
     */
    public int isInNeutral(String key) {
        // Local Variable deceleration.
        int lo = 0;
        int hi = neutral.length - 1;
        // Local Variable deceleration ends.
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int result = key.compareTo(neutral[mid]);
            if (result < 0) {
                hi = mid - 1;
            }// if ends.
            else if (result > 0) {
                lo = mid + 1;
            }// else if ends.
            else {
                return mid;
            }// else ends.
        }// while ends.
        return -1;
    }// isInNeutral ends.

    /**
     * compute method checks the sum of the sentiment analysed
     * ratings for each restaurant and checks if the overall
     * rating for the restaurant rating would be positive,
     * negative or neutral.
     */
    public void compute(){
        // Local Variable deceleration.
        int count = 0;
        // Local Variable deceleration ends.
        for (int i =0; i< restID.size(); i++) {
            ArrayList<Double> rate =  reviewRate.get(i);
            int sum = 0;
            int value = 0;
            for (Double rating: rate) {
                count = count + 1;
                if(rating > 0){
                    value = 1;
                }// if ends.
                else if(rating < 0){
                    value = -1;
                }// else if ends.
                else{
                    value = 0;
                }// else ends.
                sum = sum + value;
            }// for ends.
            int star = calculateStar(sum);
            Average.add(i, sum);
            starArray.add(i,star);
        }// for ends.
        for (int i = 0; i < restID.size(); i++) {
            System.out.println(restID.get(i) + "\t = " + Average.get(i)+ "\t = " + starArray.get(i));
        }// for ends.
        System.out.println("Total Review Count is = " + count);
    }// compute ends.

    public int calculateStar(int sum){
        int star=0;
        if(sum > 40){
            star = 5;
        }
        else if(sum > 15){
            star = 4;
        }
        else if(sum > 0){
            star = 3;
        }
        else if(sum > -10){
            star = 2;
        }
        else{
            star = 1;
        }
        return star;
    }

    public void database(){
        String url1 = "jdbc:mysql://localhost:3306/ratetouille?user=root&password=password";

          try {
              Connection conn1 = DriverManager.getConnection(url1);
              System.out.println("Connected to database Ratetouille");


              String sql = "show tables like 'reviews'";
              PreparedStatement statement = conn1.prepareStatement(sql);
              int row = statement.executeUpdate();
              System.out.println("Value of row for review =  " + row );
              if (row < 0) {
                  String sql3 = "DROP TABLE reviews";
                  PreparedStatement statement3 = conn1.prepareStatement(sql3);
                  statement3.executeUpdate();
                  System.out.println("Dropped reviews");
              }

              sql = "show tables like 'restaurant'";
              statement = conn1.prepareStatement(sql);
              row = statement.executeUpdate();
              System.out.println("Value of row for restaurant =  " + row );
              if (row < 0) {
                  String sql0 = "DROP TABLE restaurant";
                  PreparedStatement statement0 = conn1.prepareStatement(sql0);
                  statement0.executeUpdate();
              }

              String sql1 = "CREATE TABLE restaurant(rid varchar(6), lid varchar(3), cuisine varchar(50), review_rating int, star int, primary key(rid));";
              PreparedStatement statement1 = conn1.prepareStatement(sql1);
              statement1.executeUpdate();

              String sql2 = "INSERT INTO restaurant (rid, lid, cuisine, review_rating, star) VALUES (?, ?, ?, ?, ?)";
              PreparedStatement statement2 = null;

              CSVReader reader1 = null;
              try {
                  //parsing a CSV file into CSVReader class constructor
                  reader1 = new CSVReader(new FileReader("restaurant_details.csv"));

                  String[] nextLine;
                  int i = 0;
//                  System.out.println(nextLine[0] + " " + nextLine[1] + " " + nextLine[2]);
                  //reads one line at a time
                  while ((nextLine = reader1.readNext()) != null) {
//                      System.out.println(nextLine[0]);
                      statement2 = conn1.prepareStatement(sql2);
                      statement2.setString(1, nextLine[0]);
                      statement2.setString(2 , nextLine[1]);
                      statement2.setString(3 , nextLine[2]);
                      statement2.setInt(4 , Average.get(i));
                      statement2.setInt(5 , starArray.get(i));
                      statement2.executeUpdate();
                      i = i + 1;
                  }// end while
                  System.out.println("Count of reviews = " + i);
              }// end try
              catch (Exception e) {
                  e.printStackTrace();
              }// end catch


              String sql4 = "CREATE TABLE reviews(entryid int not null auto_increment,rid varchar(6), review varchar(16000), primary key(entryid));"; //add this after removing ?  , constraint fk_reviews_restaurant foreign key(rid) references restaurant(rid)
              PreparedStatement statement4 = conn1.prepareStatement(sql4);
              statement4.executeUpdate();

              String sql5 = "INSERT INTO reviews (rid, review) VALUES (?, ?)";
                int count = 0;
                int rows = 0;
                PreparedStatement statement5 = null;
                for (Reviews item: data) {
                   // System.out.println(item.restaurantID);
                    statement5 = conn1.prepareStatement(sql5);
                    statement5.setString(1 , item.restaurantID);
                    statement5.setString(2 , item.review);
                    count = count + 1;
                    rows = statement5.executeUpdate();
                }
                    System.out.println("rows added = " + count);
                    if (rows > 0){
                        System.out.println(rows + " New rows added");
                    }

          } catch (
    SQLException e) {
              System.out.println("error found in the database");
              e.printStackTrace();
         }
      }

    /**
     * Core Algorithm Ends here
     */
    //Main function of the program
    public static void main(String[] args) {
          Ratetouille p1 = new Ratetouille();
/**        new.csv is the file that contains 10,000 reviews and can be run by uncommenting the line.
*        Warning: if the next line is uncommented and run make sure that the line with test.csv must be uncommented.
*/

          p1.readReviews("new.csv");

/**        test.csv contains a small portion of 10 reviews from 3 restaurants covering all the cases of
*        negative, positive and neutral cases
*/
//          p1.readReviews("test.csv");
          System.out.println(p1.data1.size());
          p1.stemReviews(p1.data1);
          p1.readWords();
          p1.sentimentAnalysis();
          p1.compute();
          p1.database();
    }// end main
}// end Ratetouille

//https://maps.googleapis.com/maps/api/geocode/json?address=(YOUR_ADDRESS)&key=AIzaSyCgUUPw5E6Bfdel8JCaGvLdY6M1saCwV7Q

//API Key: AIzaSyCgUUPw5E6Bfdel8JCaGvLdY6M1saCwV7Q
//https://jsonplaceholder.typicode.com/albums

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;
import java.lang.Object;
import javax.json.*;
import javax.json.stream.JsonParsingException;


class DistanceBtwnCities {
  private static HttpURLConnection connection;
  
  public static void main(String[] args) {
    //System.out.println(Arrays.toString(getLocation()));
    //System.out.println(Arrays.toString(getLocation()));
    distance(getLocation(),getLocation());
  }
  
  
  
  /** Asks the user for the address they want to get the coordinates from
    * @return the address of the place in the coorect format*/
  public static String getAddress() {
    //Get the city name from the user
    String location;
    System.out.println("What city would you like to get the cooridinates for?");
    Scanner scan = new Scanner (System.in);
    location = scan.nextLine().replaceAll(" ", "+").toLowerCase();
    return location;
  }
  
  
  /** 
   * Connects to the google maps api and extracts 
   * the longatude and latitude of the address asked for
   * @return an array of JsonNumbers with the coordiantes*/
  public static JsonNumber[] getLocation (){
    //Get the url to the api
    String urlBeginning = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    String apiKey = "&key=AIzaSyCgUUPw5E6Bfdel8JCaGvLdY6M1saCwV7Q";
    String fullUrl = urlBeginning + getAddress() + apiKey;
    
    BufferedReader reader;
    String line;
    StringBuffer responseContent = new StringBuffer();
    
    JsonNumber [] loc = new JsonNumber[2];

    try {
      URL url = new URL (fullUrl);
      connection = (HttpURLConnection) url.openConnection();
      
      //request setup
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);
      
      int status = connection.getResponseCode();
      
      
      // if it doesn't work
      if(status > 299){
        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        while((line = reader.readLine()) != null){
          responseContent.append(line);
        }
        reader.close();
      } else {
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = reader.readLine()) != null){
          responseContent.append(line);
        }
        
        //Finds the object
        JsonReader jReader = Json.createReader(new StringReader(responseContent.toString()));
        JsonObject obj = jReader.readObject();
        //Finds the array results in the object
        JsonArray results = obj.getJsonArray("results");
        
        //Goes through results and finds the Geometry object
        for(int i = 0; i < results.size(); i++){
          JsonObject g = results.getJsonObject(i);
          JsonObject geometry = g.getJsonObject("geometry");
          //Finds the location object in Geometry
          JsonObject location = geometry.getJsonObject("location");
          //Finds the latitude and longatude in location
          JsonNumber lat = location.getJsonNumber("lat");
          JsonNumber lng = location.getJsonNumber("lng");
          
          System.out.println(lng_);
          //adds it to an array
          loc [0] = lat;
          loc [1] = lng;
        }
        reader.close();
        jReader.close();
      }
      //If there is an error
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JsonParsingException e) {
      e.printStackTrace();
      //} catch(SocketTimeoutException e){ 
      //  System.out.println("Took to long");
    }finally {
      connection.disconnect();
    }
    


    return loc;
  }
  
  public static void distance (JsonNumber[] loc1, JsonNumber[] loc2){
    System.out.println(Arrays.toString(loc1) + Arrays.toString(loc2));
    float dist = 0;
    Math.abs(Math.sqrt(Math.pow((loc1[0] - loc2[0]), 2) + Math.pow((loc1[1] - loc2[1]), 2)));
  }
  
  
}

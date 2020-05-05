
//https://maps.googleapis.com/maps/api/geocode/json?address=(YOUR_ADDRESS)&key=AIzaSyCgUUPw5E6Bfdel8JCaGvLdY6M1saCwV7Q

//API Key: AIzaSyCgUUPw5E6Bfdel8JCaGvLdY6M1saCwV7Q
//https://jsonplaceholder.typicode.com/albums

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.json.*;
import javax.json.stream.JsonParsingException;


class DistanceBtwnCities {
  private static HttpURLConnection connection;
  
  //Get the url to the api
  static String urlBeginning = "https://maps.googleapis.com/maps/api/geocode/json?address=";
  static String apiKey = "&key=AIzaSyCgUUPw5E6Bfdel8JCaGvLdY6M1saCwV7Q";
  static String fullUrl = urlBeginning + getAddress() + apiKey;
  
  static BufferedReader reader;
  static String line;
  static StringBuffer responseContent = new StringBuffer();
  
  public static void main(String[] args) {
    System.out.println(Arrays.toString(getLocation()));
  }
  
  
  public static JsonNumber[] getLocation (){
    JsonNumber [] loc = new JsonNumber[2];
    try {
      URL url = new URL (fullUrl);
      connection = (HttpURLConnection) url.openConnection();
      
      //request setup
      connection.setRequestMethod("GET");
      //connection.setConnectionTimeout(5000);
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
        
        JsonReader jReader = Json.createReader(new StringReader(responseContent.toString()));
        JsonObject obj = jReader.readObject();
       
        //copy code from albumsPractice if this doesn't work
        JsonArray results = obj.getJsonArray("results");
        for(int i = 0; i < results.size(); i++){
          JsonObject g = results.getJsonObject(i);
          JsonObject geometry = g.getJsonObject("geometry");
          JsonObject location = geometry.getJsonObject("location");
          JsonNumber lat = location.getJsonNumber("lat");
          JsonNumber lng = location.getJsonNumber("lng");
          loc [0] = lat;
          loc [1] = lng;
          //int[] loc = {lat, lng};
          //System.out.println(lat.toString());
          //System.out.println(lng.toString());
        }
        reader.close();
        jReader.close();// after this is done
      }
      //System.out.println(responseContent.toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JsonParsingException e) {
      e.printStackTrace();
    } finally {
      connection.disconnect();
    }
    
    return loc;
    
  }
  
  public static String getAddress() {
    //Get the city name from the user
    String city;
    System.out.println("What city would you like to get the cooridinates for?");
    Scanner scan = new Scanner (System.in);
    city = scan.nextLine().replaceAll(" ", "+").toLowerCase();
    return city;
  }
  
  
}

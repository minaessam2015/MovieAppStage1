package com.example.android.movieappstage1.NetworkUtils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mina essam on 25-Mar-17.
 */

public class NetworkUtils {
    /*this class is responsible for
    1-build the url
    2-make the http request
     */
    private static String BASE_URL="http://api.themoviedb.org/3/movie/";
    private static String SEARCH_TYPE_POPULAR="popular/";
    private static String SEARCH_TYPE_TOP_RATED="top_rated/";
    private static String API_PARAM="api_key";
    private static String API_KEY="80d165558137e1d2cd4d07092d2292df";
    public static URL makePopularSearchURL(){
        Uri uri=Uri.parse(BASE_URL+SEARCH_TYPE_POPULAR).buildUpon().appendQueryParameter(API_PARAM,API_KEY).build();
        URL builtURL=null;
        try {
            builtURL=new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.d("NetworkUtils",builtURL.toString());
        return builtURL;
    }
    public static URL makeTopRatedURL(){
        Uri uri=Uri.parse(BASE_URL+SEARCH_TYPE_TOP_RATED).buildUpon().appendQueryParameter(API_PARAM,API_KEY).build();
        URL builtURL=null;
        try {
           builtURL =new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("NetworkUtils",builtURL.toString());
        return builtURL;
    }

    //poster path URL
    private static final String POSTER_BASE_URL="http://image.tmdb.org/t/p/w500";

    public static URL makePosterpathURL(String path){
        Uri uri= Uri.parse(POSTER_BASE_URL+path).buildUpon().build();
        URL url=null;
        try {
            url=new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
       return url;
    }

    public static String makeHTTPRequest(URL url)throws IOException{
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        try {
            InputStream inputStream=connection.getInputStream();
            Scanner scanner=new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasNest=scanner.hasNext();
            if (hasNest){
                return scanner.next();
            }
            else {
                return null;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        return null;
    }


}

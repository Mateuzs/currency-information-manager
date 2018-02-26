package main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;

/**
 * Creates a router sending http requests.
 */
public class RouterHTTP implements IRouterHTTP {


    public InputStream sendRequest(String argument) throws IOException {

        URL address = new URL(argument);


            InputStream addressConnection = address.openStream();



    return addressConnection;


    }
}
package main;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.URLConnection;

/**
 *Parses input stream to json structure.
 */
public class JsonParser implements IJsonParser {

    /**
     * Parses input stream to JsonArray.
     * @param inputStream InputStream
     * @return JsonArray
     * @throws IOException
     */
    public JsonArray parseJsonDataToArray(InputStream inputStream) throws IOException {

        JsonReader jsonreader = Json.createReader(inputStream);

        JsonArray jsonArray = jsonreader.readArray();

        return jsonArray;
    }

    /**
     * Parses input stream to JsonObject.
     * @param inputStream InputStream
     * @return
     * @throws IOException
     */

    public JsonObject parseJsonDataToObject(InputStream inputStream) throws IOException {


        JsonReader jsonReader = Json.createReader(inputStream);

        JsonObject jsonObject = jsonReader.readObject();


        return jsonObject;

    }


}
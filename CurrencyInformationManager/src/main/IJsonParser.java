package main;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.io.InputStream;

/**
 * Parses stream from http request to json structure.
 */
public interface IJsonParser {

    /**
     * parses input stream to json Array.
     * @param inputStream InputStream
     * @return JsonArray
     * @throws IOException
     */
    JsonArray parseJsonDataToArray(InputStream inputStream) throws IOException;

    /**
     * parses input stream to json Object
     * @param inputStream InputStream
     * @return JsonObject
     * @throws IOException
     */
    JsonObject parseJsonDataToObject(InputStream inputStream) throws IOException;

}

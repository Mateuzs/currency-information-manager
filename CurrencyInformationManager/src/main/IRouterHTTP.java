package main;

import javax.json.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;

/**
 * Sends HTTP requests.
 */
public interface IRouterHTTP {

  /**
   * Sends HTTP request.
   * @param argument String
   * @return InputStream
   * @throws IOException
   */
  public InputStream sendRequest(String argument) throws IOException;


}

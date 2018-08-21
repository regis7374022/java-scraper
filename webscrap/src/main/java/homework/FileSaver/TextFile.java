package homework.FileSaver;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Рома on 20.08.2018.
 */
public class TextFile implements SaveToFile {

  @Override
  public void save(String fileName, String content) {
    fileName = fileName.replace("https://", "");
    fileName = fileName.replaceAll("[^a-zA-Z0-9]+", "-");

    try (PrintWriter out = new PrintWriter("sourses/" + fileName + ".txt")) {
      out.println(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

package homework;

import homework.FileSaver.FileFactory;
import homework.parser.ProcessorFactory;

/**
 * Hello world!
 */
public class App {

  private final static String URL = "https://www.ynet.co.il";
  private final static int LINKS_PER_PAGE = 5;
  private final static int DEPTH = 3;
  private static boolean isNeedUnique = true;

  public static void main(String[] args) {
    Scrapper scrapper = new Scrapper(ProcessorFactory.JSOUP_PROCESSOR, FileFactory.TEXT_FILE,
        LINKS_PER_PAGE, DEPTH, isNeedUnique, URL);
    scrapper.scrap();
  }


}

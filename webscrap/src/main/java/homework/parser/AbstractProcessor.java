package homework.parser;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;

/**
 * Created by Рома on 20.08.2018.
 */
public interface AbstractProcessor<T> {
     T getWebPage(String url);
     void loadDocument(String url);
     ArrayList<String> getListOfLinks(boolean isUniqueLinks, int numOfLinks);
     String getDocumentAsString();

}

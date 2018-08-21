package homework.parser.processors;

import static homework.parser.constants.JsoupConstants.HREF;
import static homework.parser.constants.JsoupConstants.HTML_LINK_NODE;

import homework.parser.DocumentProccesing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Рома on 20.08.2018.
 */
public class JsoupProcessing extends DocumentProccesing<Document> {

  private Document document;

  public JsoupProcessing() {
    uniqueCheck = new HashSet<>();
    counterOfElementsInHash = uniqueCheck.size();
  }

  @Override
  public ArrayList<String> getListOfLinks(boolean isUniqueLinks, int numOfLinks) {
    Elements links = getAllLinksFromWebPage(document);
    return isUniqueLinks ? getUniqueLinksByNum(links, numOfLinks)
        : getRegularLinksByNum(links, numOfLinks);
  }

  @Override
  public Document getWebPage(String url) {
    try {
      return Jsoup.connect(url).get();
    } catch (IOException e) {
      return new Document(url);
    }
  }

  @Override
  public void loadDocument(String url) {
    this.document = getWebPage(url);
  }

  public Elements getAllLinksFromWebPage(Document webPage) {
    return webPage.select(HTML_LINK_NODE);
  }

  @Override
  public String getDocumentAsString() {
    return document.wholeText();
  }

  private String getLinkAsString(Element element) {
    return element.attr(HREF);
  }

  public ArrayList<String> getUniqueLinksByNum(Elements linkArray, int numOfLinks) {
    return linkArray.stream().map(this::getLinkAsString).filter(this::isUnique).limit(numOfLinks)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private ArrayList<String> getRegularLinksByNum(Elements linkArray, int numOfLinks) {
    return linkArray.stream().map(this::getLinkAsString).limit(numOfLinks)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private boolean isUnique(String link) {
    uniqueCheck.add(link);
    if (uniqueCheck.size() > counterOfElementsInHash) {
      counterOfElementsInHash = uniqueCheck.size();
      return true;
    }
    return false;
  }
}

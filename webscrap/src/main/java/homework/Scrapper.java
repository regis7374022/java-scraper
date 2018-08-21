package homework;

import homework.FileSaver.FileFactory;
import homework.FileSaver.SaveToFile;
import homework.parser.AbstractProcessor;
import homework.parser.ProcessorFactory;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Рома on 20.08.2018.
 */
public class Scrapper {

  private final AbstractProcessor abstractProcessor;
  private final SaveToFile saveToFile;
  private final int LINKS_PER_PAGE;
  private final int DEPTH;
  private final boolean isNeedUnique;
  private final String URL;
  private Queue<String> linksToProcces;

  public Scrapper(ProcessorFactory factory, FileFactory saveToFile,
      int LINKS_PER_PAGE, int DEPTH,
      boolean isNeedUnique, String url) {
    this.abstractProcessor = factory.getProcessor();
    this.saveToFile = saveToFile.getToFile();
    this.LINKS_PER_PAGE = LINKS_PER_PAGE;
    this.DEPTH = DEPTH;
    this.isNeedUnique = isNeedUnique;
    URL = url;
    this.linksToProcces = new LinkedList<>();
  }

  @SuppressWarnings("unchecked")
  public void scrap() {
    abstractProcessor.loadDocument(URL);
    saveToFile.save(URL, abstractProcessor.getDocumentAsString());
    linksToProcces.addAll(abstractProcessor.getListOfLinks(isNeedUnique, LINKS_PER_PAGE));
    for (int i = 0; i < DEPTH; i++) {
      int tempSize = linksToProcces.size();
      ExecutorService pool = Executors.newFixedThreadPool(tempSize);
      Queue<String> linkedTemp = new LinkedList<>();
      ArrayList<Callable<ArrayList<String>>> callables = new ArrayList<>();

      for(int j =0; j < tempSize; j++) {
        callables.add(this::call);
      }
      callables.stream().map(pool::submit).forEach(x -> {
        try {
          linkedTemp.addAll(x.get());
        } catch (InterruptedException | ExecutionException e) {
          /// TODO romanz : 8/20/2018 add logger and throw checked exception
          e.printStackTrace();
        }
      });
      linksToProcces.addAll(linkedTemp);
    }
  }

  @SuppressWarnings("unchecked")
  private ArrayList<String> call() {
    String tempUrl = linksToProcces.poll();
    abstractProcessor.loadDocument(tempUrl);
    saveToFile.save(tempUrl, abstractProcessor.getDocumentAsString());
    return abstractProcessor.getListOfLinks(isNeedUnique, LINKS_PER_PAGE);
  }
}

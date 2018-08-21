package homework.parser;

import java.util.HashSet;

/**
 * Created by Рома on 20.08.2018.
 */
public abstract class DocumentProccesing<T> implements AbstractProcessor<T> {

  protected HashSet<String> uniqueCheck;
  protected int counterOfElementsInHash;

}

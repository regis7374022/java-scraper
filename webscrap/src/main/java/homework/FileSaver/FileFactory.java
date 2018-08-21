package homework.FileSaver;

/**
 * Created by Рома on 20.08.2018.
 */
public enum FileFactory {
  TEXT_FILE("text", new TextFile());

  private final String name;
  private final SaveToFile toFile;

  FileFactory(String name, SaveToFile toFile) {
    this.name = name;
    this.toFile = toFile;
  }

  public String getName() {
    return name;
  }

  public SaveToFile getToFile() {
    return toFile;
  }
}

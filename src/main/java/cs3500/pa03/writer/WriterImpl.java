package cs3500.pa03.writer;

import java.io.IOException;
import java.util.Objects;

/**
 * writes to some appendable output
 */
public class WriterImpl implements Writer {
  // Fields
  private final Appendable appendable;
  // Constructor

  /**
   * constructor
   *
   * @param appendable any appendable object to write to
   */
  public WriterImpl(Appendable appendable) {
    this.appendable = Objects.requireNonNull(appendable);
  }

  /**
   * try to write the given phrase to appendable, otherwise throw exception
   *
   * @param phrase the content to write
   */
  @Override // @Override since it's from the interface
  public void write(String phrase) {
    try {
      appendable.append(phrase); // this may fail, hence the try-catch
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
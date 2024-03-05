package cs3500.pa03.view;

import cs3500.pa03.writer.Writer;
import cs3500.pa03.writer.WriterImpl;
import java.util.Objects;

/**
 * represents view
 */
public class CoreView {
  private Appendable output;
  private Writer writer;

  /**
   * constructor
   *
   * @param output output "device"
   */
  public CoreView(Appendable output) {
    this.output = Objects.requireNonNull(output);
    this.writer = new WriterImpl(this.output);
  }

  /**
   * writes the given string to the output
   *
   * @param str the string to write to the output
   */
  public void writeToOutput(String str) {
    this.writer.write(str);
  }
}

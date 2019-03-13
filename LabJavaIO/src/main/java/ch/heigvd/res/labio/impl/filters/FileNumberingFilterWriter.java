package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private Integer count = 1;
  private boolean newLine = true;

  public FileNumberingFilterWriter(Writer out){
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    if(off > str.length() || (off + len) > str.length())
      throw new InvalidParameterException("off or/and (off + len) are too long");

    for(int c : str.substring(off, off + len).toCharArray())
      write(c);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if(off > cbuf.length || (off + len) > cbuf.length)
      throw new InvalidParameterException("off or/and (off + len) are too long");

    for(int i = off; i < (off + len); ++i)
      write(cbuf[i]);
  }

  @Override
  public void write(int c) throws IOException {
    if(newLine) {
      if(c != '\n')
        insertNumberLine();

      newLine = false;
    }

    super.write(c);

    if(c == '\r')
      newLine = true;

    if(c == '\n')
      insertNumberLine();
  }

  @Override
  public void close() throws IOException {
    if(newLine)
      insertNumberLine();

    super.close();
  }

  private void insertNumberLine() throws IOException {
    for(int c : String.format("%d\t", count++).toCharArray())
      super.write(c);
  }

}

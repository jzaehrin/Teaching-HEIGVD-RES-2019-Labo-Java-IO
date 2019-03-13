package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    if(off > str.length() || (off + len) > str.length())
      throw new InvalidParameterException("off or/and (off + len) are too long");

    super.write(str.substring(off, off + len).toUpperCase(), 0, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if(off > cbuf.length || (off + len) > cbuf.length)
      throw new InvalidParameterException("off or/and (off + len) are too long");

    for(int i = off; i < (off + len); ++i)
      cbuf[i] = Character.toUpperCase(cbuf[i]);

    super.write(cbuf, off, len);
  }

  @Override
  public void write(int c) throws IOException {
    super.write(Character.toUpperCase(c));
  }

}

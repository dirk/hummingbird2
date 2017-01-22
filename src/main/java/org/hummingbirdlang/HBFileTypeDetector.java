package org.hummingbirdlang;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;

public final class HBFileTypeDetector extends FileTypeDetector {
  @Override
  public String probeContentType(Path path) throws IOException {
    if (path.getFileName().toString().endsWith(".hb")) {
      return HBLanguage.MIME_TYPE;
    }
    return null;
  }
}

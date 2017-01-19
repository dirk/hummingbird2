package org.hummingbirdlang;

import java.io.File;
import java.io.IOException;
import com.oracle.truffle.api.source.Source;

public final class Main {
    public static void main(String[] args) throws IOException {
        Source source = Source.newBuilder(new File(args[0])).build();
    }
}

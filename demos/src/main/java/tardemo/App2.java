package tardemo;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

/**
 * Created by wangguojun01 on 2018/5/17.
 */
public class App2 {
    public static void deGzipArchive(String dir, String filepath) throws Exception {
        final File input = new File(filepath);
        final InputStream is = new FileInputStream(input);
        final CompressorInputStream in = new GzipCompressorInputStream(is, true);
        TarArchiveInputStream tin = new TarArchiveInputStream(in);
        TarArchiveEntry entry = tin.getNextTarEntry();
        while (entry != null) {
            File archiveEntry = new File(dir, entry.getName());
            archiveEntry.getParentFile().mkdirs();
            if (entry.isDirectory()) {
                archiveEntry.mkdir();
                entry = tin.getNextTarEntry();
                continue;
            }
            OutputStream out = new FileOutputStream(archiveEntry);
            IOUtils.copy(tin, out);
            out.close();
            entry = tin.getNextTarEntry();
        }
        in.close();
        tin.close();
    }
}

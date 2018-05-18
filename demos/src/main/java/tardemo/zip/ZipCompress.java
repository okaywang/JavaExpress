package tardemo.zip;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * Created by wangguojun01 on 2018/5/17.
 */

class ZipCompress {
    public static void zip(String zipFileName, String sourceFileName) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bos = new BufferedOutputStream(out);
        File sourceFile = new File(sourceFileName);
        compress(out, bos, sourceFile, sourceFile.getName());
        bos.close();
        out.close();
    }

    private static void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws Exception {
        if (sourceFile.isDirectory()) {
            File[] flist = sourceFile.listFiles();
            if (flist.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/"));
            } else {
                for (int i = 0; i < flist.length; i++) {
                    compress(out, bos, flist[i], base + "/" + flist[i].getName());
                }
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = null;
            BufferedInputStream bis = null;
            try {
                fos = new FileInputStream(sourceFile);
                bis = new BufferedInputStream(fos);

                int tag;
                while ((tag = bis.read()) != -1) {
                    bos.write(tag);
                }
            } finally {
                bis.close();
                fos.close();
            }
        }
    }
}


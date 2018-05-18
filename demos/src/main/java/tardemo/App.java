package tardemo;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Path;

/**
 * Created by wangguojun01 on 2018/5/16.
 */
public class App {
    private static String inputStr;
    private static String name = "data.xml";

    public static void before() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
        sb.append("\r\n");
        sb.append("<dataGroup>");
        sb.append("\r\n\t");
        sb.append("<dataItem>");
        sb.append("\r\n\t\t");
        sb.append("<data>");
        sb.append("Test");
        sb.append("</data>");
        sb.append("\r\n\t");
        sb.append("<dataItem>");
        sb.append("\r\n");
        sb.append("</dataGroup>");

        inputStr = sb.toString();
    }

    public static void testArchiveFile() throws Exception {

        byte[] contentOfEntry = inputStr.getBytes();

        String path = "d:/" + name;

        FileOutputStream fos = new FileOutputStream(path);

        fos.write(contentOfEntry);
        fos.flush();
        fos.close();

        TarUtils.archive(path);

        TarUtils.dearchive(path + ".tar");

        File file = new File(path);

        FileInputStream fis = new FileInputStream(file);

        DataInputStream dis = new DataInputStream(fis);

        byte[] data = new byte[(int) file.length()];

        dis.readFully(data);

        fis.close();

        String outputStr = new String(data);
        System.out.println(outputStr);
    }

    public static void testArchiveDir() throws Exception {
        String path = "d:/fd";
        TarUtils.archive(path);

        TarUtils.dearchive(path + ".tar", "d:/fds");
    }

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(500000);
        byte[] data = new byte[10000];
        TarArchiveOutputStream archiveOutputStream = new TarArchiveOutputStream(new GzipCompressorOutputStream(new BufferedOutputStream(bos)));
        archiveOutputStream.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);

        String[] files = {"C:\\Users\\wangguojun01\\Documents\\baas\\codetest\\chaincodeendorsementpolicy.yaml",
                "C:\\Users\\wangguojun01\\Documents\\baas\\codetest\\gpayorder_cc.go",
                "C:\\Users\\wangguojun01\\Documents\\baas\\codetest\\resources\\readme.txt"};

        for (String filename : files) {
            File localFile1 = new File(filename);
            String relativePath = filename.substring(filename.indexOf("baas"));
            ArchiveEntry archiveEntry = new TarArchiveEntry(localFile1, "src/" + relativePath);
            archiveOutputStream.putArchiveEntry(archiveEntry);
            FileInputStream fileInputStream = new FileInputStream(localFile1);
            IOUtils.copy(fileInputStream, archiveOutputStream);
            IOUtils.closeQuietly(fileInputStream);
            archiveOutputStream.closeArchiveEntry();
        }
        IOUtils.closeQuietly(archiveOutputStream);

        byte[] fileContent = bos.toByteArray();
        System.out.println(new String(fileContent));
        String target = "C:\\Users\\wangguojun01\\Documents\\baas\\a.gz";
        FileUtils.writeByteArrayToFile(new File(target), fileContent);

        FileInputStream fileInputStream = new FileInputStream(target);
        CompressorInputStream in = new GzipCompressorInputStream(fileInputStream, true);
        TarArchiveInputStream tin = new TarArchiveInputStream(in);
        ArchiveEntry nextEntry = tin.getNextEntry();
        while (nextEntry != null){
            System.out.println(nextEntry.getName());
            File archiveEntry = new File("C:\\Users\\wangguojun01\\Documents\\baas\\wj", nextEntry.getName());
//            if (archiveEntry.isDirectory()){
//                archiveEntry.mkdir();
//                nextEntry = tin.getNextEntry();
//                continue;
//            }
            archiveEntry.getParentFile().mkdirs();

            OutputStream out = new FileOutputStream(archiveEntry);
            IOUtils.copy(tin, out);
            out.close();
            nextEntry = tin.getNextEntry();
        }
        in.close();
        tin.close();

    }

    public static void main1(String[] args) throws Exception {
        File path = new File("D:\\testtar");
        TarUtils.archive(path);
    }
}

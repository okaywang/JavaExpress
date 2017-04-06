package zhaopin;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by guojun.wang on 2017/4/6.
 */
public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //generate();

        // Create a File object on the root of the directory containing the class file
        File file = new File("D:\\");


        // Convert File to a URL
        URL url = file.toURL();          // file:/c:/myclasses/
        URL[] urls = new URL[]{url};

        // Create a new class loader with the directory
        ClassLoader cl = new URLClassLoader(urls);

        // Load in the class; MyClass.class should be located in
        // the directory file:/c:/myclasses/com/mycompany
        Class cls = cl.loadClass("zhaopin.HelloWorld");
        Object o = cls.newInstance();
        System.out.println(o);
        System.out.println(cls.getName());

    }

    private static void generate() throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        String className = "zhaopin/HelloWorld";
        classWriter.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null);


//        MethodVisitor initVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
//        initVisitor.visitCode();
//        initVisitor.visitVarInsn(Opcodes.ALOAD, 0);
//        initVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "V()");
//        initVisitor.visitInsn(Opcodes.RETURN);
//        initVisitor.visitMaxs(1, 1);
//        initVisitor.visitEnd();

        MethodVisitor helloVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "sayHello123", "()V", null, null);
        helloVisitor.visitCode();
        helloVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        helloVisitor.visitLdcInsn("hello world!");
        helloVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        helloVisitor.visitInsn(Opcodes.RETURN);
        helloVisitor.visitMaxs(1, 1);
        helloVisitor.visitEnd();

        classWriter.visitEnd();
        byte[] code = classWriter.toByteArray();
        File file = new File("D:\\zhaopin\\HelloWorld.class");
        FileOutputStream output = new FileOutputStream(file);
        output.write(code);
        output.close();
    }

}

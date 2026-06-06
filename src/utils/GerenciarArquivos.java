package utils;

import java.io.*;

public class GerenciarArquivos {

    public static void escreverObjectos(Object obj, String file) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            IO.println(e.getMessage());
        }
    }

    public static Object lerObjectos(String file) {
        Object objs = new Object();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            objs = ois.readObject();
        } catch(ClassNotFoundException | IOException e) {
            IO.println(e.getMessage());
        }

        return objs;
    }

}

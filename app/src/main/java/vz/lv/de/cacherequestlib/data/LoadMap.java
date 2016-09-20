package vz.lv.de.cacherequestlib.data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import vz.lv.de.cacherequestlib.model.ObjectSaver;


public class LoadMap {

    public static String folderName= "/vzlvdecacherequestlibloadmap/";
    Context context;

    public LoadMap(Context context) {
        this.context = context;
    }

    public LoadMap() {

    }

    public void save(Object o) {
        try {
            final File dir = new File(context.getCacheDir()+folderName);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.e("ALERT", "could not create the directories");
                } else {
                    Log.e("ALERT", "create the directories");
                }
            } else {
                Log.e("ALERT", "is folder");
            }
            long time = new Date().getTime();
            ObjectSaver objectSaver = (ObjectSaver) o;
            objectSaver.setNameCatchFile(String.valueOf(time));
            objectSaver.setContext(null);
            FileOutputStream fout = new FileOutputStream(context.getCacheDir()+folderName + time + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(objectSaver);
            oos.close();
        } catch (IOException e) {
            Log.e("ALERT", "IOException" + e.toString());
            e.printStackTrace();
        }
    }

    public void readFile() {
        if (context == null)
            throw new RuntimeException("Context is null!");
        try {
            File file = new File(context.getCacheDir()+folderName);
            for (File file1 : file.listFiles()) {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(file1));
                ObjectSaver myPersonObject = (ObjectSaver) input.readObject();
                myPersonObject.setContext(context);
                myPersonObject.start();
                input.close();
            }
        } catch (ClassNotFoundException | IOException e) {
            Log.e("ALERT", "readFile " + e.toString());
            e.printStackTrace();
        }
    }
}

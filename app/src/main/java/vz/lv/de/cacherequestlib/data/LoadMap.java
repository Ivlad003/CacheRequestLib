package vz.lv.de.cacherequestlib.data;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vz.lv.de.cacherequestlib.model.ObjectSaver;


public class LoadMap {

    Context context;

    public LoadMap(Context context) {
        this.context = context;
    }

    public LoadMap() {

    }

    public void save(Object o) {
        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/folderName/");
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
            FileOutputStream fout = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/folderName/" + time + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(objectSaver);
            oos.close();
        } catch (IOException e) {
            Log.e("ALERT", "IOException" + e.toString());
            e.printStackTrace();
        }
    }

    public List<ObjectSaver> readFile() {
        if (context == null)
            throw new RuntimeException("Context is null!");

        List<ObjectSaver> objectSavers = new ArrayList<>();
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/folderName/");
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
        return objectSavers;
    }
}

package vz.lv.de.cacherequestlib.model;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.io.Serializable;

import vz.lv.de.cacherequestlib.data.LoadMap;


public abstract class ObjectSaver implements Serializable {
    private Context mContext;
    private String nameCatchFile = "";

    public ObjectSaver(Context mContext) {
        if (mContext == null)
            throw new RuntimeException("Context is null!");
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getNameCatchFile() {
        return nameCatchFile;
    }

    public void setNameCatchFile(String mId) {
        this.nameCatchFile = mId;
    }

    public void start() {
        if (mContext == null)
            throw new RuntimeException("Context is null!");
        if (isOnline(mContext)) {
            run(mContext);
        } else {
            saveIfNoInternet();
        }
    }

    public void startAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mContext == null)
                    throw new RuntimeException("Context is null!");
                if (isOnline(mContext)) {
                    ObjectSaver.this.run(mContext);
                } else {
                    saveIfNoInternet();
                }
            }
        }).start();
    }

    protected abstract void run(Context context);

    protected void saveIfNoInternet() {
        new LoadMap(mContext).save(this);
    }

    public void removeCatch() {
        File file = new File(mContext.getCacheDir().getAbsolutePath()
                + LoadMap.folderName + getNameCatchFile() + ".ser");
        file.delete();
    }

    public boolean isOnline(Context mContext) {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}

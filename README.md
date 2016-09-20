# SmilesLib


<a href='https://bintray.com/ivlad003/cacherequestlib/CacheRequestLib?source=watch' alt='Get automatic notifications about new "CacheRequestLib" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>  | Version 0.0.1 |
| ------------- | ------------- |

```gradle

dependencies {
    compile 'com.github.ivlad003:CacheRequestLib:0.1'
}

```

Example use
```java
 
public class TestObject extends ObjectSaver {
    public TestObject(Context mContext) {
        super(mContext);
    }

    @Override
    public void run(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<ResponseBody> result = service.listRepos("ivlad003");
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("TestObject", "response " + response.body().string());
                    removeCatch();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                removeCatch();
            }
        });
        Log.d("TestObject2","TestObject2");
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TestObject testObject= new TestObject(this);

        testObject.start();
    }
}
 
```

## AndroidManifest.xml

```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <receiver android:name="vz.lv.de.cacherequestlib.listener.ListenerEnableInternet">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
                <action android:name="android.net.wifi.WIFI_STATE_CHANGED" />
            </intent-filter>
    </receiver>
```
License
----

Apache-2.0

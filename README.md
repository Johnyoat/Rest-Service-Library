# A Simple Rest Library for android
This library is a simple state manager written in Java for android

[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Rest%20Service%20Library-green.svg?style=flat )]( https://android-arsenal.com/details/1/7029 )

### SDK Requiremnet ###
 minSdkVersion >= 15 (Android 4 and above )

### Importing to project ###

#### Example for Gradle: ####

Step 1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
		maven { url "https://maven.google.com" }
	}
}
```

##### If you're using gradle 3.+.+ 
```groovy
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
		google()
	}
}
```

Step 2. Add the dependency
```groovy
implementation 'com.github.Johnyoat:rest-service-lib:0.4'
implementation 'com.google.code.gson:gson:2.8.5'
```

Set 3. Enable Internet Permission

eg [MainActivity.java](https://github.com/Johnyoat/rest-service-lib/blob/master/app/src/main/java/com/litetech/libs/restservice/MainActivity.java)

```java

public class MainActivity extends AppCompatActivity implements RestService.CallBack {

    private TextView restData;
    private RestService restService;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restData = findViewById(R.id.restData);
        dialog = new ProgressDialog(this);

        dialog.setMessage("Fetching Data...");
        dialog.setCancelable(false);

        Button fetchBtn = findViewById(R.id.fetchBtn);

        //Rest Url
        final String restUrl = "https://jsonplaceholder.typicode.com/posts";

        //Setting up Library
//        final RestService restService = new RestService(this);

        //Executing call,Note it's Async call
//        RestService restService = new RestService(this);

        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting Up Rest Service
                restService = new RestService(MainActivity.this);
                //Executing call,Note it's Async call
                restService.execute(restUrl);
                dialog.show();

            }
        });

    }

    //Response from the server
    @Override
    public void onResult(String response, String apiType) {
        if (response != null) {
            dialog.dismiss();
            restData.setText(response);
        } else {
            dialog.dismiss();
            Toast.makeText(this, "Check Network Connectivity", Toast.LENGTH_SHORT).show();
        }
    }
}

```

### Adding Headers (Optional) ###
```java
HashMap<String,String> headers = new HashMap<>();
// headers.put("HeaderName","Value");
headers.put("origin","*")
restService.setHeaders(headers);
```


### Get Method ###
```java
restService.execute(restUrl);
```

### Serializing ###
You can use any json serializing libray but I'll use GSON in this example

```java
Object obj = new Object();
String json = new Gson().toJson(obj);
```



### Post ###
```java
restService.execute(restUrl,json,"post");
```

### Put ###
```java
restService.execute(restUrl,json,"put");
```

## License

Copyright 2018 Litetech Ghana

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

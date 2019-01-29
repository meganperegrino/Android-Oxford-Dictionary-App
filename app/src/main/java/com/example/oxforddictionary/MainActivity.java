package com.example.oxforddictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = dictionaryEntries();


    }

    public void requestApiButtonClick(View v){
        MyRequestAsync myRequestAsync = new MyRequestAsync(this,
                (TextView)findViewById(R.id.textView), (EditText)findViewById(R.id.editText));
        myRequestAsync.execute(url);
    }

    public String dictionaryEntries() {
        final String language = "en";
        String word = "Ace";
        String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        //return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/";
    }

}

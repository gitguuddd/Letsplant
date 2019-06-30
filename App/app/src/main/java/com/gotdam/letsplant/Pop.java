package com.gotdam.letsplant;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Pop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        Button ideti = (Button) findViewById(R.id.ideti);
        Button ismesti = (Button) findViewById(R.id.ismesti) ;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int plotis = dm.widthPixels;
        int aukstis = dm.heightPixels;

        getWindow().setLayout((int) (plotis*.8), (int) (aukstis*.1));

        ideti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"Paspaudete ideti", Toast.LENGTH_LONG).show();
            }
        });
        ismesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"Paspaudete ismesti", Toast.LENGTH_LONG).show();
            }
        });
    }
}
/*val intent: Intent = Intent(applicationContext, Pop::class.java)
            startActivity(intent)*/

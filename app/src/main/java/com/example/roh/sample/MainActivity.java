package com.immacbytes.listviewmysql;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.*;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.content.DialogInterface;
public class MainActivity extends AppCompatActivity {

    private boolean success = false; // boolean
    LinearLayout lLayout;
    TextView tView;
    EditText tf1, tf2,tf3,tf4,tf5,tf6;
    Button btn,cancle;
    AlertDialog.Builder builder;
    //private static final String DB_URL = "jdbc:mysql://DATABASE_IP/DATABASE_NAME";
    private static final String DB_URL = "jdbc:mysql://192.168.1.3/andro"; //"jdbc:mysql://DATABASE_IP/DATABASE_NAME";
    private static final String USER = "root";
    private static final String PASS = "root";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    DB db = new DB();
        Connection con=db.getConnection();
        if (con == null) {
            Toast.makeText(getApplicationContext(),"Fail to connect", Toast.LENGTH_LONG).show();
            builder = new AlertDialog.Builder(this);
            AlertDialog alert =builder.create();
            alert.setTitle("Fail to connect");
            alert.show();
        }
        lLayout = new LinearLayout(this);
        lLayout.setOrientation(LinearLayout.VERTICAL);
        lLayout.setBackgroundColor(Color.BLACK);
        //-1(LayoutParams.MATCH_PARENT) is fill_parent or match_parent since API level 8
        //-2(LayoutParams.WRAP_CONTENT) is wrap_content
        lLayout.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        tView = new TextView(this);
        tView.setText("Hello, This is a view created programmatically! " +
                "You CANNOT change me that easily :-)");

        tf1 =new EditText(this);
        tf1. setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);//change input text size
        tf1.setHint("Enter Firstname.");//placeholder
        tf1.setHintTextColor(Color.BLUE);
        tf1. setTypeface(Typeface.MONOSPACE);
       // tf1.setText("Monospace font");
          //tf1.setText("NO.1");
        tf2 =new EditText(this);
        tf2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);//change input text size
        tf2.setHint("Enter Middlename.");//placeholder
        tf2.setHintTextColor(Color.BLUE);
        tf2. setTypeface(Typeface.MONOSPACE);
        //tf2.setText("Monospace font");
//
        tf3 =new EditText(this);
        tf3. setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);//change input text size
        tf3.setHint("Enter Lastname");//placeholder
        tf3.setHintTextColor(Color.BLUE);
        tf3.setTypeface(Typeface.MONOSPACE);
        //tf3. setText("Monospace font");
//
        tf4 =new EditText(this);
        tf4. setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);//change input text size
        tf4.setHint("Enter Username ");//placeholder
        tf4.setHintTextColor(Color.BLUE);
        tf4. setTypeface(Typeface.MONOSPACE);
//        tf4.setText("Monospace font");
//
        tf5 =new EditText(this);
        tf5. setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);//change input text size
        tf5.setHint("Enter Password ");//placeholder
        tf5.setHintTextColor(Color.BLUE);
        tf5. setTypeface(Typeface.MONOSPACE);
        tf5.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD); //type password ****
//        tf5.setText("Monospace font");
//
        tf6 =new EditText(this);
        tf6. setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);//change input text size
        tf6.setHint("Enter Remarks ");//placeholder
        tf6.setHintTextColor(Color.BLUE);
        tf6. setTypeface(Typeface.MONOSPACE);
//        tf6.setText("Monospace font");

        btn = new Button(this);
        btn.setText("Save");

        cancle = new Button(this);
        cancle.setText("Clear");

        final RelativeLayout.LayoutParams buttonParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);

        RelativeLayout.LayoutParams textParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

        textParams.addRule(RelativeLayout.ABOVE, btn.getId());
        textParams.addRule(RelativeLayout.ABOVE, cancle.getId());
        textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textParams.setMargins(0, 0, 0, 80);

        tView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        lLayout.addView(btn,buttonParams);
        lLayout.addView(cancle,buttonParams);
        lLayout.addView(tView);
        lLayout.addView(tf1,textParams);
        lLayout.addView(tf2,textParams);
        lLayout.addView(tf3,textParams);
        lLayout.addView(tf4,textParams);
        lLayout.addView(tf5,textParams);
        lLayout.addView(tf6,textParams);
        setContentView(lLayout);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    String fname = tf1.getText().toString();
                    String middle = tf2.getText().toString();
                    String last = tf3.getText().toString();
                    String username = tf4.getText().toString();
                    String pass = tf5.getText().toString();

//                    if(fname.isEmpty() || middle.isEmpty() || last.isEmpty() || username.isEmpty()|| pass.isEmpty()){
//                        Toast.makeText(getApplicationContext(),"All field requires", Toast.LENGTH_LONG).show();
//                            return;
//                    }
//                    else {
                        if(fname.isEmpty()){
                            Toast.makeText(getApplicationContext(),"FirstName is required", Toast.LENGTH_LONG).show();
                            return;
                        }else if(middle.isEmpty()){
                            Toast.makeText(getApplicationContext(),"MiddleName required", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if(last.isEmpty()){
                            Toast.makeText(getApplicationContext(),"LastName is required", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if(username.isEmpty()){
                            Toast.makeText(getApplicationContext(),"UserName is required", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if(pass.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Password is required", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {

                            int i = DB.save(fname, middle, last, username, pass);  //db operation not working
                            if (i > 0) {
                                Toast.makeText(getApplicationContext(),"Successfully saved", Toast.LENGTH_LONG).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Fail to save", Toast.LENGTH_LONG).show();
                            }
                        }catch(Exception ex){
                                Toast.makeText(getApplicationContext(),String.valueOf(ex), Toast.LENGTH_LONG).show();
                            }


                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),String.valueOf(e), Toast.LENGTH_LONG).show();
                }
            }
        });

cancle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
    }
});


    }


}

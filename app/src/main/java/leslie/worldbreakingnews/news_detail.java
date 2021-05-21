package leslie.worldbreakingnews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Leslie on 5/21/2021.
 */

public class news_detail extends AppCompatActivity {
    String title,description,time,image_url,name,author;
    ImageView img_view;
    TextView tx_arr[]= new TextView[5];//textView_title,textView_description,textView_time,textView_name,textView_author
    int tx_layout[] = {R.id.title,R.id.description,R.id.time,R.id.name,R.id.author};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences SystemInfo = getSharedPreferences("data", Context.MODE_PRIVATE);
        String data = SystemInfo.getString("font_size", "22");

        img_view = (ImageView) findViewById(R.id.imageView);

        for (int i=0;i<tx_layout.length;i++)
        {
            tx_arr[i] = (TextView) findViewById(tx_layout[i]);
            tx_arr[i].setTextSize(Integer.parseInt(data));
        }
        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        title = bundle.getString("title");
        description ="      "+bundle.getString("description");
        time = bundle.getString("publishedAt");
        image_url = bundle.getString("urlToImage");
        name = bundle.getString("name");
        author = bundle.getString("author");
        getSupportActionBar().setTitle("News");

        Log.d("------------>img",image_url);
        if(image_url == "NO_IMG" || image_url.equals(null))
        {
            Picasso.with(this).load(R.drawable.no_image).resize(1000, 700).into(img_view);
        }
        else
        {
            Picasso.with(this).load(image_url).resize(1000, 700).into(img_view);
        }

        tx_arr[0].setText(title);
        tx_arr[1].setText(description);
        tx_arr[2].setText(time);
        tx_arr[3].setText("Source: "+name);
        tx_arr[4].setText("  "+author);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Runtime.getRuntime().gc();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
    }


}

package leslie.worldbreakingnews;

/**
 * Created by Leslie on 5/21/2021.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class CategoryListAdapt extends ArrayAdapter<List_Item> {
    private ArrayList<List_Item> Item;
    private Context context;
    private int resource;
    private String tx_size , tx_style;
    private int page;
    String image1[] ={"http://mqtt.racinglog.pw/EA/logo_mingpao.png", "http://mqtt.racinglog.pw/EA/logo_oncc.png", "http://mqtt.racinglog.pw/EA/logo_yahoo.png", "http://mqtt.racinglog.pw/EA/logo_ettoday.png", "http://mqtt.racinglog.pw/EA/logo_hk01.png", "http://mqtt.racinglog.pw/EA/logo_headline.png", "http://mqtt.racinglog.pw/EA/logo_rthk.png", "http://mqtt.racinglog.pw/EA/logo_thinkhk.png", "http://mqtt.racinglog.pw/EA/logo_ltn.png"};
    String image2[] ={"http://mqtt.racinglog.pw/EA/logo_all.png","http://mqtt.racinglog.pw/EA/logo_business.png","http://mqtt.racinglog.pw/EA/logo_entertainment.png","http://mqtt.racinglog.pw/EA/logo_health.png","http://mqtt.racinglog.pw/EA/logo_science.png","http://mqtt.racinglog.pw/EA/logo_sports.png","http://mqtt.racinglog.pw/EA/logo_technology.png"};
    public CategoryListAdapt(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<List_Item> Item, String tx_size,String tx_style,int page) {
        super(context, resource, Item);
        this.Item = Item;
        this.context = context;
        this.resource = resource;
        this.tx_size = tx_size;
        this.tx_style = tx_style;
        this.page = page;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item2, null, true);
        }
        if (page == 1) {
            List_Item item = getItem(position); // Object
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imgView);
            Picasso.with(getContext().getApplicationContext()).load(image1[position]).into(imageView);

            TextView textView = (TextView) convertView.findViewById(R.id.txtView);
            textView.setText(item.getTitle());
            textView.setTextSize(Integer.parseInt(tx_size));
            Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+tx_style+".ttf");
            textView.setTypeface(custom_font);


        }
        if (page == 2) {
            List_Item item = getItem(position); // Object
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imgView);
            Picasso.with(getContext().getApplicationContext()).load(image2[position]).into(imageView);
            TextView textView = (TextView) convertView.findViewById(R.id.txtView);
            textView.setText(item.getTitle());
            textView.setTextSize(Integer.parseInt(tx_size));
            Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+tx_style+".ttf");
            textView.setTypeface(custom_font);

        }
        return convertView;
    }
}

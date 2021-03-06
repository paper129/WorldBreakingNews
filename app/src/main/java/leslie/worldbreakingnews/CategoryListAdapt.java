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
    String image1[] ={"http://107.174.218.110/logo_mingpao.png", "http://107.174.218.110/gnews.jpg", "http://107.174.218.110/logo_yahoo.png",
            "http://107.174.218.110/logo_ettoday.png", "http://107.174.218.110/logo_hk01.png", "http://107.174.218.110/sv.png",
            "http://107.174.218.110/logo_rthk.png", "http://107.174.218.110/logo_thinkhk.png", "http://107.174.218.110/logo_ltn.png"};
    String image2[] ={"http://107.174.218.110/logo_all.png","http://107.174.218.110/logo_business.png",
            "http://107.174.218.110/logo_entertainment.png","http://107.174.218.110/logo_health.png","http://107.174.218.110/logo_science.png",
            "http://107.174.218.110/logo_sports.png","http://107.174.218.110/logo_technology.png"};
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

package leslie.worldbreakingnews;

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

/**
 * Created by Leslie on 5/21/2021.
 */

public class RadioListAdapt extends ArrayAdapter<List_Item> {
    private ArrayList<List_Item> Item;
    private Context context;
    private int resource;
    private String tx_size,tx_style;
    private String image[]={"http://107.174.218.110/rthk_01.png", "http://107.174.218.110/rthk_02.png",
            "http://107.174.218.110/rthk_03.png", "http://107.174.218.110/rthk_04.png", "http://107.174.218.110/rthk_05.png"};

    public RadioListAdapt(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<List_Item> Item, String tx_size, String tx_style) {
        super(context, resource, Item);
        this.Item = Item;
        this.context = context;
        this.resource = resource;
        this.tx_size = tx_size;
        this.tx_style = tx_style;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item3,null,true);
        }

        List_Item item = getItem(position); // Object

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgView3);
        Picasso.with(getContext().getApplicationContext()).load(image[position]).resize(450, 450).into(imageView);

        TextView textView = (TextView) convertView.findViewById(R.id.txtView3);
        textView.setText(item.getTitle());
        textView.setTextSize(Integer.parseInt(tx_size));
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+tx_style+".ttf");
        textView.setTypeface(custom_font);
        return convertView;
    }
}


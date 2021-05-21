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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<List_Item>{

    ArrayList<List_Item> Item;
    Context context;
    int resource;
    String tx_size;
    String tx_style;
    public CustomListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<List_Item> Item,String tx_size,String tx_style) {
        super(context, resource, Item);
        this.Item = Item;
        this.context = context;
        this.resource = resource;
        this.tx_size = tx_size;
        this.tx_style= tx_style;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item1,null,true);
        }

        List_Item item = getItem(position); // Object
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgView);
        if(item.getImage()=="NO_IMG")
        {
            Log.d("IMAGe","NO");
            imageView.setImageResource(R.drawable.no_image);
            imageView.getLayoutParams().height = 310;
            imageView.getLayoutParams().width = 450;
        }
        else {
            Picasso.with(getContext().getApplicationContext()).load(item.getImage()).resize(450, 300).into(imageView);
        }
        Log.d("------------>",item.getImage());
        TextView textView = (TextView) convertView.findViewById(R.id.txtView);
        textView.setText(item.getTitle());
        textView.setTextSize(Integer.parseInt(tx_size));
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+tx_style+".ttf");
        textView.setTypeface(custom_font);
        return convertView;
    }


}


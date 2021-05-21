package leslie.worldbreakingnews;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Leslie on 5/21/2021.
 */

public class menu_SettingFragment extends Fragment {

    private TextView tx_all[] = new TextView[2];
    private int tx_id[] ={R.id.txFontSize,R.id.txFontStyle};
    private Spinner sp1,sp2;
    private String font_size[]={"14","16","18","22","24","26"};
    private String font_Style[] = {"font1","font2"};

    public menu_SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu__setting, container, false);
        SharedPreferences SystemInfo = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String data = SystemInfo.getString("font_size", "22");

        Log.d("System Info",data);

        for (int i=0;i<tx_all.length;i++)
        {
            tx_all[i] =(TextView) view.findViewById(tx_id[i]);
            tx_all[i].setTextSize(Integer.parseInt(data));

        }


        sp1 = (Spinner) view.findViewById(R.id.fontSize);
        sp2 = (Spinner) view.findViewById(R.id.fontStyle);
        ArrayAdapter<String> sizeAd1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, font_size);
        sizeAd1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(sizeAd1);

        ArrayAdapter<String> sizeAd2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, font_Style);
        sizeAd2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(sizeAd2);

        for(int i=0;i<font_size.length;i++)
        {
            if(font_size[i].equals(data))
            {
                sp1.setSelection(i,true);

                break;
            }
        }
        for(int i=0;i<font_Style.length;i++)
        {
            if(font_Style[i].equals(data))
            {
                sp2.setSelection(i,true);
                break;
            }
        }
        sp1.setOnItemSelectedListener(sp1_Lis);
        sp2.setOnItemSelectedListener(sp2_Lis);


        return view;
    }
    private AdapterView.OnItemSelectedListener sp1_Lis = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SharedPreferences SystemInfo = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            int value = sp1.getSelectedItemPosition();
            SystemInfo.edit().putString("font_size",font_size[value]).commit();
            changeTextSize(font_size[value]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener sp2_Lis = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SharedPreferences SystemInfo = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            int value = sp2.getSelectedItemPosition();
            SystemInfo.edit().putString("font_style",font_Style[value]).commit();
            changeTextStyle(font_Style[value]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



    private void changeTextSize(String size){
        for (int i=0;i<tx_all.length;i++)
        {
            tx_all[i].setTextSize(Integer.parseInt(size));
        }
    }
    private void changeTextStyle(String style){
        for (int i=0;i<tx_all.length;i++)
        {
            Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/"+style+".ttf");
            tx_all[i].setTypeface(custom_font);
        }
    }



}




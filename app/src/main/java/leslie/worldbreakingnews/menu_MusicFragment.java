package leslie.worldbreakingnews;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Leslie on 5/21/2021.
 */

public class menu_MusicFragment extends Fragment {

    private ListView lv;
    private ArrayList<List_Item> arrayList;
    int image[]={R.drawable.rthk_01, R.drawable.rthk_02, R.drawable.rthk_03, R.drawable.rthk_04, R.drawable.rthk_05};
    String list[]={"RTHK01", "RTHK02", "RTHK03", "RTHK04", "RTHK05"};
    ImageView small_img;
    TextView small_text;
    Button btn_play;
    MediaPlayer mPlayer = new MediaPlayer( );
    boolean playingState=false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu__music, container, false);
        SharedPreferences SystemInfo = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String data1 = SystemInfo.getString("font_size", "22");
        String data2 = SystemInfo.getString("font_Style","font1");
        lv = (ListView) view.findViewById(R.id.lv);
        small_img = (ImageView) view.findViewById(R.id.small_img);
        small_text = (TextView) view.findViewById(R.id.small_text);
        btn_play = (Button) view.findViewById(R.id.small_button);
        btn_play.setBackgroundResource(R.drawable.play_button);
        GlobalVariable gv = (GlobalVariable) getActivity().getApplicationContext();
        int temp = gv.getsharePlaying();

        if(temp != 0)
        {
            mPlayer = gv.getPlayingmow();
            small_img.setImageResource(image[temp-1]);
            small_text.setText(list[temp-1]);
            playingState = true;
            btn_play.setBackgroundResource(R.drawable.pause_button);
        }

        arrayList = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            arrayList.add(new List_Item(list[i],list[i]));
        }
        RadioListAdapt adapter = new RadioListAdapt(getActivity().getApplicationContext(),R.layout.list_item3,arrayList,data1,data2);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                play_music(position);

            }
        });
        btn_play.setOnClickListener(triggerbutton);
        return view;
    }
    private View.OnClickListener triggerbutton = new View.OnClickListener(){
        public void  onClick(View v)
        {
            if(playingState)
            {
                btn_play.setBackgroundResource(R.drawable.pause_button);
                playingState=false;
            }
            else
            {
                btn_play.setBackgroundResource(R.drawable.play_button);
                mPlayer.pause();
                mPlayer.reset();
                playingState=true;
            }
        }
    };

    private void play_music(int position)
    {
        GlobalVariable gv = (GlobalVariable) getActivity().getApplicationContext();
        small_img.setImageResource(image[position]);
        small_text.setText(list[position]);
        String url="";


        btn_play.setBackgroundResource(R.drawable.play_button);
        url="";
        mPlayer.pause();
        mPlayer.reset();


        switch (position)
        {
            case 0:
                url = "https://rthkaudio1-lh.akamaihd.net/i/radio1_1@355864/index_56_a-p.m3u8?sd=10&rebase=on";
                gv.setsharePlaying(1);
                break;
            case 1:
                url = "https://rthkaudio2-lh.akamaihd.net/i/radio2_1@355865/index_56_a-p.m3u8?sd=10&rebase=on";
                gv.setsharePlaying(2);
                break;
            case 2:
                url = "https://rthkaudio3-lh.akamaihd.net/i/radio3_1@355866/index_56_a-p.m3u8?sd=10&rebase=on";
                gv.setsharePlaying(3);
                break;
            case 3:
                url = "https://rthkaudio4-lh.akamaihd.net/i/radio4_1@355867/index_56_a-p.m3u8?sd=10&rebase=on";
                gv.setsharePlaying(4);
                break;
            case 4:
                url = "https://rthkaudio5-lh.akamaihd.net/i/radio5_1@355868/index_56_a-p.m3u8?sd=10&rebase=on";
                gv.setsharePlaying(5);
                break;

        }
        Uri path = Uri.parse(url);
        try{
            mPlayer.reset();
            mPlayer.setDataSource(getActivity().getApplicationContext(), path);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mPlayer.start();
                    btn_play.setBackgroundResource(R.drawable.pause_button);
                }
            });
            gv.setPlayingnow(mPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


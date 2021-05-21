package leslie.worldbreakingnews;

import android.app.Application;
import android.media.MediaPlayer;

/**
 * Created by Leslie on 5/21/2021.
 */

public class GlobalVariable extends Application {
    private int sharePlaying;
    private MediaPlayer playingnow;

    public void setsharePlaying(int sharePlaying){
        this.sharePlaying = sharePlaying;
    }

    public int getsharePlaying(){
        return sharePlaying;
    }
    public  void  setPlayingnow(MediaPlayer playingnow){
        this.playingnow=playingnow;
    }
    public  MediaPlayer getPlayingmow()
    {
        return playingnow;
    }


}

package leslie.worldbreakingnews;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LogWriter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout ;
    Fragment frag1;
    private TextView tx_headerEmail;
    private  FirebaseAuth firebaseAuth;
    private  MenuItem menu_log;
    private boolean btn_logout = false;
    private Fragment frag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setNavigationDrawer();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);


                NavigationView navView = (NavigationView) findViewById(R.id.navigation);
                Menu menu = navView.getMenu();
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/OldNewspaperTypes.ttf");
                tx_headerEmail = (TextView) findViewById(R.id.header_email);
                menu_log = (MenuItem)menu.findItem(R.id.Login_item);
                if(user != null) {
                    String email = user.getEmail().toString();
                    tx_headerEmail.setText(email);
                    tx_headerEmail.setTypeface(null,Typeface.BOLD);
                    Log.d("INFO_email", user.getEmail());
                    menu_log.setTitle("Logout");
                    btn_logout = true;
                }
                else
                {
                    tx_headerEmail.setText("Please login");
                    tx_headerEmail.setTypeface(custom_font);
                    menu_log.setTitle("Login");
                    btn_logout = false;
                }

            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        frag1 = new menu_HotNewsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, frag1); // replace a Fragment with Frame Layout
        transaction.commit();
        getSupportActionBar().setTitle("Hot News");

    }

    private void setNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        final Fragment fragment = null;
        Class fragmentClass;
        navView.setNavigationItemSelectedListener(new
                                                          NavigationView.OnNavigationItemSelectedListener() {

                                                              public boolean onNavigationItemSelected(MenuItem menuItem) {
                                                                  int positionId=0;
                                                                  frag2 = null;
                                                                  positionId = menuItem.getItemId();

                                                                  if (positionId == R.id.Hot_News) {

                                                                      drawerLayout.closeDrawers();
                                                                      getSupportActionBar().setTitle("Hot News");
                                                                      frag2 = new menu_HotNewsFragment();
                                                                      Log.d("----------logd","1");

                                                                  } else if (positionId == R.id.Category) {

                                                                      drawerLayout.closeDrawers();
                                                                      getSupportActionBar().setTitle("Category");
                                                                      frag2 = new menu_CategoryFragment();
                                                                      Log.d("----------logd","2");


                                                                  } else if (positionId == R.id.Following) {

                                                                      drawerLayout.closeDrawers();
                                                                      getSupportActionBar().setTitle("Following");
                                                                      frag2 = new menu_FollowingFragment();
                                                                      Log.d("----------logd","3");


                                                                  }else if (positionId == R.id.News_Map) {

                                                                      drawerLayout.closeDrawers();
                                                                      getSupportActionBar().setTitle("News Map");
                                                                      frag2 = new menu_NewsMapFragment();
                                                                      Log.d("----------logd","4");


                                                                  } else if (positionId == R.id.Music) {

                                                                      drawerLayout.closeDrawers();
                                                                      getSupportActionBar().setTitle("Live radio");
                                                                      frag2 = new menu_MusicFragment();
                                                                      Log.d("----------logd","5");


                                                                  } else if (positionId == R.id.Setting) {

                                                                      drawerLayout.closeDrawers();
                                                                      getSupportActionBar().setTitle("Setting");
                                                                      frag2 = new menu_SettingFragment();
                                                                      Log.d("----------logd","6");

                                                                  }
                                                                  else if (positionId == R.id.Login_item) {
                                                                      if(btn_logout) {
                                                                          firebaseAuth.signOut();
                                                                          drawerLayout.closeDrawers();
                                                                      }
                                                                      else{
                                                                          drawerLayout.closeDrawers();
                                                                          getSupportActionBar().setTitle("Login");
                                                                          frag2 = new menu_LoginFragment();
                                                                          Log.d("----------logd", "7");
                                                                      }



                                                                  }

                                                                  if (frag2 != null) {
                                                                      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                                                      transaction.replace(R.id.frame, frag2); // replace a Fragment with Frame Layout
                                                                      transaction.commit(); // commit the changes
                                                                      drawerLayout.closeDrawers(); // close the all open Drawer Views
                                                                      Log.d("----------logd","Passed");
                                                                      return true;
                                                                  }
                                                                  else {
                                                                      Log.d("----------logd", "Nothing");
                                                                      return false;
                                                                  }


                                                              }
                                                          });


    }

}


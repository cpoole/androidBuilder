package activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import DAO.menuItem;
import database.FinalDataSource;
import fragments.MenuFragment;
import fragments.NavigationDrawerFragment;
import fragments.PunchCardFragment;
import managers.menuManager;
import managers.userManager;
import punchbug.{{app.title}}.NavigationDrawerCallbacks;
import punchbug.{{app.title}}.R;


public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {
    public static menuItem[] food;
    public static final String PREFERENCE_FILENAME = "itp.341.poole.connor.a5.app.app_prefs";
    public static final String PREFERENCE_INITIAL_BOOT = "itp.341.poole.connor.a5.app.initial_boot";
    public static final String PREFERENCE_NUM_PUNCHES = "itp.341.poole.connor.a5.app.num_punches";

    SharedPreferences prefs;
    private FinalDataSource menuDatasource;

    public static final int SCAN_FAIL = 2;
    public static final int SCAN_REQUEST = 1337;
    public static final String SCAN_RESULT = "BARCODE_SCANNER_RESULT_DATA";
    public static final String SCAN_RESULT_TYPE= "BARCODE_SCANNER_RESULT_TYPE";

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    public static Intent newInstagramProfileIntent(PackageManager pm, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                String username = url.substring(url.lastIndexOf("/") + 1);
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

    public static Intent newPhoneCallIntent(String number){
        String uri = "tel:" + number.trim() ;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        return(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTitle = getTitle();


        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        ArrayList<menuItem> menuItems;
        menuDatasource = new FinalDataSource(this);
        menuDatasource.open();

        prefs = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
        String FileContent = "";
        if(prefs.getString(PREFERENCE_INITIAL_BOOT, "default") == "default"){
            try {
                AssetManager am = getApplicationContext().getAssets();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(am.open("menu.json")));
                while(true){
                    String line = bfr.readLine();
                    if (line == null)
                        break;
                    FileContent += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor prefEditor = prefs.edit();
            prefEditor.putString(PREFERENCE_INITIAL_BOOT,"false");
            prefEditor.commit();

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            menuItems = new ArrayList<menuItem>(Arrays.asList(gson.fromJson(FileContent, menuItem[].class)));
            for(int i=0; i< menuItems.size();i++){
                String strPrice = String.valueOf(menuItems.get(i).getPrice());
                menuDatasource.createMenuItem(menuItems.get(i).getType(), menuItems.get(i).getTitle(), menuItems.get(i).getDescription(), strPrice);
            }

        }else{
            menuItems = (ArrayList<menuItem>) menuDatasource.getAllMenuItems();
        }

        menuManager.setMenuEntries(menuItems);
        userManager.setNumPunches(prefs.getInt(PREFERENCE_NUM_PUNCHES, 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO customize the options menu
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position){
            case 0:
                fragmentManager.beginTransaction().replace(R.id.container, PunchCardFragment.newInstance(position + 1)).commit();
                break;
            case 1:
                fragmentManager.beginTransaction().replace(R.id.container, MenuFragment.newInstance(position + 1)).commit();
                break;
            case 2:
                //fragmentManager.beginTransaction().replace(R.id.container, AboutUsFragment.newInstance(position + 1))
                     //   .commit();
                break;
            case 3:
                startActivity(newPhoneCallIntent({{app.phone}}));
                break;
            case 4:
                //fragmentManager.beginTransaction().replace(R.id.container, OurStoryFragment.newInstance(position + 1))
                   //     .commit();
                break;
            case 5:
            {% if(app.instagram != null) %}
                startActivity(newInstagramProfileIntent(this.getPackageManager(),"http://instagram.com/{{ app.instagram }}/"));
            {% endif %}
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id == R.id.menu_item){
            Toast.makeText(this, "hit ellipsis", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.add_punch){
            Toast.makeText(this, "hit add punch",Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(this, zBarScanner.class), SCAN_REQUEST);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SCAN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://punchbug.org/~test/json/responder.php?deviceType=iOS&deviceID=C9370A93-2921-4ED3-A47F-698567E5E557&clientID=2&appVersion=2.2.0&loyaltyCode=redeem&datestamp=2014-12-10@22:21:27");
                String rawResponse = null;
                try {
                    rawResponse = EntityUtils.toString(httpPost.getEntity());
                    JSONObject jsonResponse = new JSONObject(rawResponse);
                    Log.d("{{ app.title }}","json: "+rawResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }  catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "Code = " + data.getStringExtra(SCAN_RESULT), Toast.LENGTH_SHORT).show();

                // Do something with the contact here (bigger example below)
            }
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                break;
        }
    }
}

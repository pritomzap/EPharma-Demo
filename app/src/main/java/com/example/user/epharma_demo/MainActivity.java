package com.example.user.epharma_demo;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.epharma_demo.adapter.CustomExpandableListAdapter;
import com.example.user.epharma_demo.datasource.ExpandableListDataSource;
import com.example.user.epharma_demo.fragment.navigation.FragmentNavigationManager;
import com.example.user.epharma_demo.fragment.navigation.NavigationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

/**
 * Created by user on 12/2/2017.
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;
    private List<String> mExpandableListTitle;
    private NavigationManager mNavigationManager;
    private Map<String, List<String>> mExpandableListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
        mNavigationManager = FragmentNavigationManager.obtain(this);

        initItems();

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(listHeaderView);

        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());

        addDrawerItems();
        setupDrawer();

        if (savedInstanceState == null) {
            selectFirstItemAsDefault();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void selectFirstItemAsDefault() {
        if (mNavigationManager != null) {
//            String firstActionMovie = getResources().getStringArray(R.array.baby_care)[0];
//            mNavigationManager.showFragmentBaby(firstActionMovie);
//            getSupportActionBar().setTitle(firstActionMovie);
            mNavigationManager.showFragmentContent();

        }
    }

    private void initItems() {
        items = getResources().getStringArray(R.array.film_genre);
    }

    private void addDrawerItems() {
        TextView tv = (TextView) findViewById(R.id.email);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/font2.otf");
        tv.setTypeface(tf);
        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle(R.string.film_genres);
            }
        });
        //ON GROUP CLICK LISTENER
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i > 10) {
                    if (i == 2)
                        mNavigationManager.showFragmentBody("Body Care");
                    else if (i == 12)
                        mNavigationManager.showFragmentProfile();
                    else if (i == 14)
                        mNavigationManager.showFragmentRegistration();
                    else if (i == 13)
                        mNavigationManager.showFragmentRegistration();
                    else if (i == 3)
                        mNavigationManager.showFragmentBody("Diabetic Care");//

                    else if (i == 4)
                        mNavigationManager.showFragmentBody("Eye and Ear Care");
                    else if (i == 7)
                        mNavigationManager.showFragmentBody("Healthcare Accessories");
                    else if (i == 8)
                        mNavigationManager.showFragmentBody("Mutivitamins");


                    else
                        Toasty.error(getApplicationContext(), "Zero Child no fragment created " + i, Toast.LENGTH_SHORT, true).show();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }

                //Toasty.error(getApplicationContext(),""+i, Toast.LENGTH_SHORT, true).show();
                return false;
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (mExpandableListAdapter.getChildrenCount(groupPosition) != 0) {

                    String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
                            .get(childPosition).toString();
                    getSupportActionBar().setTitle(selectedItem);

                    if (items[0].equals(mExpandableListTitle.get(groupPosition))) {
                        //mNavigationManager.showFragmentBaby(selectedItem);
                        mNavigationManager.showFragmentBody(selectedItem);//baby care
                    } else if (items[1].equals(mExpandableListTitle.get(groupPosition))) {
                        //mNavigationManager.showFragmentBeauty(selectedItem);
                        mNavigationManager.showFragmentBody(selectedItem);
                    } else if (items[4].equals(mExpandableListTitle.get(groupPosition))) {
                        //mNavigationManager.showFragmentPersonal(selectedItem);
                        mNavigationManager.showFragmentBody(selectedItem);
                    } else if (items[9].equals(mExpandableListTitle.get(groupPosition))) {
                        mNavigationManager.showFragmentBody(selectedItem);
                    } else {
                        throw new IllegalArgumentException("Not supported fragment type");
                    }
                }
                //else
                //Toasty.error(getApplicationContext(), "Zero Child", Toast.LENGTH_SHORT, true).show();


                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.film_genres);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

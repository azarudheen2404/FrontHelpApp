package in.esquareinfo.web;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar ntoolbar;
    public DrawerLayout drawer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ntoolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer1 = (DrawerLayout) findViewById(R.id.drawer);
        //TOOLBAR

        setSupportActionBar(ntoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigation();

    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void onBackPressed()
    {
        return;
    }

    public void navigation(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer1, ntoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer1.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case android.R.id.home:
                drawer1.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item1) {
       /* int id = item1.getItemId();
        if(id==R.id.nav_quickrecharge){
            Intent qckrec = new Intent(Navigation.this,QuickRecharge.class);
            startActivity(qckrec);
        }
        if (id==R.id.nav_changepassword){
            Intent changepassword = new Intent(Navigation.this,ChangePassword.class);
            startActivity(changepassword);
        }
        if (id==R.id.nav_feedback){
            Intent feedback = new Intent(Navigation.this,FeedBack.class);
            startActivity(feedback);
        }
        if (id==R.id.nav_busdet){
            Intent feedback = new Intent(Navigation.this,Bus.class);
            startActivity(feedback);
        }
        if (id==R.id.nav_recdetails){
            Intent recdet = new Intent(Navigation.this,RechargeDetails.class);
            startActivity(recdet);
        }
        if (id==R.id.nav_buscan){
            Intent recdet = new Intent(Navigation.this,Cancel.class);
            startActivity(recdet);
        }*/
        drawer1.closeDrawer(GravityCompat.START);
        return true;

    }



}

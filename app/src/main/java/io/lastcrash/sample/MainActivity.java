package io.lastcrash.sample;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import io.lastcrash.LastCrash;
import io.lastcrash.LastCrashListener;

public class MainActivity extends AppCompatActivity implements LastCrashListener {

    private final String LASTCRASH_API_KEY = "<REPLACE WITH YOUR LASTCRASH PROVIDED API KEY>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MainActivity self = this;
        ProviderInstaller.installIfNeededAsync(this, new ProviderInstaller.ProviderInstallListener() {
            @Override
            public void onProviderInstalled() {
                LastCrash.setListener(self);
                LastCrash.configure(LASTCRASH_API_KEY, self);
            }

            @Override
            public void onProviderInstallFailed(int errorCode, Intent recoveryIntent) {
                GooglePlayServicesUtil.showErrorNotification(errorCode, getApplicationContext());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View test = null;
                test.getAlpha();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void lastCrashDidCrash() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to send crash reports?")
                .setPositiveButton("Send Crash Reports", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LastCrash.send();
                    }
                })
                .setNegativeButton("Don't Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LastCrash.clear();
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }
}

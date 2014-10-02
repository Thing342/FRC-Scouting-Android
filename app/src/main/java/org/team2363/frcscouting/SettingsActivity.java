package org.team2363.frcscouting;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import org.team2363.frcscouting.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {

    public static final int CHOOSE_SCHEDULE = 1111;
    public static final int CHOOSE_SCORESHEET = 2222;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AerialAssault", "setupActionBar()");
        Brain.getInstance().setupActionBar(this);
        //addPreferencesFromResource(R.xml.preferences);
        setPreferenceScreen(makePreferences());
        Log.d("AerialAssault", "addPreferencesFromResources()");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                // TODO: If Settings has multiple levels, Up should navigate up
                // that hierarchy.
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /////////////////--PRIVATE METHODS--//////////////////

    private PreferenceScreen makePreferences() {
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
        ListPreference type = new ListPreference(this);

        String entries[][] = {{"Red 1", "Red 2", "Red 3", "Blue 1", "Blue 2", "Blue 3", "Dev"},
                {"red1", "red2", "red3", "blue1", "blue2", "blue3", "dev"}};
        type.setEntries(entries[0]);
        type.setEntryValues(entries[1]);
        type.setTitle("Select Device Type");
        type.setKey("deviceType");
        type.setSummary("Teams this devices is scouting.");
        type.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this)
                        .edit().putString("devicetype", (String) newValue).commit();
                Brain.getInstance().setupActionBar(SettingsActivity.this);
                return true;
            }
        });

        root.addPreference(type);
        return root;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

}

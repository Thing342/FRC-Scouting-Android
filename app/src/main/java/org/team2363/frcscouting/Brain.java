package org.team2363.frcscouting;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Xml;

import org.team2363.frcscouting.widgets.CheckBoxWidget;
import org.team2363.frcscouting.widgets.CounterWidget;
import org.team2363.frcscouting.widgets.NotesWidget;
import org.team2363.frcscouting.widgets.RatingWidget;
import org.team2363.frcscouting.widgets.SliderWidget;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import android.net.Uri;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wes on 9/19/14.
 */
public class Brain {
    private static Brain brain = new Brain();
    public static final String DIRECTORY = "/storage/emulated/0/Scouting/",
        SCOUTING_FILE = DIRECTORY + "scouting.scout.xml",
        SCHEDULE_FILE = DIRECTORY + "schedule.sched.xml",
        SCORESHEET_FILE = DIRECTORY + "scoresheet.scores.xml";
    public static final int DEVICE_COLOR_RED = 0xffe91e63;
    public static final int DEVICE_COLOR_BLUE = 0xff3945ab;
    public static final int DEVICE_COLOR_DEV = 0xff6d4c41;

    public static Brain getInstance() {
        return brain;
    }

    private Brain() {
    }

    private Match matchList[];

    public void setupActionBar(Activity activity) {
        ActionBar bar = activity.getActionBar();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);

        String color = prefs.getString("devicetype", "dev");
        String colorName;

        if(color.contains("red")) {
            bar.setBackgroundDrawable(new ColorDrawable(DEVICE_COLOR_RED));
            colorName = "Red";
        } else if (color.contains("blue")) {
            bar.setBackgroundDrawable(new ColorDrawable(DEVICE_COLOR_BLUE));
            colorName = "Blue";
        } else {
            bar.setBackgroundDrawable(new ColorDrawable(DEVICE_COLOR_DEV));
            colorName = "Dev Mode!";
        }

        bar.setTitle(activity.getString(R.string.app_name) + " - " + colorName);
    }

    public boolean newMatchList(final Context context) {

        final boolean[] result = new boolean[1]; // WTF IntelliJ????

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File schedule = new File(SCHEDULE_FILE);
                    File scores = new File(SCORESHEET_FILE);
                    matchList = MatchList.create(context, schedule, scores);
                    result[0] = true;
                    Log.d("MatchList Size", Integer.toString(matchList.length));
                }
                catch (IOException e) {
                    e.printStackTrace();
                    result[0] = false;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    result[0] = false;
                }
            }
        }).start();

        return result[0];
    }

   public boolean restoreMatchList(final Context context, boolean background) {

        final boolean[] result = new boolean[1]; // WTF IntelliJ????

        if (background) new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File restoreFile = new File(SCOUTING_FILE);
                    Log.d("restorePath", restoreFile.getPath());
                    matchList = MatchList.restore(context, restoreFile);
                    result[0] = true;
                } catch (IOException e) {
                    result[0] = false;
                    e.printStackTrace();
                }
            }
        }).start();

       else try {
            File restoreFile = new File(SCOUTING_FILE);
            Log.d("restorePath", restoreFile.getPath());
            matchList = MatchList.restore(context, restoreFile);
            result[0] = true;
        } catch (IOException e) {
            result[0] = false;
            e.printStackTrace();
        }

        return result[0];
    }

    public Match[] getMatchList() {
        if(matchList == null) {
            Log.d("MatchList", "Match List Empty!");
            return new Match[0];
        }
        return matchList;
    }

    public Match getMatchByID(String id) {
        String nums[] = id.split("m");
        Log.d("ID", Arrays.toString(nums));
        int place = new Integer(nums[1]) - 1;
        return matchList[place];
    }

    public void saveMatches(final Context context, final String filename) {

        final String color = PreferenceManager.getDefaultSharedPreferences(context).getString("devicetype", "dev");

        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintWriter writer = null;
                FileOutputStream out;

                try {
                    out = new FileOutputStream(SCOUTING_FILE);
                    writer = new PrintWriter(out);

                    CharSequence xml = MatchList.toXML(matchList, color);
                    Log.d("XMLPreview", xml.toString());
                    writer.write(xml.toString());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    writer.flush();
                    writer.close();
                }
            }
        }).start();


    }
}

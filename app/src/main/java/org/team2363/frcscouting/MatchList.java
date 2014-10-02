package org.team2363.frcscouting;

import android.content.Context;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wes on 9/20/14.
 */
public final class MatchList {
    public static Match[] create(Context context, File schedule, File scoreSheet) throws IOException, XmlPullParserException {
        FileInputStream scheduleInput = new FileInputStream(schedule),
                scoreInput = new FileInputStream(scoreSheet);

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(scoreInput, "UTF-8");

        List<Widget> template = new ArrayList<Widget>();

        int event = parser.getEventType();

        while(event != XmlPullParser.END_DOCUMENT) {
            String name = parser.getName();
            switch (event) {
                case XmlPullParser.START_TAG: break;
                case XmlPullParser.END_TAG:
                    if (name.equals("counter")) template.add(new CounterWidget(context, parser));
                    else if (name.equals("checkbox")) template.add(new CheckBoxWidget(context, parser));
                    else if (name.equals("notes")) template.add(new NotesWidget(context, parser));
                    else if (name.equals("rating")) template.add(new RatingWidget(context, parser));
                    else if (name.equals("slider")) template.add(new SliderWidget(context, parser));
                    break;
            }

            event = parser.next();
        }

        parser.setInput(scheduleInput, "UTF-8");
        scoreInput.close();

        template.toArray(new Widget[template.size()]);
        List<Match> matches = new ArrayList<Match>();
        event = parser.getEventType();

        for(int n = 1; event != XmlPullParser.END_DOCUMENT; event = parser.next()) {
            String name = parser.getName();
            if(name != null && name.equals("match")) {
                int team = new Integer(parser.getAttributeValue(null, "team"));
                matches.add(new Match(n, team, getClone(template)));
                n++;
            }
        }

        return matches.toArray(new Match[matches.size()]);
    }

    public static Match[] create(Context context, InputStream schedule, InputStream scoreSheet) throws IOException, XmlPullParserException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(scoreSheet, "UTF-8");

        List<Widget> template = new ArrayList<Widget>();

        int event = parser.getEventType();

        while(event != XmlPullParser.END_DOCUMENT) {
            String name = parser.getName();
            switch (event) {
                case XmlPullParser.START_TAG: break;
                case XmlPullParser.END_TAG:
                    if (name.equals("counter")) template.add(new CounterWidget(context, parser));
                    else if (name.equals("checkbox")) template.add(new CheckBoxWidget(context, parser));
                    else if (name.equals("notes")) template.add(new NotesWidget(context, parser));
                    else if (name.equals("rating")) template.add(new RatingWidget(context, parser));
                    else if (name.equals("slider")) template.add(new SliderWidget(context, parser));
                    break;
            }

            event = parser.next();
        }

        parser.setInput(schedule, "UTF-8");
        scoreSheet.close();

        template.toArray(new Widget[template.size()]);
        List<Match> matches = new ArrayList<Match>();
        event = parser.getEventType();

        for(int n = 1; event != XmlPullParser.END_DOCUMENT; event = parser.next()) {
            String name = parser.getName();
            if(name != null && name.equals("match")) {
                int team = new Integer(parser.getAttributeValue(null, "team"));
                matches.add(new Match(n, team, getClone(template)));
                n++;
            }
        }

        schedule.close();

        return matches.toArray(new Match[matches.size()]);
    }

    private static Widget[] getClone(List<Widget> list) {
        Widget clone[] = new Widget[list.size()];
        for(int i=0; i<list.size(); i++) {
            clone[i] = list.get(i).clone();
        }

        return clone;
    }

    public static CharSequence toXML(Match[] matches, String role) {
        StringBuilder xml = new StringBuilder();
        xml.append("﻿<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<event>\n");
        for(Match m : matches) xml.append(m.toXML(role) + "\n");
        xml.append("</event>");

        return xml;
    }

    public static Match[] restore(Context context, File restore) throws IOException {
        try {
            FileInputStream input = new FileInputStream(restore);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(input, "UTF-8");
            List<Match> matches = new ArrayList<Match>();

            int event = parser.next();

            while(event != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                if(name != null && name.equals("match")) {
                    int number = new Integer(parser.getAttributeValue(null, "number"));
                    int team = new Integer(parser.getAttributeValue(null, "team"));

                    matches.add(new Match(context, parser, number, team));
                    Log.d("g", "restore");
                }

                Log.d("r", "PestoreP");
                event = parser.next();
            }

            return matches.toArray(new Match[matches.size()]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException x) {
            x.printStackTrace();
            return null;
        }
    }

}

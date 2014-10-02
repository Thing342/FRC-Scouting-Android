package org.team2363.frcscouting;

import android.content.Context;

import org.team2363.frcscouting.widgets.CheckBoxWidget;
import org.team2363.frcscouting.widgets.CounterWidget;
import org.team2363.frcscouting.widgets.NotesWidget;
import org.team2363.frcscouting.widgets.RatingWidget;
import org.team2363.frcscouting.widgets.SliderWidget;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wes on 9/19/14.
 */
public class Match implements Comparable<Match>, Cloneable {
    public final int number, team;
    private final Widget data[];

    public Match(int number, int team, Widget[] data) {
        this.number = number;
        this.team = team;
        this.data = data;
    }

    //TODO: correct xml parsing

    public Match(Context context, XmlPullParser parser, int number, int team) throws IOException, XmlPullParserException {
        this.number = number;
        this.team = team;

        ArrayList<Widget> widgets = new ArrayList<Widget>();
        parser.require(XmlPullParser.START_TAG, null, "match");

        int event = parser.next();

        while (event != XmlPullParser.END_TAG || !parser.getName().equals("match") ) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                event = parser.next();
                continue;
            }
            String name = parser.getName();
            if (name.equals("counter")) widgets.add(new CounterWidget(context, parser, true));
            else if (name.equals("checkbox")) widgets.add(new CheckBoxWidget(context, parser, true));
            else if (name.equals("notes")) widgets.add(new NotesWidget(context, parser, true));
            else if (name.equals("rating")) widgets.add(new RatingWidget(context, parser, true));
            else if (name.equals("slider")) widgets.add(new SliderWidget(context, parser, true));

            event = parser.next();
        }

        data = widgets.toArray(new Widget[widgets.size()]);

    }

    protected Widget[] getData() {
        return data;
    }

    public CharSequence toXML(String role) {
        StringBuilder sb = new StringBuilder();
        sb.append("<match team = \"" + team + "\" number = \"" + number + " role = " + role +  "\">\n");

        for(Widget w : data) sb.append(w.toXML() + "\n");
        sb.append("</match>");

        return sb;
    }

    public String toString() {
        return "Q" + number + " - Team " + team;
    }

    public String toID() {
        return "m" + number + "m" + team;
    }

    @Override
    public int compareTo(Match another) {
        return another.number - this.number;
    }

    @Override
    protected Match clone() throws CloneNotSupportedException {
        Widget clone[] = new Widget[data.length];
        for(int i=0; i<data.length; i++) clone[i] = data[i].clone();
        return new Match(number, team, clone);
    }

}

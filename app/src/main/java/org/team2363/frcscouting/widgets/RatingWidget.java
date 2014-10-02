package org.team2363.frcscouting.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.team2363.frcscouting.R;
import org.team2363.frcscouting.Widget;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by wes on 9/20/14.
 */
public class RatingWidget implements Widget<Float> {

    private final String id;
    private final LinearLayout container;
    private final RatingBar rating;
    private final TextView widgetTitle;
    private final int stars;
    private final double step;

    public RatingWidget(Context context, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.END_TAG, null, "rating");
        id = parser.getAttributeValue(null, "id");

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_stars, null);

        widgetTitle = (TextView) container.findViewById(R.id.widget_name);
        rating = (RatingBar) container.findViewById(R.id.rating_widget);

        step = Double.parseDouble(parser.getAttributeValue(null, "step"));
        stars = Integer.parseInt(parser.getAttributeValue(null, "scale"));
        String text = parser.getAttributeValue(null, "name");

        rating.setNumStars(5);
        rating.setStepSize(new Float(step));

        widgetTitle.setText(text + ": ");

    }

    public RatingWidget(Context context, XmlPullParser parser, boolean restore) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "rating");
        id = parser.getAttributeValue(null, "id");

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_stars, null);

        widgetTitle = (TextView) container.findViewById(R.id.widget_name);
        rating = (RatingBar) container.findViewById(R.id.rating_widget);

        step = Double.parseDouble(parser.getAttributeValue(null, "step"));
        stars = Integer.parseInt(parser.getAttributeValue(null, "scale"));
        String text = parser.getAttributeValue(null, "name");

        rating.setNumStars(stars);
        rating.setStepSize(new Float(step));

        parser.next();

        rating.setRating(new Float(parser.getText()));

        widgetTitle.setText(text);

    }

    public RatingWidget(Context context, String id, CharSequence text, float step, int stars) {
        this.id = id;

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_stars, null);

        widgetTitle = (TextView) container.findViewById(R.id.widget_name);
        rating = (RatingBar) container.findViewById(R.id.rating_widget);

        this.step = step;
        this.stars = stars;

        rating.setNumStars(stars);
        rating.setStepSize(step);

        widgetTitle.setText(text + ": ");
    }

    @Override
    public Float getValue() {
        return rating.getRating();
    }

    @Override
    public View getView() {
        return container;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toXML() {
        return "<rating id = \"" + id + "\" name = \"" + widgetTitle.getText().toString() + "\" scale = \"" + stars +
                "\" step = \"" + step + "\">" + getValue() + "</rating>";
    }

    @Override
    public Widget<Float> clone() {
       return new RatingWidget(getView().getContext(), id, widgetTitle.getText(), new Float(step), stars);
    }
}

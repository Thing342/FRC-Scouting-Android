package org.team2363.frcscouting.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.team2363.frcscouting.R;
import org.team2363.frcscouting.Widget;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by wes on 9/19/14.
 */
public class CheckBoxWidget implements Widget<Boolean> {

    private final String id;
    private final LinearLayout container;
    private final CheckBox check;

    public CheckBoxWidget(Context context, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.END_TAG, null, "checkbox");
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_checkbox, null);
        id = parser.getAttributeValue(null, "id");
        String text = parser.getAttributeValue(null, "name");

        check = (CheckBox) container.findViewById(R.id.widget_name);
        check.setText(text);
    }

    public CheckBoxWidget(Context context, XmlPullParser parser, boolean restore) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "checkbox");
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_checkbox, null);
        id = parser.getAttributeValue(null, "id");
        String text = parser.getAttributeValue(null, "name");

        parser.next();

        boolean value = new Boolean(parser.getText());

        check = (CheckBox) container.findViewById(R.id.widget_name);
        check.setText(text);
        check.setChecked(value);
    }

    public CheckBoxWidget(Context context, CharSequence text, String id) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_checkbox, null);

        this.id = id;

        check = (CheckBox) container.findViewById(R.id.widget_name);
        check.setText(text);
    }

    @Override
    public Boolean getValue() {
        return check.isChecked();
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
        return "<checkbox id = \"" + id + "\" name = \"" + check.getText().toString() + "\" >" + getValue() + "</checkbox>";
    }

    @Override
    public Widget<Boolean> clone() {
        return new CheckBoxWidget(getView().getContext(), check.getText(), id);
    }
}

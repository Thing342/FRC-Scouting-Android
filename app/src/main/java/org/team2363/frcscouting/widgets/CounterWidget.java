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
public class CounterWidget implements Widget<Integer> {

    private final String id;
    private final LinearLayout container;
    private final Button plus, minus;
    private final TextView widgetTitle, widgetValue;
    private final int minValue;

    private int value = 0;

    public CounterWidget(Context context, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.END_TAG, null, "counter");
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        id = parser.getAttributeValue(null, "id");
        String text = parser.getAttributeValue(null, "name");

        container = (LinearLayout) li.inflate(R.layout.widget_counter, null);
        widgetTitle = (TextView) container.findViewById(R.id.widget_name);
        widgetValue = (TextView) container.findViewById(R.id.widget_value);
        widgetTitle.setText(text + ": ");
        widgetValue.setText(Integer.toString(value));

        minValue = Integer.parseInt(parser.getAttributeValue(null, "minValue"));

        plus = (Button) container.findViewById(R.id.counter_plus);
        plus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                widgetValue.setText(Integer.toString(++value));
            }
        });

        minus = (Button) container.findViewById(R.id.counter_minus);
        minus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(value - 1 >= CounterWidget.this.minValue)
                    widgetValue.setText(Integer.toString(--value));
            }
        });
    }

    public CounterWidget(Context context, XmlPullParser parser, boolean restore) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "counter");
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        id = parser.getAttributeValue(null, "id");
        String text = parser.getAttributeValue(null, "name");

        container = (LinearLayout) li.inflate(R.layout.widget_counter, null);
        widgetTitle = (TextView) container.findViewById(R.id.widget_name);
        widgetValue = (TextView) container.findViewById(R.id.widget_value);
        widgetTitle.setText(text);
        widgetValue.setText(Integer.toString(value));

        minValue = Integer.parseInt(parser.getAttributeValue(null, "minValue"));

        parser.next();

        value = new Integer(parser.getText());

        plus = (Button) container.findViewById(R.id.counter_plus);
        plus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                widgetValue.setText(Integer.toString(++value));
            }
        });

        minus = (Button) container.findViewById(R.id.counter_minus);
        minus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(value - 1 >= CounterWidget.this.minValue)
                    widgetValue.setText(Integer.toString(--value));
            }
        });
    }

    public CounterWidget(Context context, CharSequence text, String id, int minValue) {
        this.id = id;

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_counter, null);

        widgetTitle = (TextView) container.findViewById(R.id.widget_name);
        widgetValue = (TextView) container.findViewById(R.id.widget_value);

        widgetTitle.setText(text + ": ");
        widgetValue.setText(Integer.toString(value));

        this.minValue = minValue;

        plus = (Button) container.findViewById(R.id.counter_plus);
        plus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                widgetValue.setText(Integer.toString(++value));
            }
        });

        minus = (Button) container.findViewById(R.id.counter_minus);
        minus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(value - 1 >= CounterWidget.this.minValue)
                widgetValue.setText(Integer.toString(--value));
            }
        });
    }

    @Override
    public Integer getValue() {
        return value;
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
        return "<counter id = \"" + id + "\" name= \"" + widgetTitle.getText().toString() + "\" minValue = \"" + minValue + "\">" + getValue() + "</counter>";
    }

    @Override
    public Widget<Integer> clone() {
        return new CounterWidget(getView().getContext(), widgetTitle.getText(), id, minValue);
    }


}
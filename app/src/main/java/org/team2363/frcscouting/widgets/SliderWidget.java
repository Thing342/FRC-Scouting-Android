package org.team2363.frcscouting.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.team2363.frcscouting.R;
import org.team2363.frcscouting.Widget;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by wes on 9/20/14.
 */
public class SliderWidget implements Widget<Integer> {

    private final String id;
    private final LinearLayout container;
    private final SeekBar slider;
    private final TextView widgetTitle, widgetValue;
    private final int min, max, step;

    public SliderWidget(Context context, XmlPullParser parser, boolean restore) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "slider");
        id = parser.getAttributeValue(null, "id");

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_slider, null);

        widgetTitle = (TextView) container.findViewById(R.id.widget_name);
        widgetValue = (TextView) container.findViewById(R.id.widget_value);

        this.min = new Integer(parser.getAttributeValue(null, "min"));
        this.max = new Integer(parser.getAttributeValue(null, "max"));
        this.step = new Integer(parser.getAttributeValue(null, "step"));
        String text = parser.getAttributeValue(null, "name");

        slider = (SeekBar) container.findViewById(R.id.slider_widget);
        slider.setMax((int) ((max-min) / step));

        parser.next();

        slider.setProgress(convalue(new Integer(parser.getText())));
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                widgetValue.setText(Integer.toString(getValue()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Do nothing!
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Do nothing!
            }
        });

        widgetTitle.setText(text);
        widgetValue.setText(Integer.toString(getValue()));
    }

    public SliderWidget(Context context, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.END_TAG, null, "slider");
        id = parser.getAttributeValue(null, "id");

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_slider, null);

        widgetTitle = (TextView) container.findViewById(R.id.widget_name);
        widgetValue = (TextView) container.findViewById(R.id.widget_value);

        this.min = new Integer(parser.getAttributeValue(null, "min"));
        this.max = new Integer(parser.getAttributeValue(null, "max"));
        this.step = new Integer(parser.getAttributeValue(null, "step"));
        String text = parser.getAttributeValue(null, "name");

        slider = (SeekBar) container.findViewById(R.id.slider_widget);
        slider.setMax((int) ((max-min) / step));
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                widgetValue.setText(Integer.toString(getValue()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Do nothing!
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Do nothing!
            }
        });

        widgetTitle.setText(text + ": ");
        widgetValue.setText(Integer.toString(getValue()));
    }

    public SliderWidget(Context context, String id, CharSequence text, int min, int max, int step) {
        this.id = id;

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_slider, null);

        widgetTitle = (TextView) container.findViewById(R.id.widget_name);
        widgetValue = (TextView) container.findViewById(R.id.widget_value);

        this.min = min;
        this.max = max;
        this.step = step;

        slider = (SeekBar) container.findViewById(R.id.slider_widget);
        slider.setMax((int) ((max-min) / step));
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                widgetValue.setText(Integer.toString(getValue()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            //Do nothing!
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            //Do nothing!
            }
        });

        widgetTitle.setText(text + ": ");
        widgetValue.setText(Integer.toString(getValue()));
    }

    private int convalue(int val) {
        return (val - min) / step;
    }


    @Override
    public Integer getValue() {
        int progress = slider.getProgress();
        return (progress * step) + min;
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
        return "<slider id = \"" + id + "\"  name = \"" + widgetTitle.getText().toString() + "\" min = \"" + min + "\" " +
                "max = \"" + max + "\" step = \"" + step + "\">" + getValue() + "</slider>";
    }

    @Override
    public Widget<Integer> clone() {
        return new SliderWidget(getView().getContext(), id, widgetTitle.getText(), min, max, step);
    }

}

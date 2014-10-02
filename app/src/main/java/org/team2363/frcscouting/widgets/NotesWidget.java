package org.team2363.frcscouting.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.team2363.frcscouting.R;
import org.team2363.frcscouting.Widget;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by wes on 9/20/14.
 */
public class NotesWidget implements Widget<CharSequence> {

    private final EditText textBox;
    private final LinearLayout container;
    private final TextView title;
    private final String id;

    public NotesWidget(Context context, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.END_TAG, null, "notes");
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_notes, null);

        id = parser.getAttributeValue(null, "id");
        String name = parser.getAttributeValue(null, "name");
        String hint = parser.getAttributeValue(null, "hint");
        int lines = Integer.parseInt(parser.getAttributeValue(null, "lines"));

        title = (TextView) container.findViewById(R.id.widget_name);
        title.setText(name);

        textBox = (EditText) container.findViewById(R.id.widget_notes);
        textBox.setHint(hint);
        textBox.setLines(lines);
    }

    public NotesWidget(Context context, XmlPullParser parser, boolean restore) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "notes");
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_notes, null);

        id = parser.getAttributeValue(null, "id");
        String name = parser.getAttributeValue(null, "name");
        String hint = parser.getAttributeValue(null, "hint");
        int lines = Integer.parseInt(parser.getAttributeValue(null, "lines"));

        title = (TextView) container.findViewById(R.id.widget_name);
        title.setText(name);

        textBox = (EditText) container.findViewById(R.id.widget_notes);
        textBox.setHint(hint);
        textBox.setLines(lines);

        parser.next();

        textBox.setText(parser.getText());
    }

    public NotesWidget(Context context, String id, CharSequence name, CharSequence hint, int lines) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) li.inflate(R.layout.widget_notes, null);

        this.id = id;

        title = (TextView) container.findViewById(R.id.widget_name);
        title.setText(name);

        textBox = (EditText) container.findViewById(R.id.widget_notes);
        textBox.setHint(hint);
        textBox.setLines(lines);
    }
    @Override
    public CharSequence getValue() {
        return textBox.getText();
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
        return "<notes id = \"" + id +"\" name = \"" + title.getText().toString() + "\" hint = \"" +
                textBox.getHint() + "\" lines=\"" + textBox.getLineCount() + "\">"+ getValue() + "</notes>";
    }

    @Override
    public Widget<CharSequence> clone() {
        return new NotesWidget(getView().getContext(), id, title.getText(), textBox.getHint(), textBox.getLineCount());
    }
}

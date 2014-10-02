package org.team2363.frcscouting;

import android.view.View;

/**
 * Created by wes on 9/19/14.
 */
public interface Widget<T> extends Cloneable{
    public T getValue();
    public View getView();
    public String getId();
    public String toXML();
    public Widget<T> clone();
}

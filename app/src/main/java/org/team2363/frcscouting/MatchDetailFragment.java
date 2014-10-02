package org.team2363.frcscouting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A fragment representing a single Match detail screen.
 * This fragment is either contained in a {@link MatchListActivity}
 * in two-pane mode (on tablets) or a {@link MatchDetailActivity}
 * on handsets.
 */
public class MatchDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private Match mMatch;
    private LinearLayout view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mMatch = Brain.getInstance().getMatchByID(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = (LinearLayout) inflater.inflate(R.layout.fragment_match_detail, container, false);
        for(Widget w : mMatch.getData()) view.addView(w.getView());

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        OutputStream out = null;

        Brain.getInstance().saveMatches(getActivity(), "scouting.xml");
        Toast.makeText(getActivity(), "Scouting data saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (view != null) {
            view.removeAllViews();
        }
    }
}

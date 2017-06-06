package alex.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieDescriptionFragment extends Fragment {
    final static String ARG_POSITION = "position";
    final static String ARG_OVERVIEW = "movie_overview";
    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Restoring the previous movie selection set by onSaveInstanceState()
        // if activity was recreated (EX: screen rotate)
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if there are arguments passed to the fragment.
        Bundle args = getArguments();
        if (args != null) {
            updateMovieView(args.getString(ARG_OVERVIEW), args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            updateMovieView("Data unavailable" ,mCurrentPosition);
        }
    }

    public void updateMovieView(String data, int position) {
        TextView movie = (TextView) getActivity().findViewById(R.id.movie);
        movie.setText(data);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
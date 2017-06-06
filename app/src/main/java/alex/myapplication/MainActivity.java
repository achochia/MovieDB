package alex.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity 
        implements MovieListFragment.OnMovieSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_layout);

        if (findViewById(R.id.fragment_container) != null) {

            // Check for previous state
            if (savedInstanceState != null) {
                return;
            }

            MovieListFragment firstFragment = new MovieListFragment();

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onMovieSelected(int position, MovieListFragment.Movies movies) {
        MovieDescriptionFragment movieFrag = (MovieDescriptionFragment)
                getSupportFragmentManager().findFragmentById(R.id.movie_description_fragment);

        if (movieFrag != null) {
            movieFrag.updateMovieView(movies.getOverview(position), position);

        } else {

            MovieDescriptionFragment newFragment = new MovieDescriptionFragment();
            Bundle args = new Bundle();
            args.putInt(MovieDescriptionFragment.ARG_POSITION, position);
            args.putString(MovieDescriptionFragment.ARG_OVERVIEW, movies.getOverview(position));
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
}
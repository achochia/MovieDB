package alex.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends ListFragment {
    OnMovieSelectedListener mCallback;
    RequestQueue queue;
    Movies movies;
    String input;
    EditText searchBar;

    interface OnMovieSelectedListener {
        void onMovieSelected(int position, Movies movies);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getFragmentManager().findFragmentById(R.id.movie_list_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        searchBar = (EditText) getActivity().findViewById(R.id.search);
        searchBar.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event)
                    {
                        input = searchBar.getText().toString();
                        updateEntries();
                        return false;
                    }
                }
        );
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof Activity)
            mCallback = (OnMovieSelectedListener) context;
        else
            throw new RuntimeException("OnMovieSelectedListener is not implemented in " + context.getClass());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onMovieSelected(position, movies);

        getListView().setItemChecked(position, true);
    }

    public void updateEntries()
    {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, Keys.APIKey + input,
                onMoviesLoaded, onMoviesError);
        queue.add(request);
    }

    private final Response.Listener<String> onMoviesLoaded = new Response.Listener<String>()
    {
        @Override
        public void onResponse(String response)
        {
            Log.d("Receiving packet", "onMoviesLoaded");

            Gson gson = new Gson();
            movies = gson.fromJson(response, Movies.class);

            setListAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_activated_1, movies.getNames()));
        }
    };

    private final Response.ErrorListener onMoviesError = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            error.printStackTrace();
        }
    };

    class Movies
    {
        Movie[] results;

        private List<String> getNames()
        {
            List<String> retVal = new ArrayList<>();
            for(Movie movie : results)
            {
                retVal.add(movie.title);
            }

            Log.d("Movies:", retVal.toString());

            return retVal;
        }

        String getOverview(int position)
        {
            return results[position].overview;
        }
    }
}
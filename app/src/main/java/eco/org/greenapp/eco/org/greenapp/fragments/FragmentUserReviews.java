package eco.org.greenapp.eco.org.greenapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import eco.org.greenapp.R;
import eco.org.greenapp.eco.org.greenapp.adapters.ReviewAdapter;
import eco.org.greenapp.eco.org.greenapp.classes.Review;
import eco.org.greenapp.eco.org.greenapp.constants.GeneralConstants;

/**
 * Created by danan on 4/5/2018.
 */

public class FragmentUserReviews extends Fragment {

    private View view;
    private ListView lvReviews;
    private List<Review> lista;
   // SwipeRefreshLayout swipeContainer;
    String username;

    public FragmentUserReviews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("username",null);
        }
        GetUserReviews gd = new GetUserReviews();
        gd.execute(username);

        lista = new ArrayList<>();
        if(view==null)
        {
            view=inflater.inflate(R.layout.fragment_fragment_user_reviews, container,false);
        }
        else
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

/*

        final ReviewAdapter adapter=new ReviewAdapter(getActivity(),R.layout.review_item,lista);
        lvReviews=(ListView)view.findViewById(R.id.idLvReview);

        lvReviews.setAdapter(adapter);
*/

     /*   swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.idSwipeReview);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                lista.clear();
                adapter.notifyDataSetChanged();

                swipeContainer.setRefreshing(false);

                GetUserReviews gd = new GetUserReviews();
                gd.execute(username);

            }
        });*/

        return view;
    }

    public class GetUserReviews extends AsyncTask<String, Void, String> {

        String username;
        @Override
        protected String doInBackground(String... strings) {
            username = strings[0];
            try {
                URL url = new URL(GeneralConstants.URL+"/select_user_reviews.php");
                //URL url = new URL("http://10.38.31.11:8080/greenapp/select_user_reviews.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);

                OutputStream outputStream = con.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String getByEmail = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                bufferedWriter.write(getByEmail);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "iso-8859-1"));
                String result;

                StringBuilder sb = new StringBuilder();

                while((result = bufferedReader.readLine())!=null){
                    sb.append(result);
                }
                con.disconnect();
                return sb.toString();
            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray vectorReviews = new JSONArray(s);
                for(int i=0; i<vectorReviews.length(); i++){
                    JSONObject adItem = vectorReviews.getJSONObject(i);
                    Review review = new Review();

                    review.setData_adaugare(adItem.getString("data"));
                    review.setContinut(adItem.getString("detalii"));
                    review.setNota(Float.parseFloat(adItem.getString("nota")));
                    review.setUser(adItem.getString("username"));
                    review.setUrlProfil(adItem.getString("fotografie"));
                    lista.add(review);
                }


                final ReviewAdapter adapter=new ReviewAdapter(getActivity(),R.layout.review_item,lista);
                lvReviews=(ListView)view.findViewById(R.id.idLvReview);

                lvReviews.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }}


}

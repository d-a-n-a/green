package eco.org.greenapp.eco.org.greenapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import eco.org.greenapp.R;
import eco.org.greenapp.eco.org.greenapp.AddReviewTask;
import eco.org.greenapp.eco.org.greenapp.constants.GeneralConstants;
import eco.org.greenapp.eco.org.greenapp.constants.SharedPreferencesConstants;

public class Feedback extends AppCompatActivity {
Intent intent;
JSONObject json;
String user;
TextInputEditText detalii;
RatingBar nota;

String txtDetalii;
double iNota;
int tranzactie;
String currentUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        detalii = (TextInputEditText)findViewById(R.id.detailsReviewInput);
        nota = (RatingBar)findViewById(R.id.ratingUser);

        currentUsername = getSharedPreferences(GeneralConstants.SESSION, Context.MODE_PRIVATE).getString(GeneralConstants.TOKEN, null);

        intent = getIntent();
        if (intent != null) {
            user = intent.getStringExtra("user");
            tranzactie = intent.getIntExtra("idTranzactie",0);
        }
        ((TextView) findViewById(R.id.textFeedback)).setText("Adaugare review pentru utilizatorul " + user);



            ((Button) findViewById(R.id.btnAddReview)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     iNota = nota.getRating();
                    if (detalii.getText().toString().trim().isEmpty())
                        Toast.makeText(getApplicationContext(), "Completati campul 'detalii'!", Toast.LENGTH_LONG).show();
                    else {
                        json = new JSONObject();

                        try {
                            json.put("autor", currentUsername);
                            json.put("user", user);
                            json.put("detalii", detalii.getText().toString().trim());
                            json.put("nota", iNota*2);
                            json.put("tranzactie", tranzactie);
                            AddReviewTask addReviewTask = new AddReviewTask(getApplicationContext());
                            addReviewTask.execute(json);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    }
}

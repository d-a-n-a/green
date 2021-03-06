package eco.org.greenapp.eco.org.greenapp.activities;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import eco.org.greenapp.R;
import eco.org.greenapp.eco.org.greenapp.constants.GeneralConstants;
import eco.org.greenapp.eco.org.greenapp.profile_activities.ExecuteInsertTasks;

public class AddDemandProduct extends AppCompatActivity implements OnMapReadyCallback {

   SupportMapFragment mapFragment;

   TextInputEditText denumireProdus, descriereProdus, detaliiPredare;
   Spinner categorie;
   EditText durataAnunt;
   HashMap<String,String> valori;

//momentan ignor zona aproximativa de predare. daca e, fac sa generez anunturile de la utilizatorii din apropiere si gata. ca oricum pentru
    //fiecare utilizator exista o singura adresa, dar mai multe produse pe care le ofera/cere
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_demand_product);

           /* mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.productMapView);
            mapFragment.getMapAsync(this);*/



       denumireProdus = (TextInputEditText)findViewById(R.id.productNameInput);
       descriereProdus = (TextInputEditText)findViewById(R.id.productDescriptionInput);
       detaliiPredare = (TextInputEditText)findViewById(R.id.productDetailsInput);
       categorie = (Spinner)findViewById(R.id.spinnerProductCategory);
       durataAnunt = (EditText)findViewById(R.id.durataAnunt);

       valori = new HashMap<String, String>();

            ((Button)findViewById(R.id.btnAddAd)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(denumireProdus.getText().toString().trim().isEmpty() || descriereProdus.getText().toString().isEmpty() || detaliiPredare.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(), "Trebuie completate toate campurile!", Toast.LENGTH_LONG).show();
                    else{


                        Date date = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
                        String dataIntroducerii = simpleDateFormat.format(date);

                        final String valabilitate = "0";
                        valori.put("cod", GeneralConstants.INSERT_DEMAND);
                        valori.put("email", getSharedPreferences(GeneralConstants.SESSION, Context.MODE_PRIVATE).getString("email",null));
                        valori.put("data_introducerii", dataIntroducerii);
                        valori.put("durata", durataAnunt.getText().toString());
                        valori.put("tip", "doresc");
                        valori.put("denumire", denumireProdus.getText().toString().trim());
                        valori.put("valabilitate", valabilitate);
                        valori.put("categorie", categorie.getSelectedItem().toString());
                        valori.put("detalii", detaliiPredare.getText().toString().trim());
                        valori.put("descriere", descriereProdus.getText().toString().trim());

                        ExecuteInsertTasks executeInsertTasks  = new ExecuteInsertTasks(getApplicationContext());
                        executeInsertTasks.execute(valori);
                        finish();
                    }
                }
            });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng position = new LatLng(44.446255, 26.087572);
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title("locatie")
        );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,15));
    }
}

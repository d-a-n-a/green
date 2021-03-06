package eco.org.greenapp.eco.org.greenapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import eco.org.greenapp.R;
import eco.org.greenapp.eco.org.greenapp.GetImageTask;
import eco.org.greenapp.eco.org.greenapp.ProductImageTask;
import eco.org.greenapp.eco.org.greenapp.classes.Advertisement;
import eco.org.greenapp.eco.org.greenapp.constants.GeneralConstants;

public class AdForProduct extends AppCompatActivity implements OnMapReadyCallback {

    ImageView produtPhoto;
    SupportMapFragment mapFragment;
    Advertisement ad;
    Intent intent;

    TextView denumireProdus;
    TextView descriereProdus;
    TextView categorie;
    TextView valabilitate;
    TextView locatie;
    TextView detalii;
    TextView status;
    TextView zile;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_ad_for_product);

        denumireProdus = (TextView)findViewById(R.id.productName);
        descriereProdus = (TextView)findViewById(R.id.productDescription);
        categorie = (TextView)findViewById(R.id.category);
        valabilitate  = (TextView)findViewById(R.id.dataValabilitate);
        locatie = (TextView)findViewById(R.id.productLocation);
        detalii = (TextView)findViewById(R.id.productDetails);
        status = (TextView)findViewById(R.id.statusType);
        zile = (TextView)findViewById(R.id.daysSincePosted);
        imageView = (ImageView)findViewById(R.id.userProfileImage);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locationMap);
        mapFragment.getMapAsync(this);


        intent = getIntent();
        if(intent != null)
             ad = (Advertisement)intent.getSerializableExtra("selectedAd");
        produtPhoto = (ImageView)findViewById(R.id.backgroundLayout);

        if(ad.getTip().equals("doresc")){
            produtPhoto.setPadding(30,30,30,30);
            produtPhoto.setBackground(getDrawable(R.drawable.wanted));
         }

        ProductImageTask productImageTask = new ProductImageTask(produtPhoto, getApplicationContext());
        productImageTask.execute(GeneralConstants.Url+ad.getUrl());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                intent.putExtra("username", ad.getUsername());
                startActivity(intent);
            }
        });

        denumireProdus.setText(ad.getDenumireProdus());
        descriereProdus.setText(ad.getDetaliiAnunt());
        categorie.setText(ad.getCategorie());
        valabilitate.setText(ad.getValabilitate());
        locatie.setText(ad.getLocatieUser());
        detalii.setText(ad.getDescriereProdus());
        status.setText(ad.getStatusAnunt());
        ((TextView)findViewById(R.id.usernameanunt)).setText(ad.getUsername());
        ((TextView)findViewById(R.id.usernameanunt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                intent.putExtra("username", ad.getUsername());
                startActivity(intent);
            }
        });
        String dataIntroducerii = ad.getDataPostarii().trim();
        Log.i("introducere", dataIntroducerii);
        long days=0;
        try {

            Date oldDate = GeneralConstants.SDF.parse(dataIntroducerii);
            Log.i("oldDate", oldDate.toString());

            Date currentDate = new Date();
            Log.i("curent", currentDate.toString());
            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            days = hours / 24;

Log.i("days", ""+days);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        zile.setText("anunt postat acum "+days + " zile");

        ((Button)findViewById(R.id.btnTakeProduct)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ad.getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contactare anunt: "+ad.getDenumireProdus());
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(),"Aplicatia Gmail nu este instalata pe dispozitiv.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng position = new LatLng(ad.getLatitudine(), ad.getLongitudine());
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title(ad.getLocatieUser())
        );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,15));
    }
}

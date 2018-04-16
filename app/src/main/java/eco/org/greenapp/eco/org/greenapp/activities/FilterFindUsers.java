package eco.org.greenapp.eco.org.greenapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eco.org.greenapp.R;
import eco.org.greenapp.eco.org.greenapp.classes.User;
import eco.org.greenapp.eco.org.greenapp.profile_activities.ChangeLocation;

public class FilterFindUsers extends AppCompatActivity {

    TextView txtViewSchimbareLocatie;
    CheckBox cbCereri, cbOferte;
    SeekBar seekBarDistanta;
    int kmDistanta;
    int selectie = 0;
    Switch swHaine, swAlimente, swAltele;
    Button btnFiltrare;
    List<User> listaUtilizatori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_find_users);

        listaUtilizatori = new ArrayList<>();

        txtViewSchimbareLocatie = (TextView)findViewById(R.id.textViewChangeLocation);
        txtViewSchimbareLocatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangeLocation.class));
            }
        });

        cbCereri = (CheckBox)findViewById(R.id.checkBoxCereri);
        cbOferte = (CheckBox)findViewById(R.id.checkBoxOferte);


        seekBarDistanta = (SeekBar)findViewById(R.id.seekbarDistanta);
        kmDistanta = seekBarDistanta.getProgress();
        swHaine = (Switch)findViewById(R.id.switchHaine);
        swAlimente = (Switch)findViewById(R.id.switchAlimente);
        swAltele = (Switch)findViewById(R.id.switchAltele);
        btnFiltrare = (Button)findViewById(R.id.btnFiltrare);

        if(cbCereri.isSelected() || cbOferte.isSelected())
            selectie = 1;
        if(swAltele.isChecked() || swAlimente.isChecked() || swHaine.isChecked())
            selectie = 1;


        btnFiltrare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectie == 0)
                    Toast.makeText(getApplicationContext(), "Alegeti cel putin un criteriu.", Toast.LENGTH_SHORT).show();
                else
                    if(!cbOferte.isSelected() && !cbCereri.isSelected())
                        Toast.makeText(getApplicationContext(), "Selectati Cereri si/sau Oferte", Toast.LENGTH_LONG).show();
                    else
                    {
                        //TODO
                        //filtrare avand categoriile
                            //nu exista utilizatori => popup: refa criteriile sau renunta
                            //exista utilizatori => afisare in listview
                                        //onClick ma duce catre pagina lui personala
                         Intent intent = new Intent();
                        intent.putExtra("listaUtilizatori", (Serializable)listaUtilizatori);
                        startActivity(intent);


                    }
            }
        });
    }

    public class GetUsersByCriteria extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

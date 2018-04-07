package eco.org.greenapp.eco.org.greenapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import eco.org.greenapp.R;
import eco.org.greenapp.eco.org.greenapp.GetImageTask;
import eco.org.greenapp.eco.org.greenapp.classes.Advertisement;

/**
 * Created by danan on 4/1/2018.
 */

public class AdvertisementAdapter extends ArrayAdapter<Advertisement> {
    private List<Advertisement> listaAnunturi;
    private int idLayout;

    public AdvertisementAdapter(@NonNull Context context, int resource, List<Advertisement> anunturi) {
        super(context, resource, anunturi);
        this.idLayout = resource;
        this.listaAnunturi = anunturi;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater li = LayoutInflater.from(getContext());
        View view = li.inflate(this.idLayout,parent, false);

        TextView denumire = (TextView)view.findViewById(R.id.adTitle);
        TextView user = (TextView)view.findViewById(R.id.ownerUser);
        TextView tip = (TextView)view.findViewById(R.id.adType);
        TextView dataPostare  = (TextView)view.findViewById(R.id.timeAdded);
        TextView status = (TextView)view.findViewById(R.id.statusInfo);
        ImageView photo = (ImageView)view.findViewById(R.id.productPhoto);

        Advertisement ad = this.listaAnunturi.get(position);
        denumire.setText(ad.getDenumireProdus());
        user.setText(ad.getUsername());
        tip.setText(ad.getTip());
        dataPostare.setText(ad.getDataPostarii());
        status.setText(ad.getStatusAnunt());
        if(!ad.getUrl().isEmpty()) {
            GetImageTask getImageTask = new GetImageTask(photo);
            getImageTask.execute("http://192.168.100.4:8080" + ad.getUrl());
        }
        return view;
    }
}

package sg.edu.rp.c346.id20046523.mycomment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Show> showsList;

    public CustomAdapter(@NonNull Context context, int resource, ArrayList<Show> objects) {
        super(context, resource, objects);

        parent_context=context;
        layout_id = resource;
        showsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout_id,parent,false);

        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvYear = rowView.findViewById(R.id.tvYear);
        TextView tvVoice = rowView.findViewById(R.id.tvVoice);
        ImageView iv =rowView.findViewById(R.id.iv);
        RatingBar rb = rowView.findViewById(R.id.rbStars);

        Show shows = showsList.get(position);
        tvName.setText(shows.getName());
        tvYear.setText(Integer.toString(shows.getYor()));
        tvVoice.setText(shows.getVoice());
        rb.setRating(shows.getStars());

        if(shows.getId() %2==0)
        {
            iv.setImageResource(R.drawable._200px_heart_coraz_n_svg);
        }
        else
        {
            iv.setImageResource(R.drawable.thumbs_up);
        }

        return rowView;

    }
}

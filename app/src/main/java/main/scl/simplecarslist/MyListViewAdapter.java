package main.scl.simplecarslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyListViewAdapter extends ArrayAdapter<Person>{

    private final List<Person> personList;
    private final int resource;
    private final LayoutInflater inflater;

    public MyListViewAdapter(@NonNull Context context, int resource, @NonNull List<Person> personList) {
        super(context, resource,personList);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.personList = personList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Person person = personList.get(position);
        View listItem = (convertView==null)
                ?inflater.inflate(resource,null)
                :convertView;

        ((TextView)listItem.findViewById(R.id.LASTNAME)).setText(person.getLastName());
        ((TextView)listItem.findViewById(R.id.FIRSTNAME)).setText(person.getFirstName());
        ((TextView)listItem.findViewById(R.id.MAKE)).setText(person.getMake());
        ((TextView)listItem.findViewById(R.id.MODEL)).setText(person.getModel());
        return listItem;
    }
}

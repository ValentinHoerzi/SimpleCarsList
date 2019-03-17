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
    private final List<Person> placeHolder;
    private final int resource;
    private final LayoutInflater inflater;


    public MyListViewAdapter( Context context, int resource, List<Person> personList) {
        super(context, resource,personList);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.personList = personList;
        placeHolder = new ArrayList<>(personList);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
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

    public void filter(String text,int id){
        text = text.toLowerCase();
        personList.clear();

        if(text.length() == 0) {
            personList.addAll(placeHolder);
        }else{
            for(Person p : placeHolder){
                if(id == 0){
                    if(p.getFirstName().toLowerCase().contains(text) || p.getLastName().toLowerCase().contains(text) || p.getMake().toLowerCase().contains(text) || p.getModel().toLowerCase().contains(text)){
                        personList.add(p);
                    }
                }else if(id == 1){
                    if(p.getMake().toString().toLowerCase().equals(text.toString().toLowerCase())){
                        personList.add(p);
                    }
                }else if(id == 2){
                    personList.addAll(placeHolder);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void addPerson(Person p){
        placeHolder.add(p);
        personList.add(p);
        notifyDataSetChanged();
    }
}

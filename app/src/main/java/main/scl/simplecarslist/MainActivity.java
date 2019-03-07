package main.scl.simplecarslist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyListViewAdapter listViewAdapter;
    List<Person> listViewDataset;

    Spinner spinner;
    ArrayAdapter<Make> spinnerAdapter;

    List<Make> availableMake;
    List<Model> availableModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        initListView();
        initSpinner();
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,availableMake);
        spinner.setAdapter(spinnerAdapter);
    }

    private void initListView() {
        listView = findViewById(R.id.listView);
        listViewDataset = new ArrayList<>();
        availableMake = new ArrayList<>();
        availableModel = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("cars.csv")))) {
            br.readLine();
            String line = br.readLine();
            while(line!=null){
                String[] arr = line.split(",");
                String firstName = arr[1];
                String lastName = arr[2];
                String make = arr[11];
                String model = arr[12];
                availableMake.add(new Make(make));
                availableModel.add(new Model(model));

                listViewDataset.add(new Person(firstName,lastName,make,model));
                line = br.readLine();
            }
        }catch(Exception e){
        }

        listViewAdapter = new MyListViewAdapter(getApplicationContext(),R.layout.my_list_view_layout,listViewDataset);
        listView.setAdapter(listViewAdapter);

        availableModel =  availableModel.stream().distinct().sorted(Comparator.comparing(Model::getModelName)).collect(Collectors.toList());
        availableMake = availableMake.stream().distinct().sorted(Comparator.comparing(Make::getMakeName)).collect(Collectors.toList());
    }
}

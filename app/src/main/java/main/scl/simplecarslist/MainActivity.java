package main.scl.simplecarslist;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

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
    List<Person> listViewDatasetFiltered;

    Spinner spinner;
    ArrayAdapter<Make> spinnerAdapter;

    List<Make> availableMake;
    List<Model> availableModel;

    SearchView searchView;

    String FILE_SEPERATOR = ",";

    FloatingActionButton fab;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        initListView();
        initSpinner();
        initSearchView();
        initFloatingActionButton();
    }

    private void initFloatingActionButton() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
            MyCustomDialog dialog = new MyCustomDialog(view.getContext(),availableMake,availableModel);
            dialog.show();
        });
    }


    private void initSearchView() {
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listViewAdapter.filter(newText);
                return false;
            }
        });
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner);
        listViewDatasetFiltered = new ArrayList<>();
        setSpinnerAdapter(availableMake);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                listViewDatasetFiltered.clear();
                Make item = spinnerAdapter.getItem(pos);
                for(Person p : listViewDataset){
                    if(p.getMake().toString().toLowerCase().equals(item.toString().toLowerCase())){
                        listViewDatasetFiltered.add(p);
                    }
                }
                setListViewAdapter(listViewDatasetFiltered);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"Noting",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setListViewAdapter(List<Person> data){
        listViewAdapter = new MyListViewAdapter(getApplicationContext(),R.layout.my_list_view_layout,data);
        listView.setAdapter(listViewAdapter);
    }
    private void setSpinnerAdapter(List<Make> data){
        spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,data);
        spinner.setAdapter(spinnerAdapter);
    }
    private void initListView() {
        listView = findViewById(R.id.listView);
        listViewDataset = new ArrayList<>();
        availableMake = new ArrayList<>();
        availableModel = new ArrayList<>();


        try(BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("carsExtension.csv")))){
            String line = br.readLine();
            while(line!=null){
                String[] arr = line.split(FILE_SEPERATOR);
                String firstName = arr[0];
                String lastName = arr[1];
                String make = arr[2];
                String model = arr[3];
                availableMake.add(new Make(make));
                availableModel.add(new Model(model));

                listViewDataset.add(new Person(firstName,lastName,make,model));
                line = br.readLine();
            }
        }catch(Exception e){
            try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("cars.csv")))) {
                br.readLine();
                String line = br.readLine();
                while(line!=null){
                    String[] arr = line.split(FILE_SEPERATOR);
                    String firstName = arr[1];
                    String lastName = arr[2];
                    String make = arr[11];
                    String model = arr[12];
                    availableMake.add(new Make(make));
                    availableModel.add(new Model(model));

                    listViewDataset.add(new Person(firstName,lastName,make,model));
                    line = br.readLine();
                }
            }catch(Exception ee){
            }
        }

        setListViewAdapter(listViewDataset);
        availableModel =  availableModel.stream().distinct().sorted(Comparator.comparing(Model::getModelName)).collect(Collectors.toList());
        availableMake = availableMake.stream().distinct().sorted(Comparator.comparing(Make::getMakeName)).collect(Collectors.toList());
    }
}

package main.scl.simplecarslist;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

    SearchView searchView;

    FloatingActionButton fab;

    List<Make> availableMake;
    List<Model> availableModel;

    public static final String FILE_SEPERATOR = ",";

    String EXTENTION_FILE = "carsExt.csv";
    String ASSETS_FILE = "cars.csv";

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
        handleOutputFile(0,null);
    }


    private void initListView() {
        listView = findViewById(R.id.listView);
        listViewDataset = new ArrayList<>();
        availableMake = new ArrayList<>();
        availableModel = new ArrayList<>();

        availableMake.add(new Make(""));

        try(BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(EXTENTION_FILE)))){
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
            try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(ASSETS_FILE)))) {
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

    private void setListViewAdapter(List<Person> data){
        listViewAdapter = new MyListViewAdapter(getApplicationContext(),R.layout.my_list_view_layout,data);
        listView.setAdapter(listViewAdapter);
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner);
        setSpinnerAdapter(availableMake);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                listViewAdapter.filter(spinnerAdapter.getItem(pos).toString(),1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"Noting Selected",Toast.LENGTH_LONG).show();
            }
        });

        listViewAdapter.filter("",3);
    }

    private void setSpinnerAdapter(List<Make> data){
        spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,data);
        spinner.setAdapter(spinnerAdapter);
    }

    private void initFloatingActionButton() {
        fab = findViewById(R.id.fab);

        fab.setOnClickListener((view) -> {
            MyCustomDialog dialog = new MyCustomDialog(view.getContext(), availableMake, availableModel);
            dialog.show();

            dialog.fbButton.setOnClickListener(view1 -> {
                String firstName = dialog.fbEtFirstName.getText().toString();
                String lastName = dialog.fbEtLastName.getText().toString();
                String make = dialog.fbSpinnerMake.getSelectedItem().toString();
                String model = dialog.fbSpinnerModel.getSelectedItem().toString();

                if(firstName.length() != 0 &&lastName.length() != 0 &&model.length() != 0) {

                    Person newPerson = new Person(firstName, lastName, make, model);
                    listViewDataset.add(newPerson);
                    listViewAdapter.addPerson(newPerson);
                    handleOutputFile(1,newPerson);
                }
                dialog.dismiss();
            });
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
            public boolean onQueryTextChange(String txt) {
                listViewAdapter.filter(txt,0);
                return false;
            }
        });
    }


    private void handleOutputFile(int mode,Person pers) {
        if(mode == 0){
            try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(EXTENTION_FILE, MODE_PRIVATE)))){
                for(Person p : listViewDataset){
                    bw.write(p.toString());
                    bw.newLine();
                }
            }catch(Exception e){

            }
        }else if(mode == 1){
            try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(EXTENTION_FILE, MODE_APPEND)))){
                    bw.write(pers.toString());
                    bw.newLine();
            }catch(Exception e){

            }
        }
    }
}

package main.scl.simplecarslist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyListViewAdapter listViewAdapter;
    List<Person> listViewDataset;

    Spinner spinner;
    ArrayAdapter<Make> spinnerAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        initListView();
    }

    private void initListView() {
        listView = findViewById(R.id.listView);
        listViewDataset = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("cars.csv")))) {
            br.readLine();
            String line = br.readLine();
            while(line!=null){
                String[] arr = line.split(",");
                String firstName = arr[1];
                String lastName = arr[2];
                String make = arr[11];
                String model = arr[12];
                listViewDataset.add(new Person(firstName,lastName,make,model));
                line = br.readLine();
            }
        }catch(Exception e){
        }

        listViewAdapter = new MyListViewAdapter(getApplicationContext(),R.layout.my_list_view_layout,listViewDataset);
        listView.setAdapter(listViewAdapter);
    }
}

package main.scl.simplecarslist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MyCustomDialog extends Dialog{

    private final Context context;
    Button button;
    EditText etFirstName;
    EditText etLastName;

    Spinner make;
    ArrayAdapter<Make> makeAdapter;

    Spinner model;
    ArrayAdapter<Model> modelAdapter;

    public MyCustomDialog(@NonNull Context context, List<Make> makes,List<Model> models) {
        super(context);
        this.context = context;

        makeAdapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,makes);
        modelAdapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,models);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.my_dialog_layout);

        initUI();
    }

    private void initUI() {
        button = findViewById(R.id.fbButton);
        etFirstName = findViewById(R.id.fbEditViewFirstname);
        etLastName = findViewById(R.id.fbEditViewLastname);

        make = findViewById(R.id.fbSpinnerMake);
        make.setAdapter(makeAdapter);

        model = findViewById(R.id.fbSpinnerModel);
        model.setAdapter(modelAdapter);

        button.setOnClickListener( view -> {

        });
    }
}

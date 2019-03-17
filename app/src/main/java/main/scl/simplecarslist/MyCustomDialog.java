package main.scl.simplecarslist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class MyCustomDialog extends Dialog{

    private final Context context;
    Button fbButton;
    EditText fbEtFirstName;
    EditText fbEtLastName;

    Spinner fbSpinnerMake;
    private ArrayAdapter<Make> makeAdapter;

    Spinner fbSpinnerModel;
    private ArrayAdapter<Model> modelAdapter;

    public MyCustomDialog(@NonNull Context context, List<Make> makes,List<Model> models) {
        super(context);
        this.context = context;

        makeAdapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,makes);
        modelAdapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,models);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_dialog_layout);
        initUI();
    }

    private void initUI() {
        fbButton = findViewById(R.id.fbButton);
        fbEtFirstName = findViewById(R.id.fbEditViewFirstname);
        fbEtLastName = findViewById(R.id.fbEditViewLastname);

        fbSpinnerMake = findViewById(R.id.fbSpinnerMake);
        fbSpinnerMake.setAdapter(makeAdapter);

        fbSpinnerModel = findViewById(R.id.fbSpinnerModel);
        fbSpinnerModel.setAdapter(modelAdapter);
    }
}

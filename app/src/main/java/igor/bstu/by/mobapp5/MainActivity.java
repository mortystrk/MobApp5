package igor.bstu.by.mobapp5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    double bmr;
    double amr;
    EditText weight, growth, age;
    TextView conclusionView;
    double physicalActivity = -1;
    Gender gender;

    private AlertDialog CreateDialog(int message, int textOnPositiveButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton(textOnPositiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();
    }

    private boolean isEmpty(EditText etText){
        return etText.getText().toString().trim().length() == 0;
    }

    public void onRadioButtonClicked(View view) {
        // если переключатель отмечен
        boolean checked = ((RadioButton) view).isChecked();
        // нажатая кнопка
        switch (view.getId()){
            case R.id.radioButtonWoman:
                if(checked){
                    gender = Gender.WOMAN;
                }
                break;
            case R.id.radioButtonMan:
                if(checked){
                    gender = Gender.MAN;
                }
        }
    }

    public void onLowPhysical(View view){
        physicalActivity = 0;
        amr = 1.2;
    }

    public void onMediumPhysical(View view){
        physicalActivity = 0;
        amr = 1.55;
    }

    public void onHardPhysical(View view){
        physicalActivity = 0;
        amr = 1.9;
    }

    public void onCalculation(View view){

        if(gender == null){
            AlertDialog dialog = CreateDialog(R.string.gender, R.string.understood);
            dialog.show();
            return;
        }
        if(isEmpty(weight) || isEmpty(growth) || isEmpty(age)){
            AlertDialog dialog = CreateDialog(R.string.not_all_fields_are_filled, R.string.understood);
            dialog.show();
            return;
        }

        double weight, growth, age;
        weight = Double.parseDouble(this.weight.getText().toString());
        growth = Double.parseDouble(this.growth.getText().toString());
        age = Double.parseDouble(this.age.getText().toString());

        if(weight < 0 || growth < 0 || age < 0){
            AlertDialog dialog = CreateDialog(R.string.incorrect_data, R.string.understood);
            dialog.show();
            return;
        }
        if(physicalActivity < 0){
            AlertDialog dialog = CreateDialog(R.string.no_physical_activity_selected, R.string.understood);
            dialog.show();
            return;
        }

        Double result;

        switch (gender){
            case WOMAN:
                bmr = 655.0955 + (9.5634 * weight) + (1.8496 * growth) - (4.6756 * age);
                result = bmr * amr;

                conclusionView.append(String.format("%.2f", result));
                break;
            case MAN:
                bmr = 66.4730 + (13.7516 * weight) + (5.0033 * growth) - (6.7550 * age);
                result = bmr * amr;
                conclusionView.append(String.format("%.2f", result));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = (EditText) findViewById(R.id.weightText);
        growth = (EditText) findViewById(R.id.growthText);
        age = (EditText) findViewById(R.id.ageText);
        conclusionView = (TextView) findViewById(R.id.conclusionView);
    }
}

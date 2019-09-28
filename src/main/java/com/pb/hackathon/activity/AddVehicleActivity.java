package com.pb.hackathon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pb.hackathon.MyApplication;
import com.pb.hackathon.R;
import com.pb.hackathon.model.Vehicle;
import com.pb.hackathon.util.ApiClient;
import com.pb.hackathon.util.AppUtils;
import com.pb.hackathon.util.SharedPreferencesUtil;

import java.math.BigDecimal;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText regNumberEdt, licenseNoEdt, vehicleLengthEdt, vehicleWidthEdt, vehicleHeightEdt, vehicleWeightEdt;

    private Button btnAddVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        initView();
    }

    private void initView() {
        regNumberEdt = (EditText) findViewById(R.id.edtRegistrationNumber);
        licenseNoEdt = (EditText) findViewById(R.id.edtLicenseNumber);
        vehicleLengthEdt = (EditText) findViewById(R.id.edtVehicleLength);
        vehicleWidthEdt = (EditText) findViewById(R.id.edtVehicleWidth);
        vehicleHeightEdt = (EditText) findViewById(R.id.edtVehicleHeight);
        vehicleWeightEdt = (EditText) findViewById(R.id.edtVehicleWeightCapacity);

        btnAddVehicle = (Button) findViewById(R.id.btnAddVehicle);

        btnAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicle vehicle = new Vehicle();
                vehicle.setRegNumber(regNumberEdt.getText().toString());
                vehicle.setLicenseNo(licenseNoEdt.getText().toString());
                vehicle.setShipperId(SharedPreferencesUtil.getUser());
                try {

                    BigDecimal length = new BigDecimal(vehicleLengthEdt.getText().toString());
                    BigDecimal width = new BigDecimal(vehicleWidthEdt.getText().toString());
                    BigDecimal height = new BigDecimal(vehicleHeightEdt.getText().toString());

                    BigDecimal volume = length.multiply(width).multiply(height);
                    vehicle.setVolume(volume);
                    vehicle.setWeight(new BigDecimal(vehicleWeightEdt.getText().toString()));
                    send(AppUtils.toJSONString(vehicle));
                }catch (Exception e) {
                    Toast.makeText(MyApplication.getAppContext(), "Please enter valid values for length, height and width", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void send(String jsonBody) {
        if(MyApplication.checkNetworkConnection())
            new AddVehicleTask().execute(jsonBody);
        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

    }

    private void showSuccessDialog(String title, String message, String btn1Text) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                btn1Text,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    class AddVehicleTask extends AsyncTask<String, String, Void> {

        boolean isSuccess = false;

        @Override
        protected Void doInBackground(String... args) {
            try {
                String response = ApiClient.httpPost("/vehicle",args[0]);
                isSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(isSuccess){
                showSuccessDialog("Add a Vehicle", "Vehicle has been added successfully.", "OK");
                Intent intent = new Intent(getApplicationContext(), ListVehicleActivity.class);
                startActivity(intent);
            }
            else {
                showSuccessDialog("Error!", "Operation failed", "OK");
            }
        }
    }

}




package com.pb.hackathon.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pb.hackathon.R;
import com.pb.hackathon.model.Vehicle;
import com.pb.hackathon.util.ApiClient;
import com.pb.hackathon.util.SharedPreferencesUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListVehicleActivity extends AppCompatActivity {

    private ListView vehicleLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vehicle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vehicleLV = (ListView) findViewById(R.id.shipperVehicleList);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddVehicleActivity.class);
                startActivity(intent);
            }
        });

        ListVehicle listVehicle = new ListVehicle();
        listVehicle.execute();
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


    class VehicleListAdapter extends BaseAdapter {

        List<Vehicle> vehicles = new ArrayList<>();
        VehicleListAdapter(List<Vehicle> vehicles) {
            if(vehicles != null)
                this.vehicles = vehicles;
        }

        @Override
        public int getCount() {
            return vehicles.size();
        }

        @Override
        public Object getItem(int position) {
            return vehicles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }


        class Holder {
            private TextView regNumberTV;
            private TextView licenseNumberTV;
            private TextView weightTV;
            private TextView volumeTV;
        }

        @Override
        public View getView(int position, View row, ViewGroup parent)
        {
            Holder holder = null;
            if(row == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.list_vehcile_shipper_item_row, parent, false);
                holder = new Holder();

                holder.regNumberTV = (TextView) row.findViewById(R.id.tvRegNumber);
                holder.licenseNumberTV = (TextView) row.findViewById(R.id.tvLicenseNumber);
                holder.weightTV = (TextView) row.findViewById(R.id.tvCapacityWeight);
                holder.volumeTV = (TextView) row.findViewById(R.id.tvCapacityVolume);
                row.setTag(holder);
            }
            else {
                holder = (Holder)row.getTag(); //not sure what this is doing
            }

            Vehicle vehicle = (Vehicle) getItem(position);

            holder.regNumberTV.setText(vehicle.getRegNumber());
            holder.licenseNumberTV.setText(vehicle.getLicenseNo());
            holder.weightTV.setText(vehicle.getWeight()+ "");
            holder.volumeTV.setText(vehicle.getVolume() +"");

            return row;
        }
    }


    class ListVehicle extends AsyncTask<String, String, Void> {

        boolean isSuccess = false;
        String response = "";
        @Override
        protected Void doInBackground(String... args) {
            try {
                response = ApiClient.httpGet("/vehicle/shipperId/"+ SharedPreferencesUtil.getUser());
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
                Type listType = new TypeToken<List<Vehicle>>() {}.getType();
                List<Vehicle> vehicles = new Gson().fromJson(response, listType);
                VehicleListAdapter listAdapter = new VehicleListAdapter(vehicles);
                vehicleLV.setAdapter(listAdapter);
                vehicleLV.setSmoothScrollbarEnabled(true);
            }
            else {
                showSuccessDialog("Error!", "Get Operation failed", "OK");
            }
        }
    }

}

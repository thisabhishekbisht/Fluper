package com.abhishek.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.demo.R;
import com.abhishek.demo.adapter.CustomAdapter;

import com.abhishek.demo.model.Product;
import com.abhishek.demo.roomdatabase.DatabaseClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private Button btn_update, btn_insert, btn_select, btn_delete;
    Integer deleteItem;
    EditText edt_name, edt_id, edt_discription, edt_regularPrice, edt_salesPrice;

    private Animation zoomin;
   private Animation zoomout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.my_recycler_view);
        btn_delete = findViewById(R.id.btn_delete);
        btn_insert = findViewById(R.id.btn_insert);
        btn_update = findViewById(R.id.btn_update);
        btn_select = findViewById(R.id.btn_select);
        // set listner
        zoomin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        zoomout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
        btn_select.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        getDataFromJson();


    }

    private void getDataFromJson() {

        try {
            String str = LoadJsonFromAsset(this);
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("products");
            List<Product> listProduct = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Integer id = jsonObj.getInt("id");
                String name = jsonObj.getString("name");
                String description = jsonObj.getString("description");
                Integer regularPrice = jsonObj.getInt("regularPrice");
                Integer salePrice = jsonObj.getInt("salePrice");
                listProduct.add(new Product(id, name, description, regularPrice, salePrice));
            }


            updateDatabase(listProduct);


        } catch (Exception e) {

            Log.e("e ", "" + e);
        }
    }

    private void updateDatabase(final List<Product> listProduct) {


        //adding to database
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .taskDao()
                .insertAllOrders(listProduct);

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        updateDatatoUI();

    }


    public void updateDatatoUI() {
        //adding to database
        List<Product> dataList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .taskDao().getAll();

        for (int i = 0; i < dataList.size(); i++) {
            Log.e("", "" + dataList.size());
        }

        if (!dataList.isEmpty()) {
            // update UI


            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(llm);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            adapter = new CustomAdapter(this ,dataList);
            recyclerView.setAdapter(adapter);



        }

    }

    public String LoadJsonFromAsset(Context context) {
        String json = null;
        try {
            InputStream in = context.getAssets().open("products.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                // create a Dialog component
                final Dialog dialog = new Dialog(this);

                //tell the Dialog to use the dialog.xml as it's layout description
                dialog.setContentView(R.layout.dialog_delete);
                dialog.setTitle("Android Custom Dialog Box");

                TextView txt = (TextView) dialog.findViewById(R.id.tv_msg);
                AppCompatSpinner spinner = dialog.findViewById(R.id.spinner_items);

                loadSpinnerData(spinner);

                txt.setText("Are you sure that you want to delete current item");

                Button dialogButton = dialog.findViewById(R.id.btn_deleteItem);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (deleteItem != 0) {
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .taskDao().delete(deleteItem);
                            Toast.makeText(MainActivity.this, "Item deleted sucessfully .", Toast.LENGTH_SHORT).show();
                            updateDatatoUI();
                        } else {
                            Toast.makeText(MainActivity.this, "Please choose item to delete", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                });
                dialog.show();


                break;
            case R.id.btn_insert:
                // create a Dialog component
                final Dialog dialogInsert = new Dialog(this);

                //tell the Dialog to use the dialog.xml as it's layout description
                dialogInsert.setContentView(R.layout.dialog_insert);
                dialogInsert.setTitle("Android Custom Dialog Box");

                TextView txt_msg = dialogInsert.findViewById(R.id.tv_msg);
                edt_name = dialogInsert.findViewById(R.id.tv_name_insert);
                edt_id = dialogInsert.findViewById(R.id.tv_id_insert);
                edt_discription = dialogInsert.findViewById(R.id.tv_discription_insert);
                edt_regularPrice = dialogInsert.findViewById(R.id.tv_regularPrice_insert);
                edt_salesPrice = dialogInsert.findViewById(R.id.tv_salePrice_insert);

                txt_msg.setText("Please make sure all fields are filled !");

                Button dialogButtonInsert = dialogInsert.findViewById(R.id.btn_InsertItem);

                dialogButtonInsert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //adding to database
                        Boolean isAlreadyExists = false;
                        final List<Product> dataList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .taskDao().getAll();

                        for (int i = 0; i < dataList.size(); i++) {

                            if(dataList.get(i).getId().equals(Integer.parseInt(edt_id.getText().toString())))
                            {
                                isAlreadyExists = true;
                            }

                        }
                      if(!isAlreadyExists)
                      {
                          DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                  .taskDao().insert(new Product( Integer.parseInt(edt_id.getText().toString()),edt_name.getText().toString(), edt_discription.getText().toString(), Integer.parseInt(String.valueOf(edt_regularPrice.getText())), Integer.parseInt(String.valueOf(edt_salesPrice.getText()))));
                          Toast.makeText(MainActivity.this, "Sucessfully Inserted Record", Toast.LENGTH_SHORT).show();
                          updateDatatoUI();
                          dialogInsert.dismiss();
                      }
                      else
                      {
                          isAlreadyExists = false;
                          Toast.makeText(MainActivity.this, "Id already Exists , Please change", Toast.LENGTH_SHORT).show();


                      }
                    }
                });
                dialogInsert.show();
                break;
            case R.id.btn_select:
                break;
            case R.id.btn_update:
                // create a Dialog component
                final Dialog dialogUpdate = new Dialog(this);

                //tell the Dialog to use the dialog.xml as it's layout description
                dialogUpdate.setContentView(R.layout.dialog_update);
                dialogUpdate.setTitle("Android Custom Dialog Box");

                final AppCompatSpinner spinnerUpdate = dialogUpdate.findViewById(R.id.spinner_itemsUpdate);
                edt_name = dialogUpdate.findViewById(R.id.tv_name_update);
                edt_id = dialogUpdate.findViewById(R.id.tv_id_update);
                edt_discription = dialogUpdate.findViewById(R.id.tv_discription_update);
                edt_regularPrice = dialogUpdate.findViewById(R.id.tv_regularPrice_update);
                edt_salesPrice = dialogUpdate.findViewById(R.id.tv_salePrice_update);

                //

                //adding to database
                final List<Product> dataList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao().getAll();
                final List<Integer> data = new ArrayList<>();
                for (int i = 0; i < dataList.size(); i++) {
                    data.add(dataList.get(i).getId());

                }

                // set initial data
                Integer id = dataList.get(0).getId();
                String name = dataList.get(0).getName();
                String disc = dataList.get(0).getDescription();
                Integer regularPrice = dataList.get(0).getRegularPrice();
                Integer salesPrice = dataList.get(0).getSalePrice();
                Log.e("data", "id " + id + " name " + name + " desc " + disc + regularPrice + salesPrice);
                edt_id.setText(String.valueOf(id));
                edt_name.setText(name);
                edt_discription.setText(disc);
                edt_regularPrice.setText(String.valueOf(regularPrice));
                edt_salesPrice.setText(String.valueOf(salesPrice));

                final ArrayAdapter<Integer>[] arrayAdapter = new ArrayAdapter[]{new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, data)};
                arrayAdapter[0].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUpdate.setAdapter(arrayAdapter[0]);
                spinnerUpdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        edt_id.setText(dataList.get(position).getId().toString());
                        edt_name.setText(dataList.get(position).getName());
                        edt_discription.setText(dataList.get(position).getDescription());
                        edt_regularPrice.setText(dataList.get(position).getRegularPrice().toString());
                        edt_salesPrice.setText(dataList.get(position).getSalePrice().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                TextView txt_msgUpdate = dialogUpdate.findViewById(R.id.tv_msgUpdate);
                txt_msgUpdate.setText("Are you sure that you want to delete current item");

                Button dialogButtonUpdate = dialogUpdate.findViewById(R.id.btn_updateItem);

                dialogButtonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .taskDao().updateQuantity(dataList.get(spinnerUpdate.getSelectedItemPosition()).getId(), edt_name.getText().toString(), edt_discription.getText().toString(), Integer.parseInt(String.valueOf(edt_regularPrice.getText())), Integer.parseInt(String.valueOf(edt_salesPrice.getText())));
                        Toast.makeText(MainActivity.this, "Sucessfully Updated Record", Toast.LENGTH_SHORT).show();
                        updateDatatoUI();
                        dialogUpdate.dismiss();
                    }
                });
                dialogUpdate.show();
                break;
        }

    }

    private void loadSpinnerDataForUpdate(AppCompatSpinner spinnerUpdate) {

    }

    private void loadSpinnerData(AppCompatSpinner spinner) {


        //adding to database
        final List<Product> dataList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .taskDao().getAll();
        final List<Integer> data = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            data.add(dataList.get(i).getId());

        }


        final ArrayAdapter<Integer>[] arrayAdapter = new ArrayAdapter[]{new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, data)};
        arrayAdapter[0].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter[0]);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deleteItem = Integer.parseInt(dataList.get(position).getId().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
}
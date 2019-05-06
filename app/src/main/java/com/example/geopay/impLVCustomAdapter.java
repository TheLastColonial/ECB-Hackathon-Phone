package com.example.geopay;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class impLVCustomAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;
    //to store the  images
    private final TypedArray imageArray;
    //to store the list of merchants
    private final ArrayList<String> nameArray;
    //to reference checkboxes state
    private final boolean[] boolArray;

    //costructor
    public impLVCustomAdapter(Activity context, ArrayList<String> nameArrayParam, TypedArray imageIDArrayParam){

        super(context,R.layout.list_item , nameArrayParam);
        this.context=context;
        this.imageArray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.boolArray = new boolean[imageArray.length()]; //all of the values are false by default
    }

    public boolean[] getCheckedArray() {
        return boolArray;
    }

    public ArrayList<String> getNamesArray(){
        return nameArray;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item, null,true);

        //this code gets references to objects in the  file
        CheckBox nameTextField = (CheckBox ) rowView.findViewById(R.id.checkBox);
        nameTextField.setChecked(boolArray[position]);
        nameTextField.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolArray[position] = isChecked;
            }
        });
        ImageView img = (ImageView) rowView.findViewById(R.id.listImg);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray.get(position));
        img.setImageDrawable(imageArray.getDrawable(position));

        return rowView;
    };
}


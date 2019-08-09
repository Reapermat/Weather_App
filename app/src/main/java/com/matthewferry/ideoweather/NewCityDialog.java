package com.matthewferry.ideoweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class NewCityDialog extends DialogFragment {

    NewCityListener mListener;
    String city;
    String language = MainActivity.getLanguage();
    String enterNewCity;
    String addCity;
    String addValidCity;
    String cancel;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mListener = (NewCityListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "must implement NewCitylistener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder((getActivity()));
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.new_city, null);
        final EditText myCityTxt = dialogLayout.findViewById(R.id.dialogCity);
        enterNewCity = getString(R.string.enter_new_city);
        addCity = getString(R.string.enter_city);
        addValidCity = getString(R.string.enter_valid_city);
        cancel = getString(R.string.cancel);

        myCityTxt.setHint(enterNewCity);

        builder.setView(dialogLayout).setPositiveButton(addCity, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                city = myCityTxt.getText().toString();


                if (!city.equals("")) {
                    mListener.onAddCity(city);
                } else {
                    Toast.makeText(getContext(), addValidCity, 10).show();
                }
            }
        }).setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onCancel(NewCityDialog.this);
                NewCityDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }



    public interface NewCityListener{
        void onAddCity(String city);
        void onCancel(DialogFragment dialogFragment);
    }
}

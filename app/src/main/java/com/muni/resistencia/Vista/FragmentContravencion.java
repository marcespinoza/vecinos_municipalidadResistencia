package com.muni.resistencia.Vista;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.muni.resistencia.R;



public class FragmentContravencion extends Fragment {

    Dialog popup, evaluacionPopUp;
    GridLayout mainGrid;
    Button reclamo, evaluacion;

    public FragmentContravencion() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentContravencion newInstance(String param1, String param2) {
        FragmentContravencion fragment = new FragmentContravencion();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_contravencion, container, false);
        mainGrid =  v.findViewById(R.id.gridMain);
        popup = new Dialog(getActivity());
        popup.setContentView(R.layout.popup_servicios);
        reclamo = popup.findViewById(R.id.reclamo);
        reclamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
                Intent i = new Intent(getActivity(), Reclamo_activity.class);
                startActivity(i);
            }
        });
        evaluacion = popup.findViewById(R.id.evaluacion);
        evaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
                evaluacionPopUp = new Dialog(getActivity());
                evaluacionPopUp.setContentView(R.layout.popup_calificacion);
                evaluacionPopUp.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
                evaluacionPopUp.show();
            }
        });
        popup.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        setGridListener(mainGrid);
        return v;
    }

    private void setGridListener(GridLayout gridLayout){
        for(int i=0; i<gridLayout.getChildCount(); i++){
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popup.show();
                }
            });
        }
    }

}

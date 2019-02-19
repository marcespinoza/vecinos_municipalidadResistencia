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

    GridLayout mainGrid;
    String idContravencion, idComision;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_contravencion, container, false);
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) {
            idComision = getActivity().getIntent().getExtras().getString("idComision");
        }
        mainGrid =  v.findViewById(R.id.gridMain);
        setGridListener(mainGrid);
        return v;
    }

    private void setGridListener(final GridLayout gridLayout){
        for(int i=0; i<gridLayout.getChildCount(); i++){
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (gridLayout.indexOfChild(view)){
                        case 0: idContravencion = "1"; break;
                        case 1: idContravencion = "2"; break;
                        case 2: idContravencion = "3"; break;
                        case 3: idContravencion = "4"; break;
                        case 4: idContravencion = "5"; break;
                        case 5: idContravencion = "6"; break;
                        case 6: idContravencion = "7"; break;
                        case 7: idContravencion = "8"; break;
                    }
                    Intent i = new Intent(getActivity(), Reclamo_activity.class);
                    i.putExtra("idContravencion", idContravencion);
                    i.putExtra("IdComision", idComision);
                    i.putExtra("tipo","contravencion");
                    startActivity(i);
                }
            });
        }
    }

}

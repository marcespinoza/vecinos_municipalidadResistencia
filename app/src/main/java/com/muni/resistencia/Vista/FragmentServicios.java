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

import com.muni.resistencia.Interfaces.EvaluacionInterface;
import com.muni.resistencia.Interfaces.ReclamoInterface;
import com.muni.resistencia.Presentador.EvaluacionPresentador;
import com.muni.resistencia.Presentador.ReclamoPresentador;
import com.muni.resistencia.R;


public class FragmentServicios extends Fragment {

    GridLayout mainGrid;
    Dialog popup, evaluacionPopUp, evaluacionResiduoPopUp;
    Button reclamo, evaluacion, bueno, regular, malo;
    int iDservicio;
    EvaluacionInterface.Presentador evaluacionPresentador;
    ReclamoInterface.Presentador reclamoPresentador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setGridListener(final GridLayout gridLayout){
        for(int i=0; i<gridLayout.getChildCount(); i++){
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (gridLayout.indexOfChild(view)){
                        case 1: iDservicio = 1; break;
                        case 2: iDservicio = 2; break;
                        case 3: iDservicio = 3; break;
                        case 4: iDservicio = 4; break;
                        case 5: iDservicio = 5; break;
                        case 6: iDservicio = 6; break;
                        case 7: iDservicio = 7; break;
                        case 8: iDservicio = 8; break;
                        case 9: iDservicio = 9; break;
                        case 10: iDservicio = 10; break;
                    }
                    popup.show();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_servicios, container, false);
        evaluacionPresentador = new EvaluacionPresentador();
        reclamoPresentador = new ReclamoPresentador();
        mainGrid =  v.findViewById(R.id.gridMain);
        popup = new Dialog(getActivity());
        popup.setContentView(R.layout.popup_servicios);
        reclamo = popup.findViewById(R.id.reclamo);
        evaluacion = popup.findViewById(R.id.evaluacion);
        evaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
                if(iDservicio!=3){
                evaluacionPopUp = new Dialog(getActivity());
                evaluacionPopUp.setContentView(R.layout.popup_calificacion);
                evaluacionPopUp.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
                Button bueno = evaluacionPopUp.findViewById(R.id.buenoBtn);
                bueno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                Button regular = evaluacionPopUp.findViewById(R.id.regularBtn);
                regular.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                Button malo = evaluacionPopUp.findViewById(R.id.maloBtn);
                malo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                evaluacionPopUp.show();
            }else{
                    evaluacionResiduoPopUp = new Dialog(getActivity());
                    evaluacionResiduoPopUp.setContentView(R.layout.popup_calificacion_residuos);
                    evaluacionResiduoPopUp.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
                    evaluacionResiduoPopUp.show();
                }
            }
        });
        reclamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
                Intent i = new Intent(getActivity(), Reclamo_activity.class);
                i.putExtra("IdServicio", iDservicio);
                i.putExtra("IdComision", iDservicio);
                startActivity(i);
            }
        });
        popup.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        setGridListener(mainGrid);
        return v;
    }


}

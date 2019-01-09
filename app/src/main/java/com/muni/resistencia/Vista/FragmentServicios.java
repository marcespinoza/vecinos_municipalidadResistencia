package com.muni.resistencia.Vista;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.muni.resistencia.Interfaces.EvaluacionInterface;
import com.muni.resistencia.Interfaces.ReclamoInterface;
import com.muni.resistencia.Presentador.EvaluacionPresentador;
import com.muni.resistencia.Presentador.ReclamoPresentador;
import com.muni.resistencia.R;


public class FragmentServicios extends Fragment implements EvaluacionInterface.Vista {

    GridLayout mainGrid;
    Dialog popup, evaluacionPopUp, evaluacionResiduoPopUp;
    Button reclamo, evaluacion, bueno, regular, malo;
    String idServicio, idComision;
    EvaluacionInterface.Presentador evaluacionPresentador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idComision = getActivity().getIntent().getExtras().getString("idComision");
    }

    private void setGridListener(final GridLayout gridLayout){
        for(int i=0; i<gridLayout.getChildCount(); i++){
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (gridLayout.indexOfChild(view)){
                        case 0: idServicio = "1"; break;
                        case 1: idServicio = "2"; break;
                        case 2: idServicio = "3"; break;
                        case 3: idServicio = "4"; break;
                        case 4: idServicio = "5"; break;
                        case 5: idServicio = "6"; break;
                        case 6: idServicio = "7"; break;
                        case 7: idServicio = "8"; break;
                        case 8: idServicio = "9"; break;
                        case 9: idServicio = "10"; break;
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
        evaluacionPresentador = new EvaluacionPresentador(this);
        mainGrid =  v.findViewById(R.id.gridMain);
        popup = new Dialog(getActivity());
        popup.setContentView(R.layout.popup_servicios);
        reclamo = popup.findViewById(R.id.reclamo);
        evaluacion = popup.findViewById(R.id.evaluacion);
        evaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
                if(!idServicio.equalsIgnoreCase("4")){
                evaluacionPopUp = new Dialog(getActivity());
                evaluacionPopUp.setContentView(R.layout.popup_calificacion);
                evaluacionPopUp.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
                Button bueno = evaluacionPopUp.findViewById(R.id.buenoBtn);
                bueno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        evaluacionPresentador.enviarEvaluacion(idComision, idServicio, "bueno");
                    }
                });
                Button regular = evaluacionPopUp.findViewById(R.id.regularBtn);
                regular.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        evaluacionPresentador.enviarEvaluacion(idComision, idServicio, "regular");
                    }
                });
                Button malo = evaluacionPopUp.findViewById(R.id.maloBtn);
                malo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        evaluacionPresentador.enviarEvaluacion(idComision, idServicio, "malo");
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
                i.putExtra("idServicio", idServicio);
                i.putExtra("IdComision", idComision);
                startActivity(i);
            }
        });
        popup.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        setGridListener(mainGrid);
        return v;
    }


    @Override
    public void mostrarToast(final String mensaje) {
        evaluacionPopUp.dismiss();
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(getActivity(), mensaje,  Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}

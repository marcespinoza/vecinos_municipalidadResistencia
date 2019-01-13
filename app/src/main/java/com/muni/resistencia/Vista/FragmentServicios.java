package com.muni.resistencia.Vista;

import android.app.AlertDialog;
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
import com.muni.resistencia.Presentador.EvaluacionPresentador;
import com.muni.resistencia.R;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;


public class FragmentServicios extends Fragment implements EvaluacionInterface.Vista {

    GridLayout mainGrid;
    Dialog popup, evaluacionPopUp, evaluacionResiduoPopUp;
    Button reclamo, evaluacion;
    String idServicio, idComision;
    EvaluacionInterface.Presentador evaluacionPresentador;
    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_servicios, container, false);
        idComision = getActivity().getIntent().getExtras().getString("idComision");
        evaluacionPresentador = new EvaluacionPresentador(this);
        dialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage("Registrando evaluación..")
                .build();
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
                        enviarEvaluacion(idComision, idServicio, "9");
                    }
                });
                Button regular = evaluacionPopUp.findViewById(R.id.regularBtn);
                regular.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        enviarEvaluacion(idComision, idServicio, "5");
                    }
                });
                Button malo = evaluacionPopUp.findViewById(R.id.maloBtn);
                malo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        enviarEvaluacion(idComision, idServicio, "1");
                    }
                });
                evaluacionPopUp.show();
            }else{
                    evaluacionResiduoPopUp = new Dialog(getActivity());
                    evaluacionResiduoPopUp.setContentView(R.layout.popup_calificacion_residuos);
                    evaluacionResiduoPopUp.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
                    Button bueno = evaluacionResiduoPopUp.findViewById(R.id.bueno_Btn);
                    bueno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            enviarEvaluacion(idComision, idServicio, "9");
                        }
                    });
                    Button regular = evaluacionResiduoPopUp.findViewById(R.id.regular_Btn);
                    regular.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            enviarEvaluacion(idComision, idServicio, "5");
                        }
                    });
                    Button malo = evaluacionResiduoPopUp.findViewById(R.id.malo_Btn);
                    malo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            enviarEvaluacion(idComision, idServicio, "1");
                        }
                    });
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
                i.putExtra("tipo","servicio");
                startActivity(i);
            }
        });
        popup.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
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

    void enviarEvaluacion(String idComision, String idServicio, String calificacion){
      if(evaluacionPopUp!=null) evaluacionPopUp.dismiss();
      if(evaluacionResiduoPopUp!=null) evaluacionResiduoPopUp.dismiss();
        dialog.show();
        evaluacionPresentador.enviarEvaluacion(idComision, idServicio, calificacion);
    }

    @Override
    public void mostrarToast(final String mensaje) {
        dialog.dismiss();
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(getActivity(), mensaje,  Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}

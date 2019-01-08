package com.muni.resistencia.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.muni.resistencia.Vista.FragmentContravencion;
import com.muni.resistencia.Vista.FragmentServicios;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentServicios();
            case 1: return new FragmentContravencion();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override    public CharSequence getPageTitle(int position) {        switch (position){
        case 0: return "Servicios";
        case 1: return "Contravenciones";
        default: return null;
    }
    }
}

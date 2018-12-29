package com.muni.resistencia.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.muni.resistencia.Vista.Contravencion;
import com.muni.resistencia.Vista.Servicios;
import com.muni.resistencia.Vista.Servicios_activity;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Servicios();
            case 1: return new Contravencion();
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

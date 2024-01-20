package com.cifpceuta.appplanifica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PorDefecto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PorDefecto extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CORREO = "Correo";

    // TODO: Rename and change types of parameters
    private String mCorreo;

    public Fragment_PorDefecto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param correo Correo del usuario logueado.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_PorDefecto newInstance(String correo) {
        Fragment_PorDefecto fragment = new Fragment_PorDefecto();
        Bundle args = new Bundle();
        //Recogemos los datos del usuario
        args.putString(ARG_CORREO, correo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCorreo = getArguments().getString(ARG_CORREO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tvUsuario;
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        // Vincular el correo electrónico al TextView en el layout
        tvUsuario = rootView.findViewById(R.id.tvUserLogueado);
        tvUsuario.setText(mCorreo);

        // Inflate the layout for this fragment
        return rootView;
    }
}
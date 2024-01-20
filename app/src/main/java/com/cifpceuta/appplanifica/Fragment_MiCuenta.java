package com.cifpceuta.appplanifica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_MiCuenta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_MiCuenta extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NOMBRE = "Nombre";
    private static final String ARG_CORREO = "Correo";
    private static final String ARG_TURNO = "Turno";
    private static final String ARG_GRUPO = "Grupo";

    // TODO: Rename and change types of parameters
    private String mNombre;
    private String mCorreo;
    private String mTurno;
    private String mGrupo;

    public Fragment_MiCuenta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nombre nombre del usuario logueado.
     * @param correo correo del usuario logueado.
     * @param turno  turno del usuario logueado.
     * @param grupo  grupo del usuario logueado.
     * @return A new instance of fragment BlankFragment_MiCuenta.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_MiCuenta newInstance(String nombre, String correo, String turno, String grupo) {
        Fragment_MiCuenta fragmentCuenta = new Fragment_MiCuenta();
        Bundle args = new Bundle();
        //Recogemos los datos del usuario
        args.putString(ARG_NOMBRE, nombre);
        args.putString(ARG_CORREO, correo);
        args.putString(ARG_TURNO, turno);
        args.putString(ARG_GRUPO, grupo);
        fragmentCuenta.setArguments(args);
        return fragmentCuenta;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNombre = getArguments().getString(ARG_NOMBRE);
            mCorreo = getArguments().getString(ARG_CORREO);
            mTurno = getArguments().getString(ARG_TURNO);
            mGrupo = getArguments().getString(ARG_GRUPO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tvUsuario;
        View rootView = inflater.inflate(R.layout.fragment_mi_cuenta, container, false);

        // Vincular el correo electrónico al TextView en el layout
        tvUsuario = rootView.findViewById(R.id.tvNombreUser);
        tvUsuario.setText("Nombre : "+mNombre);
        tvUsuario = rootView.findViewById(R.id.tvEmailUser);
        tvUsuario.setText("Correo : "+mCorreo);
        tvUsuario = rootView.findViewById(R.id.tvTurnoUser);
        tvUsuario.setText("Turno : "+mTurno);
        tvUsuario = rootView.findViewById(R.id.tvGrupoUser);
        tvUsuario.setText("Grupo : "+mGrupo);

        // Inflate the layout for this fragment
        return rootView;
    }
}
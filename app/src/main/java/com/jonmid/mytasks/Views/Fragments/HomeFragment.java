package com.jonmid.mytasks.Views.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jonmid.mytasks.Adapters.UserAdapter;
import com.jonmid.mytasks.HttpManager;
import com.jonmid.mytasks.Models.User;
import com.jonmid.mytasks.Parser.Json;
import com.jonmid.mytasks.R;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View view;
    ProgressBar loader;
    RecyclerView myRecycler;
    List<User> myUser;
    UserAdapter myAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        loader = (ProgressBar) view.findViewById(R.id.loader);
        myRecycler = (RecyclerView) view.findViewById(R.id.myRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecycler.setLayoutManager(linearLayoutManager);

        showTolbar(getResources().getString(R.string.txt_title_toolbar_Container),true);
        setHasOptionsMenu(true); // para poder poner toolbar  en fragmento

        onClickButton();
        return view;

    }

    // Metodo para validar la conexion a internet
    public Boolean isOnLine(){
        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connec.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }


    // Medodo para manejar el evento del item del menu
    public void onClickButton(){
        if (isOnLine()){
            MyTask task = new MyTask();
            task.execute("https://jsonplaceholder.typicode.com/users");
            //Toast.makeText(getActivity(), "Funciona", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "Sin conexión", Toast.LENGTH_SHORT).show();
        }
    }



    // Tarea asincrona para obtener los datos desde internet
    private class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String content = null;
            try {
                content = HttpManager.getData(params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                myUser = Json.parserJsonUser(s);

            } catch (Exception e) {
                e.printStackTrace();
            }
            cargarDatos();
            loader.setVisibility(View.GONE);
        }
        public void cargarDatos() {

            // Crear un objeto de tipo "PostAdapter" y retorna el item de mi layout (item.xml)
            myAdapter = new UserAdapter(getActivity().getApplicationContext(), myUser);
            // inyectar el item en mi RecyclerView
            myRecycler.setAdapter(myAdapter);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_cargar_datos:
                onClickButton();
                return (true);
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    private void showTolbar(String title, boolean upButton) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.id_tb_toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }


}

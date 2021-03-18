package co.com.registropersonamovil2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.registropersonamovil2021.adapter.PersonaAdapter;
import co.com.registropersonamovil2021.entity.Persona;
import co.com.registropersonamovil2021.persistencia.Connection;
import co.com.registropersonamovil2021.util.ActionBarUtil;

public class MainActivity extends AppCompatActivity {

   private Connection connection;
   private List<Persona> listaPersonas;
   @BindView(R.id.listViewPersonas)
   ListView listViewPersonas;
   private PersonaAdapter personaAdapter;
   public static Boolean existe = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActionBarUtil.getInstance(this, false).setToolBar(getString(R.string.listado_persona));
        listaPersonas = new ArrayList<>();
        loadInformation();
        goToIntent();
    }


    private void goToIntent() {
        listViewPersonas.setClickable(true);
        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToRegistroPersona(i);

            }
        });
    }
    private void loadInformation() {
        listaPersonas =  Connection.getDbMainThread(this).getPersonaDao().findAll();
        if(listaPersonas.isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.sin_infomacion,Toast.LENGTH_LONG).show();
        }else{
            personaAdapter = new PersonaAdapter(this,listaPersonas);
            listViewPersonas.setAdapter(personaAdapter);
        }
    }

    public void goToRegistroPersona(View view) {
        Intent intent = new Intent(this,RegistroPersonaActivity.class);
        startActivity(intent);
    }

    public void goToRegistroPersona(int i) {
        Persona personaSelecionado = (Persona) listViewPersonas.getItemAtPosition(i);
        Intent intent = new Intent(this,RegistroPersonaActivity.class);
        intent.putExtra("persona", personaSelecionado);
        existe=true;
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        MainActivity.existe=false;
        loadInformation();
    }


}
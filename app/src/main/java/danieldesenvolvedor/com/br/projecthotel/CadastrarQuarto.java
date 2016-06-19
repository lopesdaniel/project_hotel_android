package danieldesenvolvedor.com.br.projecthotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import danieldesenvolvedor.com.br.projecthotel.model.Quarto;

public class CadastrarQuarto extends AppCompatActivity {


    List<String> tipoQuartos;
    EditText txtValor;
    Spinner comboBox;
    RadioGroup radioGroupDisp;
    RadioButton rdbDisp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_quarto);




        tipoQuartos = new ArrayList<>();
        tipoQuartos.add("Individual");
        tipoQuartos.add("Casal");
        tipoQuartos.add("Triplo");

        txtValor = (EditText)findViewById(R.id.txtValor);

        comboBox = (Spinner)findViewById(R.id.spnTipo);
        radioGroupDisp = (RadioGroup)findViewById(R.id.rdgDisp);

        ArrayAdapter<String> tipoQuartosAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                tipoQuartos);

        comboBox.setAdapter(tipoQuartosAdapter);

    }

    private void addQuarto(){
        final String valor = txtValor.getText().toString().trim();

        final String tipo = comboBox.getSelectedItem().toString().trim();

        int selectedId = radioGroupDisp.getCheckedRadioButtonId();
        rdbDisp = (RadioButton)findViewById(selectedId);

        final String disponivel = rdbDisp.getText().toString().trim();

        class AddQuarto extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CadastrarQuarto.this,"Adicionando...","Aguarde...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(CadastrarQuarto.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_QUARTO_VALOR,valor);
                params.put(Config.KEY_QUARTO_TIPO,tipo);
                params.put(Config.KEY_QUARTO_DISP,disponivel);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }

        }

        AddQuarto aq = new AddQuarto();
        aq.execute();
    }

    public void click_cad_quarto(View view){
        addQuarto();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}

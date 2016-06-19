package danieldesenvolvedor.com.br.projecthotel;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import danieldesenvolvedor.com.br.projecthotel.model.Quarto;

public class VerQuarto extends AppCompatActivity {


    List<String> tipoQuartos;
    List<String> tipoQ;
    EditText txtValor, txtid;
    Spinner comboBox;
    RadioGroup radioGroupDisp;
    RadioButton rdbDisp;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_quarto);

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.QUARTO_ID);

        tipoQuartos = new ArrayList<>();
        tipoQuartos.add("Individual");
        tipoQuartos.add("Casal");
        tipoQuartos.add("Triplo");

        txtid = (EditText) findViewById(R.id.txteditId);
        txtValor = (EditText)findViewById(R.id.txtValor);


        comboBox = (Spinner)findViewById(R.id.spnTipo);
        radioGroupDisp = (RadioGroup)findViewById(R.id.rdgDisp);

        ArrayAdapter<String> tipoQuartosAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                tipoQuartos);

        comboBox.setAdapter(tipoQuartosAdapter);

        txtid.setText(id);

        getQuarto();

    }

    private void getQuarto(){
        class GetQuarto extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VerQuarto.this,"Pesquisando...","Aguarde...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showQuarto(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_QUARTO,id);
                return s;
            }
        }
        GetQuarto ge = new GetQuarto();
        ge.execute();
    }

    private void showQuarto(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String valor = c.getString(Config.TAG_VALOR);
            String tipo = c.getString(Config.TAG_TIPO);
            String disp = c.getString(Config.TAG_DISP);

            txtValor.setText(valor);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateQuarto(){

        final String valor = txtValor.getText().toString().trim();

        final String tipo = comboBox.getSelectedItem().toString().trim();

        int selectedId = radioGroupDisp.getCheckedRadioButtonId();
        rdbDisp = (RadioButton)findViewById(selectedId);

        final String disponivel = rdbDisp.getText().toString().trim();

        class AlteraQuarto extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VerQuarto.this,"Atualizando...","Aguarde...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(VerQuarto.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_QUARTO_ID,id);
                hashMap.put(Config.KEY_QUARTO_VALOR,valor);
                hashMap.put(Config.KEY_QUARTO_TIPO,tipo);
                hashMap.put(Config.KEY_QUARTO_DISP,disponivel);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_EMP, hashMap);

                return s;
            }

        }

        AlteraQuarto aq = new AlteraQuarto();
        aq.execute();
    }

    private void deleteQuarto(){
        class DeletaQuarto extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VerQuarto.this, "Deletando...", "Aguarde...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(VerQuarto.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_EMP, id);
                return s;
            }
        }

        DeletaQuarto dq = new DeletaQuarto();
        dq.execute();
    }

    private void confirmDeleteQuarto(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Tem certeza que quer deletar esse Quarto ?");

        alertDialogBuilder.setNegativeButton("Não",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        alertDialogBuilder.setPositiveButton("Sim",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteQuarto();
                        startActivity(new Intent(VerQuarto.this,MainActivity.class));
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void click_altera_quarto(View view){
        updateQuarto();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void click_del_quarto(View view){
        confirmDeleteQuarto();

    }

}

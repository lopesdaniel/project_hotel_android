package danieldesenvolvedor.com.br.projecthotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TodosQuartos extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_quartos);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showQuarto(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String valor = jo.getString(Config.TAG_VALOR);
                String tipo = jo.getString(Config.TAG_TIPO);
                String disponivel = jo.getString(Config.TAG_DISP);


                HashMap<String,String> quartos = new HashMap<>();
                quartos.put(Config.TAG_ID,id);
                quartos.put(Config.TAG_VALOR,valor);
                quartos.put(Config.TAG_TIPO, tipo);
                quartos.put(Config.TAG_DISP, disponivel);
                list.add(quartos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TodosQuartos.this, list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_VALOR,Config.TAG_TIPO,Config.TAG_DISP},
                new int[]{R.id.id, R.id.valor, R.id.tipo, R.id.disponivel});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TodosQuartos.this,"Recuperando dados","Aguarde...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showQuarto();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, VerQuarto.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String qId = map.get(Config.TAG_ID).toString();
        intent.putExtra(Config.QUARTO_ID,qId);
        startActivity(intent);
    }
}

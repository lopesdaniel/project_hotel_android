package danieldesenvolvedor.com.br.projecthotel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click_cad_quarto(View view){
        Intent intent = new Intent(getApplicationContext(), CadastrarQuarto.class);
        startActivity(intent);
    }

    public void click_list_quartos(View view){
        Intent intent = new Intent(getApplicationContext(), TodosQuartos.class);
        startActivity(intent);
    }

}

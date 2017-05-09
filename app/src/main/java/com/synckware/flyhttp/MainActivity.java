package com.synckware.flyhttp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView txtView = (TextView) findViewById(R.id.txt_ip);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String SUA_URL = "https://api.ipify.org/?format=json";

                /**Instância da classe FlyHttp
                 * Passando 3 parâmetros:
                 * @Método: POST ou GET
                 * @URL: Endereço do site que deseja fazer uma requisição
                 * @Contexto: Contexto para poder fazer a requisição
                 * */

                FlyHttp flyHttp = new FlyHttp(FlyHttp.Metode.GET, SUA_URL, MainActivity.this);

                /**
                 * HashMap com os parâmetros que deseja enviar para o servidor por POST
                 * durante a requisição. No servidor recuperar a chave e o valor
                 */
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("key", "value");

                /**
                 * Setando os parâmetros no objeto que fará a requisição.
                 */
                flyHttp.setParametros(params);

                /**
                 * Fazendo a chamada de requisição do servidor:
                 * @buildJSONObject: Retornará os dados em formato JSON
                 * @buildStringbject: Retornará os dados em formato String
                 *
                 * @Descrição: VolleyCallback é uma interface que garantira o callback e o tempo de espera:
                 * Essa interface implementa 3 métodos:
                 * @onSuccessJSONObject: Resultado da requisição em formato JSONObject
                 * @onSuccessString: Resultado da requisição em formato String
                 * @onError: Chamado quando da erro durante a requisição.
                 */
                flyHttp.buildJSONObject(new VolleyCallback() {
                    @Override
                    public void onSuccessJSONObject(JSONObject result) throws JSONException {

                        Toast.makeText(getApplicationContext(), "Sucess!",
                                Toast.LENGTH_LONG).show();
                        txtView.setText(String.format("Your IP is: %s", result.getString("ip")));
                        txtView.setTextColor(Color.BLUE);
                    }

                    @Override
                    public void onSuccessString(String result) throws Exception {

                    }

                    @Override
                    public void onError(String result) throws Exception {
                        Toast.makeText(getApplicationContext(), "Error",
                                Toast.LENGTH_LONG).show();
                        txtView.setText("Error: "+result);
                        txtView.setTextColor(Color.RED);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

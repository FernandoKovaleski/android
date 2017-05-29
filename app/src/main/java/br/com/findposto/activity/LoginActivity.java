package br.com.findposto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.findposto.R;

public class LoginActivity extends BaseActivity {



    String URL = "http://192.168.43.6:8080/projeto/rest/login/verificar";


    EditText editEmail1, editSenha1;
    Button btnLogar;
    TextView textCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail1 = (EditText) findViewById(R.id.editEmail1);
        editSenha1 = (EditText) findViewById(R.id.editSenha1);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        textCadastro = (TextView) findViewById(R.id.textCadastro);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("fernando", "teste");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("fernando", response.toString());
                    }
                           // Toast.makeText(LoginActivity.this, "Logado com Successo!", Toast.LENGTH_LONG).show();
                           // startActivity(new Intent(LoginActivity.this, PostosActivity.class));
                        //} else {
                          //  Toast.makeText(LoginActivity.this, "Email ou senha incorretos!", Toast.LENGTH_LONG).show();

                        //}

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("fernando","Ocorreu algum erro -> " + volleyError);
                        Toast.makeText(LoginActivity.this, "Ocorreu algum erro -> " + volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                      protected Map<String, String> getParams() {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("email", editEmail1.getText().toString());
                        parameters.put("senha", editSenha1.getText().toString());
                        return parameters;
                    }

                };

                RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
                rQueue.add(jsonObjectRequest);
            }
        });

        textCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
            }
        });
    }
}

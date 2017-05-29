package br.com.findposto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import br.com.findposto.R;

public class CadastroActivity extends BaseActivity implements View.OnClickListener {

    private static String URL = "http://192.168.1.11:8080/projeto/rest/login/cadastrar";


    public static final String KEY_PASSWORD = "senha";
    public static final String KEY_EMAIL = "email";

    private EditText email;
    private EditText senha;
    private Button btnRegistrar, btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email = (EditText) findViewById(R.id.editEmail2);
        senha = (EditText) findViewById(R.id.editSenha2);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        btnRegistrar = (Button) findViewById(R.id.btnCadastrar);


        btnLogar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
    }

    private void registerUser() {
        final String eemail = email.getText().toString().trim();
        final String ssenha = senha.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CadastroActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CadastroActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_PASSWORD, ssenha);
                params.put(KEY_EMAIL, eemail);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == btnRegistrar) {
            registerUser();
        }
        if (v == btnLogar) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
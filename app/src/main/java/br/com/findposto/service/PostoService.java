package  br.com.findposto.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.findposto.model.Posto;
import br.com.findposto.util.HttpHelper;

public class PostoService extends BaseService {

    /*
        Obtém a lista de postos em JSON do web service e converte para List<Posto>.
     */
    public static List<Posto> getPosto(String url) throws IOException {
        HttpHelper http = new HttpHelper(); //Cria uma instância de util.HpptHelper
        http.setContentType("application/json; charset=utf-8"); //seta o Content-Type e a codificação de caracteres

        String json = http.doGet(URL_BASE + url); //obtém o objeto JSON do servidor através de um GET

        //Converte JSON para um List
        Type listType = new TypeToken<ArrayList<Posto>>() {}.getType();
        List<Posto> postos = new Gson().fromJson(json, listType);

        return postos;
    }

    public static boolean delete(String url) throws IOException {
        HttpHelper http = new HttpHelper(); //Cria uma instância de util.HpptHelper
        http.setContentType("application/json; charset=utf-8"); //seta o Content-Type e a codificação de caracteres

        Log.d(TAG, "Delete posto: " + URL_BASE + url); //um log para depurar

        // Request HTTP
        String json = http.doDelete(URL_BASE + url); // Request HTTP, REST DELETE
        Log.d(TAG, "JSON delete: " + json); //um log para depurar

        // Converção JSON em Response
        Gson gson = new Gson(); //cria uma instância de GSON
        Response response = gson.fromJson(json, Response.class); //converte JSON para Response
        if (!response.isOk()) { //testa se o servidor respondeu com 200 (ok)
            throw new IOException("Erro ao excluir o registro: " + response.getMsg()); //lança uma exceção se o retorno do servidor não for 200 (ok)
        }

        return true;
    }

    /*
        Insere um registro no web service utilizando a operação POST, e converte um JSON em rest.Response.
     */
    public static boolean post(String url, Posto posto) throws IOException {
        String jsonPosto = new Gson().toJson(posto); //parse Posto -> JSON
        Log.d(TAG, ">> savePosto: " + jsonPosto); //um log para depurar

        HttpHelper http = new HttpHelper(); //Cria uma instância de util.HpptHelper
        http.setContentType("application/json; charset=utf-8"); //seta o Content-Type e a codificação de caracteres

        // Request HTTP
        String json = http.doPost(URL_BASE + url, jsonPosto.getBytes(), "UTF-8"); // Request HTTP, REST PUT
        Log.d(TAG, "<< savePosto: " + json); //um log para depurar

        Response response = new Gson().fromJson(json, Response.class);
        if (!response.isOk()) { //testa se o servidor respondeu com 200 (ok)
            throw new IOException("Erro ao alterar o registro: " + response.getMsg()); //lança uma exceção se o retorno do servidor não for 200 (ok)
        }

        return true;
    }

    /*
        Altera um registro no web service utilizando a operação PUT, e converte um JSON em rest.Response.
     */
    public static boolean put(String url, Posto posto) throws IOException {
        String jsonPosto = new Gson().toJson(posto); //parse Posto -> JSON
        Log.d(TAG, ">> savePosto: " + jsonPosto); //um log para depurar

        HttpHelper http = new HttpHelper(); //Cria uma instância de util.HpptHelper
        http.setContentType("application/json; charset=utf-8"); //seta o Content-Type e a codificação de caracteres

        // Request HTTP
        String json = http.doPut(URL_BASE + url, jsonPosto.getBytes(), "UTF-8"); // Request HTTP, REST PUT
        Log.d(TAG, "<< savePosto: " + json); //um log para depurar

        Response response = new Gson().fromJson(json, Response.class);
        if (!response.isOk()) { //testa se o servidor respondeu com 200 (ok)
            throw new IOException("Erro ao alterar o registro: " + response.getMsg()); //lança uma exceção se o retorno do servidor não for 200 (ok)
        }

        return true;
    }
}

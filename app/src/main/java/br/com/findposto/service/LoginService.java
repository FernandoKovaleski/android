package br.com.findposto.service;

/**
 * Created by Fernando on 14/05/2017.

public class LoginService extends BaseService {


    //insere um novo usuario no banco de dados//
    public static boolean put(String url, Login login) throws IOException {
        String jsonLogin = new Gson().toJson(login); //parse Login -> JSON
        Log.d(TAG, ">> savePosto: " + jsonLogin); //um log para depurar

        HttpHelper http = new HttpHelper(); //Cria uma instância de util.HpptHelper
        http.setContentType("application/json; charset=utf-8"); //seta o Content-Type e a codificação de caracteres

        // Request HTTP
        String json = http.doPut(URL_BASE + url, jsonLogin.getBytes(), "UTF-8"); // Request HTTP, REST PUT
        Log.d(TAG, "<< saveLogin: " + json); //um log para depurar

        Response response = new Gson().fromJson(json, Response.class);
        if (!response.isOk()) { //testa se o servidor respondeu com 200 (ok)
            throw new IOException("Erro ao cadastrar o usuário: " + response.getMsg()); //lança uma exceção se o retorno do servidor não for 200 (ok)
        }

        return true;
    }

    //autenticar login//
    public static boolean post(String url, Login login) throws IOException {
        String jsonLogin = new Gson().toJson(login); //parse Login -> JSON
        Log.d(TAG, ">> saveLogin: " + jsonLogin); //um log para depurar

        HttpHelper http = new HttpHelper(); //Cria uma instância de util.HpptHelper
        http.setContentType("application/json; charset=utf-8"); //seta o Content-Type e a codificação de caracteres

        // Request HTTP
        String json = http.doPost(URL_BASE + url, jsonLogin.getBytes(), "UTF-8"); // Request HTTP, REST PUT
        Log.d(TAG, "<< saveLogin: " + json); //um log para depurar

        Response response = new Gson().fromJson(json, Response.class);
        if (!response.isOk()) { //testa se o servidor respondeu com 200 (ok)
            throw new IOException("Erro ao autenticar usuario o registro: " + response.getMsg()); //lança uma exceção se o retorno do servidor não for 200 (ok)
        }

        return true;
    }
}
**/
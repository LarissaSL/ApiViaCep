package modelos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import excecoes.ErroFormatoCep;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Endereco {

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;


    public Endereco(String cep) {
        if(cep.length() < 8) {
            throw new ErroFormatoCep("Por favor digite o CEP com 8 digitos. Ex.: 12345678");
        }

        this.cep = cep;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public Endereco buscar() {
        String endereco = "https://viacep.com.br/ws/" + this.cep + "/json/";

        HttpResponse<String> response = null;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException error) {
            System.out.println("Não foi possível encontrar o CEP informado, por favor tente novamente");
            System.out.println(error.getMessage());
        }

        if (response != null && response.statusCode() == 200) {
            Gson gson = new Gson();
            Endereco enderecoResponse = gson.fromJson(response.body(), Endereco.class);
            return enderecoResponse;
        } else {
            return null;
        }
    }

    public static void gravarListaEmJSON(List<Endereco> listaDeEnderecos) {
        try {
            FileWriter escrita = new FileWriter("ceps.json");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(listaDeEnderecos);
            escrita.write(json);
            escrita.close();

        } catch (IOException error) {
            System.out.println("Erro ao gravar no json " + error);
        }
    }

    @Override
    public String toString() {
        return "[ " + getLogradouro() + ", " + getBairro() + ", " + getCep() + " - " + getLocalidade() + " ]";
    }
}

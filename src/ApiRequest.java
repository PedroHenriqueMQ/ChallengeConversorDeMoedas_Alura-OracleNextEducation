import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ApiRequest {
    public Optional<Double> obterMoedaEquivalente(String origem, String destino) {
        String urlIncial = "https://v6.exchangerate-api.com/v6/d5eea149b25c916869f508b6/latest/";
        URI endereco = URI.create(urlIncial + origem);

        try {
            HttpRequest requisicao = HttpRequest.newBuilder()
                    .uri(endereco).build();

            HttpResponse<String> respostaDaRequisicao = HttpClient.newHttpClient()
                    .send(requisicao, HttpResponse.BodyHandlers.ofString());

            Optional <MoedaEquivamente> corpoDaRespostaParaObjeto = Optional.of(new Gson().fromJson(
                    respostaDaRequisicao.body(), MoedaEquivamente.class
            ));

            if(corpoDaRespostaParaObjeto.isEmpty())
                throw new DestinoImpossivelException(
                        "Impossível convertar da moeda " + origem +
                        " para a moeda " + destino +"."
                );
            else
                return Optional.of(corpoDaRespostaParaObjeto.get().conversionRates().get(destino));
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao estabelecer conexão!");
            return Optional.empty();
        }
    }
}

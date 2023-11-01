import com.aposta.model.Pagador;
import com.aposta.model.TipoPessoa;
import com.aposta.model.TransacaoPagamento;
import com.aposta.service.TransacaoPagamentoService;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        TransacaoPagamentoService service = new TransacaoPagamentoService();

        Pagador pagador = new Pagador(18, TipoPessoa.PESSOA_FISICA);

        String qrCode = "101md90m1d9m0d129dm09m1";
        BigDecimal valor = BigDecimal.valueOf(520.00);

        TransacaoPagamento transacao = new TransacaoPagamento(pagador, qrCode, valor);
        service.efetuarPagamento(transacao);
    }
}
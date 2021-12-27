package teste.integracao;

import conta.sistema.dominio.modelo.NegocioException;
import conta.sistema.porta.ContaRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Serviço de banco de dados - Conta")
@ContextConfiguration(classes = Config.class)
@ExtendWith(SpringExtension.class)
public class TesteContaRepositorio {

    @Inject
    private ContaRepositorio rep;

    @DisplayName("Pesquisa de conta com número nulo")
    @Test
    void teste01() {
        try {
            var conta = rep.get(null);
            assertNull(conta, "Conta deve ser nula");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula");
        }
    }

    @DisplayName("Pesquisa de conta com número inexistente")
    @Test
    void teste02() {
        try {
            var conta = rep.get(1234);
            assertNull(conta, "Conta deve ser nula");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula");
        }
    }

    @DisplayName("Pesquisa de conta existente")
    @Test
    void teste03() {
        try {
            var conta = rep.get(50);
            assertNotNull(conta, "Conta não deve ser nula");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta");
        }
    }

    @DisplayName("Altera conta como nulo")
    @Test
    void teste04() {
        try {
            rep.alterar(null);
            fail("Não deve alterar conta nula");
        } catch (NegocioException e) {
            assertEquals("Conta é obrigatória.", e.getMessage());
        }
    }

    @DisplayName("Altera conta como nulo")
    @Test
    void teste05() {
        try {
            var c1 = rep.get(50);
            c1.setSaldo(new BigDecimal("1.00"));
            c1.setCorrentista("Alterado");
            rep.alterar(c1);

            var c2 = rep.get(50);
            assertEquals(c1.getSaldo(), c2.getSaldo(), "Deve bater o saldo");
            assertEquals(c1.getCorrentista(), c2.getCorrentista(), "Deve bater o correntista");
        } catch (NegocioException e) {
            fail("Deve alterar a conta");
        }
    }

}

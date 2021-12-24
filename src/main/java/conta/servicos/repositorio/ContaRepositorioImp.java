package conta.servicos.repositorio;

import conta.sistema.dominio.modelo.Conta;
import conta.sistema.dominio.modelo.NegocioException;
import conta.sistema.porta.ContaRepositorio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.inject.Inject;
import javax.inject.Named;

import static java.util.Objects.isNull;

@Named
public class ContaRepositorioImp implements ContaRepositorio {

    private static final String ERRO = "Erro inesperado de acesso ao banco de dados. Entre em contato com o administrador";
    private JdbcTemplate jdbc;

    @Inject
    public ContaRepositorioImp(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Conta get(Integer numero) {
        if(isNull(numero))
            return null;

        var sql = "select * from conta where numero = ?";
        var pm = new Object[]{numero};
        RowMapper<Conta> orm = (rs, nm) ->
                new Conta(rs.getInt(1), rs.getBigDecimal(2), rs.getString(3));

        try {
            var lista = jdbc.query(sql, pm, orm);
            if(lista.isEmpty())
                return null;
            else
                return lista.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NegocioException(ERRO);
        }
    }

    @Override
    public void alterar(Conta conta) {

    }

}

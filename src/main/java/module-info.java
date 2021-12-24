module conta.servicos {

    // usar conta sistema
    requires conta.sistema;

    // usar spring
    requires javax.inject;
    requires spring.tx;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires java.sql;
    requires spring.jdbc;

    // abre repositorio
    opens conta.servicos.repositorio;

}
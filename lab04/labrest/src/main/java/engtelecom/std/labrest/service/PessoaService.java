package engtelecom.std.labrest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import engtelecom.std.labrest.entities.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class PessoaService {
    // criando uma lista para simular um banco de dados em memória
    private final List<Pessoa> pessoas;
    // criando um contador para gerar ids. O contador é estático para que seja compartilhado entre todas as instâncias da classe PessoaService. O contador é do tipo AtomicLong para que as operações de incremento e decremento sejam atômicas
    private static final AtomicLong contador = new AtomicLong();
    public PessoaService() {
        pessoas = new ArrayList<>();
// adicionando algumas pessoas para facilitar os testes
        this.cadastrar(new Pessoa("João", "joao@example.org"));
        this.cadastrar(new Pessoa("Maria", "maria@example.net"));
        this.cadastrar(new Pessoa("Juca", "juca@example.com"));
    }
    public Pessoa cadastrar(Pessoa pessoa) {
// gerando um id para a pessoa e ignorando o id enviado
        pessoa.setId(contador.incrementAndGet());
        pessoas.add(pessoa);
        return pessoa;
    }
    public List<Pessoa> buscarTodos() {
        return pessoas;
    }
    public Pessoa buscarPorId(Long id) {
        return this.pessoas.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }
    public Pessoa atualizar(Pessoa pessoa) {
        Pessoa p = buscarPorId(pessoa.getId());
        if (p != null) {
            p.setNome(pessoa.getNome());
            p.setEmail(pessoa.getEmail());
        }
        return p;
    }
    public boolean excluir(Long id) {
        return this.pessoas.removeIf(p -> p.getId().equals(id));
    }
}

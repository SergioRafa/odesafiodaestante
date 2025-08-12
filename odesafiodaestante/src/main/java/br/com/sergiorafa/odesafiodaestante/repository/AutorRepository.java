package br.com.sergiorafa.odesafiodaestante.repository;

import br.com.sergiorafa.odesafiodaestante.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);
    List<Autor> findByAnoNascimento(Integer ano);
    List<Autor> findByAnoNascimentoGreaterThanEqual(Integer ano);
    List<Autor> findByAnoNascimentoBetween(Integer anoInicio, Integer anoFim);
}
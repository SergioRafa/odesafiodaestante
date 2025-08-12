package br.com.sergiorafa.odesafiodaestante.repository;

import br.com.sergiorafa.odesafiodaestante.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByTituloContainingIgnoreCase(String nomeLivro);

    // Método para buscar livros por idioma.

    List<Livro> findByIdiomasContainingIgnoreCase(String idioma);
}
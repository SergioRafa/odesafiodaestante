package br.com.sergiorafa.odesafiodaestante.model;

import br.com.sergiorafa.odesafiodaestante.dto.LivroDTO;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;

    //  Campo para armazenar a lista de idiomas
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "livro_idiomas", joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "idioma")
    private List<String> idiomas;

    public Livro() {}

    public Livro(LivroDTO livroDTO) {
        this.titulo = livroDTO.titulo();
        this.autores = livroDTO.autores().stream()
                .map(a -> new Autor(a.nome(), a.anoNascimento(), a.anoMorte(), this))
                .collect(Collectors.toList());

        // Mapeamento da lista de idiomas do DTO para a entidade
        this.idiomas = livroDTO.idiomas();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    // Getter para a lista de idiomas
    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    @Override
    public String toString() {
        return "Título: " + titulo;
    }
}
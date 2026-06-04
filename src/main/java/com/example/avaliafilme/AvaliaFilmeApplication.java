package com.example.avaliafilme;

import com.example.avaliafilme.Model.FilmeModel;
import com.example.avaliafilme.Repository.FilmeRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AvaliaFilmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvaliaFilmeApplication.class, args);
    }

    @Bean
    public CommandLineRunner carregarFilmesIniciais(FilmeRepository filmeRepository) {
        return args -> {
            if (filmeRepository.count() == 0) {
                filmeRepository.saveAll(List.of(
                        FilmeModel.builder()
                                .titulo("Interestelar")
                                .genero("Ficção Científica")
                                .anoLancamento(2014)
                                .diretor("Christopher Nolan")
                                .build(),
                        FilmeModel.builder()
                                .titulo("O Poderoso Chefão")
                                .genero("Crime")
                                .anoLancamento(1972)
                                .diretor("Francis Ford Coppola")
                                .build(),
                        FilmeModel.builder()
                                .titulo("Batman: O Cavaleiro das Trevas")
                                .genero("Ação")
                                .anoLancamento(2008)
                                .diretor("Christopher Nolan")
                                .build(),
                        FilmeModel.builder()
                                .titulo("A Origem")
                                .genero("Ficção Científica")
                                .anoLancamento(2010)
                                .diretor("Christopher Nolan")
                                .build(),
                        FilmeModel.builder()
                                .titulo("Clube da Luta")
                                .genero("Drama")
                                .anoLancamento(1999)
                                .diretor("David Fincher")
                                .build(),
                        FilmeModel.builder()
                                .titulo("Forrest Gump")
                                .genero("Drama")
                                .anoLancamento(1994)
                                .diretor("Robert Zemeckis")
                                .build(),
                        FilmeModel.builder()
                                .titulo("À Procura da Felicidade")
                                .genero("Drama")
                                .anoLancamento(2006)
                                .diretor("Gabriele Muccino")
                                .build(),
                        FilmeModel.builder()
                                .titulo("Vingadores: Ultimato")
                                .genero("Super-Herói")
                                .anoLancamento(2019)
                                .diretor("Anthony e Joe Russo")
                                .build()
                ));
            }
        };
    }

}

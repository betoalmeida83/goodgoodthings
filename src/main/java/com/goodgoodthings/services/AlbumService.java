package com.goodgoodthings.services;

import com.goodgoodthings.dtos.AlbumDTO;
import com.goodgoodthings.dtos.ImagemDTO;
import com.goodgoodthings.entities.*;
import com.goodgoodthings.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private GrupoPrecificacaoRepository grupoPrecificacaoRepository;

    @Autowired
    private InativacaoRepository inativacaoRepository;

    @Autowired
    private AtivacaoRepository ativacaoRepository;


    public AlbumDTO toDTO(Album album) {
        List<ImagemDTO> imagensDTO = album.getImagens().stream()
                .map(img -> ImagemDTO.builder()
                        .id(img.getId())
                        .url(img.getUrl())
                        .tipo(img.getTipoImagem())
                        .build())
                .collect(Collectors.toList());

        List<String> generos = album.getGeneros().stream()
                .map(Genero::getNome)
                .collect(Collectors.toList());

        return AlbumDTO.builder()
                .id(album.getId())
                .albumTitulo(album.getAlbumTitulo())
                .artista(album.getArtista())
                .gravadora(album.getGravadora())
                .anoLancamento(album.getAnoLancamento())
                .descricao(album.getDescricao())
                .formato(album.getFormato())
                .preco(album.getPreco())
                .precoCusto(album.getPrecoCusto())
                .ativo(album.isAtivo())
                .estoque(album.getEstoque())
                .imagens(imagensDTO)
                .generos(generos)
                .grupoPrecificacao(album.getGrupoPrecificacao().getNome())
                .motivoInativacao(album.getMotivoInativacao() != null ? album.getMotivoInativacao().getMotivoInativacao() : null)
                .motivoAtivacao((album.getMotivoAtivacao() != null ? album.getMotivoAtivacao().getMotivoAtivacao() : null))
                .build();
    }

    public Album fromDTO(AlbumDTO dto) {
        Album album = new Album();
        album.setAlbumTitulo(dto.getAlbumTitulo());
        album.setArtista(dto.getArtista());
        album.setGravadora(dto.getGravadora());
        album.setAnoLancamento(dto.getAnoLancamento());
        album.setDescricao(dto.getDescricao());
        album.setFormato(dto.getFormato());
        album.setPreco(dto.getPreco());
        album.setEstoque(dto.getEstoque());
        album.setAtivo(dto.isAtivo());

        GrupoPrecificacao grupo = grupoPrecificacaoRepository
                .findByNome(dto.getGrupoPrecificacao())
                .orElseThrow(() -> new RuntimeException("Grupo de precificação não oncontrado!"));
        album.setGrupoPrecificacao(grupo);

        if (dto.getGeneros() != null) {
            List<Genero> generos = generoRepository.findAllByNomeIn(dto.getGeneros());
            album.setGeneros(generos);
        }

        if (dto.getImagens() != null) {
            List<Imagem> imagens = dto.getImagens().stream().map(imgDTO -> {
                Imagem img = new Imagem();
                img.setUrl(imgDTO.getUrl());
                img.setTipoImagem(imgDTO.getTipo());
                img.setAlbum(album);
                return img;
            }).toList();
            album.setImagens(imagens);
        }

        return album;
    }

    // Criar novo Album
    public AlbumDTO salvarAlbum(AlbumDTO dto) {
        Album album = fromDTO(dto);
        validarPrecoMinimo(album); // RN0013 e RN0014
        return toDTO(albumRepository.save(album));
    }

    // Buscar todos os Álbuns
    public List<AlbumDTO> buscarTodosAlbuns() {
        return albumRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar Álbum por Id
    public AlbumDTO buscarAlbumPorId(Long id) {
        return albumRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Album não encontrado!"));
    }

    // Buscar Álbuns com Filtros Combinados
    @Transactional(readOnly = true)
    public List<AlbumDTO> buscarAlbunsFiltrados(String artista, String titulo, String genero, Formato formato) {
        List<Album> resultados = albumRepository.findAll().stream()
                .filter(a -> artista == null || a.getArtista().toLowerCase().contains(artista.toLowerCase()))
                .filter(a -> titulo == null || a.getAlbumTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .filter(a -> genero == null || a.getGeneros().stream().anyMatch(g -> g.getNome().equalsIgnoreCase(genero)))
                .filter(a -> formato == null || a.getFormato() == formato)
                .toList();

        return resultados.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Atualizar Album
    public AlbumDTO atualizarAlbum(Long id, AlbumDTO albumDTO) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado!"));

        album.setAlbumTitulo(albumDTO.getAlbumTitulo());
        album.setArtista(albumDTO.getArtista());
        album.setGravadora(albumDTO.getGravadora());
        album.setAnoLancamento(albumDTO.getAnoLancamento());
        album.setDescricao(albumDTO.getDescricao());
        album.setFormato(albumDTO.getFormato());
        album.setPreco(albumDTO.getPreco());
        album.setPrecoCusto(albumDTO.getPrecoCusto());
        album.setEstoque(albumDTO.getEstoque());
        album.setAtivo(albumDTO.isAtivo());

        GrupoPrecificacao grupo = grupoPrecificacaoRepository
                .findByNome(albumDTO.getGrupoPrecificacao())
                .orElseThrow(() -> new RuntimeException("Grupo de precificação não encontrado"));
        album.setGrupoPrecificacao(grupo);

        validarPrecoMinimo(album);

        if (albumDTO.getGeneros() != null) {
            List<Genero> generos = generoRepository.findAllByNomeIn(albumDTO.getGeneros());
            album.setGeneros(generos);
        }

        if (albumDTO.getImagens() != null) {
            album.getImagens().clear();
            List<Imagem> novasImagens = albumDTO.getImagens().stream().map(imgDTO -> {
                Imagem img = new Imagem();
                img.setUrl(imgDTO.getUrl());
                img.setTipoImagem(imgDTO.getTipo());
                img.setAlbum(album);
                return img;
            }).toList();

            album.getImagens().addAll(novasImagens);
        }

        return toDTO(albumRepository.save(album));
    }

    // Deletar Album
    public boolean deletarAlbum(Long id) {
        if (!albumRepository.existsById(id)) {
            return false;
        }
        albumRepository.deleteById(id);
        return true;
    }

    // Ativação e Inativação manual (RF0012, RF0016 e RN0015, RN0017)
    public AlbumDTO inativarAlbum(Long id, String motivoDescricao) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado"));

        Inativacao motivo = inativacaoRepository
                .findByMotivoInativacao(motivoDescricao)
                .orElseThrow(() -> new RuntimeException("Motivo de inativação não encontrado"));

        album.setAtivo(false);
        album.setMotivoInativacao(motivo);
        return toDTO(albumRepository.save(album));
    }

    public AlbumDTO ativarAlbum(Long id, String motivoDescricao) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado"));

        Ativacao motivo = ativacaoRepository
                .findByMotivoAtivacao(motivoDescricao)
                .orElseThrow(() -> new RuntimeException("Motivo de ativação não encontrado"));

        album.setAtivo(true);
        album.setMotivoAtivacao(motivo);
        return toDTO(albumRepository.save(album));
    }


    // Inativação automática (RF0013 e RN0016)
    public void inativacaoAutomatica(double precoMinimo) {
        Inativacao foraDeMercado = inativacaoRepository
                .findByMotivoInativacao("FORA DO MERCADO!")
                .orElseThrow(() -> new RuntimeException("Motivo FORA DE MERCADO não cadastrado!"));

        albumRepository.findAll().stream()
                .filter(a -> a.getEstoque() == 0.00 && a.getPreco() < precoMinimo && a.isAtivo())
                .forEach(album -> {
                    album.setAtivo(false);
                    album.setMotivoInativacao(foraDeMercado);
                    albumRepository.save(album);
                });
    }

    // Validação de preço (RN0013 e RN0014)
    private void validarPrecoMinimo(Album album) {
        if (album.getGrupoPrecificacao() == null) {
            throw new IllegalStateException("Álbum deve estar associado a um grupo de precificação.");
        }
        if (album.getPrecoCusto() == null) {
            throw new IllegalStateException("Preço de custo (precoCusto) é obrigatório para validar margem mínima.");
        }
        if (album.getPreco() == null) {
            throw new IllegalStateException("Preço de venda (preco) é obrigatório.");
        }

        double margemMinimaPercent = album.getGrupoPrecificacao().getMargemMinimaLucro(); // ex.: 20 = 20%
        double precoMinimo = album.getPrecoCusto() * (1 + margemMinimaPercent / 100.0);

        if (album.getPreco() < precoMinimo) {
            throw new IllegalArgumentException(
                    String.format("Preço inválido. Preço mínimo para o grupo é %.2f (margem mínima %.2f%%).",
                            precoMinimo, margemMinimaPercent)
            );
        }
    }

    public double calcularPrecoFinal(Album album, double margemDesejada) {
        double margemMinima = album.getGrupoPrecificacao().getMargemMinimaLucro();
        if (margemDesejada < margemMinima) {
            throw new IllegalArgumentException(
                    String.format("A margem informada (%.2f%%) é menor que a mínima exigida (%.2f%%).",
                            margemDesejada, margemMinima)
            );
        }
        return album.getPrecoCusto() * (1 + margemDesejada / 100.0);
    }

}





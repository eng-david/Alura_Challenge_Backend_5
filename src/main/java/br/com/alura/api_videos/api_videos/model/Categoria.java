package br.com.alura.api_videos.api_videos.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String cor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<Video> videos;
}

package ap1.bigdata.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank(message = "Rua não pode ser vazia")
    @Size(min = 3, max = 255, message = "Rua deve ter entre 3 e 255 caracteres")
    private String rua;

    @Column
    @NotBlank(message = "Número não pode ser vazio")
    private String numero;

    @Column
    @NotBlank(message = "Bairro não pode ser vazio")
    @Size(min = 3, max = 100, message = "Bairro deve ter entre 3 e 100 caracteres")
    private String bairro;

    @Column
    @NotBlank(message = "Cidade não pode ser vazia")
    @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
    private String cidade;

    @Column
    @NotBlank(message = "Estado não pode ser vazio")
    @Pattern(regexp = "SP|RJ|MG|...", message = "Estado inválido")
    private String estado;

    @Column
    @NotBlank(message = "CEP não pode ser vazio")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "Formato de CEP inválido")
    private String cep;

}

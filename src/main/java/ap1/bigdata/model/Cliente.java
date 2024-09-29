package ap1.bigdata.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank(message = "Campo nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Column
    @NotBlank(message = "Campo cpf não pode ser vazio")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato de CPF inválido")
    @Column(unique = true)
    private String cpf;

    @Column
    @NotBlank(message = "Campo e-mail não pode ser vazio")
    @Email(message = "Formato de email inválido")
    @Column(unique = true)
    private String email; 

    @Column
    private LocalDateTime DataNasc;

    @Column
    @Pattern(
        regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", 
        message = "O telefone deve estar no formato (XX) XXXXX-XXXX."
    )
    private String telefone;

    @OneToMany
    @JoinColumn(referencedColumnName = "id", name = "cliente_id")
    private List<Endereco> enderecos;

    public void associarEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
    }
}

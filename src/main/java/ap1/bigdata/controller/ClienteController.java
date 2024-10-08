package ap1.bigdata.controller;
import ap1.bigdata.model.Cliente;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ap1.bigdata.model.Cliente;
import ap1.bigdata.repository.ClienteRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteRepository clienteRepository;

    // Busca todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        return new ResponseEntity<>(clienteRepository.findAll(), HttpStatus.OK);
    }

    // Busca um cliente por ID
    @GetMapping("{id}")
    public ResponseEntity<Cliente> getById(@PathVariable("id") int id) {
        return this.clienteRepository.findById(id)
                                     .map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK))
                                     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Cria um novo cliente
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody Cliente cliente) {
        // Verifica se o CPF já está cadastrado
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            return new ResponseEntity<>("CPF já cadastrado", HttpStatus.BAD_REQUEST);
        }

        // Verifica se o e-mail já está cadastrado
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            return new ResponseEntity<>("E-mail já cadastrado", HttpStatus.BAD_REQUEST);
        }

        // Salva o cliente se não houver duplicidade
        this.clienteRepository.save(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    // Atualiza um cliente existente
    @PutMapping("{id}")
    public ResponseEntity<Cliente> update(@PathVariable("id") int id, @Valid @RequestBody Cliente clienteAtualizado) {
        return this.clienteRepository.findById(id)
                                     .map(cliente -> {
                                         cliente.setNome(clienteAtualizado.getNome());
                                         cliente.setCpf(clienteAtualizado.getCpf());
                                         cliente.setEmail(clienteAtualizado.getEmail());
                                         cliente.setDataNasc(clienteAtualizado.getDataNasc());
                                         cliente.setTelefone(clienteAtualizado.getTelefone());
                                         clienteRepository.save(cliente);
                                         return new ResponseEntity<>(cliente, HttpStatus.OK);
                                     })
                                     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deleta um cliente por ID
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Optional<Cliente> clienteOpt = this.clienteRepository.findById(id);

        if (clienteOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.clienteRepository.delete(clienteOpt.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

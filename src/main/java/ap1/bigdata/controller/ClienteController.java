package ap1.bigdata.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private ClienteRepository postRepository;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Cliente> getById(@PathVariable("id") int id) {
        /*Optional<Post> postOptional = this.postRepository.findById(id);

        if (postOptional.isPresent() == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(postOptional.get(), HttpStatus.NOT_FOUND);*/

        return this.postRepository.findById(id)
                                  .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                                  .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente) {
        this.postRepository.save(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        
        Optional<Cliente> optPost = this.postRepository.findById(id);

        if (optPost.isPresent() == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        this.postRepository.delete(optPost.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}
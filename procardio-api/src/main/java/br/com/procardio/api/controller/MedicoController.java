package br.com.procardio.api.controller;//aula 3

import br.com.procardio.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/medicos")
class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id) {
        medicoService.deletarMedico(id);

        return ResponseEntity.noContent().build();
    }

}

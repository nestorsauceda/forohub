package com.nestoraluraoracleone.forohub.controller;

import com.nestoraluraoracleone.forohub.dto.ActualizarTopicoDTO;
import com.nestoraluraoracleone.forohub.dto.ListarTopicoDTO;
import com.nestoraluraoracleone.forohub.dto.RegistrarTopicoDTO;
import com.nestoraluraoracleone.forohub.model.Topico;
import com.nestoraluraoracleone.forohub.service.ActualizarTopicoService;
import com.nestoraluraoracleone.forohub.service.EliminarTopicoService;
import com.nestoraluraoracleone.forohub.service.ListarTopicoService;
import com.nestoraluraoracleone.forohub.service.RegistrarTopicoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private RegistrarTopicoService topicoService;

    @Autowired
    private ListarTopicoService listarTopicoService;

    @Autowired
    private ActualizarTopicoService actualizarTopicoService;

    @Autowired
    private EliminarTopicoService eliminarTopicoService;

    @PostMapping
    public ResponseEntity<?> crearTopico(@RequestBody @Valid RegistrarTopicoDTO topicoDTO) {
        try {
            Topico nuevoTopico = topicoService.registrarTopico(topicoDTO);
            String response = String.format(
                "{ \"titulo\": \"%s\", \"mensaje\": \"%s\", \"fechaCreacion\": \"%s\" }",
                nuevoTopico.getTitulo(),
                nuevoTopico.getMensaje(),
                nuevoTopico.getFechaCreacion().format(DateTimeFormatter.ISO_DATE_TIME)
            );
            return ResponseEntity.status(201).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("{ \"error\": \"" + e.getMessage() + "\" }");
        }
    }

    @GetMapping
    public ResponseEntity<List<ListarTopicoDTO>> listarTopicos() {
        List<ListarTopicoDTO> topicos = listarTopicoService.listarTopicos();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListarTopicoDTO> obtenerDetalle(@PathVariable Long id) {
        ListarTopicoDTO detalle = listarTopicoService.obtenerPorId(id);
        return ResponseEntity.ok(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid ActualizarTopicoDTO topicoDTO
    ) {
        actualizarTopicoService.actualizarTopico(id, topicoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        eliminarTopicoService.eliminarPorId(id);
        return ResponseEntity.noContent().build(); // Código 204 No Content
    }
}
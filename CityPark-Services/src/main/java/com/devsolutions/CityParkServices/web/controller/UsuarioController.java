package com.devsolutions.CityParkServices.web.controller;

import com.devsolutions.CityParkServices.entity.Usuario;
import com.devsolutions.CityParkServices.service.UsuarioService;
import com.devsolutions.CityParkServices.web.dto.UsuarioCreateDto;
import com.devsolutions.CityParkServices.web.dto.UsuarioResponseDto;
import com.devsolutions.CityParkServices.web.dto.UsuarioSenhaDto;
import com.devsolutions.CityParkServices.web.dto.mapper.UsuarioMapper;
import com.devsolutions.CityParkServices.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Inclui todos os parâmetros para cadastro, edição e leitura de um usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
        responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

        })

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto ) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Recuperar um usuário pelo id", description = "Requisição exige um Bearer Token. Acesso retrito a ADMIN|CLIENTE",
            security = @SecurityRequirement(name = "security"),
         responses = {
             @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
             @ApiResponse(responseCode = "403", description = "Usúario sem permissão para acessar este recurso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
             @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

         })

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENTE') AND #id == authentication.principal.id)")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));

    }

    @Operation(summary = "Atualizar senha", description = "Requisição exige um Bearer Token. Acesso retrito a ADMIN|CLIENTE",
            security = @SecurityRequirement(name = "security"),
         responses = {
             @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
             @ApiResponse(responseCode = "400", description = "Senha não confere",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
             @ApiResponse(responseCode = "403", description = "Usúario sem permissão para acessar este recurso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
             @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatados",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

         })

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "listar todos os usuários cadastrado", description = "Requisição exige um Bearer Token. Acesso retrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Usúario sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDto>> getALL() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));

    }

}


















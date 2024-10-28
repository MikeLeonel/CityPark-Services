package com.devsolutions.CityParkServices;

import com.devsolutions.CityParkServices.web.dto.EstacionamentoCreateDto;
import com.devsolutions.CityParkServices.web.dto.PageableDto;
import com.devsolutions.CityParkServices.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EstacionamentoIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarCheckin_ComDadosValidos_RetornarCreatedAndLocation() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-2222").marca("RENOUT").modelo("CLIO 1.0")
                .cor("BRANCO").clienteCpf("94392380033")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "noobmaster@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("placa").isEqualTo("ABC-2222")
                .jsonPath("marca").isEqualTo("RENOUT")
                .jsonPath("modelo").isEqualTo("CLIO 1.0")
                .jsonPath("cor").isEqualTo("BRANCO")
                .jsonPath("clienteCpf").isEqualTo("94392380033")
                .jsonPath("recibo").exists()
                .jsonPath("dataEntrada").exists()
                .jsonPath("vagaCodigo").exists();


    }

    @Test
    public void criarCheckin_ComRoleCliente_RetornarErrorStatus403() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-2222").marca("RENOUT").modelo("CLIO 1.0")
                .cor("BRANCO").clienteCpf("94392380033")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "starmaster@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");



    }

    @Test
    public void criarCheckin_ComDadosInvalidos_RetornarErrorStatus422() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("").marca("").modelo("")
                .cor("").clienteCpf("")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "starmaster@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");



    }

    @Test
    public void criarCheckin_ComCpfInexistente_RetornarErrorStatus404() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-2222").marca("RENOUT").modelo("CLIO 1.0")
                .cor("BRANCO").clienteCpf("89511085034")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "noobmaster@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");



    }

    @Sql(scripts = "/sql/estacionamentos/estacionamentos-insert-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/estacionamentos/estacionamentos-delete-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void criarCheckin_ComVagasOcupadas_RetornarErrorStatus404() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-2222").marca("RENOUT").modelo("CLIO 1.0")
                .cor("BRANCO").clienteCpf("94392380033")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "noobmaster@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");



    }

    @Test
    public void buscarCheckin_ComPerfilAdmin_RetornarDadosStatus200() {

        testClient.get()
                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20240815-185217")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "noobmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("ABC-0001")
                .jsonPath("marca").isEqualTo("RENOUT")
                .jsonPath("modelo").isEqualTo("SANDERO")
                .jsonPath("cor").isEqualTo("AZUL")
                .jsonPath("clienteCpf").isEqualTo("94392380033")
                .jsonPath("recibo").isEqualTo("20240815-185217")
                .jsonPath("dataEntrada").isEqualTo("2024-08-15 06:52:17")
                .jsonPath("vagaCodigo").isEqualTo("A-01");



    }

    @Test
    public void buscarCheckin_ComReciboInexistente_RetornarErrorStatus404() {

        testClient.get()
                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20240815-666666")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "starmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in/20240815-666666")
                .jsonPath("method").isEqualTo("GET");



    }
    @Test
    public void criarCheckOut_ComReciboExistente_RetornarSucesso() {

        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20240815-185217")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "noobmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("ABC-0001")
                .jsonPath("marca").isEqualTo("RENOUT")
                .jsonPath("modelo").isEqualTo("SANDERO")
                .jsonPath("cor").isEqualTo("AZUL")
                .jsonPath("dataEntrada").isEqualTo("2024-08-15 06:52:17")
                .jsonPath("clienteCpf").isEqualTo("94392380033")
                .jsonPath("vagaCodigo").isEqualTo("A-01")
                .jsonPath("recibo").isEqualTo("20240815-185217")
                .jsonPath("dataSaida").exists()
                .jsonPath("valor").exists()
                .jsonPath("desconto").exists();




    }

    @Test
    public void criarCheckOut_ComReciboInexistente_RetornarErrorStatus404() {

        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20240815-111111")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "noobmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20240815-111111")
                .jsonPath("method").isEqualTo("PUT");


    }
    @Test
    public void criarCheckOut_ComRoleCliente_RetornarErrorStatus403() {

        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20240815-185217")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "starmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20240815-185217")
                .jsonPath("method").isEqualTo("PUT");


    }
    @Test
    public void buscarEstacionamentos_PorClienteCpf_RetornarSucesso() {

        PageableDto responseBody = testClient.get()
                .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=0", "59644826000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "noobmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

        responseBody = testClient.get()
                .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=1", "59644826000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "noobmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);



    }

    @Test
    public void buscarEstacionamentos_PorClienteCpfComPerfilCliente_RetornarErrorStatus403() {

        testClient.get()
                .uri("/api/v1/estacionamentos/cpf/{cpf}", "59644826000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "starmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/cpf/59644826000")
                .jsonPath("method").isEqualTo("GET");


    }

    @Test
    public void buscarEstacionamentos_DoClienteLogado_RetornarSucesso() {

        PageableDto responseBody = testClient.get()
                .uri("/api/v1/estacionamentos?size=1&page=0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "leonelmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

        responseBody = testClient.get()
                .uri("/api/v1/estacionamentos?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "leonelmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);



    }
    @Test
    public void buscarEstacionamentos_DeClienteLogadoComPerfilAdmin_RetornarErrorStatus403() {

        testClient.get()
                .uri("/api/v1/estacionamentos")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "noobmaster@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos")
                .jsonPath("method").isEqualTo("GET");


    }


}

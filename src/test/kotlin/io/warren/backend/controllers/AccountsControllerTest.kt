package io.warren.backend.controllers

import io.warren.backend.converters.AccountConverter
import io.warren.backend.fixtures.AccountFixture
import io.warren.backend.fixtures.AccountRequestFixture
import io.warren.backend.repositories.AccountRepository
import io.warren.backend.services.AccountsService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.anyObject
import org.mockito.Mockito.times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [AccountsController::class])
@RunWith(SpringRunner::class)
@Import(AccountsService::class, AccountConverter::class)
class AccountsControllerTest {

    @MockBean
    val accountsRepository: AccountRepository? = null

    @Autowired
    val webClient: WebTestClient? = null

    @Test
    fun createAccount() {
        val accountRequest = AccountRequestFixture()
            .withUsername("teste")
            .build()
        val accountReturnDB = AccountFixture()
            .withCustomerName(accountRequest.customerName!!)
            .withUsername(accountRequest.username!!)
            .build()

        Mockito.`when`(accountsRepository!!.findAllByUsername(Mockito.anyString())).thenReturn(Flux.just())
        Mockito.`when`(accountsRepository!!.save(Mockito.anyObject())).thenReturn(Mono.just(accountReturnDB))

        webClient!!.post()
            .uri("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .header("transactionId", "312321321")
            .body(BodyInserters.fromObject(accountRequest))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .jsonPath("username")
            .isEqualTo("teste")

        Mockito.verify(accountsRepository, times(1)).save(anyObject())
    }

    @Test
    fun createAccountWithError() {
        val accountNoCustomerNameRequest = AccountRequestFixture()
            .withoutCustomerName()
            .build()

        val accountNoUsernameRequest = AccountRequestFixture()
            .withoutUsername()
            .build()

        val accountNoTypeRequest = AccountRequestFixture()
            .withoutType()
            .build()

        webClient!!.post()
            .uri("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .header("transactionId", "312321321")
            .body(BodyInserters.fromObject(accountNoCustomerNameRequest))
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody()

        webClient!!.post()
            .uri("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .header("transactionId", "312321321")
            .body(BodyInserters.fromObject(accountNoUsernameRequest))
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody()

        webClient!!.post()
            .uri("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .header("transactionId", "312321321")
            .body(BodyInserters.fromObject(accountNoTypeRequest))
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody()
    }

    @Test
    fun createAccountAlreadyExisted() {
        val accountRequest = AccountRequestFixture()
            .withUsername("teste")
            .build()
        val accountReturnDB = AccountFixture()
            .withCustomerName(accountRequest.customerName!!)
            .withUsername(accountRequest.username!!)
            .build()

        Mockito.`when`(accountsRepository!!.findAllByUsername(Mockito.anyString())).thenReturn(Flux.just(accountReturnDB))

        webClient!!.post()
            .uri("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .header("transactionId", "312321321")
            .body(BodyInserters.fromObject(accountRequest))
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody()
            .jsonPath("message")
            .isEqualTo("Já existe uma conta com esse username")
    }
}
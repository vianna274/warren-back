package io.warren.backend.repositories

import io.warren.backend.fixtures.AccountFixture
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountRepositoryTest {
    @Autowired
    val accountRepository: AccountRepository? = null

    @Test
    fun saveAccount() {
        val newAccount = AccountFixture().build()
        val accountDB = accountRepository!!.save(newAccount).block()

        assert(accountDB?.id == newAccount.id)

        accountRepository!!.deleteById(newAccount.id.toString()).block()
    }

    @Test
    fun updateAccount() {
        val newAccount = AccountFixture()
            .withAvailableBalance(BigDecimal.ZERO)
            .build()
        val accountDB = accountRepository!!.save(newAccount).block()

        assert(accountDB?.availableBalance == BigDecimal(0.0))

        val updatedAccount = AccountFixture()
            .withId(newAccount.id)
            .withAvailableBalance(BigDecimal(10.0))
            .build()

        val updatedAccountDB = accountRepository!!.save(updatedAccount).block()

        val findAccount = accountRepository!!.findById(newAccount.id.toString()).block()

        assert(findAccount?.availableBalance == BigDecimal(10.0))

        accountRepository!!.deleteById(newAccount.id.toString()).block()
    }
}
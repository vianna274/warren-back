package io.warren.backend.repositories

import io.warren.backend.fixtures.AccountFixture
import io.warren.backend.fixtures.TransactionFixture
import org.bson.types.ObjectId
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionRepositoryTest {
    @Autowired
    val transactionsRepository: TransactionsRepository? = null

    @Test
    fun saveTransaction() {
        val newTransaction = TransactionFixture().build()
        val transactionDB = transactionsRepository!!.save(newTransaction).block()

        assert(transactionDB?.id == newTransaction.id)

        transactionsRepository!!.deleteById(newTransaction.id.toString()).block()
    }

    @Test
    fun updateAccount() {
        val randomId = ObjectId.get()
        val newTransaction = TransactionFixture()
            .withDestinationAccountId(randomId)
            .build()

        val newTransaction2 = TransactionFixture()
            .withSourceAccountId(randomId)
            .build()

        transactionsRepository!!.save(newTransaction).block()
        transactionsRepository!!.save(newTransaction2).block()
        var transactions = transactionsRepository!!.findAllBySourceAccountIdOrDestinationAccountId(randomId, randomId)
            .collectList()
            .block()

        assert(transactions!!.size == 2)

        transactionsRepository!!.deleteById(newTransaction.id.toString()).block()
        transactionsRepository!!.deleteById(newTransaction2.id.toString()).block()
    }
}
package com.theagilemonkeys.notes.api

import com.horizen.box.Box
import com.horizen.box.ZenBox
import com.horizen.box.data.ZenBoxData
import com.horizen.node.NodeMemoryPool
import com.horizen.node.SidechainNodeView
import com.horizen.proof.Signature25519
import com.horizen.proposition.Proposition
import com.horizen.proposition.PublicKey25519Proposition
import com.horizen.transaction.AbstractRegularTransaction
import com.horizen.transaction.BoxTransaction
import java.util.*
import kotlin.math.abs

private fun boxesFromMemoryPool(memoryPool: NodeMemoryPool): List<ByteArray> = memoryPool.transactions
    .map { transaction: BoxTransaction<Proposition?, Box<Proposition?>?> ->
        transaction.boxIdsToOpen().map { it.data() }
    }.flatten()

@Throws(IllegalStateException::class)
fun getTransactionFundingBoxes(view: SidechainNodeView, payment: Long): TransactionBoxes {
    val paymentBoxes = mutableListOf<Box<Proposition>>()
    val boxIdsToExclude = boxesFromMemoryPool(view.nodeMemoryPool)
    var amountToPay = payment

    run boxSelection@{
        view.nodeWallet.boxesOfType(ZenBox::class.java, boxIdsToExclude)
            .forEach { box ->
                if (amountToPay <= 0) return@boxSelection
                paymentBoxes += box
                amountToPay -= box.value()
            }
    }

    check(amountToPay <= 0) { "Not enough coins to pay the fee." }

    val change = abs(amountToPay)
    val regularOutputs: MutableList<ZenBoxData> = ArrayList()
    if (change > 0) {
        regularOutputs.add(ZenBoxData(paymentBoxes.first().proposition() as PublicKey25519Proposition, change))
    }

    return TransactionBoxes(paymentBoxes, regularOutputs)
}

fun <T : AbstractRegularTransaction> SidechainNodeView.createSignedTransaction(
    fundingBoxes: List<Box<Proposition>>,
    transactionCreation: (List<Signature25519>) -> T
): T {
    val messageToSign = transactionCreation(Collections.nCopies<Signature25519>(fundingBoxes.size, null)).messageToSign()

    return transactionCreation(
        fundingBoxes
            .map { box ->
                nodeWallet.secretByPublicKey(box.proposition()).get()
                    .sign(messageToSign) as Signature25519
            }
    )
}

class TransactionBoxes(val fundingInputs: List<Box<Proposition>>, val changeOutput: List<ZenBoxData>)
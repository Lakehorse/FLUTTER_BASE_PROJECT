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
    val paymentBoxes = mutableListOf<Box<Proposition>>
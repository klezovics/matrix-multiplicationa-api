package com.klezovich

import org.apache.commons.math3.linear.MatrixUtils
import javax.enterprise.context.ApplicationScoped

fun main() {
    val mData = kotlin.arrayOf<DoubleArray>(doubleArrayOf(1.0, 2.0), doubleArrayOf(3.0, 4.0))
    val rMatrix = MatrixUtils.createRealMatrix(mData)
    print(rMatrix)
}

@ApplicationScoped
class MatrixMultiplicationService {

    fun multiply(m1: Array<DoubleArray>, m2: Array<DoubleArray>): Array<DoubleArray> {
        val rm1 = MatrixUtils.createRealMatrix(m1)
        val rm2 = MatrixUtils.createRealMatrix(m2)

        val result = rm1.multiply(rm2)
        return result.data
    }
}

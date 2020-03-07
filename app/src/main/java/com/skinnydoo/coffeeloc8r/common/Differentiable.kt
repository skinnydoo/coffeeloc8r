package com.skinnydoo.coffeeloc8r.common

interface Differentiable {

    /**
     * Returns true if this and other has same category. Default implementation returns equals.
     * data class should override this.
     */
    fun isTheSame(other: Differentiable): Boolean = equals(other)

    /**
     * Returns true if this equals to other. Default implementation returns equals
     */
    fun areContentsTheSame(other: Differentiable): Boolean = equals(other)
}

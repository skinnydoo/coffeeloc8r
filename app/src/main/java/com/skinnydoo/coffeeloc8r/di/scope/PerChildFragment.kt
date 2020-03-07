package com.skinnydoo.coffeeloc8r.di.scope

import javax.inject.Scope

/**
 * Specifies that the lifespan of a dependency to be the same as that of a
 * child fragment
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class PerChildFragment

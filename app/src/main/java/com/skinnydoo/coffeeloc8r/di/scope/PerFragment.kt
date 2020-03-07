package com.skinnydoo.coffeeloc8r.di.scope

import javax.inject.Scope

/**
 * Specifies the lifespan of a dependency be the same as that of an Activity
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class PerFragment

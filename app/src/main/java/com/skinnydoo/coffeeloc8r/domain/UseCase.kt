package com.skinnydoo.coffeeloc8r.domain

import com.skinnydoo.coffeeloc8r.utils.network.Result

/**
 * [Req] input data passed to a request.
 * [Res] result received from a request.
 */
interface UseCase<Req, Res : Any> {
    suspend operator fun invoke(req: Req): Result<Res>
}

package com.pryjda.app.model.request

import com.pryjda.app.model.request.validation.validators.PayloadChecking

data class ModelRequestDTO(
        @field: PayloadChecking(message = "Wrong payload")
        val payload: List<InternalModelRequestDTO>
)
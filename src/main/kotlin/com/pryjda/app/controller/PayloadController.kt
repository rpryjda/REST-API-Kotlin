package com.pryjda.app.controller

import com.pryjda.app.model.request.FormatTypes
import com.pryjda.app.model.request.InternalModelRequestDTO
import com.pryjda.app.model.request.ModelRequestDTO
import com.pryjda.app.model.request.validation.order.CreateUserSequence
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController("/payload")
class PayloadController {

    @PostMapping
    fun createModel(@Valid @RequestBody model: ModelRequestDTO): ModelRequestDTO {

        println(model)
        println(model.payload.size)

        return model
    }

    @GetMapping
    fun getSampleModel() = ModelRequestDTO(
            listOf(
                    InternalModelRequestDTO(
                            "SAMPLE title",
                            "SAMPLE text",
                            FormatTypes.FIFE
                    )
            )
    )
}
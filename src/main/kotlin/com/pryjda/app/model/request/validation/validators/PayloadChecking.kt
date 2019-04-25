package com.pryjda.app.model.request.validation.validators

import com.pryjda.app.model.request.InternalModelRequestDTO
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Constraint(validatedBy = [PayloadCheckingValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class PayloadChecking(
        val message: String = "",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<*>> = []
)

class PayloadCheckingValidator
    : ConstraintValidator<PayloadChecking, List<InternalModelRequestDTO>> {

    override fun isValid(payload: List<InternalModelRequestDTO>, context: ConstraintValidatorContext?): Boolean {

        if(payload.size > 3) return false
        payload.forEach {
            if(it.text.length > 12) return false
            else if(it.title.length > 12) return false
        }
        return true
    }
}



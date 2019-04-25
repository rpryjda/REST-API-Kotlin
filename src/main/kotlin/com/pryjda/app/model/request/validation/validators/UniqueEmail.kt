package com.pryjda.app.model.request.validation.validators

import com.pryjda.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Constraint(validatedBy = [UniqueEmailValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class UniqueEmail(
        val message: String = "Email must be unique.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<*>> = []
)

class UniqueEmailValidator(@Autowired val userRepository: UserRepository)
    : ConstraintValidator<UniqueEmail, String> {

    override fun isValid(email: String, constraintValidatorContext: ConstraintValidatorContext?): Boolean {
        val isPresent = userRepository
                .findUserByEmail(email)
                .isPresent
        return !isPresent
    }
}



package com.pryjda.app.model.request.validation.validators

import com.pryjda.app.model.request.enum_field.AcademicYear
import com.pryjda.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Constraint(validatedBy = [EnumValuesValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnumValues(
        val message: String = "Wrong enum value.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<*>> = []
)

class EnumValuesValidator(@Autowired val userRepository: UserRepository)
    : ConstraintValidator<EnumValues, AcademicYear> {

    override fun isValid(academicYear: AcademicYear, constraintValidatorContext: ConstraintValidatorContext?): Boolean =
//            AcademicYear.values().contains(AcademicYear.valueOf(academicYear))
            AcademicYear.values().contains(academicYear)
}



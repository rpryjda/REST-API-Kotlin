package com.pryjda.app.model.request.validation.validators

import com.pryjda.app.model.request.enum_field.AcademicYear
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Constraint(validatedBy = [EnumValues2Validator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnumValues2(
        val message: String = "Wrong enum value.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<*>> = []
)

class EnumValues2Validator
    : ConstraintValidator<EnumValues2, AcademicYear> {

    override fun isValid(academicYear: AcademicYear, constraintValidatorContext: ConstraintValidatorContext?): Boolean =
//            AcademicYear.values().contains(AcademicYear.valueOf(academicYear))
            AcademicYear.values().contains(academicYear)
}



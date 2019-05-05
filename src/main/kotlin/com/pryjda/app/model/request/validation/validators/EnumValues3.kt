package com.pryjda.app.model.request.validation.validators

import com.pryjda.app.exception.ValidationException
import com.pryjda.app.model.request.enum_field.AcademicYear
import com.pryjda.app.model.request.enum_field.CourseOfStudy
import org.springframework.web.bind.MethodArgumentNotValidException
import java.util.*
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Constraint(validatedBy = [EnumValues3Validator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnumValues3(
        val message: String = "Wrong enum value.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<*>> = []
)

class EnumValues3Validator
    : ConstraintValidator<EnumValues3, String> {

    override fun isValid(courseOfStudy: String, constraintValidatorContext: ConstraintValidatorContext?): Boolean
//            AcademicYear.values().contains(AcademicYear.valueOf(academicYear))
    {
        val contains = CourseOfStudy.values()
                .map {
                    it.name
                }
                .contains(courseOfStudy)
        if (!contains)
            throw ValidationException("Choose values from: ${CourseOfStudy.values().joinToString ()}")


        return true
    }

}



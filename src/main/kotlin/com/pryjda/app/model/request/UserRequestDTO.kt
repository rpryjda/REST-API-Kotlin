package com.pryjda.app.model.request

import com.pryjda.app.model.request.enum_field.AcademicYear
import com.pryjda.app.model.request.validation.order.CreateUserStep_no1
import com.pryjda.app.model.request.validation.order.CreateUserStep_no2
import com.pryjda.app.model.request.validation.order.CreateUserStep_no3
import com.pryjda.app.model.request.validation.validators.EnumValues2
import com.pryjda.app.model.request.validation.validators.EnumValues3
import com.pryjda.app.model.request.validation.validators.UniqueEmail
import javax.validation.constraints.*

data class UserRequestDTO(
        @field: NotBlank(message = "Name is required", groups = arrayOf(CreateUserStep_no1::class))
        @field: Size(min = 2, max = 30, message = "Name must have at least two characters", groups = arrayOf(CreateUserStep_no2::class))
        val name: String?,

        @field: NotBlank(message = "Surname is required", groups = arrayOf(CreateUserStep_no1::class))
        @field: Size(min = 2, max = 30, message = "Surname must have at least two characters", groups = arrayOf(CreateUserStep_no2::class))
        val surname: String?,

        @field: NotBlank(message = "E-mail is required", groups = arrayOf(CreateUserStep_no1::class))
        @field: Email(message = "Incorrect e-mail address", groups = arrayOf(CreateUserStep_no2::class))
        @field: UniqueEmail(groups = arrayOf(CreateUserStep_no3::class))
        val email: String?,

        @field: NotBlank(message = "Password is required", groups = arrayOf(CreateUserStep_no1::class))
        @field: Size(min = 3, max = 16, message = "Password must have between 3 and 16 characters", groups = arrayOf(CreateUserStep_no2::class))
        val password: String?,

        @field: NotNull(message = "Index number is required", groups = arrayOf(CreateUserStep_no1::class))
        @field: Positive(message = "Index number must be positive number", groups = arrayOf(CreateUserStep_no2::class))
        val indexNumber: Int?,

//        @field:EnumValues(groups = arrayOf(CreateUserStep_no1::class))
        @field:EnumValues2(groups = arrayOf(CreateUserStep_no1::class))
        val academicYear: AcademicYear?,

        @field:EnumValues3(groups = arrayOf(CreateUserStep_no1::class))
        val courseOfStudy: String?)
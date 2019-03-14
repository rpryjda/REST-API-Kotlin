package com.pryjda.app.model.request

data class UserRequestDTO(val name: String?,
                          val surname: String?,
                          val email: String?,
                          val password: String?,
                          val indexNumber: Int?,
                          val academicYear: String?,
                          val courseOfStudy: String?)
package com.pryjda.app

import com.pryjda.app.entity.User
import com.pryjda.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(@Autowired val userRepository: UserRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        userRepository.save(User(null, "email_No_1@op.pl", "1234", 100266, true))
        userRepository.save(User(null, "email_No_2@op.pl", "1234", 200266, true))
        userRepository.save(User(null, "email_No_3@op.pl", "1234", 300266, true))
    }
}
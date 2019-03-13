package com.pryjda.app

import com.pryjda.app.entity.Lecture
import com.pryjda.app.entity.Role
import com.pryjda.app.entity.User
import com.pryjda.app.entity.UserProfile
import com.pryjda.app.repository.LectureRepository
import com.pryjda.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class DataLoader @Autowired constructor(val userRepository: UserRepository,
                                        val lectureRepository: LectureRepository) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {

        var admin: User = User(null, "admin@op.pl", "admin1234", null, true)

        var userNo1: User = User(null, "email_No_1@op.pl", "1234", 100266, true)
        var userNo2: User = User(null, "email_No_2@op.pl", "1234", 200266, true)
        var userNo3: User = User(null, "email_No_3@op.pl", "1234", 300266, true)

        var userProfileNo1: UserProfile = UserProfile(null, "Rafał", "Kalinowaski", "Second", "IT")
        var userProfileNo2: UserProfile = UserProfile(null, "Norbert", "Miły", "Third", "Civil Engeenering")
        var userProfileNo3: UserProfile = UserProfile(null, "Antonina", "Pryjda", "First", "Chemistry")

        userNo1.userProfile = userProfileNo1
        userNo2.userProfile = userProfileNo2
        userNo3.userProfile = userProfileNo3

        var roleAdmin: Role = Role(null, "ADMIN")
        var roleUser: Role = Role(null, "USER")

        admin.roles.add(roleAdmin)
        admin.roles.add(roleUser)

        userRepository.save(admin)

        userNo1.roles.add(roleUser)
        userNo2.roles.add(roleUser)
        userNo3.roles.add(roleUser)

        var lectureNo1: Lecture = Lecture(null, "Lecture No 1", "Kotlin programming", "John Smith", LocalDateTime.of(2019, 3, 18, 7, 0))
        lectureNo1.users.add(userNo1)
        lectureNo1.users.add(userNo2)
        lectureNo1.users.add(userNo3)

        var lectureNo2: Lecture = Lecture(null, "Lecture No 2", "Kotlin programming part 2", "John Smith", LocalDateTime.of(2019, 3, 19, 7, 0))
        lectureNo2.users.add(userNo2)

        var lectureNo3: Lecture = Lecture(null, "Lecture No 3", "Kotlin programming part 3", "John Smith", LocalDateTime.of(2019, 3, 20, 7, 0))
        lectureNo3.users.add(userNo1)

        lectureRepository.save(lectureNo1)
        lectureRepository.save(lectureNo2)
        lectureRepository.save(lectureNo3)
    }
}
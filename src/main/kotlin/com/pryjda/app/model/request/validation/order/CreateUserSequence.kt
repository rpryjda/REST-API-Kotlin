package com.pryjda.app.model.request.validation.order

import javax.validation.GroupSequence

@GroupSequence(CreateUserStep_no1::class, CreateUserStep_no2::class, CreateUserStep_no3::class )
interface CreateUserSequence

interface CreateUserStep_no1
interface CreateUserStep_no2
interface CreateUserStep_no3
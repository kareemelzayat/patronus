package com.patronus.interview.kareem.advice

import com.patronus.interview.kareem.domain.User
import com.patronus.interview.kareem.repository.UserRepository
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import java.nio.file.attribute.UserPrincipalNotFoundException

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiresAdmin()

/**
 * This is supposed to work; a before advice that ensures ADMIN role on its annotated method
 * Unfortunately, I do not have enough time to figure out how to make it work in Spring Boot
 * In Micronaut, it is easier
 */
@Aspect
@Component
class RequiresAdminAspect(
    private val userRepository: UserRepository
) {
    @Before("@annotation(RequiresAdmin) && args(principalId)")
    fun checkAdmin(joinPoint: JoinPoint, principalId: Long) {
        val user: User = userRepository.findById(principalId).orElseThrow()
        if (!user.isAdmin()) {
            throw UserPrincipalNotFoundException("Unauthorized User: $principalId")
        }
    }
}

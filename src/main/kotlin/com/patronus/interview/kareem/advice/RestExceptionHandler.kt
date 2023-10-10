package com.patronus.interview.kareem.advice

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.nio.file.attribute.UserPrincipalNotFoundException

/**
 * an advice class that handles thrown exceptions into a meaningful API response;
 * ideally contains some method to handle a set of exception types
 *
 * e.g.
 * @ExceptionHandler(DeviceNotFoundException::class)
 * protected fun handleNotFound(ex: Exception, request: WebRequest): ResponseEntity<Any> {
 *      return handleExceptionInternal(ex, "Device not found", HttpHeaders(), HttpStatus.NOT_FOUND, request)
 * }
 */
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(UserPrincipalNotFoundException::class)
    protected fun handleNotAdmin(ex: Exception, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(ex, "User principal not found", HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(NoSuchElementException::class)
    protected fun handleNoElementFound(ex: Exception, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(ex, "Element not found", HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }
}
package com.patronus.interview.kareem.config

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component

/**
 * I tried to use Netty as a non-blocking server. Apparently it did not work
 * The App keeps using Tomcat. I removed the `suspend` keyword from all controller methods
 */
@Component
class NettyWebServerFactoryPortCustomizer : WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {
    override fun customize(factory: NettyReactiveWebServerFactory?) {
        factory!!.port = 8088
    }
}
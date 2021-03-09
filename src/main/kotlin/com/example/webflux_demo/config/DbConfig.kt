package com.example.webflux_demo.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
class DbConfig {

    @Bean
    fun initializeConnectionFactory(
        @Qualifier("connectionFactory") connectionFactory: ConnectionFactory
    ) = ConnectionFactoryInitializer().apply {
        setConnectionFactory(connectionFactory)
        setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
    }
}
package org.ojacquemart.eurobets.firebase.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
@EnableScheduling
open class SchedulingConfig {

    @Bean
    open fun taskScheduler(): ThreadPoolTaskScheduler {
        return ThreadPoolTaskScheduler();
    }

}
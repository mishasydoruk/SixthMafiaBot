package com.example.sixthmafiabot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig
{
    @Bean(name = "repoExecutor")
    public Executor repoExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("AsynchRepoThread-");
        executor.setKeepAliveSeconds(200);
        executor.initialize();
        return executor;
    }

    @Bean(name = "serviceExecutor")
    public Executor serviceExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("AsynchServiceThread-");
        executor.setKeepAliveSeconds(200);
        executor.initialize();
        return executor;
    }
}
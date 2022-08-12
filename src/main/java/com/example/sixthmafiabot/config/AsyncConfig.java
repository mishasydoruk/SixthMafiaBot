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
    private final Integer repoCorePoolSize = 5;
    private final Integer repoMaxPoolSize = 10;
    private final Integer repoQueueCapacity = 100;
    private final Integer repoKeepAliveSeconds = 200;

    private final Integer serviceCorePoolSize = 10;
    private final Integer serviceMaxPoolSize = 10;
    private final Integer serviceQueueCapacity = 100;
    private final Integer serviceKeepAliveSeconds = 200;

    @Bean(name = "repoExecutor")
    public Executor repoExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(repoCorePoolSize);
        executor.setMaxPoolSize(repoMaxPoolSize);
        executor.setQueueCapacity(repoQueueCapacity);
        executor.setThreadNamePrefix("AsynchRepoThread-");
        executor.setKeepAliveSeconds(repoKeepAliveSeconds);
        executor.initialize();
        return executor;
    }

    @Bean(name = "serviceExecutor")
    public Executor serviceExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(serviceCorePoolSize);
        executor.setMaxPoolSize(serviceMaxPoolSize);
        executor.setQueueCapacity(serviceQueueCapacity);
        executor.setThreadNamePrefix("AsynchServiceThread-");
        executor.setKeepAliveSeconds(serviceKeepAliveSeconds);
        executor.initialize();
        return executor;
    }
}
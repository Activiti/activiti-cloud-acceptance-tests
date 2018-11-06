/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.RuntimeTestsConfigurationProperties;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonEncoder;
import feign.jackson.JacksonEncoder;
import org.activiti.api.runtime.conf.impl.CommonModelAutoConfiguration;
import org.activiti.api.runtime.conf.impl.ProcessModelAutoConfiguration;
import org.activiti.api.task.conf.impl.TaskModelAutoConfiguration;
import org.activiti.cloud.api.model.shared.impl.conf.CloudCommonModelAutoConfiguration;
import org.activiti.cloud.api.process.model.impl.conf.CloudProcessModelAutoConfiguration;
import org.activiti.cloud.api.task.model.impl.conf.CloudTaskModelAutoConfiguration;
import org.activiti.cloud.qa.rest.feign.*;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import services.audit.AuditService;
import services.audit.admin.AuditAdminService;
import services.query.ProcessQueryService;
import services.query.TaskQueryService;
import services.query.admin.ProcessQueryAdminService;
import services.query.admin.TaskQueryAdminService;
import services.runtime.ProcessRuntimeService;
import services.runtime.TaskRuntimeService;
import services.runtime.admin.ProcessRuntimeAdminService;
import services.runtime.admin.TaskRuntimeAdminService;

/**
 * Feign Configuration
 */
@Configuration
@Import({JacksonAutoConfiguration.class,
        FeignConfiguration.class,
        CloudCommonModelAutoConfiguration.class,
        CloudProcessModelAutoConfiguration.class,
        CloudTaskModelAutoConfiguration.class,
        CommonModelAutoConfiguration.class,
        ProcessModelAutoConfiguration.class,
        TaskModelAutoConfiguration.class})
public class RuntimeFeignConfiguration {

    private final RuntimeTestsConfigurationProperties runtimeTestsConfigurationProperties;

    private final ObjectMapper objectMapper;

    public RuntimeFeignConfiguration(RuntimeTestsConfigurationProperties runtimeTestsConfigurationProperties,
                                     ObjectMapper objectMapper) {
        this.runtimeTestsConfigurationProperties = runtimeTestsConfigurationProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public ProcessRuntimeService processRuntimeService() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(objectMapper),
                         new HalDecoder(objectMapper))
                .target(ProcessRuntimeService.class,
                        runtimeTestsConfigurationProperties.getRuntimeBundleUrl());
    }

    @Bean
    public TaskRuntimeService taskRuntimeService() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(objectMapper),
                        new HalDecoder(objectMapper))
                .target(TaskRuntimeService.class,
                        runtimeTestsConfigurationProperties.getRuntimeBundleUrl());
    }

    @Bean
    public ProcessRuntimeAdminService processRuntimeAdminService() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(objectMapper),
                        new HalDecoder(objectMapper))
                .target(ProcessRuntimeAdminService.class,
                        runtimeTestsConfigurationProperties.getRuntimeBundleUrl());
    }

    @Bean
    public TaskRuntimeAdminService taskRuntimeAdminService() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(objectMapper),
                        new HalDecoder(objectMapper))
                .target(TaskRuntimeAdminService.class,
                        runtimeTestsConfigurationProperties.getRuntimeBundleUrl());
    }

    @Bean
    public ProcessQueryService processQueryService() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(),
                        new HalDecoder(objectMapper))
                .target(ProcessQueryService.class,
                        runtimeTestsConfigurationProperties.getQueryUrl());
    }

    @Bean
    public TaskQueryService taskQueryService() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(),
                        new HalDecoder(objectMapper))
                .target(TaskQueryService.class,
                        runtimeTestsConfigurationProperties.getQueryUrl());
    }

    @Bean
    public ProcessQueryAdminService processQueryAdminService() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(),
                        new HalDecoder(objectMapper))
                .target(ProcessQueryAdminService.class,
                        runtimeTestsConfigurationProperties.getQueryUrl());
    }

    @Bean
    public TaskQueryAdminService taskQueryAdminService() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(),
                        new HalDecoder(objectMapper))
                .target(TaskQueryAdminService.class,
                        runtimeTestsConfigurationProperties.getQueryUrl());
    }

    @Bean
    public AuditService auditClient() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(),
                         new HalDecoder(objectMapper))
                .target(AuditService.class,
                        runtimeTestsConfigurationProperties.getAuditEventUrl());
    }

    @Bean
    public AuditAdminService auditAdminClient() {
        return FeignRestDataClient
                .builder(new JacksonEncoder(),
                        new HalDecoder(objectMapper))
                .target(AuditAdminService.class,
                        runtimeTestsConfigurationProperties.getAuditEventUrl());
    }
}
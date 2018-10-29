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

package org.activiti.cloud.qa.model.modeling;

import org.activiti.cloud.organization.api.Model;
import org.activiti.cloud.organization.api.ModelType;

/**
 * Modeling model identifier
 */
public class ModelIdentifier<M> implements ModelingIdentifier<M> {

    private String modelName;

    private String modelType;

    private String modelVersion;

    public ModelIdentifier(String modelName,
                           String modelType) {
        this(modelName,
             modelType,
             null);
    }

    public ModelIdentifier(String modelName,
                           String modelType,
                           String modelVersion) {
        this.modelName = modelName;
        this.modelType = modelType;
        this.modelVersion = modelVersion;
    }

    @Override
    public boolean test(M modelingContext) {
        return modelingContext instanceof Model &&
                modelName.equals(((Model) modelingContext).getName()) &&
                modelType.equalsIgnoreCase(((Model) modelingContext).getType()) &&
                (modelVersion == null || modelVersion.equals(((Model) modelingContext).getVersion()));
    }

    public static ModelIdentifier identified(String modelName,
                                             String modelType) {
        return new ModelIdentifier(modelName,
                                   modelType);
    }

    public static ModelIdentifier identified(String modelName,
                                             String modelType,
                                             String modelVersion) {
        return new ModelIdentifier(modelName,
                                   modelType,
                                   modelVersion);
    }
}

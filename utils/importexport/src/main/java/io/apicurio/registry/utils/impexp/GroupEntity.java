/*
 * Copyright 2021 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.registry.utils.impexp;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author eric.wittmann@gmail.com
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@ToString
@RegisterForReflection
public class GroupEntity extends Entity {

    public String groupId;
    public String description;
    public String artifactsType;
    public String createdBy;
    public long createdOn;
    public String modifiedBy;
    public long modifiedOn;
    public Map<String, String> properties;

    /**
     * @see io.apicurio.registry.utils.impexp.Entity#getEntityType()
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.Group;
    }

}

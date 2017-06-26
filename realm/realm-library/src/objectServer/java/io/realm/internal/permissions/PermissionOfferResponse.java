/*
 * Copyright 2017 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.realm.internal.permissions;

import java.util.Date;
import java.util.UUID;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * This model is used to apply permission changes defined in the permission offer
 * object represented by the specified token, which was created by another user's
 * {@link PermissionOffer} object.
 *
 * It should be used in conjunction with an {@link io.realm.SyncUser}'s management Realm.
 *
 * @see <a href="https://realm.io/docs/realm-object-server/#permissions">Permissions description</a> for general
 * documentation.
 */
public class PermissionOfferResponse extends RealmObject implements BasePermissionApi {

    // Base fields
    @PrimaryKey
    @Required
    private String id = UUID.randomUUID().toString();
    @Required
    private Date createdAt = new Date();
    @Required
    private Date updatedAt = new Date();
    private Integer statusCode; // nil=not processed, 0=success, >0=error
    private String statusMessage;

    // Request fields
    @Required
    private String token;
    private String realmUrl;

    public PermissionOfferResponse() {
        // No args constructor required by Realm
    }

    /**
     * Construct a permission offer response object used to apply permission changes
     * defined in the permission offer object represented by the specified token,
     * which was created by another user's {@link PermissionOffer} object.
     *
     * @param token The received token which uniquely identifies another user's
     *              {@link PermissionOffer}.
     */
    public PermissionOfferResponse(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Non-null 'token' required.");
        }
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * Check if the request was successfully handled by the Realm Object Server.
     *
     * @return {@code true} if request was handled successfully. {@code false} if not. See {@link #getStatusMessage()}
     *         for the full error message.
     */
    public boolean isSuccessful() {
        return statusCode != null && statusCode == 0;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    public String getToken() {
        return token;
    }

    public String getRealmUrl() {
        return realmUrl;
    }
}

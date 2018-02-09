/*
 * Copyright (c) 2014-2018 Globo.com - ATeam
 * All rights reserved.
 *
 * This source is subject to the Apache License, Version 2.0.
 * Please see the LICENSE file for more information.
 *
 * Authors: See AUTHORS file
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.galeb.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.galeb.core.exceptions.BadRequestException;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UK_target_name_pool_id", columnNames = { "name", "pool_id" }) })
public class Target extends AbstractEntity implements WithStatus {

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pool_id", nullable = false, foreignKey = @ForeignKey(name="FK_target_pool"))
    private Pool pool;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "target", cascade = CascadeType.REMOVE)
    private Set<HealthStatus> healthStatus = new HashSet<>();

    @Column(nullable = false)
    private String name;

    @Transient
    private Map<Long, Status> status = new HashMap<>();

    public Pool getPool() {
        return pool;
    }

    public void setPools(Pool pool) {
        this.pool = pool;
    }

    @JsonIgnore
    public HealthStatus.Status healthStatusConsolidated() {
        return this.healthStatus.stream().anyMatch(h -> h.getStatus().equals(HealthStatus.Status.HEALTHY)) ?
               HealthStatus.Status.HEALTHY :
               this.healthStatus.stream().map(HealthStatus::getStatus).findAny().orElse(HealthStatus.Status.UNKNOWN);
    }

    public Set<HealthStatus> getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(Set<HealthStatus> healthStatus) {
        if (healthStatus != null) {
            this.healthStatus.clear();
            this.healthStatus.addAll(healthStatus);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.hasText(name, "name is not valid");
        this.name = name;
    }

    @Override
    public Map<Long, Status> getStatus() {
        return status;
    }

    @Override
    public void setStatus(Map<Long, Status> status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return Objects.equals(getName(), target.getName()) || Objects.equals(getId(), target.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}

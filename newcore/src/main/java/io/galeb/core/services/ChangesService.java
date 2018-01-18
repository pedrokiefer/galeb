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

package io.galeb.core.services;

import io.galeb.core.entity.AbstractEntity;
import io.galeb.core.entity.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChangesService {

    /**
     * Description arguments:
     * {0} - Environment Id
     * {1} - Entity Simple Name Class
     * {2} - Entity Id
     * {3} - Entity Last Modified At
     */
    private static final String FORMAT_KEY_HAS_CHANGE = "haschange:{0}:{1}:{2}:{3}";

    @Autowired
    StringRedisTemplate redisTemplate;

    public void register(Environment e, AbstractEntity entity, String version) {
        String simpleNameClass = entity.getClass().getSimpleName().toLowerCase();
        String envId = String.valueOf(e.getId());
        Long entityId = entity.getId();
        String entityLastModifiedAt = String.valueOf(entity.getLastModifiedAt().getTime());
        String keyFormatted = MessageFormat.format(FORMAT_KEY_HAS_CHANGE, envId, simpleNameClass, entityId, entityLastModifiedAt);
        redisTemplate.opsForValue().setIfAbsent(keyFormatted, version);
    }

    public boolean hasByEnvironmentId(Long environmentId) {
        String keyFormatted = MessageFormat.format(FORMAT_KEY_HAS_CHANGE, environmentId, "*", "*", "*");
        return hasKey(keyFormatted);
    }

    public Set<Long> listEnvironmentIds(AbstractEntity entity) {
        String simpleNameClass = entity.getClass().getSimpleName().toLowerCase();
        Long entityId = entity.getId();
        String keyFormatted = MessageFormat.format(FORMAT_KEY_HAS_CHANGE, "*",simpleNameClass, entityId, "*");
        Set<String> keys = keys(keyFormatted);
        return keys.stream().map(k -> Long.parseLong(new MessageFormat(FORMAT_KEY_HAS_CHANGE).parse(k, new ParsePosition(0))[0].toString())).collect(Collectors.toSet());
    }

    public void removeAllWithOldestVersion(String envid, Long version) {
        String key = MessageFormat.format(FORMAT_KEY_HAS_CHANGE, envid, "*", "*", "*");
        keys(key).stream().forEach(k -> {
            String value = redisTemplate.opsForValue().get(k);
            if (version >= Long.valueOf(value)) {
                redisTemplate.delete(k);
            }
        });
    }

    private boolean hasKey(String key) {
        final Set<String> result = keys(key);
        return result != null && !result.isEmpty();
    }

    private Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

}
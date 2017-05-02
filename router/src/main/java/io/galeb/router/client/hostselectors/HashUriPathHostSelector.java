/*
 * Copyright (c) 2014-2017 Globo.com - ATeam
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

package io.galeb.router.client.hostselectors;

import io.galeb.core.enums.SystemEnv;
import io.galeb.router.client.ExtendedLoadBalancingProxyClient.Host;
import io.galeb.router.client.hostselectors.consistenthash.ConsistentHash;
import io.galeb.router.client.hostselectors.consistenthash.HashAlgorithm;
import io.undertow.server.HttpServerExchange;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class HashUriPathHostSelector extends ClientStatisticsMarker implements HashHostSelector {

    private final HashAlgorithm hashAlgorithm = new HashAlgorithm(HashAlgorithm.HashType.valueOf(SystemEnv.HASH_ALGORITHM.getValue()));
    private final int numReplicas = Integer.parseInt(SystemEnv.HASH_NUM_REPLICAS.getValue());
    private final ConsistentHash<Integer> consistentHash = new ConsistentHash<>(hashAlgorithm, numReplicas, Collections.emptyList());
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    @Override
    public int selectHost(final Host[] availableHosts, final HttpServerExchange exchange) {
        if (!initialized.getAndSet(true)) {
            final LinkedHashSet<Integer> listPos = convertToMapStream(availableHosts)
                                                    .map(Map.Entry::getKey)
                                                    .collect(Collectors.toCollection(LinkedHashSet::new));
            consistentHash.rebuild(hashAlgorithm, numReplicas, listPos);
        }
        int pos = consistentHash.get(getKey(exchange));
        stamp(availableHosts[pos], exchange);
        return pos;
    }

    private String getKey(final HttpServerExchange exchange) {
        return exchange.getRelativePath();
    }

    // Test only
    @Override
    public synchronized void reset() {
        initialized.set(false);
    }
}

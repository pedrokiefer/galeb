/**
 *
 */

package io.galeb.router.client.hostselectors;

import io.galeb.router.client.ExtendedLoadBalancingProxyClient;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.AttachmentKey;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface HostSelector {

    AttachmentKey<String> REAL_DEST = AttachmentKey.create(String.class);

    int selectHost(ExtendedLoadBalancingProxyClient.Host[] availableHosts, HttpServerExchange exchange);

    default Stream<Map.Entry<Integer, ExtendedLoadBalancingProxyClient.Host>> convertToMapStream(final ExtendedLoadBalancingProxyClient.Host[] availableHosts) {
        return IntStream.range(0, availableHosts.length)
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> availableHosts[i]))
                .entrySet()
                .stream();
    }
}

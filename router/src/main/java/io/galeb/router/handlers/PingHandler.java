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

package io.galeb.router.handlers;

import io.galeb.core.enums.SystemEnv;
import io.galeb.router.configurations.ManagerClientCacheConfiguration.ManagerClientCache;
import io.galeb.router.services.UpdaterService;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicLong;

import static io.galeb.router.handlers.PingHandler.HealthcheckBody.*;

@Component
public class PingHandler implements HttpHandler {

    enum HealthcheckBody {
        FAIL,
        EMPTY,
        OUTDATED,
        WORKING
    }

    private static final long OBSOLETE_TIME = 10000;
    private final Log logger = LogFactory.getLog(this.getClass());

    private final AtomicLong lastPing = new AtomicLong(0L);
    private final ManagerClientCache cache;
    private final UpdaterService updaterService;
    private final RootHandler rootHandler;

    @Autowired
    public PingHandler(final ManagerClientCache cache,
                       @Lazy UpdaterService updaterService,
                       @Lazy RootHandler rootHandler) {
        this.cache = cache;
        this.updaterService = updaterService;
        this.rootHandler = rootHandler;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.putAttachment(PoolHandler.POOL_NAME, "__ping__");
        exchange.putAttachment(PathGlobHandler.RULE_NAME, "__ping__");

        boolean hasNoUpdate = exchange.getQueryParameters().containsKey("noupdate");
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseHeaders().put(Headers.SERVER, "GALEB");
        String statusBody = getStatusBody(hasNoUpdate);
        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseSender().send(statusBody);
        if (!hasNoUpdate) {
            updaterService.sched();
        }
        exchange.endExchange();
    }

    private String getStatusBody(boolean hasNoUpdate) {
        return isFailed() ? FAIL.name() :
            (isOutdated(hasNoUpdate) ? OUTDATED.name() :
            (isEmpty() ? EMPTY.name() : WORKING.name()));
    }

    private boolean isEmpty() {
        return cache.isEmpty();
    }

    private boolean isOutdated(boolean hasNoUpdate) {
        long currentObsoleteTime = System.currentTimeMillis() - OBSOLETE_TIME;
        return hasNoUpdate ? lastPing.get() < currentObsoleteTime : lastPing.getAndSet(System.currentTimeMillis()) < currentObsoleteTime;
    }

    private boolean isFailed() {
        // TODO: Check all system
        try {
            Integer.parseInt(SystemEnv.POOL_MAXCONN.getValue());
            Integer.parseInt(SystemEnv.ROUTER_PORT.getValue());
            Integer.parseInt(SystemEnv.STATSD_PORT.getValue());
            Integer.parseInt(SystemEnv.SYSLOG_PORT.getValue());
            Assert.hasText(SystemEnv.STATSD_HOST.getValue(), "STATSD_HOST NULL");
            Assert.hasText(SystemEnv.SYSLOG_HOST.getValue(), "SYSLOG_HOST NULL");
            return rootHandler.isFailed();
        } catch(Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return true;
        }
    }
}

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

package io.galeb.router.services;

import io.galeb.core.enums.SystemEnv;
import io.galeb.router.kv.ExternalData;

import java.util.List;

public interface ExternalDataService {
    String ROOT_KEY         = "/";
    String PREFIX_KEY       = ROOT_KEY + SystemEnv.CLUSTER_ID.getValue();
    String VIRTUALHOSTS_KEY = PREFIX_KEY + "/virtualhosts";
    String POOLS_KEY        = PREFIX_KEY + "/pools";

    List<ExternalData> listFrom(String key);

    List<ExternalData> listFrom(String key, boolean recursive);

    List<ExternalData> listFrom(ExternalData node);

    ExternalData node(String key);

    ExternalData node(String key, boolean recursive);

    ExternalData node(String key, ExternalData.Generic def);

    ExternalData node(String key, boolean recursive, ExternalData.Generic def);

    boolean exist(String key);
}

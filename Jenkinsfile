pipeline {
  agent any
  stages {
    stage('get last packages') {
      steps {
        sh '''#!/bin/bash
version="$(curl -s -L -H \'Authorization: token \'${GITHUB_TOKEN} https://api.github.com/repos/galeb/galeb/releases/latest | tee /tmp/releases.json | jq -r .tag_name | sed \'s/^v//\')"
cat /tmp/releases.json
if [ "x${version}" != "x" -a "x${version}" != "xnull" ]; then
rm -f /tmp/*.rpm
for service in api legba kratos router health; do
package=galeb-${service}-${version}.el7.noarch.rpm
curl -s -k -I -w "%{http_code}" ${ARTIFACTORY_REPO}/${package} -o /dev/null | grep \'^200$\' > /dev/null
if [ $? -ne 0 ]; then
echo "ARTIFACTORY: Package ${package} not found. Getting from github"
curl -s -v -k -L -H \'Authorization: token \'${GITHUB_TOKEN} https://github.com/galeb/galeb/releases/download/v${version}/${package} -o /tmp/${package} || true
else
echo "ARTIFACTORY: Package ${package} found. Getting from local repo"
curl -s -v -k -L ${ARTIFACTORY_REPO}/${package} -o /tmp/${package} || true
fi
done
else
exit 1
fi'''
      }
    }
    stage('setup') {
      parallel {
        stage('update API') {
          steps {
            sh '''#!/bin/bash
myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_API}"
scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no /tmp/galeb-api-*.el7.noarch.rpm root@${GALEB_API}:/tmp
$myssh "/bin/yum clean all; /bin/yum install jdk1.8.0_144 -y; /bin/yum remove -y galeb-api && /bin/yum install -y /tmp/galeb-api-*.el7.noarch.rpm && rm -f /tmp/galeb-api-*.el7.noarch.rpm"
$myssh "id galeb > /dev/null 2>&1 || (groupadd galeb && useradd -g galeb -d /opt/galeb galeb)"
$myssh "mkdir -p /opt/logs/galeb && chmod 777 -R /opt/logs/galeb || true"
$myssh "/usr/bin/systemctl enable galeb || true"
$myssh "/sbin/sysctl -w net.ipv4.tcp_fin_timeout=5"
$myssh "/sbin/sysctl -w net.core.somaxconn=40000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_syn_backlog=20000"
$myssh "/sbin/sysctl -w net.core.netdev_max_backlog=10000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_syncookies=1"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_tw_buckets=512"
$myssh "/sbin/sysctl -w net.ipv4.tcp_tw_reuse=1"
$myssh "/sbin/sysctl -w net.ipv4.ip_local_port_range=\'15000 65000\'"
$myssh "/sbin/sysctl -w fs.file-max=100000"
$myssh "/sbin/swapoff -a; /bin/sed -i -e \'/.*swap.*/d\' /etc/fstab"'''
          }
        }
        stage('update LEGBA') {
          steps {
            sh '''#!/bin/bash
myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_LEGBA}"
scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no /tmp/galeb-legba-*.el7.noarch.rpm root@${GALEB_LEGBA}:/tmp
$myssh "/bin/yum clean all; /bin/yum install jdk1.8.0_144 -y; /bin/yum remove -y galeb-legba && /bin/yum install -y /tmp/galeb-legba-*.el7.noarch.rpm && rm -f /tmp/galeb-legba-*.el7.noarch.rpm"
$myssh "id galeb > /dev/null 2>&1 || (groupadd galeb && useradd -g galeb -d /opt/galeb galeb)"
$myssh "mkdir -p /opt/logs/galeb && chmod 777 -R /opt/logs/galeb || true"
$myssh "/usr/bin/systemctl enable galeb || true"
$myssh "/sbin/sysctl -w net.ipv4.tcp_fin_timeout=5"
$myssh "/sbin/sysctl -w net.core.somaxconn=40000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_syn_backlog=20000"
$myssh "/sbin/sysctl -w net.core.netdev_max_backlog=10000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_syncookies=1"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_tw_buckets=512"
$myssh "/sbin/sysctl -w net.ipv4.tcp_tw_reuse=1"
$myssh "/sbin/sysctl -w net.ipv4.ip_local_port_range=\'15000 65000\'"
$myssh "/sbin/sysctl -w fs.file-max=100000"
$myssh "/sbin/swapoff -a; /bin/sed -i -e \'/.*swap.*/d\' /etc/fstab"'''
          }
        }
        stage('update KRATOS') {
          steps {
            sh '''#!/bin/bash
myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_KRATOS}"
scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no /tmp/galeb-kratos-*.el7.noarch.rpm root@${GALEB_KRATOS}:/tmp
$myssh "/bin/yum clean all; /bin/yum install jdk1.8.0_144 -y; /bin/yum remove -y galeb-kratos && /bin/yum install -y /tmp/galeb-kratos-*.el7.noarch.rpm && rm -f /tmp/galeb-kratos-*.el7.noarch.rpm"
$myssh "id galeb > /dev/null 2>&1 || (groupadd galeb && useradd -g galeb -d /opt/galeb galeb)"
$myssh "mkdir -p /opt/logs/galeb && chmod 777 -R /opt/logs/galeb || true"
$myssh "/usr/bin/systemctl enable galeb || true"
$myssh "/sbin/sysctl -w net.ipv4.tcp_fin_timeout=5"
$myssh "/sbin/sysctl -w net.core.somaxconn=40000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_syn_backlog=20000"
$myssh "/sbin/sysctl -w net.core.netdev_max_backlog=10000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_syncookies=1"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_tw_buckets=512"
$myssh "/sbin/sysctl -w net.ipv4.tcp_tw_reuse=1"
$myssh "/sbin/sysctl -w net.ipv4.ip_local_port_range=\'15000 65000\'"
$myssh "/sbin/sysctl -w fs.file-max=100000"
$myssh "/sbin/swapoff -a; /bin/sed -i -e \'/.*swap.*/d\' /etc/fstab"'''
          }
        }
        stage('update ROUTER') {
          steps {
            sh '''#!/bin/bash
myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_ROUTER}"
scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no /tmp/galeb-router-*.el7.noarch.rpm root@${GALEB_ROUTER}:/tmp
$myssh "/bin/yum clean all; /bin/yum install jdk1.8.0_144 -y; /bin/yum remove -y galeb-router && /bin/yum install -y /tmp/galeb-router-*.el7.noarch.rpm && rm -f /tmp/galeb-router-*.el7.noarch.rpm"
$myssh "id galeb > /dev/null 2>&1 || (groupadd galeb && useradd -g galeb -d /opt/galeb galeb)"
$myssh "mkdir -p /opt/logs/galeb && chmod 777 -R /opt/logs/galeb || true"
$myssh "/usr/bin/systemctl enable galeb || true"
$myssh "/sbin/sysctl -w net.ipv4.tcp_fin_timeout=5"
$myssh "/sbin/sysctl -w net.core.somaxconn=40000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_syn_backlog=20000"
$myssh "/sbin/sysctl -w net.core.netdev_max_backlog=10000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_syncookies=1"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_tw_buckets=512"
$myssh "/sbin/sysctl -w net.ipv4.tcp_tw_reuse=1"
$myssh "/sbin/sysctl -w net.ipv4.ip_local_port_range=\'15000 65000\'"
$myssh "/sbin/sysctl -w fs.file-max=100000"
$myssh "/sbin/swapoff -a; /bin/sed -i -e \'/.*swap.*/d\' /etc/fstab"'''
          }
        }
        stage('update HEALTH') {
          steps {
            sh '''#!/bin/bash
myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_HEALTH}"
scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no /tmp/galeb-health-*.el7.noarch.rpm root@${GALEB_HEALTH}:/tmp
$myssh "/bin/yum clean all; /bin/yum install jdk1.8.0_144 -y; /bin/yum remove -y galeb-health && /bin/yum install -y /tmp/galeb-health-*.el7.noarch.rpm && rm -f /tmp/galeb-health-*.el7.noarch.rpm"
$myssh "id galeb > /dev/null 2>&1 || (groupadd galeb && useradd -g galeb -d /opt/galeb galeb)"
$myssh "mkdir -p /opt/logs/galeb && chmod 777 -R /opt/logs/galeb || true"
$myssh "/usr/bin/systemctl enable galeb || true"
$myssh "/sbin/sysctl -w net.ipv4.tcp_fin_timeout=5"
$myssh "/sbin/sysctl -w net.core.somaxconn=40000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_syn_backlog=20000"
$myssh "/sbin/sysctl -w net.core.netdev_max_backlog=10000"
$myssh "/sbin/sysctl -w net.ipv4.tcp_syncookies=1"
$myssh "/sbin/sysctl -w net.ipv4.tcp_max_tw_buckets=512"
$myssh "/sbin/sysctl -w net.ipv4.tcp_tw_reuse=1"
$myssh "/sbin/sysctl -w net.ipv4.ip_local_port_range=\'15000 65000\'"
$myssh "/sbin/sysctl -w fs.file-max=100000"
$myssh "/sbin/swapoff -a; /bin/sed -i -e \'/.*swap.*/d\' /etc/fstab"'''
          }
        }
      }
    }
    stage('update apikeys') {
      parallel {
        stage('update apikeys API') {
          steps {
            sh '''#!/bin/bash

ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_API} "cd /root && /root/makeenvs.sh api.galeb.qa02.globoi.com lab api"'''
          }
        }
        stage('update apikeys LEGBA') {
          steps {
            sh '''#!/bin/bash

ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_LEGBA} "cd /root && /root/makeenvs.sh legba.galeb.qa02.globoi.com lab legba"'''
          }
        }
        stage('update apikeys KRATOS') {
          steps {
            sh '''#!/bin/bash

ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_KRATOS} "cd /root && /root/makeenvs.sh kratos.galeb.qa02.globoi.com lab kratos"'''
          }
        }
        stage('update apikeys ROUTER') {
          steps {
            sh '''#!/bin/bash

ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_ROUTER} "cd /root && /root/makeenvs.sh be-qa2.router.cmal08.galeb.qa02.globoi.com lab router"'''
          }
        }
        stage('update apikeys HEALTH') {
          steps {
            sh '''#!/bin/bash

ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_HEALTH} "cd /root && /root/makeenvs.sh be-qa2.health.cmal08.galeb.qa02.globoi.com lab health"'''
          }
        }
      }
    }
    stage('Update DB Schema') {
      steps {
        sh '''#!/bin/bash

/usr/bin/mvn -Dhttps.proxyHost=$(echo $https_proxy|cut -d\'/\' -f3|cut -d\':\' -f1) -Dhttps.proxyPort=$(echo $https_proxy|cut -d\':\' -f3|cut -d\'/\' -f1) -Dhttp.proxyHost=$(echo $http_proxy|cut -d\'/\' -f3|cut -d\':\' -f1) -Dhttp.proxyPort=$(echo $http_proxy|cut -d\':\' -f3|cut -d\'/\' -f1) clean
/usr/bin/mvn -f newcore/pom.xml -Dhttps.proxyHost=$(echo $https_proxy|cut -d\'/\' -f3|cut -d\':\' -f1) -Dhttps.proxyPort=$(echo $https_proxy|cut -d\':\' -f3|cut -d\'/\' -f1) -Dhttp.proxyHost=$(echo $http_proxy|cut -d\'/\' -f3|cut -d\':\' -f1) -Dhttp.proxyPort=$(echo $http_proxy|cut -d\':\' -f3|cut -d\'/\' -f1) install && \\
/usr/bin/mvn -f api/pom.xml -Dhttps.proxyHost=$(echo $https_proxy|cut -d\'/\' -f3|cut -d\':\' -f1) -Dhttps.proxyPort=$(echo $https_proxy|cut -d\':\' -f3|cut -d\'/\' -f1) -Dhttp.proxyHost=$(echo $http_proxy|cut -d\'/\' -f3|cut -d\':\' -f1) -Dhttp.proxyPort=$(echo $http_proxy|cut -d\':\' -f3|cut -d\'/\' -f1) \\
flyway:clean -Dflyway.user=$GALEB_DB_USER -Dflyway.password=$GALEB_DB_PASS -Dflyway.url=$GALEB_DB_URL && \\
/usr/bin/mvn -f api/pom.xml -Dhttps.proxyHost=$(echo $https_proxy|cut -d\'/\' -f3|cut -d\':\' -f1) -Dhttps.proxyPort=$(echo $https_proxy|cut -d\':\' -f3|cut -d\'/\' -f1) -Dhttp.proxyHost=$(echo $http_proxy|cut -d\'/\' -f3|cut -d\':\' -f1) -Dhttp.proxyPort=$(echo $http_proxy|cut -d\':\' -f3|cut -d\'/\' -f1) \\
flyway:migrate -Dflyway.user=$GALEB_DB_USER -Dflyway.password=$GALEB_DB_PASS -Dflyway.url=$GALEB_DB_URL'''
      }
    }
    stage('Restart services') {
      parallel {
        stage('Start API') {
          steps {
            sh '''#!/bin/bash

myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_API}"
$myssh "/etc/init.d/galeb restart"

# Verifing if port 8000 is open
TEST_STATUS="PENDING"
echo $TEST_STATUS

while [ "${TEST_STATUS}" != "WORKING" ]
do
    TEST_STATUS=$(curl --noproxy \'*\' -H"Host: __info__" $GALEB_API:8000/info 2>1 | jq -r .healthy)
    echo $TEST_STATUS
    sleep 5
done'''
          }
        }
        stage('Start LEGBA') {
          steps {
            sh '''#!/bin/bash

myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_LEGBA}"
$myssh "/etc/init.d/galeb restart"
# Verifing if port 8000 is open
TEST_STATUS="PENDING"
echo $TEST_STATUS

while [ "${TEST_STATUS}" != "WORKING" ]
do
    TEST_STATUS=$(curl --noproxy \'*\' -H"Host: __info__" $GALEB_LEGBA:8000/info 2>1 | jq -r .healthy)
    echo $TEST_STATUS
    sleep 5
done'''
          }
        }
        stage('Start KRATOS') {
          steps {
            sh '''#!/bin/bash

myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_KRATOS}"
$myssh "/etc/init.d/galeb restart"

# Verifing if port 8000 is open
TEST_STATUS="PENDING"
echo $TEST_STATUS

while [ "${TEST_STATUS}" != "WORKING" ]
do
    TEST_STATUS=$(curl --noproxy \'*\' -H"Host: __info__" $GALEB_KRATOS:8000/info 2>1 | jq -r .health)
    echo $TEST_STATUS
    sleep 5
done'''
          }
        }
        stage('Start ROUTER') {
          steps {
            sh '''#!/bin/bash

myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_ROUTER}"
$myssh "/etc/init.d/galeb restart"

# Verifing if port 8080 is open
TEST_STATUS="PENDING"
echo $TEST_STATUS

while [ "${TEST_STATUS}" != "WORKING" ] && [ "${TEST_STATUS}" != "EMPTY" ] && [ "${TEST_STATUS}" != "OUTDATED" ]
do
    TEST_STATUS=$(curl --noproxy \'*\' -H"Host: __ping__" $GALEB_ROUTER:8080/info 2>1)
    echo $TEST_STATUS
    sleep 5
done'''
          }
        }
        stage('Start HEALTH') {
          steps {
            sh '''#!/bin/bash

myssh="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no root@${GALEB_HEALTH}"
$myssh "/etc/init.d/galeb restart"'''
          }
        }
      }
    }
    stage('Artifactory Upload') {
      steps {
        sh '''#!/bin/bash

for package in /tmp/galeb-*rpm; do
echo $package
curl -s -k -I -w "%{http_code}" ${ARTIFACTORY_REPO}/${package##*/} -o /dev/null | grep \'^200$\' > /dev/null
if [ $? -ne 0 ]; then
curl -H \'X-JFrog-Art-Api:\'${ARTIFACTORY_TOKEN} -XPUT ${ARTIFACTORY_REPO}/${package##*/} -T ${package}
else	
echo "Package already exists: ${package##*/}. Ignoring upload."
fi
done'''
      }
    }
    stage('Test API') {
      steps {
        sh '''#!/bin/bash

sendtest () {

  METHOD=$1

  for file in $(ls $WORKSPACE/jenkins/api/*$METHOD.json); do

    JSON=$(cat $file | tr -d \'\\n\' | sed "s,RANDOM,$RANDOM,g" | sed "s,GROU_PROJECT,$GROU_PROJECT," | sed "s,GROU_NOTIFY,$GROU_NOTIFY," | sed "s,GALEB_API,$GALEB_API,g" | sed "s,GALEB_KRATOS,$GALEB_KRATOS,g" | sed "s,GALEB_HEALTH,$GALEB_HEALTH,g" | sed "s,TOKEN_API,$TOKEN_API,g" | sed "s,GALEB_TEAM_ID,$GALEB_TEAM_ID," | sed "s,GALEB_PROJECT_ID,$GALEB_PROJECT_ID,"| sed "s,GALEB_POOL_ID,$GALEB_POOL_ID," | sed "s,GALEB_VIRTUALHOST_ID,$GALEB_VIRTUALHOST_ID,"| sed "s,GALEB_RULE_ID,$GALEB_RULE_ID," | sed "s,GALEB_TARGET_ID,$GALEB_TARGET_ID," | sed "s,GALEB_RULEORDERED_ID,$GALEB_RULEORDERED_ID,")

    if jq -e . >/dev/null 2>&1 <<<"$JSON"; then
      echo
      echo
      echo "Parsed JSON (${file}) successfully!"
      echo
    else
      echo
      echo "Failed to parse JSON (${file})! Stopping.."
      break
    fi

    echo $JSON | jq -c . > /tmp/JENKINS_API_TMP_FILE.json

    RESULT_GROU=$(curl --noproxy \'*\' -H\'content-type:application/json\' -H"x-auth-token:$TOKEN" -XPOST -d@/tmp/JENKINS_API_TMP_FILE.json ${ENDPOINT_GROU}/tests 2>1)

    rm -f /tmp/JENKINS_API_TMP_FILE.json

    TEST_STATUS=$(echo $RESULT_GROU | jq -r .status)
    TEST_URL=$(echo $RESULT_GROU | jq -r ._links.self.href)

    echo "Grou Test URL: ${TEST_URL}"
    echo "Grou Test STATUS: ${TEST_STATUS}"

    while [ "${TEST_STATUS}" != "OK" ]
    do
      TEST_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' $TEST_URL 2>1| jq -r .status)
      echo "Grou Test STATUS: ${TEST_STATUS}"
      sleep 5
    done
  done

}

# GET TOKEN GROU
TOKEN="$(curl --noproxy \'*\' --silent -I -XGET -u ${GROU_USER}:${GROU_PASSWORD} ${ENDPOINT_GROU}/token/${GROU_PROJECT} | grep \'^x-auth-token:\' | awk \'{ print $2 }\')"
echo "Token GROU: ${TOKEN}"

# GET TOKEN GALEB API
TOKEN_GALEB="$(curl --noproxy \'*\' -XGET -u admin:admin ${GALEB_API}:8000/token 2>1 | jq -r .token)"
echo "Token Galeb API: ${TOKEN_GALEB}"

# CREATE BASE64 CREDENTIALS TO GALEB API
TOKEN_API="$(echo -n admin:${TOKEN_GALEB} | base64 -w 0)"
echo "Base64 GALEB API: ${TOKEN_API}"

# CREATE BALANCEPOLICY
GALEB_BP_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"RoundRobin\\"}" -u admin:admin ${GALEB_API}:8000/balancepolicy 2>1 | jq -r .id)
echo "BalancePolicy ID: ${GALEB_BP_ID}"

# CREATE ENVIRONMENT
GALEB_ENVIRONMENT_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"BE-HOMOLOG\\"}" -u admin:admin ${GALEB_API}:8000/environment 2>1 | jq -r .id)
echo "Environment ID: ${GALEB_ENVIRONMENT_ID}"

# CREATE TEAM
GALEB_TEAM_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"team-test-$RANDOM\\"}" -u admin:admin ${GALEB_API}:8000/team 2>1 | jq -r .id)
echo "Team ID: ${GALEB_TEAM_ID}"

# CREATE PROJECT
GALEB_PROJECT_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"project-test-$RANDOM\\", \\"teams\\": [\\"http://${GALEB_API}/team/${GALEB_TEAM_ID}\\"]}" -u admin:admin ${GALEB_API}:8000/project 2>1 | jq -r .id)
echo "Project ID: ${GALEB_PROJECT_ID}"

# CREATE POOL
GALEB_POOL_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"pool-test-$RANDOM\\", \\"project\\": \\"http://${GALEB_API}/project/${GALEB_PROJECT_ID}\\", \\"environment\\": \\"http://${GALEB_API}/environment/1\\", \\"balancepolicy\\": \\"http://${GALEB_API}/balancepolicy/1\\", \\"hc_tcp_only\\": \\"true\\"}" -u admin:admin ${GALEB_API}:8000/pool 2>1 | jq -r .id)
echo "Pool ID: ${GALEB_POOL_ID}"

# CREATE TARGET
GALEB_TARGET_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"http://${GALEB_TEST_TARGET}:$RANDOM\\", \\"pool\\": \\"http://${GALEB_API}/pool/${GALEB_POOL_ID}\\"}" -u admin:admin ${GALEB_API}:8000/target 2>1 | jq -r .id)
echo "Target ID: ${GALEB_TARGET_ID}"

# CREATE VIRTUALHOST
GALEB_VIRTUALHOST_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"virtualhost-test-$RANDOM\\", \\"project\\": \\"http://${GALEB_API}/project/${GALEB_PROJECT_ID}\\", \\"environments\\": [\\"http://${GALEB_API}/environment/1\\"]}" -u admin:admin ${GALEB_API}:8000/virtualhost 2>1 | jq -r .id)
echo "VirtualHost ID: ${GALEB_VIRTUALHOST_ID}"

# CREATE RULE
GALEB_RULE_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"rule-test-$RANDOM\\", \\"project\\": \\"http://${GALEB_API}/project/${GALEB_PROJECT_ID}\\", \\"pools\\": [\\"http://${GALEB_API}/pool/${GALEB_POOL_ID}\\"], \\"matching\\": \\"/\\" }" -u admin:admin ${GALEB_API}:8000/rule 2>1 | jq -r .id)
echo "Rule ID: ${GALEB_RULE_ID}"

# GET VIRTUALHOST GROUP URL
GALEB_VIRTUALHOST_GROUP_URL=$(curl --noproxy \'*\' http://${GALEB_API}:8000/virtualhost/${GALEB_VIRTUALHOST_ID} -u admin:admin 2>1 | jq -r ._links.virtualhostgroup.href)

# GET VIRTUALHOST GROUP URL
GALEB_VIRTUALHOST_GROUP=$(curl --noproxy \'*\' ${GALEB_VIRTUALHOST_GROUP_URL} -u admin:admin 2>1 | jq -r ._links.self.href)
echo "VirtualHost Group URL: ${GALEB_VIRTUALHOST_GROUP}"

# CREATE RULE ORDERED
GALEB_RULEORDERED_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"rule\\": \\"http://GALEB_API:8000/rule/${GALEB_RULE_ID}\\", \\"environment\\": \\"http://GALEB_API:8000/environment/1\\", \\"virtualhostgroup\\": \\"${GALEB_VIRTUALHOST_GROUP}\\",\\"order\\":1}
" -u admin:admin ${GALEB_API}:8000/ruleordered 2>1 | jq -r .id)
echo "RuleOrdered ID: ${GALEB_RULEORDERED_ID}"

# POST METHOD
echo
echo
echo "========================================="
echo "               POST METHOD"
echo
sendtest post

# GET METHOD
echo
echo
echo "========================================="
echo "               GET METHOD"
echo
sendtest get

# PATCH METHOD
echo
echo
echo "========================================="
echo "              PATCH METHOD"
echo
sendtest patch

# PUT METHOD
echo
echo
echo "========================================="
echo "               PUT METHOD"
echo
sendtest put

#EOF
'''
      }
    }
    stage('Test Legba') {
      steps {
        sh '''#!/bin/bash

# GET TOKEN GROU
TOKEN="$(curl --noproxy \'*\' --silent -I -XGET -u ${GROU_USER}:${GROU_PASSWORD} ${ENDPOINT_GROU}/token/${GROU_PROJECT} | grep \'^x-auth-token:\' | awk \'{ print $2 }\')"
echo "Token GROU: ${TOKEN}"

# SEND LEGBA TEST
echo
echo
echo "=========================================="
echo "              SEND LEGBA TEST"
echo

for file in $(ls $WORKSPACE/jenkins/legba/*.json); do

  JSON=$(cat $file | tr -d \'\\n\' | sed "s,RANDOM,$RANDOM,g" | sed "s,GROU_PROJECT,$GROU_PROJECT," | sed "s,GROU_NOTIFY,$GROU_NOTIFY," | sed "s,GALEB_LEGBA,$GALEB_LEGBA,g" | sed "s,GALEB_ZONE,$GALEB_ZONE,g")

  if jq -e . >/dev/null 2>&1 <<<"$JSON"; then
    echo
    echo
    echo "Parsed JSON (${file}) successfully!"
    echo
  else
    echo
    echo "Failed to parse JSON (${file})! Stopping.."
    break
  fi

  echo $JSON | jq -c . > /tmp/JENKINS_LEGBA_TMP_FILE.json

  RESULT_GROU=$(curl --noproxy \'*\' -H\'content-type:application/json\' -H"x-auth-token:$TOKEN" -XPOST -d@/tmp/JENKINS_LEGBA_TMP_FILE.json ${ENDPOINT_GROU}/tests 2>1)

  rm -f /tmp/JENKINS_LEGBA_TMP_FILE.json

  TEST_STATUS=$(echo $RESULT_GROU | jq -r .status)
  TEST_URL=$(echo $RESULT_GROU | jq -r ._links.self.href)

  echo "Grou Test URL: ${TEST_URL}"
  echo "Grou Test STATUS: ${TEST_STATUS}"

  while [ "${TEST_STATUS}" != "OK" ]
  do
    TEST_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' $TEST_URL 2>1| jq -r .status)
    echo "Grou Test STATUS: ${TEST_STATUS}"
    sleep 5
  done
done

#EOF'''
      }
    }
    stage('Test Router') {
      steps {
        sh '''#!/bin/bash

# GET TOKEN GROU
TOKEN="$(curl --noproxy \'*\' --silent -I -XGET -u ${GROU_USER}:${GROU_PASSWORD} ${ENDPOINT_GROU}/token/${GROU_PROJECT} | grep \'^x-auth-token:\' | awk \'{ print $2 }\')"
echo "Token GROU: ${TOKEN}"

# CREATE BALANCEPOLICY RANDOM
GALEB_BP1_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"Random\\"}" -u admin:admin ${GALEB_API}:8000/balancepolicy 2>1 | jq -r .id)
echo "BalancePolicy RANDOM ID: ${GALEB_BP1_ID}"

# CREATE BALANCEPOLICY LEASTCONN
GALEB_BP2_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"LeastConn\\"}" -u admin:admin ${GALEB_API}:8000/balancepolicy 2>1 | jq -r .id)
echo "BalancePolicy LEASTCONN ID: ${GALEB_BP2_ID}"

# CREATE BALANCEPOLICY HASHSOURCEIP
GALEB_BP3_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"HashSourceIp\\"}" -u admin:admin ${GALEB_API}:8000/balancepolicy 2>1 | jq -r .id)
echo "BalancePolicy HASHSOURCEIP ID: ${GALEB_BP3_ID}"

# CREATE BALANCEPOLICY HASHURIPATH
GALEB_BP4_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"HashUriPath\\"}" -u admin:admin ${GALEB_API}:8000/balancepolicy 2>1 | jq -r .id)
echo "BalancePolicy HASHURIPATH ID: ${GALEB_BP4_ID}"

# CREATE POOL
GALEB_POOL_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"pool-test-jenkins\\", \\"project\\": \\"http://${GALEB_API}/project/1\\", \\"environment\\": \\"http://${GALEB_API}/environment/1\\", \\"balancepolicy\\": \\"http://${GALEB_API}/balancepolicy/1\\", \\"hc_tcp_only\\": \\"false\\", \\"hc_http_method\\": \\"GET\\", \\"hc_http_status_code\\": \\"200\\", \\"hc_body\\": \\"WORKING\\"}" -u admin:admin ${GALEB_API}:8000/pool 2>1 | jq -r .id)
echo "Pool ID: ${GALEB_POOL_ID}"

# CREATE POOL L7
GALEB_POOL_L7_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"pool-l7-test-jenkins\\", \\"project\\": \\"http://${GALEB_API}/project/1\\", \\"environment\\": \\"http://${GALEB_API}/environment/1\\", \\"balancepolicy\\": \\"http://${GALEB_API}/balancepolicy/1\\", \\"hc_tcp_only\\": \\"true\\"}" -u admin:admin ${GALEB_API}:8000/pool 2>1 | jq -r .id)
echo "Pool L7 ID: ${GALEB_POOL_L7_ID}"

# CREATE TARGET 1
GALEB_TARGET1_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"http://${GALEB_TEST_TARGET}:8080\\", \\"pool\\": \\"http://${GALEB_API}/pool/${GALEB_POOL_ID}\\"}" -u admin:admin ${GALEB_API}:8000/target 2>1 | jq -r .id)
echo "Target 1 ID: ${GALEB_TARGET1_ID}"

# CREATE TARGET 2
GALEB_TARGET2_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"http://${GALEB_TEST_TARGET}:8081\\", \\"pool\\": \\"http://${GALEB_API}/pool/${GALEB_POOL_ID}\\"}" -u admin:admin ${GALEB_API}:8000/target 2>1 | jq -r .id)
echo "Target 2 ID: ${GALEB_TARGET2_ID}"

# CREATE TARGET 3
GALEB_TARGET3_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"http://${GALEB_TEST_TARGET}:8082\\", \\"pool\\": \\"http://${GALEB_API}/pool/${GALEB_POOL_ID}\\"}" -u admin:admin ${GALEB_API}:8000/target 2>1 | jq -r .id)
echo "Target 3 ID: ${GALEB_TARGET3_ID}"

# CREATE TARGET L7 4
GALEB_TARGET4_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"http://${GALEB_TEST_TARGET}:8083\\", \\"pool\\": \\"http://${GALEB_API}/pool/${GALEB_POOL_L7_ID}\\"}" -u admin:admin ${GALEB_API}:8000/target 2>1 | jq -r .id)
echo "Target L7 4 ID: ${GALEB_TARGET4_ID}"

# CREATE TARGET L7 5
GALEB_TARGET5_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"http://${GALEB_TEST_TARGET}:8084\\", \\"pool\\": \\"http://${GALEB_API}/pool/${GALEB_POOL_L7_ID}\\"}" -u admin:admin ${GALEB_API}:8000/target 2>1 | jq -r .id)
echo "Target L7 5 ID: ${GALEB_TARGET5_ID}"

# CREATE VIRTUALHOST
GALEB_VIRTUALHOST_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"virtualhost-test-jenkins\\", \\"project\\": \\"http://${GALEB_API}/project/1\\", \\"environments\\": [\\"http://${GALEB_API}/environment/1\\"]}" -u admin:admin ${GALEB_API}:8000/virtualhost 2>1 | jq -r .id)
echo "VirtualHost ID: ${GALEB_VIRTUALHOST_ID}"

# CREATE RULE
GALEB_RULE_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"rule-test-jenkins\\", \\"project\\": \\"http://${GALEB_API}/project/1\\", \\"pools\\": [\\"http://${GALEB_API}/pool/${GALEB_POOL_ID}\\"], \\"matching\\": \\"/\\" }" -u admin:admin ${GALEB_API}:8000/rule 2>1 | jq -r .id)
echo "Rule ID: ${GALEB_RULE_ID}"

# CREATE RULE L7
GALEB_RULE_L7_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"name\\": \\"rule-test-l7-jenkins\\", \\"project\\": \\"http://${GALEB_API}/project/1\\", \\"pools\\": [\\"http://${GALEB_API}/pool/${GALEB_POOL_L7_ID}\\"], \\"matching\\": \\"/*l7.html\\" }" -u admin:admin ${GALEB_API}:8000/rule 2>1 | jq -r .id)
echo "Rule L7 ID: ${GALEB_RULE_L7_ID}"

# GET VIRTUALHOST GROUP URL
GALEB_VIRTUALHOST_GROUP_URL=$(curl --noproxy \'*\' http://${GALEB_API}:8000/virtualhost/${GALEB_VIRTUALHOST_ID} -u admin:admin 2>1 | jq -r ._links.virtualhostgroup.href)

# GET VIRTUALHOST GROUP URL
GALEB_VIRTUALHOST_GROUP=$(curl --noproxy \'*\' ${GALEB_VIRTUALHOST_GROUP_URL} -u admin:admin 2>1 | jq -r ._links.self.href)
echo "VirtualHost Group URL: ${GALEB_VIRTUALHOST_GROUP}"

# CREATE RULE ORDERED
GALEB_RULEORDERED_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"rule\\": \\"http://${GALEB_API}:8000/rule/${GALEB_RULE_ID}\\", \\"environment\\": \\"http://${GALEB_API}:8000/environment/1\\", \\"virtualhostgroup\\": \\"${GALEB_VIRTUALHOST_GROUP}\\",\\"order\\":2}
" -u admin:admin ${GALEB_API}:8000/ruleordered 2>1 | jq -r .id)
echo "RuleOrdered ID: ${GALEB_RULEORDERED_ID}"

# CREATE RULE ORDERED L7
GALEB_RULEORDERED_L7_ID=$(curl --noproxy \'*\' -H\'content-type:application/json\' -X POST -d "{\\"rule\\": \\"http://${GALEB_API}:8000/rule/${GALEB_RULE_L7_ID}\\", \\"environment\\": \\"http://${GALEB_API}:8000/environment/1\\", \\"virtualhostgroup\\": \\"${GALEB_VIRTUALHOST_GROUP}\\",\\"order\\":1}
" -u admin:admin ${GALEB_API}:8000/ruleordered 2>1 | jq -r .id)
echo "RuleOrdered L7 ID: ${GALEB_RULEORDERED_L7_ID}"

# CHECKING HEALTHY
echo
echo
echo "=========================================="
echo "            CHECKING HEALTHY"
echo

for ((i=1;i<=5;i++))
do
  TARGETIDNAME=\\$GALEB_TARGET${i}_ID
  TARGET_ID=`eval echo $TARGETIDNAME`

  echo "Target ID: ${TARGET_ID}"

  TARGET_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/target/${TARGET_ID}/healthStatus 2>1 | jq -r ._embedded.healthstatus[].status)

  echo "Target Healthy STATUS: ${TARGET_STATUS}"

  while [ "${TARGET_STATUS}" != "HEALTHY" ]
  do
    sleep 5
    TARGET_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/target/${TARGET_ID}/healthStatus 2>1 | jq -r ._embedded.healthstatus[].status)
    echo "Target Healthy STATUS: ${TARGET_STATUS}"
  done
  echo
done

# CHECKING SYNC
echo
echo
echo "=========================================="
echo "              CHECKING SYNC"
echo

# CHECK SYNC TARGET
for ((i=1;i<=5;i++))
do
  TARGETIDNAME=\\$GALEB_TARGET${i}_ID
  TARGET_ID=`eval echo $TARGETIDNAME`

  echo "Target ID: ${TARGET_ID}"

  TARGET_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/target/${TARGET_ID} 2>1 | jq -r .status[])

  echo "Target Sync STATUS: ${TARGET_STATUS}"

  while [ "${TARGET_STATUS}" != "OK" ]
  do
    sleep 5
    TARGET_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/target/${TARGET_ID} 2>1 | jq -r .status[])
    echo "Target Sync STATUS: ${TARGET_STATUS}"
  done
  echo
done

# CHECK SYNC POOL
echo "Pool ID: ${GALEB_POOL_ID}"

POOL_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/pool/${GALEB_POOL_ID} 2>1 | jq -r .status[])

echo "Pool Sync STATUS: ${POOL_STATUS}"

while [ "${POOL_STATUS}" != "OK" ]
do
  sleep 5
  POOL_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/pool/${GALEB_POOL_ID} 2>1 | jq -r .status[])
  echo "Pool Sync STATUS: ${POOL_STATUS}"
done
echo

# CHECK SYNC POOL L7
echo "Pool L7 ID: ${GALEB_POOL_L7_ID}"

POOL_L7_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/pool/${GALEB_POOL_L7_ID} 2>1 | jq -r .status[])

echo "Pool L7 Sync STATUS: ${POOL_L7_STATUS}"

while [ "${POOL_L7_STATUS}" != "OK" ]
do
  sleep 5
  POOL_L7_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/pool/${GALEB_POOL_L7_ID} 2>1 | jq -r .status[])
  echo "Pool L7 Sync STATUS: ${POOL_L7_STATUS}"
done
echo

# CHECK SYNC RULE
echo "Rule ID: ${GALEB_RULE_ID}"

RULE_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/rule/${GALEB_RULE_ID} 2>1 | jq -r .status[])

echo "Rule Sync STATUS: ${RULE_STATUS}"

while [ "${RULE_STATUS}" != "OK" ]
do
  sleep 5
  RULE_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/rule/${GALEB_RULE_ID} 2>1 | jq -r .status[])
  echo "Rule Sync STATUS: ${RULE_STATUS}"
done
echo

# CHECK SYNC RULE
echo "Rule L7 ID: ${GALEB_RULE_L7_ID}"

RULE_L7_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/rule/${GALEB_RULE_L7_ID} 2>1 | jq -r .status[])

echo "Rule L7 Sync STATUS: ${RULE_L7_STATUS}"

while [ "${RULE_L7_STATUS}" != "OK" ]
do
  sleep 5
  RULE_L7_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/rule/${GALEB_RULE_L7_ID} 2>1 | jq -r .status[])
  echo "Rule L7 Sync STATUS: ${RULE_L7_STATUS}"
done
echo

# CHECK SYNC RULEORDERED
echo "RuleOrdered ID: ${GALEB_RULEORDERED_ID}"

RULEORDERED_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/ruleordered/${GALEB_RULEORDERED_ID} 2>1 | jq -r .status[])

echo "RuleOrdered Sync STATUS: ${RULEORDERED_STATUS}"

while [ "${RULEORDERED_STATUS}" != "OK" ]
do
  sleep 5
  RULEORDERED_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/ruleordered/${GALEB_RULEORDERED_ID} 2>1 | jq -r .status[])
  echo "RuleOrdered Sync STATUS: ${RULEORDERED_STATUS}"
done
echo

# CHECK SYNC L7 RULEORDERED
echo "RuleOrdered L7 ID: ${GALEB_RULEORDERED_L7_ID}"

RULEORDERED_L7_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/ruleordered/${GALEB_RULEORDERED_L7_ID} 2>1 | jq -r .status[])

echo "RuleOrdered L7 Sync STATUS: ${RULEORDERED_L7_STATUS}"

while [ "${RULEORDERED_L7_STATUS}" != "OK" ]
do
  sleep 5
  RULEORDERED_L7_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/ruleordered/${GALEB_RULEORDERED_L7_ID} 2>1 | jq -r .status[])
  echo "RuleOrdered L7 Sync STATUS: ${RULEORDERED_L7_STATUS}"
done
echo

# CHECK SYNC VIRTUALHOST
echo "VirtualHost ID: ${GALEB_VIRTUALHOST_ID}"

VIRUTLAHOST_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/virtualhost/${GALEB_VIRTUALHOST_ID} 2>1 | jq -r .status[])

echo "VirtualHost Sync STATUS: ${VIRUTLAHOST_STATUS}"

while [ "${VIRUTLAHOST_STATUS}" != "OK" ]
do
  sleep 5
  VIRUTLAHOST_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/virtualhost/${GALEB_VIRTUALHOST_ID} 2>1 | jq -r .status[])
  echo "VirtualHost Sync STATUS: ${VIRUTLAHOST_STATUS}"
done
echo

# SEND ROUTER TEST
echo
echo
echo "=========================================="
echo "             SEND ROUTER TEST"
echo

for file in $(ls $WORKSPACE/jenkins/router/*_get.json); do

  JSON=$(cat $file | tr -d \'\\n\' | sed "s,RANDOM,$RANDOM,g" | sed "s,GROU_PROJECT,$GROU_PROJECT," | sed "s,GROU_NOTIFY,$GROU_NOTIFY," | sed "s,GALEB_ROUTER,$GALEB_ROUTER,g")

  if jq -e . >/dev/null 2>&1 <<<"$JSON"; then
    echo
    echo "Parsed JSON (${file}) successfully!"
    echo
  else
    echo
    echo "Failed to parse JSON (${file})! Stopping.."
    break
  fi

  echo $JSON | jq -c . > /tmp/JENKINS_ROUTER_TMP_FILE.json

  RESULT_GROU=$(curl --noproxy \'*\' -H\'content-type:application/json\' -H"x-auth-token:$TOKEN" -XPOST -d@/tmp/JENKINS_ROUTER_TMP_FILE.json ${ENDPOINT_GROU}/tests 2>1)

  rm -f /tmp/JENKINS_ROUTER_TMP_FILE.json

  TEST_STATUS=$(echo $RESULT_GROU | jq -r .status)
  TEST_URL=$(echo $RESULT_GROU | jq -r ._links.self.href)

  echo "Grou Test URL: ${TEST_URL}"
  echo "Grou Test STATUS: ${TEST_STATUS}"

  while [ "${TEST_STATUS}" != "OK" ]
  do
    TEST_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' $TEST_URL 2>1 | jq -r .status)
    echo "Grou Test STATUS: ${TEST_STATUS}"
    sleep 5
  done
done

# SEND ROUTER TEST BALANCY POLICY
echo
echo
echo "=========================================="
echo "     SEND ROUTER TEST BALANCY POLICY"
echo

for ((i=2;i<=5;i++)); do

  BP_URL="http://${GALEB_API}/balancepolicy/${i}"
  curl --noproxy \'*\' -s -o /dev/null -H\'content-type:application/json\' -X PATCH -d "{\\"balancepolicy\\": \\"${BP_URL}\\"}" -u admin:admin ${GALEB_API}:8000/pool/${GALEB_POOL_ID} 2>1

  # CHECK SYNC POOL
  echo
  echo "BalancePolicy URL: ${BP_URL}"
  echo

  POOL_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/pool/${GALEB_POOL_ID} 2>1 | jq -r .status[])

  echo "Pool Sync STATUS: ${POOL_STATUS}"

  while [ "${POOL_STATUS}" != "OK" ]
  do
    sleep 5
    POOL_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' -u admin:admin ${GALEB_API}:8000/pool/${GALEB_POOL_ID} 2>1 | jq -r .status[])
    echo "Pool Sync STATUS: ${POOL_STATUS}"
  done
  echo

  JSON=$(cat $WORKSPACE/jenkins/router/galeb_router_1_virtualhost_get.json | tr -d \'\\n\' | sed "s,RANDOM,$RANDOM,g" | sed "s,GROU_PROJECT,$GROU_PROJECT," | sed "s,GROU_NOTIFY,$GROU_NOTIFY," | sed "s,GALEB_ROUTER,$GALEB_ROUTER,g")

  if jq -e . >/dev/null 2>&1 <<<"$JSON"; then
    echo
    echo "Parsed JSON (${file}) successfully!"
    echo
  else
    echo
    echo "Failed to parse JSON (${file})! Stopping.."
    break
  fi

  echo $JSON | jq -c . > /tmp/JENKINS_ROUTER_TMP_FILE.json

  RESULT_GROU=$(curl --noproxy \'*\' -H\'content-type:application/json\' -H"x-auth-token:$TOKEN" -XPOST -d@/tmp/JENKINS_ROUTER_TMP_FILE.json ${ENDPOINT_GROU}/tests 2>1)

  rm -f /tmp/JENKINS_ROUTER_TMP_FILE.json

  TEST_STATUS=$(echo $RESULT_GROU | jq -r .status)
  TEST_URL=$(echo $RESULT_GROU | jq -r ._links.self.href)

  echo "Grou Test URL: ${TEST_URL}"
  echo "Grou Test STATUS: ${TEST_STATUS}"

  while [ "${TEST_STATUS}" != "OK" ]
  do
    TEST_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' $TEST_URL 2>1 | jq -r .status)
    echo "Grou Test STATUS: ${TEST_STATUS}"
    sleep 5
  done
done

##POST
for file in $(ls $WORKSPACE/jenkins/router/*_post.json); do

  JSON=$(cat $file | tr -d \'\\n\' | sed "s,RANDOM,$RANDOM,g" | sed "s,GROU_PROJECT,$GROU_PROJECT," | sed "s,GROU_NOTIFY,$GROU_NOTIFY," | sed "s,GALEB_ROUTER,$GALEB_ROUTER,g")

  if jq -e . >/dev/null 2>&1 <<<"$JSON"; then
    echo
    echo "Parsed JSON (${file}) successfully!"
    echo
  else
    echo
    echo "Failed to parse JSON (${file})! Stopping.."
    break
  fi

  echo $JSON | jq -c . > /tmp/JENKINS_ROUTER_TMP_FILE.json

  RESULT_GROU=$(curl --noproxy \'*\' -H\'content-type:application/json\' -H"x-auth-token:$TOKEN" -XPOST -d@/tmp/JENKINS_ROUTER_TMP_FILE.json ${ENDPOINT_GROU}/tests 2>1)

  rm -f /tmp/JENKINS_ROUTER_TMP_FILE.json

  TEST_STATUS=$(echo $RESULT_GROU | jq -r .status)
  TEST_URL=$(echo $RESULT_GROU | jq -r ._links.self.href)

  echo "Grou Test URL: ${TEST_URL}"
  echo "Grou Test STATUS: ${TEST_STATUS}"

  while [ "${TEST_STATUS}" != "OK" ]
  do
    TEST_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' $TEST_URL 2>1 | jq -r .status)
    echo "Grou Test STATUS: ${TEST_STATUS}"
    sleep 5
  done
done
##PATCH
for file in $(ls $WORKSPACE/jenkins/router/*_patch.json); do

  JSON=$(cat $file | tr -d \'\\n\' | sed "s,RANDOM,$RANDOM,g" | sed "s,GROU_PROJECT,$GROU_PROJECT," | sed "s,GROU_NOTIFY,$GROU_NOTIFY," | sed "s,GALEB_ROUTER,$GALEB_ROUTER,g")

  if jq -e . >/dev/null 2>&1 <<<"$JSON"; then
    echo
    echo "Parsed JSON (${file}) successfully!"
    echo
  else
    echo
    echo "Failed to parse JSON (${file})! Stopping.."
    break
  fi

  echo $JSON | jq -c . > /tmp/JENKINS_ROUTER_TMP_FILE.json

  RESULT_GROU=$(curl --noproxy \'*\' -H\'content-type:application/json\' -H"x-auth-token:$TOKEN" -XPOST -d@/tmp/JENKINS_ROUTER_TMP_FILE.json ${ENDPOINT_GROU}/tests 2>1)

  rm -f /tmp/JENKINS_ROUTER_TMP_FILE.json

  TEST_STATUS=$(echo $RESULT_GROU | jq -r .status)
  TEST_URL=$(echo $RESULT_GROU | jq -r ._links.self.href)

  echo "Grou Test URL: ${TEST_URL}"
  echo "Grou Test STATUS: ${TEST_STATUS}"

  while [ "${TEST_STATUS}" != "OK" ]
  do
    TEST_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' $TEST_URL 2>1 | jq -r .status)
    echo "Grou Test STATUS: ${TEST_STATUS}"
    sleep 5
  done
done
##PATCH
for file in $(ls $WORKSPACE/jenkins/router/*_put.json); do

  JSON=$(cat $file | tr -d \'\\n\' | sed "s,RANDOM,$RANDOM,g" | sed "s,GROU_PROJECT,$GROU_PROJECT," | sed "s,GROU_NOTIFY,$GROU_NOTIFY," | sed "s,GALEB_ROUTER,$GALEB_ROUTER,g")

  if jq -e . >/dev/null 2>&1 <<<"$JSON"; then
    echo
    echo "Parsed JSON (${file}) successfully!"
    echo
  else
    echo
    echo "Failed to parse JSON (${file})! Stopping.."
    break
  fi

  echo $JSON | jq -c . > /tmp/JENKINS_ROUTER_TMP_FILE.json

  RESULT_GROU=$(curl --noproxy \'*\' -H\'content-type:application/json\' -H"x-auth-token:$TOKEN" -XPOST -d@/tmp/JENKINS_ROUTER_TMP_FILE.json ${ENDPOINT_GROU}/tests 2>1)

  rm -f /tmp/JENKINS_ROUTER_TMP_FILE.json

  TEST_STATUS=$(echo $RESULT_GROU | jq -r .status)
  TEST_URL=$(echo $RESULT_GROU | jq -r ._links.self.href)

  echo "Grou Test URL: ${TEST_URL}"
  echo "Grou Test STATUS: ${TEST_STATUS}"

  while [ "${TEST_STATUS}" != "OK" ]
  do
    TEST_STATUS=$(curl --noproxy \'*\' -H\'content-type:application/json\' $TEST_URL 2>1 | jq -r .status)
    echo "Grou Test STATUS: ${TEST_STATUS}"
    sleep 5
  done
done
#EOF'''
      }
    }
  }
}

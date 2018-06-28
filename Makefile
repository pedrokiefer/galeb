RPM_VER=$(GALEB_VERSION)
VERSION=${RPM_VER}
RELEASE=$(shell date +%y%m%d%H%M)
SERVICES=router api legba kratos health

deploy-snapshot:
	mvn clean install -DskipTests deploy:deploy -DaltDeploymentRepository=oss-jfrog::default::http://oss.jfrog.org/artifactory/oss-snapshot-local

galeb: clean
	mvn package -DskipTests

test:
	mvn test

clean:
	mvn clean
	for service in ${SERVICES} ; do \
		rm -f dists/galeb-$$service-${RPM_VER}*.rpm; \
	done

dist: galeb
	type fpm > /dev/null 2>&1 && \
    for service in ${SERVICES} ; do \
        old=$$(pwd) && \
        cd $$service/target && \
        mkdir -p lib conf && \
        echo "#version ${VERSION}" > VERSION && \
        git show --summary >> lib/VERSION && \
        cp -av ../../wrapper lib/ || true && \
        cp -v ../wrapper.conf conf/ || true && \
        cp -v ../log4j.xml conf/ || true && \
        cp -av ../../scripts . || true  && \
        cp -av ../scripts . || true && \
        cp -av ../../initscript . || true && \
        cp -v galeb-$$service-${VERSION}-SNAPSHOT.jar lib/galeb-$$service.jar && \
        fpm -s dir \
            --rpm-rpmbuild-define '_binaries_in_noarch_packages_terminate_build 0' \
            -t rpm \
            -n "galeb-new-$$service" \
            -v ${RPM_VER} \
            --iteration ${RELEASE}.el7 \
            -a noarch \
            --rpm-os linux \
            -m 'Galeb <galeb@corp.globo.com>' \
            --url 'http://galeb.io' \
            --vendor 'Globo.com' \
            --description "Galeb $$service service" \
            --after-install scripts/postinstall \
            -f -p ../../dists/galeb-new-$$service-${RPM_VER}.el7.noarch.rpm lib/=/opt/galeb/lib/ scripts/=/opt/galeb/scripts/ conf/=/opt/galeb/conf/ initscript=/etc/init.d/galeb && \
        cd $$old; \
    done

doc:
	cd core/docs && rm -rf html && doxygen Doxyfile && \
	cd ../../health/docs && rm -rf html && doxygen Doxyfile && \
	cd ../../router/docs && rm -rf html && doxygen Doxyfile && \
  cd ../..

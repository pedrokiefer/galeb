package io.galeb.api.repository.custom;

import io.galeb.api.services.StatusService;
import io.galeb.core.entity.AbstractEntity;
import io.galeb.core.entity.Project;
import io.galeb.core.entity.VirtualhostGroup;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class VirtualhostGroupRepositoryImpl extends AbstractRepositoryImplementation<VirtualhostGroup> implements VirtualhostGroupRepositoryCustom, WithRoles {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StatusService statusService;

    @PostConstruct
    private void init() {
        setSimpleJpaRepository(VirtualhostGroup.class, em);
        setStatusService(statusService);
    }

    @Override
    public Set<String> roles(Object principal, Object criteria) {
        return Collections.emptySet();
    }

    @Override
    protected long getProjectId(Object criteria) {
        VirtualhostGroup virtualhostGroup = null;
        try {
            virtualhostGroup = em.find(VirtualhostGroup.class, ((VirtualhostGroup) criteria).getId());
        } catch (Exception ignored) {}
        if (virtualhostGroup == null) {
            return -1L;
        }
        List<Project> projects = em.createQuery("SELECT p FROM Project p INNER JOIN p.virtualhosts v WHERE v.virtualhostgroup.id = :id", Project.class)
                .setParameter("id", virtualhostGroup.getId())
                .getResultList();
        if (projects == null || projects.isEmpty()) {
            return -1;
        }
        return projects.stream().map(AbstractEntity::getId).findAny().orElse(-1L);
    }
}

package io.galeb.api.repository.custom;

import io.galeb.api.repository.EnvironmentRepository;
import io.galeb.api.services.StatusService;
import io.galeb.core.entity.AbstractEntity;
import io.galeb.core.entity.Environment;
import io.galeb.core.entity.Project;
import io.galeb.core.entity.RuleOrdered;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class RuleOrderedRepositoryImpl extends AbstractRepositoryImplementation<RuleOrdered> implements RuleOrderedRepositoryCustom, WithRoles {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StatusService statusService;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @PostConstruct
    private void init() {
        setSimpleJpaRepository(RuleOrdered.class, em);
        setStatusService(statusService);
    }

    @Override
    protected Set<Environment> getAllEnvironments(AbstractEntity entity) {
        return environmentRepository.findAllByRuleOrderedId(entity.getId());
    }

    @Override
    public Set<String> roles(Object principal, Object criteria) {
        return Collections.emptySet();
    }

    @Override
    protected long getProjectId(Object criteria) {
        RuleOrdered ruleOrdered = null;
        try {
           ruleOrdered  = em.find(RuleOrdered.class, ((RuleOrdered) criteria).getId());
        } catch (Exception ignored) {}
        if (ruleOrdered == null) {
            return -1L;
        }
        List<Project> projects = em.createQuery("SELECT p FROM Project p INNER JOIN p.rules r INNER JOIN r.rulesOrdered ro WHERE ro.id = :id", Project.class)
                .setParameter("id", ruleOrdered.getId())
                .getResultList();
        if (projects == null || projects.isEmpty()) {
            return -1;
        }
        return projects.stream().map(AbstractEntity::getId).findAny().orElse(-1L);
    }
}

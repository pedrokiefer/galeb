package io.galeb.api.dao;

import io.galeb.core.entity.AbstractEntity;
import io.galeb.core.entity.Project;
import io.galeb.core.entity.RoleGroup;
import io.galeb.core.entity.Team;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class GenericDaoService {

    @PersistenceContext
    private EntityManager em;

    public EntityManager entityManager() {
        return em;
    }

//    @Cacheable(value = "findOne", key = "{ #classEntity.name, #id }")
    public AbstractEntity findOne(Class<? extends AbstractEntity> classEntity, Long id) {
        return em.find(classEntity, id);
    }

//    @Cacheable(value = "findByName", key = "{ #classEntity.name, #name }")
    public AbstractEntity findByName(Class<? extends AbstractEntity> classEntity, String name) {
        return em.createQuery("SELECT e FROM " + classEntity.getSimpleName() + " e WHERE e.name = :name", classEntity)
            .setParameter("name", name)
            .getSingleResult();
    }

//    @Cacheable(value = "findAll", key = "{ #entityClass.name, #pageable }")
    public List<? extends AbstractEntity> findAll(Class<? extends AbstractEntity> entityClass, Pageable pageable) {
        TypedQuery<? extends AbstractEntity> query = em.createQuery("SELECT DISTINCT entity From " + entityClass.getSimpleName() + " entity", entityClass);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

//    @Cacheable(value = "findAllNamed", key = "{ #namedquery, #entityClass.name, #username, #pageable.offset, #pageable.pageNumber, #pageable.pageSize, #pageable }")
    public List<? extends AbstractEntity> findAllNamed(String namedquery, Class<? extends AbstractEntity> entityClass, String username, Pageable pageable) {
        TypedQuery<? extends AbstractEntity> query = em.createNamedQuery(namedquery, entityClass).setParameter("username", username);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

//    @Cacheable(value = "roleGroupsFromTeams", key = "#accountId")
    public List<RoleGroup> roleGroupsFromTeams(Long accountId) {
        return rolegroupsNamedQuery("roleGroupsFromTeams", accountId);
    }

//    @Cacheable(value = "roleGroupsFromAccount", key = "#accountId")
    public List<RoleGroup> roleGroupsFromAccount(Long accountId) {
        return rolegroupsNamedQuery("roleGroupsFromAccount", accountId);
    }

//    @Cacheable(value = "roleGroupsFromProjectByAccountId", key = "#accountId")
    public List<RoleGroup> roleGroupsFromProjectByAccountId(Long accountId) {
        return rolegroupsNamedQuery("roleGroupsFromProjectByAccountId", accountId);
    }

    private List<RoleGroup> rolegroupsNamedQuery(String namedquery, long id) {
        return em.createNamedQuery(namedquery, RoleGroup.class)
            .setParameter("id", id)
            .getResultList();
    }

//    @Cacheable(value = "projectsHealthStatus", key = "#id")
    public List<Project> projectsHealthStatus(Long id) {
        return projectsNamedQuery("projectHealthStatus", id);
    }

//    @Cacheable(value = "projectsFromRuleOrdered", key = "#id")
    public List<Project> projectsFromRuleOrdered(Long id) {
        return projectsNamedQuery("projectsFromRuleOrdered", id);
    }

//    @Cacheable(value = "projectsFromTarget", key = "#id")
    public List<Project> projectsFromTarget(Long id) {
        return projectsNamedQuery("projectFromTarget", id);
    }

//    @Cacheable(value = "projectFromVirtualhostGroup", key = "#id")
    public List<Project> projectFromVirtualhostGroup(Long id) {
        return projectsNamedQuery("projectFromVirtualhostGroup", id);
    }

    private List<Project> projectsNamedQuery(String namedquery, long id) {
        return em.createNamedQuery(namedquery, Project.class)
            .setParameter("id", id)
            .getResultList();
    }

//    @Cacheable(value = "projectLinkedToAccount", key = "{ #accountId, #projectId }")
    public List<Project> projectLinkedToAccount(Long accountId, Long projectId) {
        return em.createNamedQuery("projectLinkedToAccount", Project.class)
            .setParameter("account_id", accountId)
            .setParameter("project_id", projectId).getResultList();
    }

//    @Cacheable(value = "roleGroupsFromProject", key = "{ #accountId, #projectId }")
    public List<RoleGroup> roleGroupsFromProject(Long accountId, Long projectId) {
        return em.createNamedQuery("roleGroupsFromProject", RoleGroup.class)
            .setParameter("account_id", accountId)
            .setParameter("project_id", projectId)
            .getResultList();
    }

//    @Cacheable(value = "teamLinkedToAccount", key = "{ #accountId, #teamId }")
    public List<Team> teamLinkedToAccount(Long accountId, Long teamId) {
        return em.createNamedQuery("teamLinkedToAccount", Team.class)
            .setParameter("account_id", accountId)
            .setParameter("team_id", teamId).getResultList();
    }

}
SET autocommit=0;
START TRANSACTION;
INSERT INTO rolegroup (created_at, created_by, last_modified_at, last_modified_by, version, quarantine, name) VALUES (NOW(), 'system', NOW(), 'system', 0, false, 'SUPER_ADMIN');
SELECT @A:=id FROM rolegroup WHERE rolegroup.name = 'SUPER_ADMIN';
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ACCOUNT_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ACCOUNT_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ACCOUNT_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ACCOUNT_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ACCOUNT_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ACCOUNT_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ACCOUNT_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'BALANCEPOLICY_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'BALANCEPOLICY_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'BALANCEPOLICY_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'BALANCEPOLICY_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'BALANCEPOLICY_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'BALANCEPOLICY_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'BALANCEPOLICY_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ENVIRONMENT_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ENVIRONMENT_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ENVIRONMENT_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ENVIRONMENT_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ENVIRONMENT_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ENVIRONMENT_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ENVIRONMENT_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHCHECK_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHCHECK_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHCHECK_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHCHECK_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHCHECK_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHCHECK_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHCHECK_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHSTATUS_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHSTATUS_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHSTATUS_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHSTATUS_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHSTATUS_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHSTATUS_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'HEALTHSTATUS_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'POOL_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'POOL_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'POOL_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'POOL_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'POOL_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'POOL_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'POOL_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'PROJECT_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'PROJECT_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'PROJECT_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'PROJECT_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'PROJECT_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'PROJECT_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'PROJECT_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ROLEGROUP_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ROLEGROUP_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ROLEGROUP_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ROLEGROUP_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ROLEGROUP_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ROLEGROUP_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'ROLEGROUP_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULE_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULE_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULE_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULE_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULE_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULE_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULE_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULEORDERED_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULEORDERED_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULEORDERED_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULEORDERED_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULEORDERED_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULEORDERED_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'RULEORDERED_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TARGET_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TARGET_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TARGET_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TARGET_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TARGET_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TARGET_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TARGET_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TEAM_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TEAM_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TEAM_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TEAM_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TEAM_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TEAM_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'TEAM_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOST_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOST_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOST_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOST_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOST_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOST_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOST_ADMIN');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOSTGROUP_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOSTGROUP_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOSTGROUP_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOSTGROUP_DELETE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOSTGROUP_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOSTGROUP_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@A, 'VIRTUALHOSTGROUP_ADMIN');
COMMIT;
START TRANSACTION;
INSERT INTO rolegroup (created_at, created_by, last_modified_at, last_modified_by, version, quarantine, name) VALUES (NOW(), 'system', NOW(), 'system', 0, false, 'TEAM_DEFAULT');
SELECT @D:=id FROM rolegroup WHERE rolegroup.name = 'TEAM_DEFAULT';
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@D, 'TEAM_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@D, 'PROJECT_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@D, 'PROJECT_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@D, 'PROJECT_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@D, 'PROJECT_DELETE');
COMMIT;
START TRANSACTION;
INSERT INTO rolegroup (created_at, created_by, last_modified_at, last_modified_by, version, quarantine, name) VALUES (NOW(), 'system', NOW(), 'system', 0, false, 'PROJECT_DEFAULT');
SELECT @E:=id FROM rolegroup WHERE rolegroup.name = 'PROJECT_DEFAULT';
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'BALANCEPOLICY_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'BALANCEPOLICY_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'ENVIRONMENT_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'ENVIRONMENT_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'HEALTHCHECK_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'HEALTHCHECK_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'HEALTHSTATUS_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'HEALTHSTATUS_VIEW_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'POOL_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'POOL_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'POOL_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'POOL_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'RULE_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'RULE_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'RULE_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'RULE_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'RULEORDERED_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'RULEORDERED_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'RULEORDERED_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'RULEORDERED_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'TARGET_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'TARGET_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'TARGET_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'TARGET_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'VIRTUALHOST_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'VIRTUALHOST_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'VIRTUALHOST_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'VIRTUALHOST_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'VIRTUALHOSTGROUP_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'VIRTUALHOSTGROUP_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'VIRTUALHOSTGROUP_SAVE_ALL');
COMMIT;
START TRANSACTION;
INSERT INTO rolegroup (created_at, created_by, last_modified_at, last_modified_by, version, quarantine, name) VALUES (NOW(), 'system', NOW(), 'system', 0, false, 'USER_DEFAULT');
SELECT @F:=id FROM rolegroup WHERE rolegroup.name = 'USER_DEFAULT';
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@F, 'TEAM_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@F, 'TEAM_SAVE_ALL');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@F, 'TEAM_VIEW');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@E, 'TEAM_DELETE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@F, 'ACCOUNT_SAVE');
INSERT INTO rolegroup_roles (rolegroup_id, role) VALUES (@F, 'ACCOUNT_VIEW');
COMMIT;
SET autocommit=1;
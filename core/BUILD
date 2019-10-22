load("@rules_java//java:defs.bzl", "java_library")

java_library (
    name = "core",
    srcs = glob(["src/main/java/**/*.java"]),
    deps = [
        "@maven//:com_github_jnr_jnr_posix",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:commons_logging_commons_logging",
        "@maven//:org_springframework_boot_spring_boot_starter_parent",
        "@maven//:org_springframework_spring_core",
        "@maven//:javax_persistence_javax_persistence_api",
        "@maven//:org_springframework_data_spring_data_commons",
        "@maven//:org_springframework_data_spring_data_jpa",
        "@maven//:org_hibernate_hibernate_core",
        "@maven//:org_springframework_security_spring_security_core",
        "@maven//:com_google_code_gson_gson",
        "@maven//:org_apache_commons_commons_lang3",
        "@maven//:org_slf4j_slf4j_api",
    ],
    visibility = ["//visibility:public"]
)
FROM sonatype/nexus3:latest
ENV INSTALL4J_ADD_VM_PARAMS="-Xms512m -Xmx1024m -XX:MaxDirectMemorySize=2G -Djava.util.prefs.userRoot=/opt/sonatype/sonatype-work/nexus3/javaprefs"
ENV NEXUS_SECURITY_RANDOMPASSWORD false

EXPOSE 8081 8082
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=10 \
  CMD curl -f http://localhost:8081/ || exit 1


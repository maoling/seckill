### Describe what this PR does / why we need it


### Does this pull request fix one issue?

<!--If that, add "issue ZOOKEEPER-xxxx" below in the next line. For example, issue [ZOOKEEPER-4521](https://issues.apache.org/jira/browse/ZOOKEEPER-4521). Otherwise, add a blank line" -->

issue [ZOOKEEPER-xxxx](https://issues.apache.org/jira/browse/ZOOKEEPER-xxxx)

### Describe how you did it


### Describe how to verify it


### Checklist
<!-- [x]: check the box -->

- [] You have run the whole test suit in the local and all the unit cases have passed <!-- mvn verify spotbugs:check checkstyle:check -Pfull-build -Dsurefire-forkcount=4 -->
- [] CI build status all becomes green(checkstyle, spotbugs, unit cases, etc)
- [] You have verified no typos
- [] You have added documentation if needed (including upgrade docs)
- [] You have provided a benchmark report if this PR has a performance improvement
- [] You have tested the compatibility if this PR has break changes
- [] You have verified the new added dependencies are licensed in a way that is compatible with [ASF 2.0](http://www.apache.org/legal/resolved.html#category-a)


### Special notes for reviewers



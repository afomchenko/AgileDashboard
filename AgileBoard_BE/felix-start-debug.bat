@echo OFF  
cd agileboard-felix
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar bin\felix.jar %*
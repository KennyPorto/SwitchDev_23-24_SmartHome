scp -i ~/.ssh/id_rsa_dei target/switch2023project_g3-1.0-SNAPSHOT.war root@10.9.24.154:/opt/tomcat/webapps/

ssh -i ~/.ssh/id_rsa_dei root@10.9.24.154 'sudo systemctl restart tomcat'
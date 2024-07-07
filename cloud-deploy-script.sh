#!/bin/bash

# Function to handle errors
handle_error() {
    echo "Error occurred in line $1: $2"
    exit 1
}

trap 'handle_error $LINENO "$BASH_COMMAND"' ERR

#USERNAME=$(whoami)

# build the app
./mvnw package

## Copy the generated files
#cp -r frontend/dist/ /c/Users/"$USERNAME"/Desktop/dpl
#cp target/switch2023project_g3-1.0-SNAPSHOT.war /c/Users/"$USERNAME"/Desktop/dpl
#
## shellcheck disable=SC2164
#cd /c/Users/"$USERNAME"/Desktop/dpl

scp -i ~/.ssh/id_rsa_dei target/switch2023project_g3-1.0-SNAPSHOT.war root@10.9.24.154:/opt/tomcat/webapps/
scp -i ~/.ssh/id_rsa_dei -r frontend/dist/* root@10.9.24.154:/var/www/dist/

ssh -i ~/.ssh/id_rsa_dei root@10.9.24.154 'sudo service nginx restart && sudo systemctl restart tomcat'

echo 'Restarting the services and deploying the app!'
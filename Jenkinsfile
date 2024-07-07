pipeline {
  agent any
  tools {
    maven "MAVEN3"
    jdk "OracleJDK17"
  }
  stages {
    stage ('Fetch Code') {
      steps {
        git branch: 'main', credentialsId: 'GitHubIsep', url: 'https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-grupo-3.git'
      }
    }

    stage ('Build code') {
      steps {
        sh 'mvn clean package -DskipTests'
      }

      post {
        success {
          echo 'Archiving artifacts now.'
          archiveArtifacts artifacts: '**/*.jar'
        }
      }
    }

    stage('Unit Tests') {
      steps {
        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
          sh 'mvn test'
        }
      }
    }

    stage('Checkstyle Analysis') {
      steps {
        sh 'mvn checkstyle:checkstyle'
      }
    }

    stage('Deploy') {
      steps {
         sh 'cp target/switch2023project_g3-1.0-SNAPSHOT.war /opt/tomcat/webapps/'
         sh 'cp -r frontend/dist/* /var/www/dist/'
         echo 'Restarting Services...'
         sh 'sudo systemctl restart tomcat && sudo systemctl restart nginx'
      }
    }
  }
}
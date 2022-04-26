pipeline {
    agent any

    tools {
        maven "jenkins-maven"
    }

    stages {
        stage("Build and test") {
            steps {
                sh "mvn clean deploy"
            }
        }
    }
}
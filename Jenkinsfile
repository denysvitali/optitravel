pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew build' 
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true 
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test' 
                junit '**/target/*.xml' 
            }
        }
    }
}
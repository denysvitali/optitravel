pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'gradle build' 
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true 
            }
        }

        stage('Test') {
            steps {
                sh 'gradle test' 
                junit '**/target/*.xml' 
            }
        }
    }
}
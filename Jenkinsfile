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
                junit 'build/test-results/**/*.xml'
            }
        }
    }
}
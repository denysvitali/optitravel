node {
    stage('Clean') {
        deleteDir()
    }

    stage('Checkout') {
        checkout scm
    }

    stage('Build') {
        sh './gradlew build'
        archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
    }

    stage('Test') {
        sh './gradlew test'
        junit 'build/test-results/**/*.xml'
    }
}

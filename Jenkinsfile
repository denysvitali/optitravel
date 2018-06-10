node {

    stage('Clean') {
        deleteDir()
    }

    stage('Checkout') {
        scmVars = checkout scm
    }

    stage('Build') {
        sh 'java -version'
        sh './gradlew clean build'
        archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
    }

    stage('Test') {
        sh './gradlew test'
        junit 'build/test-results/**/*.xml'
    }

    stage('Push to SUPSI') {
        withCredentials([usernameColonPassword(credentialsId: 'jenkins-supsi-scm-login', variable: 'USRPW')]) {
            sh "git push https://${USRPW}@scm.ti-edu.ch/repogit/labingsw012017201812.git refs/remotes/origin/*:refs/heads/*"
        }
    }
}

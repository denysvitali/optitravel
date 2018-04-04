node {

    stage('Clean') {
        deleteDir()
    }

    stage('Checkout') {
        scmVars = checkout scm
    }

    stage('Build') {
        sh './gradlew build'
        archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
    }

    stage('Test') {
        sh './gradlew test'
        junit 'build/test-results/**/*.xml'
    }

    stage('Push to SUPSI') {
        withCredentials([usernameColonPassword(credentialsId: 'jenkins-supsi-scm-login', variable: 'USRPW')]) {
            sh "git remote add supsi https://${USRPW}@scm.ti-edu.ch/repogit/labingsw012017201812.git"
        }
        sh "git push origin/${scmVars.GIT_BRANCH} supsi/${scmVars.GIT_BRANCH}"
    }
}

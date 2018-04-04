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

    stage('Push to SUPSI') {
        SUPSI_SCM_LOGIN = credentials('jenkins-supsi-scm-login')
        sh "git remote add supsi https://${SUPSI_SCM_LOGIN_USR}:${SUPSI_SCM_LOGIN_PSW}@scm.ti-edu.ch/repogit/labingsw012017201812.git"
        sh "git push origin/\$(git rev-parse --abbrev-ref HEAD) supsi/\$(git rev-parse --abbrev-ref HEAD)"
    }
}

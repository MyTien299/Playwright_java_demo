pipeline {

    environment {
        MAVEN_OPTS = ''
		NODE       = "${params.NODE}"
        ENV        = "${params.ENV}"
		XMLPATH    = "${params.XMLPATH}"
        JAVA_HOME  = 'C:\\Program Files\\Java\\jdk-23'
        PATH       = "${env.JAVA_HOME}\\bin;${env.PATH}"
    }

	agent any

    stages {
        stage('Run Tests') {
            steps {
                dir('uat-playwright') {
                    bat "mvn --version"
                    bat "mvn test -DsuiteXmlFile=${XMLPATH}"
                }
            }
        }
    }

    post {
        always {
            dir('uat-playwright') {
                archiveArtifacts artifacts: "src/test/reports/**/*.html, src/test/reports/*.csv", fingerprint: true, allowEmptyArchive: true
            }
            bat '''rmdir /S /Q "uat-playwright" 2>nul'''
        }
    }
}

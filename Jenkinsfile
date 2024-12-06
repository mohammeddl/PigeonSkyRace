pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'mohammeddl/pigeonskyrace-app'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:${env.BUILD_NUMBER} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'dockerhub-credentials', url: 'https://index.docker.io/v1/']) {
                    sh "docker push ${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
                }
            }
        }

        stage('Deploy') {
            steps {
                sh "docker-compose down && docker-compose up -d"
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            junit '**/target/surefire-reports/*.xml'
        }
        failure {
            mail to: 'daali.22.ssss@gmail.com',
                    subject: "Failed Pipeline: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: "Something went wrong. Check the Jenkins log."
        }
    }
}

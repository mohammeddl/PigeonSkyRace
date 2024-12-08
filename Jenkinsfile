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

        stage('Test') {
            steps {
                sh 'mvn test'
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
            echo "Pipeline completed"
        }
        failure {
            echo "Pipeline failed"
        }
    }
}

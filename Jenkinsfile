pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'mohammeddl/pigeonskyrace-app'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "DEBUG: Starting Checkout stage..."
                    deleteDir() 
                }
                script {
                    echo "DEBUG: Fetching the repository from Git..."
                    git branch: 'Jenkins', url: 'https://github.com/mohammeddl/PigeonSkyRace.git'
                }
                script {
                    echo "DEBUG: Checkout stage completed successfully!"
                }
            }
        }

        stage('Build') {
            agent {
                docker {
                    image 'maven:3.8.8-eclipse-temurin-21'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                script {
                    echo "DEBUG: Starting Build stage..."
                }
                sh 'mvn clean package -DskipTests'
                script {
                    echo "DEBUG: Build stage completed successfully!"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "DEBUG: Starting Build Docker Image stage..."
                }
                sh "docker build -t ${DOCKER_IMAGE}:${env.BUILD_NUMBER} ."
                script {
                    echo "DEBUG: Docker Image built successfully!"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo "DEBUG: Starting Push Docker Image stage..."
                }
                withDockerRegistry([credentialsId: 'dockerhub-credentials', url: '']) {
                    sh "docker push ${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
                }
                script {
                    echo "DEBUG: Docker Image pushed successfully!"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "DEBUG: Starting Deploy stage..."
                }
                sh "docker-compose down && docker-compose up -d"
                script {
                    echo "DEBUG: Deploy stage completed successfully!"
                }
            }
        }
    }

    post {
        always {
            echo 'DEBUG: Pipeline completed successfully'
        }
        failure {
            echo 'DEBUG: Pipeline failed'
        }
    }
}

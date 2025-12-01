pipeline {
    agent any

    environment {
        AWS_REGION = 'eu-west-3'
        AWS_ACCOUNT_ID = '118320467932'
        ECR_REPO_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/docker_repo"
        IMAGE_NAME = 'crudapp'
        IMAGE_TAG = 'latest'
        AWS_CREDENTIAL_NAME = 'aws_credentials'
        DEPLOY_HOST = '13.36.197.61'
        JWT_SECRET = credentials('JWT_SECRET')
        JWT_ISSUER = credentials('JWT_ISSUER')
        DB_PROD_URL = credentials('DB_PROD_URL')
        DB_PROD_USER = credentials('DB_PROD_USER')
        DB_PROD_PASSWORD = credentials('DB_PROD_PASSWORD')
        AWS_ACCESS_KEY = credentials('AWS_ACCESS_KEY')
        AWS_SECRET_KEY = credentials('AWS_SECRET_KEY')
        SPRING_PROFILES_ACTIVE = 'prod'

    }
    stages {
          /* stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/ChoiMinhyeok1226/aws-test.git/',
                credentialsId: 'github_token'
            }
        } */
        stage('Prepare application.yml') {
            steps {
                script {


                    // application.yml 치환
                    sh '''
                        sed -i "s#\\\${DB_PROD_URL}#${DB_PROD_URL}#g" $WORKSPACE/src/main/resources/application.yml
                        sed -i "s#\\\${DB_PROD_USER}#${DB_PROD_USER}#g" $WORKSPACE/src/main/resources/application.yml
                        sed -i "s#\\\${DB_PROD_PASSWORD}#${DB_PROD_PASSWORD}#g" $WORKSPACE/src/main/resources/application.yml
                        sed -i "s#\\\${JWT_SECRET}#${JWT_SECRET}#g" $WORKSPACE/src/main/resources/application.yml
                        sed -i "s#\\\${JWT_ISSUER}#${JWT_ISSUER}#g" $WORKSPACE/src/main/resources/application.yml
                        sed -i "s#\\\${AWS_ACCESS_KEY}#${AWS_ACCESS_KEY}#g" $WORKSPACE/src/main/resources/application.yml
                        sed -i "s#\\\${AWS_SECRET_KEY}#${AWS_SECRET_KEY}#g" $WORKSPACE/src/main/resources/application.yml

                    '''
                }
            }
        }
       stage('Build Application') {
            steps {
                sh '''
                    chmod +x gradlew
                    ./gradlew clean build
                '''
                echo 'Build completed successfully.'
            }
            //  ./gradlew clean build -x test || true  //테스트무시할떄

        }
        stage('Docker Build') {
            steps {
                script {
                    sh """
                        docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .
                        docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${ECR_REPO_URI}:${BUILD_NUMBER}
                    """
                }
            }
            post {
                success {
                    echo 'Docker Build succeeded.'
                }
                failure {
                    error 'Docker Build failed.' // exit pipeline
                }
            }
        }

        stage('AWS ECR Login'){
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws_credentials']]) {

                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
                }
            }
        }

        stage('ECR Push') {
            steps {
                script {
                    // Docker 이미지 태그 및 푸시
                    sh """
                        docker push ${ECR_REPO_URI}:${BUILD_NUMBER}
                    """
                }
            }
             post {
                success {
                    echo 'Image upload to ECR succeeded.'
                }
                failure {
                    error 'Image upload to ECR failed.'
                }
            }
        }

        stage('Deploy to AWS EC2 VM') {
            steps {
                sshagent(credentials: ['deploy-ssh-key']) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ubuntu@${DEPLOY_HOST} '
                            set -x
                            aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com &&
                            docker pull ${ECR_REPO_URI}:${BUILD_NUMBER} &&
                            docker stop ${IMAGE_NAME} || true &&
                            docker rm -f ${IMAGE_NAME} || true &&
                            docker run -d -p 80:8090 --name ${IMAGE_NAME} ${ECR_REPO_URI}:${BUILD_NUMBER}
                        '
                    """
                }
            }
            post {
                success {
                    echo 'Application successfully deployed to EC2.'
                }
                failure {
                    error 'Application deployment to EC2 failed.'
                }
            }
        }


    }
}
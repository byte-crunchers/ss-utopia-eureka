pipeline {
    options
    {
      buildDiscarder(logRotator(numToKeepStr: '3'))
    }
    agent any

    environment {
      AWS_DEFAULT_REGION="us-east-1" 
      IMAGE_REPO_NAME="ss-utopia-eureka"
      IMAGE_REPO_NAME2="ss-utopia-gateway"
      IMAGE_TAG="latest"
      REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
      REPOSITORY_URI2 = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME2}"
    }

    stages {
      stage('checkout') {
        steps {
          git branch: 'features_kubernetes', credentialsId: 'git_login', url: 'https://github.com/byte-crunchers/ss-utopia-eureka.git'
        }
      }
      stage('get_commit_msg') {
        steps {
            script {
                env.GIT_COMMIT_MSG = sh (script: 'git log -1 --pretty=format:"%H"', returnStdout: true).trim()
            }
        }
      }
      stage("Clean install eureka") {  
        steps {
          dir('EurekaServer') {
            withMaven(maven: 'maven') {
              sh 'mvn clean package -Dmaven.test.skip' 
            }
          }
        }
      }
      stage("Clean install gateway") {  
        steps {
          dir('Gateway'){
            withMaven(maven: 'maven') {
            sh 'mvn clean package -Dmaven.test.skip' 
            }
          }
        }
      }
        
        stage("SonarQube analysis Eureka") {
            steps {
              withSonarQubeEnv('SonarQube') {
                dir('EurekaServer') {
                  withMaven(maven: 'maven') {
                    sh 'mvn sonar:sonar'
                  }
                }
              }
            }
          }
          stage("Quality Gate Eureka") {
            steps {
              echo message: "can not do on local machine "
             /* timeout(time: 5, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
              }*/
            }
          }
          
          stage("SonarQube analysis Gateway") {
            steps {
              withSonarQubeEnv('SonarQube') {
                dir('Gateway') {
                  withMaven(maven: 'maven') { 
                    sh 'mvn sonar:sonar'
                  }
                }
              }
            }
          }
           stage("Quality Gate Gateway") {
            steps {
              echo message: "can not do on local machine "
             /* timeout(time: 5, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
              }*/
            }
          }
          stage('log into ecr') {
        steps {
            script{
                sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com'
            }
        }
      }
          stage('Build Eureka') {
            steps {
                  dir('EurekaServer') {
                    sh 'docker build . -t ${AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/ss-utopia-eureka:${GIT_COMMIT_MSG}'
        }

                  }

                 
            }
        
        stage('Deploy Eureka') {
            steps {
                script{
                  sh 'docker push ${AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/ss-utopia-eureka:${GIT_COMMIT_MSG}'
                }
            }
        }
        stage('Build Gateway') {
            steps {
                  dir('Gateway') {
                    sh 'docker build . -t ${AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/ss-utopia-gateway:${GIT_COMMIT_MSG}'

                  }

                 
            }
        }
        stage('Deploy Gateway') {
            steps {
                script{
                  sh 'docker push ${AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/ss-utopia-gateway:${GIT_COMMIT_MSG}'
                }
            }
        }
        stage('Cleaning up') {
        steps{
            sh "docker rmi ${AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/ss-utopia-account:${GIT_COMMIT_MSG}"
        }
        }
    }

}
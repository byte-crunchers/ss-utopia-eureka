pipeline {
    options
    {
      buildDiscarder(logRotator(numToKeepStr: '3'))
    }
    agent any

    environment {
      AWS_ACCOUNT_ID="422288715120"
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
          git branch: 'develop', credentialsId: 'git_login', url: 'https://github.com/byte-crunchers/ss-utopia-eureka.git'
        }
      }
      stage("Clean install eureka") {  
        steps {
          dir('EurekaServer') {
           sh 'mvn clean package -Dmaven.test.skip' 
          }
        }
      }
      stage("Clean install gateway") {  
        steps {
          dir('Gateway'){
           sh 'mvn clean package -Dmaven.test.skip' 
          }
        }
      }
        
        stage("SonarQube analysis Eureka") {
            steps {
              withSonarQubeEnv('SonarQube') {
                dir('EurekaServer') {
                  sh 'mvn sonar:sonar'
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
                  sh 'mvn sonar:sonar'
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
          
          stage('Build Eureka') {
            steps {
                  dir('EurekaServer') {
                    sh 'docker build . -t ss-utopia-eureka:latest'

                  }

                 
            }
        }
        stage('Deploy Eureka') {
            steps {
                script{
                  docker.withRegistry("https://${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com", 'ecr:us-east-1:ss-AWS') 
                  {
                    docker.image('ss-utopia-eureka').push('latest')
                  }
                }
            }
        }
        stage('Build Gateway') {
            steps {
                  dir('Gateway') {
                    sh 'docker build . -t ss-utopia-gateway:latest'

                  }

                 
            }
        }
        stage('Deploy Gateway') {
            steps {
                script{
                  docker.withRegistry("https://${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com", 'ecr:us-east-1:ss-AWS') 
                  {
                    docker.image('ss-utopia-gateway').push('latest')
                  }
                }
            }
        }
    }

}
pipeline {
    agent any

    stages {
      stage('checkout') {
        steps {
          git branch: 'feature_jenkins', credentialsId: 'git_login', url: 'https://github.com/byte-crunchers/ss-utopia-eureka.git'
        }
      }
        
        stage("SonarQube analysis Eureka") {
            agent any
            steps {
              withSonarQubeEnv('SonarQube') {
                dir('EurekaServer') {
                  sh 'mvn clean package sonar:sonar'
                }
              }
            }
          }
          stage("Quality Gate") {
            steps {
              echo message: "can not do on local machine "
             /* timeout(time: 5, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
              }*/
            }
          }
          stage("SonarQube analysis Gateway") {
            agent any
            steps {
              withSonarQubeEnv('SonarQube') {
                dir('Gateway') {
                  sh 'mvn clean package sonar:sonar'
                }
              }
            }
          }
          stage('Build Eureka') {
            steps {
                  dir('EurekaServer') {
                    sh 'docker build . -t jbnilles/ss-utopia-eureka:latest'

                  }

                 
            }
        }
        stage('Deploy Eureka') {
            steps {
                sh 'docker push jbnilles/ss-utopia-eureka:latest'
            }
        }
        stage('Build Gateway') {
            steps {
                  dir('Gateway') {
                    sh 'docker build . -t jbnilles/ss-utopia-gateway:latest'

                  }

                 
            }
        }
        stage('Deploy Gateway') {
            steps {
                sh 'docker push jbnilles/ss-utopia-gateway:latest'
            }
        }
    }

}
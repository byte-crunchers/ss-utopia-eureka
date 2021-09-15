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
                  sh 'sonar:sonar'
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
            agent any
            steps {
              withSonarQubeEnv('SonarQube') {
                dir('Gateway') {
                  sh 'sonar:sonar'
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
          stage ('Package Eureka') {
            steps {
              dir('EurekaServer') {
                sh 'mvn clean package -Dmaven.test.skip'   
              }
            }         
          }
          stage ('Package Gateway') {
            steps {
              dir('Gateway') {
                sh 'mvn clean package -Dmaven.test.skip'   
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
pipeline {
    agent {
        label 'master'
    }
    tools {
        maven '3.8.4'
        jdk 'jdk8'
    }
    stages {
//        stage('Building maven and run test') {
//            steps {
//                script {
//                    sh 'mvn -B clean package'
//                }
//            }
//        }
//        stage('Building docker image'){
//            steps{
//                script{
//                    sh 'docker build -t zonesama/laundry-be .'
//                }
//            }
//        }
//        stage('Pushing builded image to docker hub'){
//            steps{
//                script{
//                    sh 'docker push "zonesama/laundry-be"'
//                }
//            }
//        }
        stage('Docker pull and run image on remote'){
            steps{
                script{
                    def remote = [:]
                    remote.name = 'zonesama'
//                    remote.host = '10.225.1.206'
                    remote.host = '6.tcp.ngrok.io'
                    remote.port = '15038'
                    remote.user = 'zonesama'
                    remote.password = '380617'
                    remote.allowAnyHosts = true
                    sshCommand remote: remote, command: "docker pull zonesama/laundry-be:latest"
                    sshCommand remote: remote, command: "docker stop laundry-be || true"
                    sshCommand remote: remote, command: "docker rm laundry-be || true"
                    sshCommand remote: remote, command: "docker run -d -p 8081:8081 -t --name laundry-be  zonesama/laundry-be"
                }
            }
        }
    }
}

def dockerImage = 'zonesama/laundry-be'
def dockerTag = 'latest'
def dockerContainerName = 'laundry-be'
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
//                    sh 'docker build -t '+ dockerImage+' .'
//                }
//            }
//        }
//        stage('Pushing builded image to docker hub'){
//            steps{
//                script{
//                    sh 'docker push '+ dockerImage
//                }
//            }
//        }
        stage('Docker pull and run image on remote'){
            steps{
                script{
                    def remote = [:]
                    remote.name = 'zonesama'
                    remote.host = '10.225.1.58'
//                    remote.host = '4.tcp.ngrok.io'
//                    remote.port = 16590
                    remote.user = 'zonesama'
                    remote.password = '380617'
                    remote.allowAnyHosts = true
//                    sshCommand remote: remote, command: "docker pull "+dockerImage+":"+dockerTag
//                    sshCommand remote: remote, command: "docker stop "+dockerContainerName+" || true"
//                    sshCommand remote: remote, command: "docker rm "+dockerContainerName+" || true"
//                    sshCommand remote: remote, command: "docker run -d -p 8081:8081 -t --name " + dockerContainerName+" "+dockerImage
                    sshCommand remote: remote, command: "kubectl apply -f  ${WORKSPACE}/deployment.yaml"
                }
            }
        }
    }
}

pipeline {
    agent {
        label 'master'
    }
    tools {
        maven '3.8.4'
        jdk 'jdk8'
    }
    def remote = [:]
    remote.name = 'zonesama'
    remote.host = '10.225.1.206'
    remote.user = 'zonesama'
    remote.password = '380617'
    remote.allowAnyHosts = true

    stages {
        stage('Building maven and run test') {
            steps {
                script {
                    sh 'mvn -B clean package'
                }
            }
        }
        stage('Building docker image'){
            steps{
                script{
                    sh 'docker build -t zonesama/laundry-be .'
                }
            }
        }
        stage('Pushing builded image to docker hub'){
            steps{
                script{
                    sh 'docker push "zonesama/laundry-be"'
                }
            }
        }
        stage('Docker pull remote'){
            sshCommand remote: remote, command: "docker ps"
        }
//        stage('Deploy our image') {
//            steps {
//                script {
//                    // Assume the Docker Hub registry by passing an empty string as the first parameter
////                    docker.withRegistry('' , 'dockerhub') {
////                        dockerImage.push()
////                    }
//                    sh './mvnw test'
//                }
//            }
//        }
    }
}
